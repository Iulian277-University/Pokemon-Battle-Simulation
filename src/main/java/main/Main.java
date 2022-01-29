package main;

import entities.*;

/**
 * This is the entry point of the program
 */
public class Main {
    public static void main(String[] args) {

        TrainerFactory trainerFactory = TrainerFactory.generateFactory();
        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();

        // GenerateTestcases.generate(10);

        // TODO: Battle on threads
        // TODO: Adapter pattern for IO

        Test.runTestcases(trainerFactory, pokemonFactory);
    }
}
