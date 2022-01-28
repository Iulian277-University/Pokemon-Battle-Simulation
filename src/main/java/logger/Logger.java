package logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

    private final StringBuilder output = new StringBuilder();
    public void print(Object outputInfo) {
        output.append(outputInfo).append("\n");
    }

    public void writeToFile() {
        String filePath = outputStream;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(output.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.setLength(0);
        }
    }
}
