package io.mte.updater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

	public static final Path root = Paths.get(System.getProperty("user.dir"));
	public static final Path appPath = Paths.get(root + File.separator + System.getProperty("program.name"));
	public static final short processId = getProcessId();
	
	// Use this class instance to handle all file related stuff
	public static final FileHandler fileHandler = new FileHandler();
	
	public static void main(String[] args) 
	{
		// Initialize logger first so we can output logs
		Logger.init(args);
		
		if (args != null && args.length > 0)
			processJVMArguments(args);
		
		runUpdater();
		/*
		 *  Do not use scanner to scan for user input, I've been getting unknown exceptions being 
		 *  thrown with no message or stack trace. It just doesn't seem to work for some reason
		 *  
		 *  Using direct System InputStream seems like the best idea, and although it only works
		 *  for ENTER at least it works and won't crash
		 */
		Logger.print("Press Enter to continue...");
		try {
			System.in.read();
		}
		catch(IOException e) {;
			Logger.error("Something went wrong while reading user input", e);
		}
	}

	private static void processJVMArguments(String[] args) {
		
		Logger.print(Logger.Level.DEBUG, "Started application with %s arguments", String.join(" ", args));
		//for (int i = args.length - 1; i >= 0; i--) {}
		
		if (args[0].equals("--launcher")) {
			FileHandler.launchApplication();
			closeJavaApplication();
		}
		else if (args[0].equals("--self-update")) {
			/*
			 *  We expect to find the process id of the main Java 
			 *  application in the following argument
			 */
			String mainAppProcId = args[1];
			if (!mainAppProcId.isEmpty()) {
				
				Logger.debug("Attempting to kill main java application");
				
				if (!executeCommand("taskkill /PID " + mainAppProcId, false)) {
					Logger.error("Unable to terminate main java application");
					terminateJavaApplication();
				}
			}
			else {
				Logger.error("Expected PID passed as JVM argument...");
				terminateJavaApplication();
			}
		}
	}
	
	private static void runUpdater() {
		
		Logger.verbose("Start updating...");
		
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

					// Open the Github website with the compare arguments in URL
					URI compareURL = RemoteHandler.getGithubCompareLink(localSHA, remoteSHA, true, true);
					if (compareURL == null || !RemoteHandler.browseWebpage(compareURL)) {
						return;
					}

					// Download latest release files
					Logger.print("\nDownloading release files...");
					if (!RemoteHandler.downloadLatestRelease(fileHandler))
						return;

					// Extract the release files to a new directory
					Logger.print("Extracting release files...");
					if (!fileHandler.extractReleaseFiles())
						return;
					
					// Move files from the target directory
					Logger.print("Updating local MTE files...");
					fileHandler.updateLocalFiles();
					
					// Update the guide version file
					Logger.print("Updating mte version file...");
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(fileHandler.local);
						writer.print(fileHandler.remote.getReleaseVersion() + " " + fileHandler.remote.getCommitSHA());
						Logger.print("\nYou're all set, good luck on your adventures!");
					} catch (FileNotFoundException e) {
						Logger.error("ERROR: Unable to find mte version file!", e);
						terminateJavaApplication();
					}
					writer.close();
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
	
	public static void closeJavaApplication() {
		
		Logger.verbose("Closing updater application...");
		
		fileHandler.updaterCleanup();
		Logger.LogFile.close();
		System.exit(0);
	}
	
	public static void terminateJavaApplication() {
		
		Logger.print("Terminating updater application...");
		
		fileHandler.updaterCleanup();
		Logger.LogFile.close();
	    System.exit(1);
	}
	
	/**
	 * Get process id of the currently running Java application
	 * @return numerical value corresponding to the process id
	 */
	private static short getProcessId() {
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		return Short.parseShort(processName.substring(0, processName.indexOf("@")));
	}
	
	public static boolean executeCommand(String cmd, boolean window) {
		
		Runtime rt = Runtime.getRuntime();
		try {
			if (window == true)
				rt.exec("cmd.exe /k start " + cmd, null, null);
			else
				rt.exec("cmd.exe /k \"" + cmd + "\"");
			
			return true;
		} 
		catch (IOException e) {
			Logger.print(Logger.Level.ERROR, e, "Unable to execute Windows command: %s", cmd);
			return false;
		}
	}
}
