package io.mte.updater;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class FileHandler {
	
	public static final UnzipUtility unzipUtility = new UnzipUtility();
	public final String localReleaseDir;
	
	private final List<File> releaseFiles;
	private final List<File> tempFiles;
	protected final VersionFile local;
	protected VersionFile remote;
	
	// Create file instances here at runtime
	// if there is any problems we can terminate application
	FileHandler() {
		
		// This is where the latest release will be unpacked
		localReleaseDir = FilenameUtils.removeExtension(RemoteHandler.RELEASE_FILENAME);
		
		releaseFiles = new ArrayList<File>();
		releaseFiles.addAll(Arrays.asList(new File("MTE-Updater.jar"),
				 new File("Morrowind_2019.md"), new File("MTE-Updater.bat")));
		
		// Store all our temporary file references here
		tempFiles = new ArrayList<File>();
		local = new VersionFile(RemoteHandler.VERSION_FILENAME);
	}
	
	public class VersionFile extends File {

		private static final long serialVersionUID = 1L;
		
		public final String filename;
		private final float releaseVer;
		private final String commitSHA;

		public VersionFile(String pathname) {
			
			super(pathname);
			filename = this.getName();
			
			if (!this.exists()) {
				System.out.println("ERROR: Unable to find '" + filename + "' file!");
				Main.terminateApplication();
			}

			String contents = readFile(filename);
			CharSequence versionLine[] = contents.split(" ");

			// The version file should contain a single line with two numbers,
			// first one being the release version and second the last release commit SHA
			boolean validVersionFile = false;
			String versionNumber = "";
			
			if (versionLine.length == 2)  
			{
				versionNumber = versionLine[0].toString();
				int vnLength = versionNumber.length();
				int vnDecimal = versionNumber.indexOf(".");
			
				// Version must be formatted properly
				if (vnLength > 1 && vnDecimal > 0 && vnDecimal <= (vnLength -2)) 
				{
					versionNumber = versionNumber.replace(".", "");
			
					if (Pattern.matches("[a-z0-9]+", versionLine[1]) && StringUtils.isNumeric(versionNumber)) {
						validVersionFile = true;
					}
				}
			}
			if (validVersionFile) {
				releaseVer = Float.parseFloat(versionLine[0].toString());
				commitSHA = versionLine[1].toString();
			}
			else {
				// We still have to initialize these variables to avoid errors
				releaseVer = 0;
				commitSHA = null;
				System.out.println("ERROR: Malformed version file '" + filename + "'!");
				Main.terminateApplication();
			}
		}
		
		public String getReleaseVersion() {
			return Float.toString(releaseVer);
		}
		public String getCommitSHA() {
			return commitSHA;
		}
	}
	
	boolean updateLocalFiles() {
		
		for (Iterator<File> iter = releaseFiles.iterator(); iter.hasNext(); ) {
			
		    File releaseFile = iter.next();
		    if (!releaseFile.exists()) {
		    	System.out.println("ERROR: Unable to find release file '" + releaseFile.getName() + "'!");
		    	return false;
		    }
		    else {
		    	Path from = releaseFile.toPath();
		    	Path to = Paths.get(Main.root + "\\" + releaseFile.getName());
		    	
		    	System.out.println("[DEBUG] Updating release file '" + releaseFile.getName() + "'");
		    	System.out.println("[DEBUG] Destination path: " + to.toString());
		    	
		    	try {
					Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
			    	
				} catch (IOException e) {
					System.out.println("ERROR: Unable to overwrite local release file '" + to.getFileName() + "!");
					return false;
				}
		    }
		}	return true;
	}
	
	boolean extractReleaseFiles() {
		
		try {
			unzipUtility.unzip(RemoteHandler.RELEASE_FILENAME, localReleaseDir);
			// TODO: Remove this from comments
			//registerTempFile(new File(localReleaseDir));
			return true;
		} catch (IOException e1) {
			System.out.println("ERROR: Unable to extract the GH repo file!");
			return false;
		}
	}
	
	/** 
	 * Create a version file instance and register as a temporary file
	 */
	void registerRemoteVersionFile() {

		remote = new VersionFile(RemoteHandler.VERSION_FILENAME + ".remote");
		registerTempFile(remote);
	}
	
	/**
	 * Clean up all temporary files created in the update process
	 */
	void updaterCleanup() {

		// Delete the temporary version file we created
		String fileEntryName = "unknown";
		try {
			ListIterator<File> tempFileItr = tempFiles.listIterator();
			while (tempFileItr.hasNext()) {
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

	/**
	 * Any files added here will be deleted before terminating application
	 * @param tmpFile
	 */
	void registerTempFile(File tmpFile) {
		
		if (tmpFile.exists())
			tempFiles.add(tmpFile);
		else System.out.println("Warning to register non-existing temporary file.");
	}
	
	/**
	 * Here we are using URL openStream method to create the input stream. Then we
	 * are using a file output stream to read data from the input stream and write
	 * to the file.
	 *
	 * @param url
	 * @param file
	 * @throws IOException
	 */
	void downloadUsingStream(URL url, String file) throws IOException {
		
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		FileOutputStream fis = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = bis.read(buffer, 0, 1024)) != -1) {
			fis.write(buffer, 0, count);
		}
		fis.close();
		bis.close();
	}

	/**
	 * Read from a text file and return the compiled string
	 *
	 * @param filename
	 *            Name of the file to read from the root directory
	 * @return Content of the text file
	 */
	String readFile(String filename) {

		// Using Apache Commons IO here
		try (FileInputStream inputStream = new FileInputStream(filename)) {
			return IOUtils.toString(inputStream, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}