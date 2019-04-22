package io.mte.updater;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.mte.updater.UserInput.Key;

public class Main {

	public static final Path root = Paths.get(System.getProperty("user.dir"));
	public static final Path appPath = Paths.get(root + File.separator + System.getProperty("program.name"));
	public static final short processId = Execute.getProcessId();
	
	// TODO: Document this variable
	public static String runMode;
	
	public static void main(String[] args) 
	{
		try {
			/*
			 *  Initialize logger first before doing anything else so we can output logs
			 *  Note that we will crash if we try to output logs before logger is initialized
			 */
			Logger.init(args, false);
			
			FileHandler.init();
			
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
			if (args.length > 1 && !args[1].isEmpty()) {
				/*
				 *  The launcher process should exit on its own
				 *  but if it hang for some reason we terminate it here
				 */
				if (Execute.isProcessRunning(args[1])) {
					
					Logger.debug("Launcher application is still running, terminating now...");
					if (!Execute.kill(Integer.parseInt(args[1]), 5)) {
						Logger.error("Unable to terminate java application");
						Execute.exit(1, false);
					}
				}
			}
			else {
				Logger.error("Expected launcher PID passed as JVM argument...");
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
		
		Logger.print("\nReading remote mte version file...");
		String remoteLine = RemoteHandler.downloadStringLine(RemoteHandler.Link.versionFile);
		VersionFile.Data remoteData = VersionFile.readData(remoteLine, VersionFile.Type.MTE);
		
		if (remoteData.isEmpty()) {
			Logger.error("Remote version file data is corrupted");
			Execute.exit(1, false);
		}
		
		Logger.verbose("Loading local version file...");
		VersionFile localVerFile = VersionFile.load(VersionFile.Type.MTE);
		
		Logger.print("Comparing version numbers...");
		
		String remoteSHA = remoteData.getCommitSHA();
		String localSHA = localVerFile.getData().getCommitSHA();
		float version = remoteData.getReleaseVersion();

		// Compare version numbers to see if we need to update
		if (!remoteSHA.equals(localSHA)) {
			Logger.print("\nYour version of the guide is out of date");

			if (localSHA.isEmpty()) {
				Logger.verbose("Local version file not found, skip showing updates");
				FileHandler.get().doUpdate(version, localSHA, remoteSHA);
				return;
			}
			// Continue asking for input until the user says yes or no
			Logger.print("Would you like to see a list of recent updates?");
			Key input = UserInput.waitFor(Key.YES, Key.NO);

			if (input == Key.YES) {					
				FileHandler.get().doUpdate(version, localSHA, remoteSHA);
			} 
			else if (input == Key.NO) {
				
				Logger.print("\nIt is strongly recommended that you update");
				Logger.print("You can always check the release section of our repository on Github:");
				Logger.print(RemoteHandler.Link.releasesPage.toString() + "\n");
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
			
			if (mwse != null && mwse.exists()) {
				Process proc = Execute.start(mwse.getName(), true, true);
				if (proc == null || proc.exitValue() != 0)
					Logger.warning("Unable to update, check logfile for more details");
			}
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
