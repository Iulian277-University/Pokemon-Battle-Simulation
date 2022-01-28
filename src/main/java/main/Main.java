package main;

import entities.*;

/**
 * This is the entry point of the program
 */
public class Main {
    public static void main(String[] args) {

        TrainerFactory trainerFactory = TrainerFactory.generateFactory();
        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();

//         GenerateTestcases.generate(10);

        // TODO: Adapter pattern for IO
        // TODO: Refactor + Modularization

        Test.runTestcases(trainerFactory, pokemonFactory);
    }
}
