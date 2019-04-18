package io.mte.updater;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public class Execute {

	/**
	 *  This method blocks until thread execution until input data is available<br>
	 *  User must press Enter to continue running the application
	 */
	public static void pause() {
		/*
		 *  Do not use scanner to scan for user input, I've been getting unknown exceptions being 
		 *  thrown with no message or stack trace. It just doesn't seem to work for some reason
		 *  
		 *  Using direct System InputStream seems like the best idea, and although it only works
		 *  for ENTER at least it works and won't crash
		 */
		Logger.print("Press Enter to continue...");
		try {
			System.in.read();
		}
		catch(IOException e) {
			Logger.error("Something went wrong while reading user input", e);
			exit(0, true, false);
		}
	}
	
	/**
	 * Terminate the currently running Java Virtual Machine.<br>
	 * <i>Note that this will prompt the user to continue before closing the java console window</i>
	 * @param code exit status <i>(nonzero value indicates abnormal termination)</i>
	 * @param clean clean all temporary files created while updating
	 */
	public static void exit(int code, boolean clean) {
		exit (code, clean, true);
	}
	
	/**
	 * Terminate the currently running Java Virtual Machine.<br>
	 * @param code exit status <i>(nonzero value indicates abnormal termination)</i>
	 * @param clean clean all temporary files created while updating
	 * @param pause prompt the user to press enter to continue
	 */
	public static void exit(int code, boolean clean, boolean pause) {
		
		if (code == 0) Logger.verbose("Closing updater application...");
		else Logger.print("Terminating updater application...");
		
		if (clean == true)
			FileHandler.updaterCleanup();
		/*
		 *  If we need to pause do it before we close the logfile stream
		 *  otherwise we get errors because we are still trying to print logs
		 */
		if (pause == true && !Main.isLauncher())
			Execute.pause();
		
		Logger.LogFile.close();
		System.exit(code);
	}
	
	/**
	 * Get process id of the currently running Java application
	 * @return numerical value corresponding to the process id
	 */
	public static short getProcessId() {
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		return Short.parseShort(processName.substring(0, processName.indexOf("@")));
	}
	
	public static boolean command(String cmd) {
		
		try {
			Logger.print(Logger.Level.DEBUG, "Excecuting cmd command: %s in a new window", cmd);
			Runtime.getRuntime().exec("cmd.exe /c start " + cmd, null, null);
			return true;
		} 
		catch (IOException e) {
			Logger.print(Logger.Level.ERROR, e, "Unable to execute Windows command: %s", cmd);
			return false;
		}
	}
	
	/**
	 * Use {@code ProcessBuilder} to start a new application or script process.<br>
	 * <i>Note that if you start a console application this way it will run hidden</i>
	 * 
	 * @param process path to the application or script we want to start
	 * @param wait should we pause the current thread and wait for the new process to terminate?
	 * @return instance of the process started or {@code null} if an error occurred
	 */
	public static Process start(String process, boolean wait) {
		
		Logger.print(Logger.Level.DEBUG, "Starting new process %s" + 
				((wait) ? " and waiting for it to terminate" : ""), process);
		try {
			Process proc = new ProcessBuilder(process).start();
			if (wait == true) proc.waitFor();
			return proc;
		} 
		catch (IOException | InterruptedException e) {
			Logger.print(Logger.Level.ERROR, e, "Unable to start new process %s", process);
			return null;
		}
	}
}
