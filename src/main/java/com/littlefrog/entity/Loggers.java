package com.littlefrog.entity;


import org.apache.log4j.Logger;

/**
 * @author DW
 */
public class Loggers {

    private static Logger logger = Logger.getLogger(Loggers.class);

    public static Logger getLogger() {
        return logger;
    }
}
