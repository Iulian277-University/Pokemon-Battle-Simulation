package main;

import entities.*;
import io.GenerateTestcases;
import io.ImportTestcases;

import java.util.List;

/** This is the entry point of the program */
public class Main {
    public static void main(String[] args) {

        TrainerFactory trainerFactory = TrainerFactory.generateFactory();
        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();

        // TODO: Get number of testcases (number of .json files in the ./testcases dir)


        // GenerateTestcases.generate(10);


        List<Trainer> trainers = ImportTestcases.getTrainers(trainerFactory, pokemonFactory);
        if (trainers.isEmpty()) {
            System.out.println("Couldn't get the trainers");
            return;
        }

        Trainer firstTrainer = trainers.get(0);
        Trainer secondTrainer = trainers.get(1);

        System.out.println(firstTrainer);
        System.out.println(secondTrainer);

    }
}
