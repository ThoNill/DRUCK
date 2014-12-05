package toni.druck.helper;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

public class DebugAssistent {
	static Logger logger = Logger.getLogger("DebugAssistent");

	public DebugAssistent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void log(Category log, Exception ex) {

		StackTraceElement elements[] = ex.getStackTrace();
		log.error("Exception [" + ex.getMessage() + "]");
		for (int i = 0; i < elements.length; i++) {
			log.error("[" + elements[i] + "] " + elements[i].getFileName()
					+ ":" + elements[i].getMethodName() + ":"
					+ elements[i].getLineNumber());
		}

	}

	public static void log(Exception ex) {
		log(logger, ex);
	}

	public static void log(Category log, Error ex) {

		StackTraceElement elements[] = ex.getStackTrace();
		log.error("Exception [" + ex.getMessage() + "]");
		for (int i = 0; i < elements.length; i++) {
			log.error("[" + elements[i] + "] " + elements[i].getFileName()
					+ ":" + elements[i].getMethodName() + ":"
					+ elements[i].getLineNumber());
		}

	}

	public static void log(Error ex) {
		log(logger, ex);
	}

	public static void logThreadInfo(Category log, int max) {
		/*
		 * Thread t = Thread.currentThread(); log.error("Thread: [" +
		 * t.getName() + "]"); StackTraceElement elements[] = t.getStackTrace();
		 * int anz = elements.length; if (anz > max) { anz = max; }; for(int
		 * i=3;i < anz;i++) { log.error("[" + elements[i] + "] " +
		 * elements[i].getFileName() + ":" + elements[i].getMethodName() + ":" +
		 * elements[i].getLineNumber()); }
		 */
	}

}
