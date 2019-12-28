package com.sodabottle.stext.utils;

import org.slf4j.Logger;

public class LogUtils {
    public enum LogState {
        INFO, DEBUG, ERROR
    }

    public static void logMessage(final String message, final Logger logger, final LogState logState) {
        switch (logState) {
            case INFO:
                logger.info(" ++++++ " + message + " ++++++ ");
                break;
            case DEBUG:
                logger.info(" ****** " + message + " ****** ");
                break;
            case ERROR:
                logger.error(" ------ " + message + " ------ ");
                break;
        }
    }
    
    public static void appender(final Logger logger) {
    	logger.info(" # # # # # # # # # # # # # # # # # # ");
    }
}
