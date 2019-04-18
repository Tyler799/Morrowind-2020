package io.mte.updater;

import java.io.File;
import java.io.PrintWriter;

public class Logger {
	/**
	 * <p>
	 * The following logger levels regulate what kind of 
	 * log output is permitted by the application.<br>
	 * Define logger level by running the application with the appropriate JVM argument.
	 * </p>
	 * <h1>LOG</h1>
	 * <ul>
	 * <li>This is the default behavior, prints only normal logs.</li>
	 * <li>Run with JVM argument: <i>none</i></li>
	 * </ul>
	 * <h1>VERBOSE</h1>
	 * <ul>
	 * <li>When you want more information, prints extended logs and warnings.</li>
	 * <li>Run with JVM argument: <i>-v, -verbose</i></li>
	 * </ul>
	 * <h1>DEBUG</h1>
	 * <ul>
	 * <li>When you are debugging the application, print everything including debug logs.</li>
	 * <li>Run with JVM argument: <i>-d, -debug</i></li>
	 * </ul>
	 */
	// TODO Add new level type that prints stackTrace directly in console
	public enum Level {
		
		LOG(Short.parseShort("0"), "", ""),
		ERROR(Short.parseShort("0"), "[ERROR] ", ""),
		WARNING(Short.parseShort("1"), "[Warning] ", ""),
		VERBOSE(Short.parseShort("1"), "[LOG] ", "-v", "-verbose"),
		DEBUG(Short.parseShort("2"), "[DEBUG] ", "-d", "-debug");
		
		private final short level;
		private final String[] arguments;
		private final String tag;
		
		Level(short lvl, String tag, String...args) {
			
			this.tag = tag;
			level = lvl;
			arguments = args;
		}
		
		public static Level getLoggerLevel(String[] args) {
			/*
			 *  Iterate through every enum entry
			 */
			for (Level entry : Level.values()) {
				/*
				 *  Iterate through the list of arguments supplied
				 */
				for (int i = args.length - 1; i >= 0; i--) {
					/*
					 *  Compare current argument supplied with each argument in the
					 *  current level entry to find a match
					 */
					for (int i2 = entry.arguments.length - 1; i2 >= 0; i2--) {
						if (args[i].equals(entry.arguments[i2]))
							return entry;
					}
				}
			}/*
			  *  If no logging argument has been passed
			  *  just set the regular logging level
			  */
			return LOG;
		}
		public String[] getArguments() {
			return arguments;
		}
	}
	
	public static class LogFile {
		
		public static final String NAME = "MTE-Updater.log";
		
		private static LogFile instance;
		private static PrintWriter writer;
		private static File file;
		
		LogFile() {
			try {
				file = new File(NAME);
				file.createNewFile();
				writer  = new PrintWriter(NAME);
			}
			catch (java.io.IOException e) {
				Logger.print(Level.ERROR, e, "Unable to create log or access log file %s", NAME);
			}
		}
		/**
		 *  Purge the log file and create a new instance
		 */
		private static void init() {
			if (instance == null)
				instance = new LogFile();
			else 
				warning("Trying to initialize LogFile more then once");
		}
		private static PrintWriter get() {
			return writer;
		}
		public static void close() {
			writer.close();
		}
		private static void clear() {
			try {
				writer  = new PrintWriter(NAME);
				writer.close();
			} 
			catch (java.io.FileNotFoundException e) {
				error("Unable to close log file, missing in action", e);
			}
		}
	}
	
	private static Level LOGGER_LEVEL;
	private static Logger logger;
	
	private Logger(String[] args) {
		LOGGER_LEVEL = Level.getLoggerLevel(args);
		LogFile.init();
	}
	/**
	 * Call only once from the main method to create a new logger instance
	 * @param args List of JVM arguments to search for a logger level argument
	 */
	public static void init(String[] args) {
		
		if (logger != null)
			warning("Trying to initialize logger more then once");
					
		Logger.logger = new Logger(args);
		verbose("Logger initialized with level " + Logger.getLevel());
	}
	
