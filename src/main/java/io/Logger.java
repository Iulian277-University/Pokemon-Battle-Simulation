package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used for printing the state
 * of each battle to an external file
 */
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

    // Pattern: Adapter
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
