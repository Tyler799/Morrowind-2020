package io.mte.updater;

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
	public enum Level {
		
		LOG(Short.parseShort("0"), "", ""),
		ERROR(Short.parseShort("0"), "[ERROR] ", ""),
		WARNING(Short.parseShort("1"), "[Warning] ", ""),
		VERBOSE(Short.parseShort("1"), "[LOG] ", "-v", "-verbose"),
		DEBUG(Short.parseShort("2"), "[DEBUG] ", "-d", "-debug");
		
		private final short level;
		private final String[] argument;
		private final String tag;
		
		Level(short lvl, String tag, String...args) {
			
			this.tag = tag;
			level = lvl;
			argument = args;
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
					for (int i2 = entry.argument.length - 1; i2 >= 0; i2--) {
						if (args[i].equals(entry.argument[i2]))
							return entry;
					}
				}
			}/*
			  *  If no logging argument has been passed
			  *  just set the regular logging level
			  */
			return LOG;
		}
		
	}
	private final Level LOGGER_LEVEL;
	private static Logger logger;
	
	private Logger(String[] args) {
		LOGGER_LEVEL = Level.getLoggerLevel(args);
	}
	/**
	 * Call only once from the main method to create a new instance of the logger
	 * @param args List of JVM arguments to search for a logger level argument
	 */
	public static void init(String[] args) {
		if (logger == null) {
			Logger.logger = new Logger(args);
			verbose("Logger initialized with level " + logger.getLevel());
		}
		else
			warning("Trying to initialize logger more then once");
	}
	
	/* Getter function to retrieve logger level value */
	private short getLevel() {
		return LOGGER_LEVEL.level;
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
			return lvl.level <= logger.getLevel();
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
			}
			else {
				System.out.printf((String)(lvl.tag + format + "\n"), (String)("'" + items[0] + "'"));
			}
		}
	}
	
	public static void print(String msg) {
		System.out.println(msg + "");
	}
	
	public static void error(String msg) {
		/*
		 * Don't ask for permission here because there are situations where
		 * a method might request error logging before the logger has initialized
		 * 
		 * if (canPrintLog(Level.ERROR))
		 */
			print(Level.ERROR.tag + msg);
	}
	
	public static void verbose(String msg) {
		if (canPrintLog(Level.VERBOSE))
			print(Level.VERBOSE.tag + msg);
	}
	
	public static void warning(String msg) {
		if (canPrintLog(Level.WARNING))
			print(Level.WARNING.tag + msg);
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
