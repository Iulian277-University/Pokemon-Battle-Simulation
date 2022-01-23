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

//        Trainer trainer1 = trainerFactory.createTrainer("Trainer1", 30,Arrays.asList(
//                pokemonFactory.createPokemon("Pikachu", Arrays.asList("Cap", "Vitamins")),
//                pokemonFactory.createPokemon("Bulbasaur", Arrays.asList("Tree", "Vitaminsx")),
//                pokemonFactory.createPokemon("Vulpix", Arrays.asList("Wand", "Trey"))
//        ));
//
//        System.out.println(trainer1);

//        Trainer trainer2 = trainerFactory.createTrainer("Trainer2", 34, Arrays.asList(
//                pokemonFactory.createPokemon("Neutre", Arrays.asList("Cap", "Vitaminsx")),
//                pokemonFactory.createPokemon("Bulbasaur", Arrays.asList("Tree", "Vitamins", "Cap")),
//                pokemonFactory.createPokemon("Vulpix", Arrays.asList("Wand", "Trey"))
//        ));

        // Arena
//        Arena arena = Arena.generateArena(trainer1, trainer2);
//        System.out.println(arena);

        // TODO: Get number of testcases (number of .json files in the ./testcases dir)

        //
//        ImportTestcases.getTrainers();

//        Trainer trainer1 = trainerFactory.createTrainer("Trainer1", 30,Arrays.asList(
//                pokemonFactory.createPokemon("Pikachu", Arrays.asList("Cap", "Vitamins")),
//                pokemonFactory.createPokemon("Bulbasaur", Arrays.asList("Tree", "Vitaminsx")),
//                pokemonFactory.createPokemon("Vulpix", Arrays.asList("Wand", "Trey"))
//        ));
//
//        System.out.println(trainer1);
//
//        Trainer trainer2 = trainerFactory.createTrainer("Trainer2", 34, Arrays.asList(
//                pokemonFactory.createPokemon("Neutre", Arrays.asList("Cap", "Vitaminsx")),
//                pokemonFactory.createPokemon("Bulbasaur", Arrays.asList("Tree", "Vitamins", "Cap")),
//                pokemonFactory.createPokemon("Vulpix", Arrays.asList("Wand", "Trey"))
//        ));


//        GenerateTestcases.generate(10);


        List<Trainer> trainers = ImportTestcases.getTrainers(trainerFactory, pokemonFactory);
        if (trainers.isEmpty()) {
            System.out.println("Couldn't get the trainers");
            return;
        }

        Trainer firstTrainer = trainers.get(0);
        Trainer secondTrainer = trainers.get(1);

        System.out.println(firstTrainer);
        System.out.println("-----");
        System.out.println(secondTrainer);

    }
}
