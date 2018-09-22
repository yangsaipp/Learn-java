package logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class LogbackTest {
	static Logger logger = LoggerFactory.getLogger(LogbackTest.class);

	public static void main(String[] args) {
		// assume SLF4J is bound to logback in the current environment
//		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		// print logback's internal status
//		StatusPrinter.print(lc);
//		while(true) {
			logger.info("sssssssssssssss");
			logger.info("sssssssssssssss");
//		}
	}
}
