package io.mte.updater;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class VersionFile {
	
	private final File file;
	private final Data data;

	private enum Entry { VERSION, SHA; }
	public enum Type { 
		MTE("mte-version.txt"), MWSE("mwse-version.txt"); 
		private String filename;
		
		Type(String name) {
			filename = name;
		}
		public String getName() {
			return filename;
		}
	}
	
	static class Data {
		private final java.util.Map<Entry, String> map;
		
		Data(java.util.Map<Entry, String> values) {
			// Create a new map instance that cannot be modified
			map = java.util.Collections.unmodifiableMap(
					new java.util.LinkedHashMap<Entry, String>(values)); 
		}
		public float getReleaseVersion() {
			return Float.parseFloat(map.get(Entry.VERSION));
		}
		public String getCommitSHA() {
			return map.get(Entry.SHA);
		}
		public boolean isEmpty() {
			return map.isEmpty();
		}
	}
	
	VersionFile(Type type) {
		
		file = new File(type.filename);
		
		String contents = FileHandler.readFile(type.filename);
		if (contents == null || contents.isEmpty()) {
			Logger.print(Logger.Level.ERROR, "Couldn't read or file is empty (%s)", type.filename);
			Execute.exit(1, true);
			// This part of the code is unreachable
			data = null; return;
		}
		
		data = readData(contents, type);
		
		if (data.map.isEmpty()) {
			Logger.print(Logger.Level.ERROR, "Malformed version file %s !", type.filename);
			Execute.exit(1, true);
		}
	}
	
	public static VersionFile load(Type type) {
		return new VersionFile(type);
	}
	public Data getData() {
		return data;
	}
	
	public static Data readData(String contents, Type type) {
		
		CharSequence versionLine[] = contents.split(" ");
		java.util.Map<Entry, String> data2 = new java.util.HashMap<>();
		/* 
		 *  MTE version file 
		 */
		if (type == Type.MTE)  
		{
			if (versionLine.length == 2) 
			{	
				/*  The version file should contain a single line with two numbers,
				 *  first one being the release version and second the last release commit SHA
				 */
				String versionNumber = versionLine[0].toString();
				int vnLength = versionNumber.length();
				int vnDecimal = versionNumber.indexOf(".");
	
				// Version must be formatted properly
				if (vnLength > 1 && vnDecimal > 0 && vnDecimal <= (vnLength -2))
				{
					versionNumber = versionNumber.replace(".", "");
					
					if (Pattern.matches("[a-z0-9]+", versionLine[1]) && StringUtils.isNumeric(versionNumber)) {
						data2.put(Entry.VERSION, versionLine[0].toString());
						data2.put(Entry.SHA, versionLine[1].toString());
					}
				}
			}
		}
		/*
		 *  MWSE version file
		 */
		else if (type == Type.MWSE)
		{
			if (versionLine.length == 1) {
				if (Pattern.matches("[a-z0-9]+", versionLine[0])) {
					data2.put(Entry.SHA, versionLine[0].toString());
				}	
			}
		}
		else {
			Exception e = new Exception();
			Logger.error("Tried to read data with an invalid type", e);
		}
		/*
		 *  The exported data set will be empty if the contents is malformed
		 *  or an invalid type was passed as an argument
		 */
		return new Data(data2);
	}
}
