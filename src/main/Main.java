package main;

import common.PokemonStats;
import entities.*;

import java.util.ArrayList;
import java.util.Arrays;


/** This is the entry point of the program */
public class Main {
    public static void main(String[] args) {

        TrainerFactory trainerFactory = TrainerFactory.generateFactory();
        System.out.println(trainerFactory);

        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();
        System.out.println(pokemonFactory);

        Trainer trainer = trainerFactory.createTrainer("Trainer1", 30,Arrays.asList(
                pokemonFactory.createPokemon("Pikachu", Arrays.asList("Cap", "Vitamins")),
                pokemonFactory.createPokemon("Bulbasaur", Arrays.asList("Tree", "Vitaminsx")),
                pokemonFactory.createPokemon("Vulpix", Arrays.asList("Wand", "Trey"))
        ));

        System.out.println(trainer);

    }
}
