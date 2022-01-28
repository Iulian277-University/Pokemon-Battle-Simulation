package logger;

public final class Logger {
    private String outputStream;

    // Pattern: Singleton
    private static Logger logger;
    private Logger(String outputStream) {
        this.outputStream = outputStream;
    }

    public static Logger generateLogger(String outputStream) {
        if (logger == null)
            logger = new Logger(outputStream);
        logger.outputStream = outputStream;
        return logger;
    }

    public void print(Object outputInfo) {
        System.out.println(outputInfo);
    }
}
