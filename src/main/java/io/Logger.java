package io;

import common.Constants;
import main.Main;

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

    private boolean stdout = false;
    private boolean file   = false;
    public void writeToStream() {
        String filePath = outputStream;

        Constants.Streams outputFileDescriptor = OutputStream.getOutputStream();
        switch (outputFileDescriptor) {
            case FILE   -> file   = true;
            case STDOUT -> stdout = true;
            case STDOUT_AND_FILE -> {
                file   = true;
                stdout = true;
            }
        }

        // Write to file if queried
        if (file) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(output.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Write to stdout if queried
        if (stdout) {
            System.out.println(output);
        }

        output.setLength(0);
    }
}
