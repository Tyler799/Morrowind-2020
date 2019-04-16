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
	 */
	public boolean unzip(String zipFilePath, String destDirectory) throws IOException {
		
		// Make sure the zip file under the given path exists
		File zipFile = new File(zipFilePath);
		if (!zipFile.exists()) {
			System.out.println("ERROR: Unable to find zip file with path '" + zipFilePath +'"');
			return false;
		}
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
	
		System.out.print("[DEBUG] Unziping from '" + zipFilePath + "' to '" + destDirectory + "'\n");
		/*
		 *  Handle 7z files using Apache Commons Compress library
		 */
		if (FilenameUtils.getExtension(zipFilePath).equals("7z")) {
			System.out.println("[DEBUG] Detected 7Zip archive, using Apache Commons Compress library");
			try (SevenZFile sevenZFile = new SevenZFile(new File(zipFilePath))) {
				SevenZArchiveEntry entry = sevenZFile.getNextEntry();
				
				// Abort if zip file is empty
				if (entry == null) {
					System.out.println("ERROR: Zip contains no valid entries!");
					System.out.println("Aborting unzipping operation...");
					return false;
				}
				while (entry != null) {
					
					System.out.println("[DEBUG] Iterating over zip entry '" + entry.getName() + "'");
					String filePath = destDirectory + File.separator + entry.getName();
					
					if (!entry.isDirectory()) {
						// if the entry is a file, extracts it
						File extrFile = extractFile(sevenZFile, entry, filePath);
						if (extrFile == null || !extrFile.exists()) {
							System.out.println("ERROR: Unable to find extracted file '" + entry.getName() + "'!");
						}
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
				System.out.println("ERROR: Unable to read archive '" + zipFile.getName() + "'!");
				return false;
			}
			return true;
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
				System.out.println("ERROR: Zip contains no valid entries!");
				System.out.println("Aborting unzipping operation...");
				closeZipInputStream(zipIn);
				return false;
			}
		}
		catch (IOException e) {
			System.out.println("ERROR: ZIP file error has occurred, unable to get new zip entry!");
			e.printStackTrace();
			closeZipInputStream(zipIn);
			return false;
		}
		// iterates over entries in the zip file
		while (entry != null) {
			
			System.out.println("[DEBUG] Iterating over zip entry '" + entry.getName() + "'");
			String filePath = destDirectory + File.separator + entry.getName();
			
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				File extrFile = extractFile(zipIn, entry.getName(), filePath);
				if (extrFile == null || !extrFile.exists()) {
					System.out.println("ERROR: Unable to find extracted file '" + entry.getName() + "'!");
				}
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
				System.out.println("ERROR: Unable to close or get next zip entry!");
				System.out.println("Aborting unzipping operation...");
				return false;
			}
		}
		if (!closeZipInputStream(zipIn))
			return false;
		
		return true;
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
		
		System.out.print("[DEBUG] Extracting zip file '" + filename + "' to '" + filePath + "'");
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(filePath));
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("ERROR: Unable to create new output stream for path '" + filePath + "'!");
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
			System.out.println("ERROR: Unable to read or write from zip input stream!");
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
	
		System.out.print("[DEBUG] Extracting zip file '" + entry.getName() + "' to '" + filePath + "'");
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(filePath));
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("ERROR: Unable to create new output stream for path '" + filePath + "'!");
			return null;
		}
		try {
	        byte[] content = new byte[(int) entry.getSize()];
	        sevenZFile.read(content, 0, content.length);
	        out.write(content);
		}
		catch (IOException e) {
			System.out.println("ERROR: Unable to read or write from zip input stream!");
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
			System.out.println("ERROR: Unable to close zip output stream!");
			e.printStackTrace();
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
			System.out.println("ERROR: Unable to close zip input stream!");
			e.printStackTrace();
			return false;
		}
	}
}
