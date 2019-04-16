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
				Logger.print(Logger.Level.ERROR, "Unable to find %s file!", filename);
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
				Logger.print(Logger.Level.ERROR, "Malformed version file %s !", filename);
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
		    	Logger.print(Logger.Level.ERROR, "Unable to find release file %s !", releaseFile.getName());
		    	return false;
		    }
		    else {
		    	Path from = releaseFile.toPath();
		    	Path to = Paths.get(Main.root + "\\" + releaseFile.getName());
		    	
		    	Logger.print(Logger.Level.DEBUG, "Updating release file %s", releaseFile.getName());
		    	Logger.print(Logger.Level.DEBUG, "Destination path: %s", to.toString());
		    	
		    	try {
					Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
			    	
				} catch (IOException e) {
					Logger.print(Logger.Level.ERROR, "Unable to overwrite local release file %s !", to.getFileName().toString());
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
			Logger.error("Unable to extract the GH repo file!");
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
			Logger.print(Logger.Level.ERROR, "Unable to delete temporary file %s !", fileEntryName);
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
		else Logger.warning("Trying to register a non-existing temporary file");
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
		
		Logger.print(Logger.Level.DEBUG, "Downloading file %s from %s", file, url.toString());
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		FileOutputStream fis = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = bis.read(buffer, 0, 1024)) != -1) {
			fis.write(buffer, 0, count);
		}
		fis.close();
		bis.close();
		
		File dlFile = new File(file);
		if (!dlFile.exists())
			Logger.print(Logger.Level.ERROR, "Unable to find downloaded file %s", dlFile.getName());
			throw new java.io.FileNotFoundException();
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
			Logger.print(Logger.Level.ERROR, "Unable to read file %s", filename);
			e.printStackTrace();
			return null;
		}
	}
}
