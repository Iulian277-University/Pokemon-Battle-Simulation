package main;

import entities.*;
import game.Arena;
import io.GenerateTestcases;

import java.util.Arrays;

/** This is the entry point of the program */
public class Main {
    public static void main(String[] args) {

//        TrainerFactory trainerFactory = TrainerFactory.generateFactory();
//        System.out.println(trainerFactory);
//
//        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();
//        System.out.println(pokemonFactory);
//
//        Trainer trainer1 = trainerFactory.createTrainer("Trainer1", 30,Arrays.asList(
//                pokemonFactory.createPokemon("Pikachu", Arrays.asList("Cap", "Vitamins")),
//                pokemonFactory.createPokemon("Bulbasaur", Arrays.asList("Tree", "Vitaminsx")),
//                pokemonFactory.createPokemon("Vulpix", Arrays.asList("Wand", "Trey"))
//        ));
//
//        System.out.println(trainer1);
//
//        Trainer trainer2 = trainerFactory.createTrainer("Trainer2", 34,Arrays.asList(
//                pokemonFactory.createPokemon("Neutre", Arrays.asList("Cap", "Vitaminsx")),
//                pokemonFactory.createPokemon("Bulbasaur", Arrays.asList("Tree", "Vitamins", "Cap")),
//                pokemonFactory.createPokemon("Vulpix", Arrays.asList("Wand", "Trey"))
//        ));
//
//
//        // Arena
//        Arena arena = Arena.generateArena(trainer1, trainer2);
//        System.out.println(arena);
//
//        Arena arena2 = Arena.generateArena(trainer2, trainer1);
//        System.out.println(arena);

        GenerateTestcases.generate(1);

    }
}
