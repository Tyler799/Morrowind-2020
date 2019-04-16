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
			print("Logger initialized with level " + logger.getLevel());
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
		return lvl.level <= logger.getLevel();
	}
	
	/**
	 * Employ printf method to output log when you have<br>
	 * multiple string items you want wrapped in quotation marks.
	 * @param lvl
	 * @param log
	 * @param items
	 */
	public static void print(Level lvl, String log, String...items) {
		
		if (items.length <= 1) {
			warning("Attempting to print log with incorrect arguments");
		}
		else if (canPrintLog(lvl)) {
			/*
			 * Wrap each string item with single quotation marks
			 */
			Object[] objects = String.join("' '", items).split("\\s+");
			objects[0] = "'" + objects[0];
			objects[objects.length - 1] += "'";
			
			System.out.printf(log + "\n", objects);
		}
	}
	
	public static void print(String msg) {
		System.out.println(msg + "");
	}
	
	public static void error(String msg) {
		print("[ERROR] " + msg);
	}
	
	public static void verbose(String msg) {
		if (canPrintLog(Level.VERBOSE))
			print("[LOG] " + msg);
	}
	
	public static void warning(String msg) {
		if (canPrintLog(Level.VERBOSE))
			print("[WARNING] " + msg);
	}
	
	public static void debug(String msg) {
		if (canPrintLog(Level.DEBUG))
			print("[DEBUG] " + msg);
	}
	
	public static void test() {
		
		print("This is a regular log");
		print(Logger.Level.LOG, "This %s a %s log", "is", "constructed");
		warning("This is warning log");
		debug("This is a debug log");
	}
}
