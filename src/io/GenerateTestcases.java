package io;

import common.Constants;

import java.util.*;

public final class GenerateTestcases {
    private GenerateTestcases() {}

    enum Pokemons {
        PIKACHU,
        BULBASAUR,
        CHARMANDER,
        SQUIRTLE,
        SNORLAX,
        VULPIX,
        EEVEE,
        JIGGLYPUFF,
        MEOWTH,
        PSYDUCK,
    }

    enum Items {
        SHIELD,
        VEST,
        SWORD,
        WAND,
        VITAMINS,
        TREE,
        CAPE,
    }

    private static String trainerName;
    private static Integer trainerAge;
    private static Map<String, List<String>> pokemons = new HashMap<>();

    public static void generate(int testIndex, int trainerIndex) {
        setFields(testIndex, trainerIndex);
        printFields();
        pokemons.clear();
    }

    private static void setFields(int testIndex, int trainerIndex) {
        // Compute trainer's name
        String trainer = "Trainer_" + testIndex + "_" + trainerIndex;

        // Generate a random age
        int lowerBound = 20;
        int upperBound = 60;
        int age = new Random().nextInt(upperBound - lowerBound) + lowerBound;

        // Set trainer's fields
        trainerName = trainer;
        trainerAge = age;

        // Generate 3 pokemons
        int pokemonsCounter = 0;
        while (pokemonsCounter < Constants.TRAINER_MAX_POKEMONS) {
            // Generate a random pokemon
            String randomPokemonName;
            randomPokemonName = Pokemons.values()[new Random().nextInt(Pokemons.values().length)].toString();
            while (pokemons.containsKey(randomPokemonName))
                randomPokemonName = Pokemons.values()[new Random().nextInt(Pokemons.values().length)].toString();
            pokemonsCounter++;

            // Generate 3 items for the current pokemon
            List<String> randomItems = new ArrayList<>();
            int counter = 0;
            while (counter < Constants.POKEMON_MAX_ITEMS) {
                String itemName = Items.values()[new Random().nextInt(Items.values().length)].toString();
                if (!randomItems.contains(itemName)) {
                    //
                    randomItems.add(itemName);
                    counter++;
                }
            }

            // Set pokemon's fields
            pokemons.put(randomPokemonName, randomItems);
        }
    }

    public static void printFields() {
        System.out.println(trainerName);
        System.out.println(trainerAge);
        System.out.println(pokemons);
    }

}