	/* Public getter function to retrieve logger level */
	public static Level getLevel() {
		return LOGGER_LEVEL;
	}
	
	/** Did the application start in debug mode */
	public static boolean isDebug() {
		return LOGGER_LEVEL == Level.DEBUG;
	}
	
	/**
	 * See if we are allowed to print the log with argument level 
	 * @param lvl Level of the log we want to print
	 * */
	private static boolean canPrintLog(Level lvl) {
		
		if (logger == null) {
			print("[Warning] " + "Trying to print a log before the logger has initialized");
			return false;
		}
		else
			return lvl.level <= Logger.getLevel().level;
	}
	
	/**
	 * Employ {@code printf} method to output log when you have string items you want<br>
	 * wrapped in single quotation marks. Also accepts a single item as an argument.
	 * 
	 * @param lvl Logging level of this log
	 * @param format A format string to process and print
	 * @param items Array of string items to wrap
	 */
	public static void print(Level lvl, String format, String...items) {
		
		if (items == null || items.length == 1 && items[0].isEmpty()) {
			warning("Attempting to print log with incorrect number of arguments (0)");
		}
		else if (canPrintLog(lvl)) {
			if (items.length > 1) {
				/*
				 * Wrap each string item with single quotation marks
				 */
				Object[] objects = String.join("' '", items).split("\\s+");
				objects[0] = "'" + objects[0];
				objects[objects.length - 1] += "'";
				
				System.out.printf(lvl.tag + format + "\n", objects);
				LogFile.get().printf(lvl.tag + format + "\n", objects);
				
				if (lvl == Level.ERROR)
					new Exception().printStackTrace(LogFile.get());
			}
			else {
				System.out.printf((String)(lvl.tag + format + "\n"), (String)("'" + items[0] + "'"));
				LogFile.get().printf((String)(lvl.tag + format + "\n"), (String)("'" + items[0] + "'"));
			}
		}
	}
	/**
	 *  <p>
	 *  Employ {@code printf} method to output log when you have string 
	 *  items you want wrapped in single<br> quotation marks
	 *  Use this overload method when you want to print stack trace to logfile.
	 *	</p>
	 *	See the {@link Logger#print(Level, String, String...) overloaded method} for additional information
	 *	<p>
	 */
	public static void print(Level lvl, Exception e, String format, String...items) {
		print(lvl, format, items);
		e.printStackTrace(LogFile.get());
	}
	
	public static void print(String msg) {
		System.out.println(msg);
		LogFile.get().println(msg);
		LogFile.get().flush();
	}
	
	public static void error(String msg) {
		/*
		 * Don't ask for permission here because there are situations where
		 * a method might request error logging before the logger has initialized
		 * 
		 * if (canPrintLog(Level.ERROR))
		 */
		print(Level.ERROR.tag + msg);
		new Exception().printStackTrace(LogFile.get());
	}
	public static void error(String msg, Exception e) {
		
		print(Level.ERROR.tag + msg);
		e.printStackTrace(LogFile.get());
	}
	
	public static void verbose(String msg) {
		if (canPrintLog(Level.VERBOSE))
			print(Level.VERBOSE.tag + msg);
	}
	
	public static boolean warning(String msg) {
		if (canPrintLog(Level.WARNING)) {
			print(Level.WARNING.tag + msg);
			return true;
		}
		return false;
	}
	public static void warning(String msg, Exception e) {
		if (warning(msg) == true)
			e.printStackTrace(LogFile.get());
	}
	
	public static void debug(String msg) {
		if (canPrintLog(Level.DEBUG))
			print(Level.DEBUG.tag + msg);
	}
	
	public static void test() {
		
		print("This is a regular log");
		print(Logger.Level.LOG, "This %s a %s log", "is", "constructed");
		warning("This is warning log");
		debug("This is a debug log");
	}
}
