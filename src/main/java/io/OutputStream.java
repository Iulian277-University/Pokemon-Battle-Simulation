package io;

import java.util.Scanner;

import common.Constants;
import common.Constants.Streams;

public final class OutputStream {
    private OutputStream() {}

    private static Constants.Streams outputFileDescriptor;
    public  static Constants.Streams getOutputStream() {
        return outputFileDescriptor;
    }

    public static void selectStream() {
        System.out.println("Output streams");
        System.out.println("\t0 - [STDOUT]");
        System.out.println("\t1 - [FILE]");
        System.out.println("\t2 - [STDOUT & FILE]");

        Scanner scanner = new Scanner(System.in);
        int fileDescriptor = -1;
        while ( fileDescriptor != Streams.FILE.ordinal() &&
                fileDescriptor != Streams.STDOUT.ordinal() &&
                fileDescriptor != Streams.STDOUT_AND_FILE.ordinal()) {

            System.out.print("Select output stream (one from above): ");
            fileDescriptor = scanner.nextInt();
        }
        scanner.close();

        System.out.println("----------");
        System.out.println("Output stream chosen: " + Streams.values()[fileDescriptor]);
        System.out.println("----------");

        outputFileDescriptor = Streams.values()[fileDescriptor];
    }
}
