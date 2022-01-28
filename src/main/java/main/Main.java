package main;

import common.Constants;
import entities.*;
import game.Arena;
import io.ImportTestcases;
import logger.Logger;
import utils.FindFilesByExtension;

import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

/**
 * This is the entry point of the program
 */
public class Main {
    public static void main(String[] args) {

        TrainerFactory trainerFactory = TrainerFactory.generateFactory();
        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();

//         GenerateTestcases.generate(10);


        List<String> filteredPaths = FindFilesByExtension.findFiles(Paths.get(Constants.TESTCASES_DIR_PATH),
                Constants.TESTCASES_FILE_EXT);

        List<String> testcasesPaths = filteredPaths
                .stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList();

        int testIndex = 1;
        for (String testcasePath: testcasesPaths) {
            System.out.println(testcasePath);
            Logger logger = Logger.generateLogger(Constants.OUTPUT_DIR_PATH + "output_test_" + testIndex + Constants.OUTPUT_FILE_EXT);
            List<Trainer> trainers = ImportTestcases.getTrainers(trainerFactory, pokemonFactory, testcasePath);

            Trainer firstTrainer  = trainers.get(0);
            Trainer secondTrainer = trainers.get(1);

            Arena arena = Arena.generateArena(firstTrainer, secondTrainer, logger);
            Arena.battle(arena);

            logger.writeToFile(testIndex);
            testIndex++;
        }
    }
}
