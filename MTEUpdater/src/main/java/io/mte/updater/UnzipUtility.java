package io.mte.updater;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.io.FilenameUtils;

public class UnzipUtility {
	/**
	 * Size of the buffer to read/write data
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified by
	 * destDirectory (will be created if does not exists)
	 *
	 * @param zipFilePath
	 * @param destDirectory
	 * @return list of files that were extracted
	 */
	public java.util.ArrayList<File> unzip(String zipFilePath, String destDirectory) throws IOException {
		
		java.util.ArrayList<File> unzippedFiles = new java.util.ArrayList<File>();
		
		// Make sure the zip file under the given path exists
		File zipFile = new File(zipFilePath);
		if (!zipFile.exists()) {
			Logger.print(Logger.Level.ERROR, "Unable to find zip file with path %s", zipFilePath);
			return null;
		}
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
	
		Logger.print(Logger.Level.DEBUG, "Unziping from %s to %s \n", zipFilePath, destDirectory);
		/*
		 *  Handle 7z files using Apache Commons Compress library
		 */
		if (FilenameUtils.getExtension(zipFilePath).equals("7z")) {
			Logger.debug("Detected 7Zip archive, using Apache Commons Compress library");
			try (SevenZFile sevenZFile = new SevenZFile(new File(zipFilePath))) {
				SevenZArchiveEntry entry = sevenZFile.getNextEntry();
				
				// Abort if zip file is empty
				if (entry == null) {
					Logger.error("Zip contains no valid entries!");
					Logger.print("Aborting unzipping operation...");
					return null;
				}
				while (entry != null) {
					
					Logger.print(Logger.Level.DEBUG, "Iterating over zip entry %s", entry.getName());
					String filePath = destDirectory + File.separator + entry.getName();
					
					if (!entry.isDirectory()) {
						// if the entry is a file, extracts it
						File extrFile = extractFile(sevenZFile, entry, filePath);
						if (extrFile == null || !extrFile.exists()) {
							Logger.print(Logger.Level.ERROR, "Unable to find extracted file %s!", entry.getName());
						}
						else unzippedFiles.add(extrFile);
					} else {
						// if the entry is a directory, make the directory
						File dir = new File(filePath);
						dir.mkdir();
					}
					// iterate over next zip entry
					entry = sevenZFile.getNextEntry();
				}
				/*
				 *  Don't forget to close the archive here, as no warning messages 
				 *  are displayed before compiling the application 
				 */
				sevenZFile.close();
			}
			catch (IOException e) {
				Logger.print(Logger.Level.ERROR, e, "Unable to read archive %s!", zipFile.getName());
				return null;
			}
			return unzippedFiles;
		}
		/*
		 * Use the standard method for regular zip files
		 */
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = null;
		
		try {
			entry = zipIn.getNextEntry();
			// Abort if zip file is empty
			if (entry == null) {
				Logger.error("Zip contains no valid entries!");
				Logger.print("Aborting unzipping operation...");
				closeZipInputStream(zipIn);
				return null;
			}
		}
		catch (IOException e) {
			Logger.error("ZIP file error has occurred, unable to get new zip entry!", e);
			closeZipInputStream(zipIn);
			return null;
		}
		// iterates over entries in the zip file
		while (entry != null) {
			
			Logger.print(Logger.Level.DEBUG, "Iterating over zip entry %s", entry.getName());
			String filePath = destDirectory + File.separator + entry.getName();
			
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				File extrFile = extractFile(zipIn, entry.getName(), filePath);
				if (extrFile == null || !extrFile.exists()) {
					Logger.print(Logger.Level.ERROR, "Unable to find extracted file %s!", entry.getName());
				}
				else unzippedFiles.add(extrFile);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				dir.mkdir();
			}
			try {
				zipIn.closeEntry();
				// iterate over next zip entry
				entry = zipIn.getNextEntry();
			}
			catch (IOException e) {
				Logger.error("Unable to close or get next zip entry!", e);
				Logger.print("Aborting unzipping operation...");
				return null;
			}
		}
		if (!closeZipInputStream(zipIn))
			return null;
		
		return unzippedFiles;
	}

	/**
	 * Extracts a regular zip file entry using standard methods
	 *
	 * @param zipIn Zip stream we are extracting from
	 * @param filename Used for debug purposes
	 * @param filePath Where we want to extract
	 * @return New instance of the extracted file or {@code null} if an error occurred
	 */
	@SuppressWarnings("resource")
	private File extractFile(ZipInputStream zipIn, String filename, String filePath)  {
		
		Logger.print(Logger.Level.DEBUG, "Extracting zip file %s to %s", filename, filePath);
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(filePath));
		}
		catch (java.io.FileNotFoundException e) {
			Logger.print(Logger.Level.ERROR, e, "Unable to create new output stream for path %s!", filePath);
			return null;
		}
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		try {
			while ((read = zipIn.read(bytesIn)) != -1) {
				bos.write(bytesIn, 0, read);
			}
		}
		catch (IOException e) {
			Logger.error("Unable to read or write from zip input stream!", e);
			closeZipOutputStream(bos);
			return null;
		}
		if (!closeZipOutputStream(bos))
			return null;
		/*
		 *  We are getting a warning here that the output stream might not be closed
		 *  but that is not true, we are trying to close it in 'closeZipOutputStream()'
		 */
		return new File(filePath);
	}
	
	/**
	 * Extract a 7z archive file using Apache Commons Compress library
	 * 
	 * @param sevenZFile Zip file we are extracting from
	 * @param entry Zip entry that we are extracting
	 * @param filePath Extraction destination
	 * @return New instance of the extracted file or {@code null} if an error occurred
	 */
	@SuppressWarnings("resource")
	private File extractFile(SevenZFile sevenZFile, SevenZArchiveEntry entry, String filePath)  {
	
		Logger.print(Logger.Level.DEBUG, "Extracting zip file %s to %s", entry.getName(), filePath);
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(filePath));
		}
		catch (java.io.FileNotFoundException e) {
			Logger.print(Logger.Level.ERROR, e, "Unable to create new output stream for path %s!", filePath);
			return null;
		}
		try {
	        byte[] content = new byte[(int) entry.getSize()];
	        sevenZFile.read(content, 0, content.length);
	        out.write(content);
		}
		catch (IOException e) {
			Logger.error("Unable to read or write from zip input stream!", e);
			closeZipOutputStream(out);
			return null;
		}
		if (!closeZipOutputStream(out))
			return null;
		/*
		 *  We are getting a warning here that the output stream might not be closed
		 *  but that is not true, we are trying to close it in 'closeZipOutputStream()'
		 */
        return new File(filePath);
	}
		
	private boolean closeZipOutputStream(BufferedOutputStream stream) 
	{
		try {
			stream.close();
			return true;
		}
		catch (IOException e) {
			Logger.error("Unable to close zip output stream!", e);
			return false;
		}
	}
	private boolean closeZipInputStream(ZipInputStream stream) 
	{	
		try {
			stream.close();
			return true;
		}
		catch (IOException e) {
			Logger.error("Unable to close zip input stream!", e);
			return false;
		}
	}
}
