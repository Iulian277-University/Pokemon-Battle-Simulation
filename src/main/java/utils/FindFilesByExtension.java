package utils;

import common.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * This is a handler class used for getting a
 * list of file-paths within a given directory
 */
public final class FindFilesByExtension {
    private FindFilesByExtension() {}

    public static List<String> findFiles(Path dirPath, String fileExtension) {

        if (!Files.isDirectory(dirPath)) {
            System.err.println(Constants.ERROR_LOG + "Path must be a valid directory");
            return Collections.emptyList();
        }

        List<String> result = null;
        try (Stream<Path> walk = Files.walk(dirPath)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> f.endsWith(fileExtension))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
