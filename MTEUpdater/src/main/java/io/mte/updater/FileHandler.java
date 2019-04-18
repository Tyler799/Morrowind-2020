package io.mte.updater;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class FileHandler {

	public static final UnzipUtility unzipUtility = new UnzipUtility();

	private final List<File> tempFiles;
	protected final VersionFile local;
	protected VersionFile remote;

	private static enum ReleaseFiles {

		APPLICATION("MTE-Updater.jar"),
		GUIDE("Morrowind_2019.md"),
		LAUNCHER("MTE-Updater.bat");

		private File instance;

		ReleaseFiles(String name) {
			instance = new File(Dir.localDirectory + File.separator + name);
		}

		private static class Dir {
			/** This is where the latest release will be unpacked */
			private static final String localDirectory =
					FilenameUtils.removeExtension(RemoteHandler.RELEASE_FILENAME);
		}

		/**
		 * Compare remote release files with their local counterparts
		 * @return a list of release files newer then local versions
		 */
		public static ArrayList<File> compare() {

			ArrayList<File> list = new ArrayList<File>();
			for (ReleaseFiles file : ReleaseFiles.values()) {

				String name = file.instance.getName();
				File localFile = new File(name);

				if (!localFile.exists()) {
					Logger.print(Logger.Level.VERBOSE, "Local file %s not found, going to update", name);
					list.add(file.instance);
					continue;
				}
				Logger.print(Logger.Level.DEBUG, "Comparing %s release to local version", name);
				try {
					if (!FileUtils.contentEquals(file.instance, localFile))
						list.add(file.instance);
				}
				catch (IOException e) {
					Logger.print(Logger.Level.ERROR, e, "Unable to compare release file %s to local version", name);
					continue;
				}
			}
			return list;
		}
	}

	// Create file instances here at runtime
	// if there is any problems we can terminate application
	FileHandler() {

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
			/*
			 *  Find out if the version file is local by checking its extension
			 *  Remote files should have the "remote" extension
			 */
			boolean isLocal = !FilenameUtils.getExtension(filename).equals("remote");
			
			if (!this.exists()) {
				/*
				 *  Remote files should always be present because we initialize
				 *  them after we perform a download from repository
				 */
				if (isLocal != true) {
					Exception e = new java.io.FileNotFoundException();
					Logger.print(Logger.Level.ERROR, e, "Unable to find %s version file!", filename);
					Execute.exit(1, true);
				}
				//Logger.print(Logger.Level.VERBOSE, 
				//		"Unable to find local version file %s, going to update", filename);
				
				releaseVer = 0;
				commitSHA = "";
				return;
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
				Execute.exit(1, true);
			}
		}

		public String getReleaseVersion() {
			return Float.toString(releaseVer);
		}
		public String getCommitSHA() {
			return commitSHA;
		}
	}

	protected static void launchApplication() {
		/*
		 *	 Create a copy of this application as a temporary file
		 */
		try {
			Logger.debug("Creating a temporary copy of application");
			String newSelfPath = FilenameUtils.removeExtension(Main.appPath.toString()) + ".tmp";
			Path selfUpdater = Files.copy(Main.appPath, Paths.get(newSelfPath), StandardCopyOption.REPLACE_EXISTING);

			String cmd = "java -jar " + selfUpdater.getFileName() + " " + Logger.getLevel().getArguments()[0] + " --update-self " + Main.processId;
			Logger.print(Logger.Level.DEBUG, "Excecuting cmd command: %s", cmd);
			Execute.command(cmd);

			// Exit gracefully so we don't have to be terminated
			Execute.exit(0, false);
		}
		catch (IOException e) {
			Logger.error("Unable to create a copy of this application", e);
			Execute.exit(1, false);
		}
	}
	
	void doUpdate(String localSHA, String remoteSHA) {
		
		// Don't show changes if local version file is not present
		if (localSHA != null && !localSHA.isEmpty()) {
			/*
			 *  Open the Github website with the compare arguments in URL
			 */
			URI compareURL = RemoteHandler.getGithubCompareLink(localSHA, remoteSHA, true, true);
			if (compareURL == null || !RemoteHandler.browseWebpage(compareURL)) {
				return;
			}
		}
		// Download latest release files
		Logger.print("\nDownloading release files...");
		if (!RemoteHandler.downloadLatestRelease(this))
			return;

		// Extract the release files to a new directory
		Logger.print("Extracting release files...");
		if (!extractReleaseFiles())
			return;
		
		// Move files from the target directory
		Logger.print("Updating local MTE files...");
		updateLocalFiles();
		
		// Update the guide version file
		Logger.print("Updating mte version file...");

		try (PrintWriter writer = new PrintWriter(local)) {
			writer.print(remote.getReleaseVersion() + " " + remote.getCommitSHA());
			Logger.print("\nYou're all set, good luck on your adventures!");
			writer.close();
		} catch (FileNotFoundException e) {
			Logger.error("ERROR: Unable to find mte version file!", e);
			Execute.exit(1, true);
		}
	}

	void updateLocalFiles() {
		
		Logger.verbose("Preparing to update release files...");
		ArrayList<File> releaseFiles = ReleaseFiles.compare();
		
		if (!releaseFiles.isEmpty()) {
			for (Iterator<File> iter = releaseFiles.iterator(); iter.hasNext(); ) {

				File updateFile = iter.next();

			    if (updateFile == null || !updateFile.exists()) {
			    	FileNotFoundException e = new FileNotFoundException();
			    	Logger.print(Logger.Level.ERROR, e, "Unable to find release file %s!", updateFile.getName());
			    	continue;
			    }
			    else {
			    	Path from = updateFile.toPath();
			    	Path to = Paths.get(updateFile.getName());

			    	Logger.print(Logger.Level.DEBUG, "Updating release file %s", updateFile.getName());
			    	Logger.print(Logger.Level.DEBUG, "Destination path: %s", to.toString());

			    	try {
						Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);

					} catch (IOException e) {
						Logger.print(Logger.Level.ERROR, e, "Unable to overwrite local release file %s !", to.getFileName().toString());
						continue;
					}
			    }
			}
		}
	}

	boolean extractReleaseFiles() {

		try {
			unzipUtility.unzip(RemoteHandler.RELEASE_FILENAME, ReleaseFiles.Dir.localDirectory);
			registerTempFile(new File(ReleaseFiles.Dir.localDirectory));
			return true;
		} catch (IOException e) {
			Logger.error("Unable to extract the GH repo file!", e);
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
			Logger.print(Logger.Level.ERROR, e, "Unable to delete temporary file %s !", fileEntryName);
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
	boolean downloadUsingStream(URL url, String file) throws IOException {

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
		if (!dlFile.exists()) {
			Logger.print(Logger.Level.ERROR, "Unable to find downloaded file %s", dlFile.getName());
			return false;
		}
		return true;
	}

	/**
	 * Read from a text file and return the compiled string
	 *
	 * @param filename Name of the file to read from the root directory
	 * @return Content of the text file
	 */
	String readFile(String filename) {

		// Using Apache Commons IO here
		try (FileInputStream inputStream = new FileInputStream(filename)) {
			return IOUtils.toString(inputStream, "UTF-8");
		} catch (IOException e) {
			Logger.print(Logger.Level.ERROR, e, "Unable to read file %s", filename);
			return null;
		}
	}
}
