package MTEUpdater;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;


public class MTEUpdater {

	static List<File> tempFiles = new ArrayList<File>();
	
	public static void main(String[] args) 
	{
		try {
			runUpdater();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Unable to find " + "'" + e.getMessage() + "'" + " file!");
		}
		
		//System.out.println("Cleaning up temporary files...");
		updaterCleanup();
	}
	
	private static void runUpdater() throws FileNotFoundException 
	{	
		// Before we do anything else check if the version file exists
		File versionTxt = new File("mte-version.txt");
		if (!versionTxt.exists()) {
			throw new FileNotFoundException("mte-version.txt");
		}
		
		// Download the guide version file from GitHub
		String url = "https://raw.githubusercontent.com/Tyler799/Morrowind-2019/updater/mte-version.txt";
		try {
			System.out.println("Downloading mte version file...");
		 	downloadUsingStream(url, "mte-version.tmp");
		} catch (IOException e) {
			System.out.println("ERROR: Unable to download guide version file!");
			return;
		}
		 
		// Register the temporary version file
		File versionTmp = new File("mte-version.tmp");
		tempFiles.add(versionTmp);
		 
		System.out.println("Comparing version numbers...");
		 
		String curVersion = readFile(versionTmp.getName());
		String lastVersion = readFile(versionTxt.getName());
		
		// Compare version number strings to see if we need to update
		if (!curVersion.equals(lastVersion)) {
			System.out.println("\nYour version of the guide is out of date");
		 	
			Scanner reader = new Scanner(System.in);
			System.out.println("Would you like to see a list of recent updates?");
		 	
		 	// Continue asking for input until the user says yes or no
			boolean inputFlag = false;
		 	while (inputFlag == false) {
		 		String input = reader.next();
			 	if (input.equals("yes") || input.equals("y")) {
			 		
			 		// It's important we close the reader as soon as possible before any
			 		// exceptions terminate the method
			 		reader.close();
			 		
			 		// Construct the URL in string format
			 		String urlString = "https://github.com/Tyler799/Morrowind-2019/compare/" + lastVersion + ".." + curVersion;
			 		
			 		// Wrap the string with an URI 
			 		URI compareURL = null;
			 		try {
						compareURL = new java.net.URI(urlString);
					} catch (URISyntaxException e) {
						System.out.print("ERROR: URL string violates RFC 2396!");
						return;
					}
					// Open the Github website with the compare arguments in URL
			 		try {
			 			java.awt.Desktop.getDesktop();
						if (Desktop.isDesktopSupported()) {
			 				java.awt.Desktop.getDesktop().browse(compareURL);
			 			}
						else {
							System.out.println("ERROR: Desktop class is not suppored on this platform.");
							return;
						}
					} catch (Exception e) {
						if (e instanceof IOException)
							System.out.print("ERROR: Unable to open web browser, default browser is not found or it failed to launch.");
						else if (e instanceof SecurityException)
							System.out.print("ERROR: Security manager denied permission or the calling thread is not allowed to create a subprocess; and not invoked from within an applet or Java Web Started application");
						return;
					}
			 		
			 		// Download repository files
			 		url = "https://github.com/Tyler799/Morrowind-2019/archive/master.zip";
			 		try {
			 			System.out.println("\nDownloading repository files...");
						downloadUsingStream(url, "Morrowind-2019.zip");
						tempFiles.add(new File("Morrowind-2019.zip"));
					} catch (IOException e1) {
						System.out.println("ERROR: Unable to download repo files!");
						return;
					}
			 		
			 		// Extract the repository files to a new directory
			 		try {
			 			System.out.println("Extracting repository files...");
			 			UnzipUtility unzipUtility = new UnzipUtility();
			 			unzipUtility.unzip("Morrowind-2019.zip", "Morrowind-2019-GH");
			 			tempFiles.add(new File("Morrowind-2019-GH"));
					} catch (IOException e1) {
						System.out.println("ERROR: Unable to extract the GH repo file!");
						return;
					}
			 		
			 		// Update the existing guide file
			 		System.out.println("Updating your Morrowind guide...");
			 		File guideGH = new File("Morrowind-2019-GH/Morrowind-2019-master/Morrowind_2019.md");
			 		if (!guideGH.exists()) {
			 			System.out.println("ERROR: Unable to find the extracted guide file!");
			 		}
			 		else {
			 			Path from = guideGH.toPath(); //convert from File to Path
			 			Path to = Paths.get("Morrowind_2019.md"); //convert from String to Path
			 			try {
							Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e) {
							System.out.println("ERROR: Unable to overwrite existing guide file!");
							return;
						}
			 		}
			 		
			 		// Update the guide version file
			 		System.out.println("Updating mte version file...");
			 		PrintWriter writer = null;
					try {
						writer = new PrintWriter(versionTxt);
						writer.print(curVersion);
						System.out.println("\nYou're all set, good luck on your adventures!");
					} catch (FileNotFoundException e) {
						System.out.println("ERROR: Unable to find mte version file!");
						return;
					}
			 		writer.close();	
			 		inputFlag = true;
			 	}
			 	else if (input.equals("no") || input.equals("n")) {
			 		System.out.println("\nNot a wise decision, may the curse of blight strike you down!");
			 		reader.close();
			 		inputFlag = true;
			 	}
		 	}
		 }
		 else System.out.println("\nYour version of the guide is up-to-date!");
	}
	
	/**
	 *  Clean up all temporary files created in the update process
	 */
	private static void updaterCleanup() {
		
		// Delete the temporary version file we created
		String fileEntryName = "unknown";
		try {
			ListIterator<File> tempFileItr = tempFiles.listIterator();
			while(tempFileItr.hasNext())
			{
				// Make sure the file exists before we attempt to delete it
				File fileEntry = tempFileItr.next();
			    if (fileEntry.exists()) {
			    	fileEntry.delete();
			    }
			}
		} catch (SecurityException e) {
			System.out.println("ERROR: Unable to delete temporary file '" + fileEntryName + "'!");
			e.printStackTrace();
		}
	}
	
	/** Here we are using URL openStream method to create the input stream. 
	 * Then we are using a file output stream to read data from the input stream and write to the file.
	 * @param urlStr 
	 * @param file
	 * @throws IOException
	 */
	private static void downloadUsingStream(String urlStr, String file) throws IOException 
	{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }
	
	/**
	 * Read from a text file and return the compiled string
	 * @param filename Name of the file to read from the root directory
	 * @return Content of the text file
	 */
	private static String readFile(String filename) {
		
		// Using Apache Commons IO here
		try(FileInputStream inputStream = new FileInputStream(filename)) {     
			 return IOUtils.toString(inputStream, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
