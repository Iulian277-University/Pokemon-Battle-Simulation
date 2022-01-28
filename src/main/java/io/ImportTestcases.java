package io;

import entities.Pokemon;
import entities.PokemonFactory;
import entities.Trainer;
import entities.TrainerFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

public final class ImportTestcases {
    private ImportTestcases() {}

    // Trainer 1
    private static String trainerName1;
    private static Integer trainerAge1;
    private static Map<String, List<String>> pokemons1 = new HashMap<>();

    // Trainer 2
    private static String trainerName2;
    private static Integer trainerAge2;
    private static Map<String, List<String>> pokemons2 = new HashMap<>();

    public static List<Trainer> getTrainers(TrainerFactory trainerFactory, PokemonFactory pokemonFactory, String testcasePath) {
        pokemons1.clear();
        pokemons2.clear();

        String content = jsonToString(testcasePath);
        boolean deserializationDone = stringToObjects(content);
        if (!deserializationDone) {
            System.err.println("Couldn't get the trainers");
            return Collections.emptyList();
        }

        // At this point, the fields are completed
        Trainer firstTrainer   = createTrainer(trainerFactory, pokemonFactory, trainerName1, trainerAge1, pokemons1);
        Trainer secondTrainer  = createTrainer(trainerFactory, pokemonFactory, trainerName2, trainerAge2, pokemons2);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(secondTrainer);
        trainers.add(firstTrainer);

        return trainers;
    }

    private static Trainer createTrainer(TrainerFactory trainerFactory, PokemonFactory pokemonFactory,
                                      String trainerName, Integer trainerAge, Map<String, List<String>> pokemons) {

        List<String> pokemonsName        = new ArrayList<>();
        List<List<String>> pokemonsItems = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry: pokemons.entrySet()) {
            pokemonsName.add(entry.getKey());
            pokemonsItems.add(entry.getValue());
        }

        ArrayList<Pokemon> pokemonsList = new ArrayList<>();
        for (int i = 0; i < pokemonsName.size(); ++i)
            pokemonsList.add(pokemonFactory.createPokemon(pokemonsName.get(i), pokemonsItems.get(i)));

        return trainerFactory.createTrainer(trainerName, trainerAge, pokemonsList);
    }


    private static String jsonToString(String testcasePath) {
        File testcaseFile = new File(testcasePath);

        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(testcaseFile.toURI())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    private static boolean stringToObjects(String content) {
        if (content == null) {
            System.err.println("Couldn't read the json file");
            return false;
        }

        JSONObject jsonContent = new JSONObject(content);
        JSONArray jsonTrainers = jsonContent.getJSONArray("trainers");

        extractFields(jsonTrainers,0);
        extractFields(jsonTrainers,1);
        return true;
    }

    private static void extractFields(JSONArray jsonTrainers, int trainerIndex) {
        JSONObject jsonTrainer = jsonTrainers.getJSONObject(trainerIndex);
        String trainerName = jsonTrainer.getString("trainerName");
        int trainerAge = jsonTrainer.getInt("trainerAge");

        if (trainerIndex == 1) {
            trainerName1 = trainerName;
            trainerAge1 = trainerAge;
        } else {
            trainerName2 = trainerName;
            trainerAge2 = trainerAge;
        }

        JSONObject jsonPokemons = jsonTrainer.getJSONObject("pokemons");
        Iterator<String> pokemonsIter = jsonPokemons.keys();
        while (pokemonsIter.hasNext()) {
            String pokemonName = pokemonsIter.next();
            JSONArray pokemonItems = jsonPokemons.getJSONArray(pokemonName);

            List<String> items = IntStream.range(0, pokemonItems.length())
                    .mapToObj(pokemonItems::getString)
                    .toList();

            if (trainerIndex == 1)
                pokemons1.put(pokemonName, items);
            else
                pokemons2.put(pokemonName, items);
        }
    }
}
