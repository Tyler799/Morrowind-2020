package io.mte.updater;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

	public static final Path root = Paths.get(System.getProperty("user.dir"));
	public static final Path appPath = Paths.get(root + File.separator + System.getProperty("program.name"));
	public static final short processId = Execute.getProcessId();
	
	// TODO: Document this variable
	public static String runMode;
	
	// Use this class instance to handle all file related stuff
	public static final FileHandler fileHandler = new FileHandler();
	
	public static void main(String[] args) 
	{
		try {
			// Initialize logger first so we can output logs
			Logger.init(args, false);
			
			if (args != null && args.length > 0)
				processJVMArguments(args);
			
			updateMWSE();
			runMTEUpdater();
			Execute.exit(0, true);
		}
		catch(Exception e) {
			Logger.error("Unhandled exception occured in main method", e);
			Execute.exit(1, false);
		}
	}

	private static void processJVMArguments(String[] args) {
		
		Logger.print(Logger.Level.DEBUG, "Started application with %s arguments", String.join(" ", args));
		//for (int i = args.length - 1; i >= 0; i--) {}
		
		// The first argument should always be a run mode definition
		runMode = args[0];
		
		if (runMode == null || runMode.isEmpty()) {
			Exception e = new IllegalArgumentException();
			Logger.error("Application run mode has not been defined", e);
			Execute.exit(1, false);
		}
		else if (isLauncher()) {
			FileHandler.launchApplication();
		}
		else if (isSelfUpdating()) {
			/*
			 *  We expect to find the process id of the main Java 
			 *  application in the following argument
			 */
			String mainAppProcId = args[1];
			if (!mainAppProcId.isEmpty()) {
				
				Logger.debug("Attempting to kill main java application");
				
				if (!Execute.command("taskkill /PID " + mainAppProcId)) {
					Logger.error("Unable to terminate main java application");
					Execute.exit(1, false);
				}
			}
			else {
				Logger.error("Expected PID passed as JVM argument...");
				Execute.exit(1, false);
			}
		}
		else {
			Exception e = new IllegalArgumentException();
			Logger.error("Unknown application run mode definition", e);
			Execute.exit(1, false);
		}
	}
	
	private static void runMTEUpdater() {
		
		Logger.verbose("Start updating mte...");
		
		Logger.print("\nDownloading mte version file...");
		if (!RemoteHandler.downloadRemoteVersionFile(fileHandler))
			return;
		
		fileHandler.registerRemoteVersionFile();

		Logger.print("Comparing version numbers...");
		
		String remoteSHA = fileHandler.remote.getCommitSHA();
		String localSHA = fileHandler.local.getCommitSHA();

		// Compare version numbers to see if we need to update
		if (!remoteSHA.equals(localSHA)) {
			Logger.print("\nYour version of the guide is out of date");

			if (localSHA.isEmpty()) {
				Logger.verbose("Local version file not found, skipping showing updates");
				fileHandler.doUpdate(localSHA, remoteSHA);
				return;
			}
			
			Scanner reader = new Scanner(System.in);
			Logger.print("Would you like to see a list of recent updates?");

			// Continue asking for input until the user says yes or no
			boolean inputFlag = false;
			while (inputFlag == false) {
				String input = reader.next();
				if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {

					// It's important we close the reader as soon as possible before any
					// exceptions terminate the method
					reader.close();
					
					fileHandler.doUpdate(localSHA, remoteSHA);
					
					inputFlag = true;
				} 
				else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
					
					Logger.print("\nIt is strongly recommended that you update");
					Logger.print("You can always check the release section of our repository on Github:");
					Logger.print(RemoteHandler.Link.releasesPage.toString() + "\n");
					reader.close();
					inputFlag = true;
				}
			}
		} else
			Logger.print("\nYour version of the guide is up-to-date!");
	}
	
	/** 
	 * <p>Run the MWSE auto-updater program</p>
	 * This is intended to make the users life easier so they only<br>
	 * have to run one updater that does it all for them 
	 */
	private static void updateMWSE() {
		/*
		 *  Don't update mwse if we are running in debug mode
		 */
		if (!Logger.isDebug()) {
			Logger.print("Attempting to update MWSE build...");
			File mwse = new File("MWSE-Update.exe");
			if (mwse != null && mwse.exists())
				Execute.start(mwse.getName(), true);
			else
				Logger.verbose("Unable to find mwse updater, skipping...");
		}
	}
	
	// TODO: Move these values into an enum
	
	/** 
	 * Did the JVM run as a launcher? 
	 */
	public static boolean isLauncher() {
		return runMode.equals("--launcher");
	}
	public static boolean isSelfUpdating() {
		return runMode.equals("--update-self");
	}
}
