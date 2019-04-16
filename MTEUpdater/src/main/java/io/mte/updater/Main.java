package io.mte.updater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {

	public static final String root = System.getProperty("user.dir");
	
	// Use this class instance to handle all file related stuff
	public static final FileHandler fileHandler = new FileHandler();
	
	public static void main(String[] args) 
	{
		Logger.init(args);
		Logger.test();
		runUpdater();
		fileHandler.updaterCleanup();
	}

	private static void runUpdater() {
		
		System.out.println("\nDownloading mte version file...");
		if (!RemoteHandler.downloadRemoteVersionFile(fileHandler))
			return;
		
		fileHandler.registerRemoteVersionFile();

		System.out.println("Comparing version numbers...");
		
		String remoteSHA = fileHandler.remote.getCommitSHA();
		String localSHA = fileHandler.local.getCommitSHA();

		// Compare version numbers to see if we need to update
		if (!remoteSHA.equals(localSHA)) {
			System.out.println("\nYour version of the guide is out of date");

			Scanner reader = new Scanner(System.in);
			System.out.println("Would you like to see a list of recent updates?");

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
					System.out.println("\nDownloading release files...");
					if (!RemoteHandler.downloadLatestRelease(fileHandler))
						return;

					// Extract the release files to a new directory
					System.out.println("Extracting release files...");
					if (!fileHandler.extractReleaseFiles())
						return;

					// Move files from the target directory
					//System.out.println("Updating local MTE files...");
					//if (!fileHandler.updateLocalFiles())
					//	return;
					
					/*// Update the guide version file
					System.out.println("Updating mte version file...");
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(fileHandler.local);
						writer.print(fileHandler.remote.getReleaseVersion() + remoteSHA);
						System.out.println("\nYou're all set, good luck on your adventures!");
					} catch (FileNotFoundException e) {
						System.out.println("ERROR: Unable to find mte version file!");
						return;
					}
					writer.close();*/
					inputFlag = true;
				} else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
					System.out.println("\nNot a wise decision, may the curse of blight strike you down!");
					reader.close();
					inputFlag = true;
				}
			}
		} else
			System.out.println("\nYour version of the guide is up-to-date!");
	}
	
	public static void terminateApplication() {
		
		System.out.println("Terminating updater application...");
	    System.exit(1);
	}
}
