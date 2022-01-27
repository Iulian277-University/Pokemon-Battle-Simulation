package main;

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

        // TODO: Get number of testcases (number of .json files in the ./testcases dir)


//         GenerateTestcases.generate(10);


        List<Trainer> trainers = ImportTestcases.getTrainers(trainerFactory, pokemonFactory);



        Trainer firstTrainer  = trainers.get(0);
        Trainer secondTrainer = trainers.get(1);

        Arena arena = Arena.generateArena(firstTrainer, secondTrainer);
        Arena.battle(arena);

        // TODO: Move this functionality to Logger Class (console or file)
        String output = Logger.getOutputBuffer().toString();
        System.out.println(output);

    }
}
