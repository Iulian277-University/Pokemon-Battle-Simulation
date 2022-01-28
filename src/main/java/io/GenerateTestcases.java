package io;

import common.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public final class GenerateTestcases {
    private GenerateTestcases() {
    }

    // Trainer 1
    private static String trainerName1;
    private static Integer trainerAge1;
    private static List<String> pokemonsOrder1 = new ArrayList<>();
    private static Map<String, List<String>> pokemons1;

    // Trainer 2
    private static String trainerName2;
    private static Integer trainerAge2;
    private static List<String> pokemonsOrder2 = new ArrayList<>();
    private static Map<String, List<String>> pokemons2;

    public static void generate(int numberOfTests) {
        for (int i = 1; i <= numberOfTests; ++i) {
            pokemons1 = setFields(i, 1);
            pokemons2 = setFields(i, 2);
            fieldsToJson(i);
            pokemons1.clear();
            pokemons2.clear();
            pokemonsOrder1.clear();
            pokemonsOrder2.clear();
        }
    }

    private static void fieldsToJson(Integer testIndex) {
        JSONObject trainer1 = new JSONObject();
        trainer1.put("trainerName", trainerName1)
                .put("trainerAge", trainerAge1)
                .put("pokemonsOrder", pokemonsOrder1)
                .put("pokemons", pokemons1);

        JSONObject trainer2 = new JSONObject();
        trainer2.put("trainerName", trainerName2)
                .put("trainerAge", trainerAge2)
                .put("pokemonsOrder", pokemonsOrder2)
                .put("pokemons", pokemons2);

        JSONArray trainers = new JSONArray();
        trainers.put(trainer1);
        trainers.put(trainer2);

        String rootJson = new JSONObject()
                .put("trainers", trainers)
                .toString(Constants.JSON_INDENTATION_FACTOR);

        writeToFile(rootJson, testIndex);
    }

    private static void writeToFile(String rootJson, Integer testIndex) {
        String filePath = Constants.TESTCASES_DIR_PATH + "Testcase_" + testIndex + Constants.TESTCASES_FILE_EXT;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(rootJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, List<String>> setFields(int testIndex, int trainerIndex) {
        // Compute trainer's name
        String trainer = "Trainer_" + testIndex + "_" + trainerIndex;

        // Generate a random age
        int lowerBound = 20;
        int upperBound = 60;
        int age = new Random().nextInt(upperBound - lowerBound) + lowerBound;

        // Generate 3 pokemons
        Map<String, List<String>> pokemonsToReturn = new HashMap<>();
        int pokemonsCounter = 0;
        while (pokemonsCounter < Constants.TRAINER_MAX_POKEMONS) {

            // Generate a random pokemon
            String randomPokemonName;
            randomPokemonName = Constants.Pokemons.values()[new Random().nextInt(Constants.Pokemons.values().length)].toString();
            while (pokemonsToReturn.containsKey(randomPokemonName))
                randomPokemonName = Constants.Pokemons.values()[new Random().nextInt(Constants.Pokemons.values().length)].toString();
            pokemonsCounter++;

            // Generate 3 items for the current pokemon
            List<String> randomItems = new ArrayList<>();
            int counter = 0;
            while (counter < Constants.POKEMON_MAX_ITEMS) {
                String itemName = Constants.Items.values()[new Random().nextInt(Constants.Items.values().length)].toString();
                if (!randomItems.contains(itemName)) {
                    randomItems.add(itemName);
                    counter++;
                }
            }

            // Set fields
            if (trainerIndex == 1) {
                trainerName1 = trainer;
                trainerAge1 = age;
            } else {
                trainerName2 = trainer;
                trainerAge2 = age;
            }
            pokemonsToReturn.put(randomPokemonName, randomItems);
        }
        for (Map.Entry<String, List<String>> entry: pokemonsToReturn.entrySet()) {
            if (trainerIndex == 1)
                pokemonsOrder1.add(entry.getKey());
            else
                pokemonsOrder2.add(entry.getKey());
        }

        return pokemonsToReturn;
    }
}
