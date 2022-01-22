package main;

import entities.*;


/** This is the entry point of the program */
public class Main {
    public static void main(String[] args) {

        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();
        System.out.println(pokemonFactory);

        Pokemon pok = pokemonFactory.createPokemon("Bulbasaur");
        System.out.println(pok);
    }
}
