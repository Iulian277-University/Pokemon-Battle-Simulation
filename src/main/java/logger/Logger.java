package logger;

public final class Logger {
    // TODO: Singleton

    private static StringBuilder outputBuffer = new StringBuilder();

    public static StringBuilder getOutputBuffer() {
        return outputBuffer;
    }
}
