package util;

import java.io.IOException;
import java.util.logging.*;

public class GlobalLogger {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static FileHandler fh;
    private static final SimpleFormatter fmt = new SimpleFormatter();
    private static final StreamHandler sh = new StreamHandler(System.out, fmt);

    static {
        try {
            fh = new FileHandler("./LOG.log");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.setUseParentHandlers(false);
        logger.addHandler(sh);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    public static void log(Level level, String data) {
        System.out.println(data);
        logger.log(level, data);
    }
}
