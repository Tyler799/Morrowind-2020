package io.mte.updater;

import java.io.IOException;
import java.util.Scanner;

/**
 * Always use this instead of System InputStream to read user input.<br>
 */
public class UserInput {
	
	private final Scanner reader = new Scanner(System.in);
	private final java.io.InputStream is = System.in;
	private static final UserInput ui = new UserInput();
	
	public enum Key {

		YES("y", "yes"),
		NO("n", "no");
		
		private final String[] keys;
		
		private Key(String...s) {
			keys = s;
		}
		public static boolean isValid(String input, Key key) {
			
			for (int i = key.keys.length - 1; i >= 0; i--)
			{
				if (input.equalsIgnoreCase(key.keys[i]))
					return true;
			}
			return false;
		}
	}
	/** 
	 *  Close the system input stream. Note that you will not be able to read user input<br> 
	 * 	after doing this so this should only be done just before exiting the application
	 */
	public static void close() {
		ui.reader.close();
	}
	/**
	 * Read last user keyboard input <i>(safe to call each operation cycle)</i>.
	 * @return last user keyboard input
	 */
	public static String read() {
		return ui.reader.next();
	}
	/**
	 *  Block thread execution until correct input data is available,<br>
	 *  the end of the stream is detected, or an exception is thrown
	 *  @param keys list of user input keys to wait for
	 */
	public static Key waitFor(Key...keys) {
		
		if (keys == null || keys.length == 0) {
			Logger.error("Invalid key argument passed");
			Execute.exit(1, false, false);
			return null;
		}
		else {
			if (Logger.isDebug()) {
				String[] keyNames = java.util.Arrays.stream(keys).map(Enum::name).toArray(String[]::new);
				Logger.print(Logger.Level.DEBUG, "Wating for user input: %s", String.join(", ", keyNames));
			}
			while (ui.reader.hasNext())
			{
				String input = ui.reader.next();
				for (int i = keys.length - 1; i >= 0; i--)
					if (Key.isValid(input, keys[i]))
					{
						Logger.print(Logger.Level.DEBUG, "Found valid user key input: %s", keys[i].toString());
						return keys[i];
					}
			}
			Logger.error("No more tokes in user input stream");
			Execute.exit(1, false, false);
			return null;
		}
	}
	/** Block thread execution until the user presses {@code enter} key */
	public static void waitForEnter() {
		/*
		 *  Do not use scanner to scan for user input, I've been getting unknown exceptions being 
		 *  thrown with no message or stack trace. It just doesn't seem to work for some reason
		 *  
		 *  Using direct System InputStream seems like the best idea, and although it only works
		 *  for ENTER at least it works and won't crash
		 */
		try {
			Logger.print("Press Enter to continue...");
			ui.is.read();
		}
		catch(IOException e) {
			Logger.error("Something went wrong while reading user input", e);
			Execute.exit(1, false, false);
		}
	}
}
