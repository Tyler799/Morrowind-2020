import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;


public class MTEUpdater {

	public static void main(String[] args) {

		// Before we do anything else check if the version file exists
		File versionTxt = new File("mte-version.txt");
		if (!versionTxt.exists()) {
			System.out.println("ERROR: Unable to find mte version file!");
			return;
		}
		
		System.out.println("Downloading mte version file...");
		String url = "https://raw.githubusercontent.com/Tyler799/Morrowind-2019/updater/mte-version.txt";
		        
		 try {        
			 downloadUsingStream(url, "mte-version.tmp");
		 } catch (IOException e) {
			 System.out.println("ERROR: Unable to download guide version file!");
			 e.printStackTrace();
		 }
		 
		 File versionTmp = new File("mte-version.tmp");
		 
		 System.out.println("Comparing version numbers...");
		 
		 String curVersion = readFile(versionTmp.getName());
		 String lastVersion = readFile(versionTxt.getName());
		 
		 if (!curVersion.equals(lastVersion)) {
		 	System.out.println("Your version of the guide is out of date");
		 	
		 	Scanner reader = new Scanner(System.in);
		 	System.out.println("Would you like to see a list of recent updates?");
		 	
		 	// Continue asking for input until the user says yes or no
			boolean inputFlag = false;
		 	while (inputFlag == false) {
		 		String input = reader.next();
			 	if (input.equals("yes") || input.equals("y")) {
			 		
			 		// Construct the URL in string format
			 		String urlString = "https://github.com/Tyler799/Morrowind-2019/compare/" + lastVersion + ".." + curVersion;
			 		
			 		// Wrap the string with an URI 
			 		URI compareURL = null;
			 		try {
						compareURL = new java.net.URI(urlString);
					} catch (URISyntaxException e) {
						System.out.print("ERROR: URL string violates RFC 2396!");
						e.printStackTrace();
					}
					// Open the Github website with the compare arguments in URL
			 		try {
						java.awt.Desktop.getDesktop().browse(compareURL);
					} catch (IOException e) {
						System.out.print("ERROR: Unable to open web browser!");
						e.printStackTrace();
					}
			 		
			 		// Download repository files
			 		url = "https://github.com/Tyler799/Morrowind-2019/archive/master.zip";
			 		try {
						downloadUsingStream(url, "Morrowind-2019.zip");
					} catch (IOException e1) {
						System.out.println("ERROR: Unable to download repo files!");
						e1.printStackTrace();
					}
			 		
			 		
			 		
			 		System.out.println("Updating mte version file...");
			 		PrintWriter writer = null;
					try {
						writer = new PrintWriter(versionTxt);
						writer.print(curVersion);
						System.out.println("You're all set, good luck on your adventures!");
					} catch (FileNotFoundException e) {
						System.out.println("ERROR: Unable to find mte version file!");
					}
			 		writer.close();	
			 		inputFlag = true;
			 	}
			 	else if (input.equals("no") || input.equals("n")) {
			 		System.out.println("Not a wise decision, may the curse of Blight strike you down!");
			 		inputFlag = true;
			 	}
		 	}
		 	// Close the scanner here
		 	reader.close();
		 }
		 else System.out.println("Your version of the guide is up-to-date!");
		 
		 // Delete the temporary version file we created
		 try {
			 versionTmp.delete();
		 } catch (SecurityException e) {
			 System.out.println("ERROR: Unable to delete 'mte-version.tmp'!");
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
