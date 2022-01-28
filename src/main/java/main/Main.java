package main;

import common.Constants;
import entities.*;
import game.Arena;
import game.Battle;
import io.ImportTestcases;
import logger.Logger;

import java.util.List;

/** This is the entry point of the program */
public class Main {
    public static void main(String[] args) {

        TrainerFactory trainerFactory = TrainerFactory.generateFactory();
        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();

//         GenerateTestcases.generate(10);



        // TODO: Get number of testcases (number of .json files in the ./testcases dir)
        // TODO: Run all testcases
        int testIndex = 1;
        Logger logger = Logger.generateLogger(Constants.OUTPUT_DIR_PATH + "output_test_" + testIndex + Constants.OUTPUT_FILE_EXT);
        List<Trainer> trainers = ImportTestcases.getTrainers(trainerFactory, pokemonFactory, testIndex);

        Trainer firstTrainer  = trainers.get(0);
        Trainer secondTrainer = trainers.get(1);

        Arena arena = Arena.generateArena(firstTrainer, secondTrainer, logger);
        Arena.battle(arena);


    }
}
