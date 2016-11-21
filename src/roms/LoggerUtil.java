package roms;

import java.util.logging.Logger;

public class LoggerUtil {
    protected static final Logger logger;

    private LoggerUtil() {

    } // prevents instantiation

    static {
        new LoggerUtil(); // dummy
        logger = Logger.getLogger("roms");
    }
}
