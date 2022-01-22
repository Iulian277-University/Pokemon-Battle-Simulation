package main;

import common.PokemonStats;
import entities.*;

import java.util.ArrayList;
import java.util.Arrays;


/** This is the entry point of the program */
public class Main {
    public static void main(String[] args) {

        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();
        System.out.println(pokemonFactory);
//
//        Pokemon pok1 = pokemonFactory.createPokemon("Bulbasaur");
//        System.out.println(pok1.getFirstAbility());
//
//        Pokemon pok2 = pokemonFactory.createPokemon("Bulbasaur");
//        System.out.println(pok2.getFirstAbility());

        Pokemon pok3 = pokemonFactory.createPokemon("Pikachu", Arrays.asList("Cape", "Vitamins"));
        System.out.println(pok3);
    }
}
