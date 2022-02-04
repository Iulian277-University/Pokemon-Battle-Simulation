package main;

import common.Constants;
import entities.PokemonFactory;
import entities.Trainer;
import entities.TrainerFactory;
import game.Arena;
import io.ImportTestcases;
import io.Logger;
import utils.FindFilesByExtension;

import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

/** This class is used for running the testcases */
public final class Test {
    private Test() {}

    public static void runTestcases(TrainerFactory trainerFactory, PokemonFactory pokemonFactory) {
        System.out.println(Constants.INFO_LOG + "Running testcases ...");
        List<String> filteredPaths = FindFilesByExtension.findFiles(Paths.get(Constants.TESTCASES_DIR_PATH),
                Constants.TESTCASES_FILE_EXT);

        List<String> testcasesPaths = filteredPaths
                .stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList();

        int testIndex = 1;
        for (String testcasePath: testcasesPaths) {
            System.out.println(Constants.STATUS_LOG + testcasePath);
            Logger logger = Logger.generateLogger(Constants.OUTPUT_DIR_PATH +
                    Constants.OUTPUT_FILE_NAME_FORMAT +
                    testIndex +
                    Constants.OUTPUT_FILE_EXT);

            logger.print("<<< TESTCASE " + testIndex + " >>>");

            List<Trainer> trainers = ImportTestcases.getTrainers(trainerFactory, pokemonFactory, testcasePath);

            Trainer firstTrainer  = trainers.get(0);
            Trainer secondTrainer = trainers.get(1);

            Arena arena = Arena.generateArena(firstTrainer, secondTrainer, logger);
            Arena.battle(arena);

            logger.writeToStream();
            testIndex++;
        }

        System.out.println(Constants.INFO_LOG + "Done testcases");
    }
}
