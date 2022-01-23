package main;

import entities.*;
import game.Arena;
import game.Battle;
import io.ImportTestcases;

import java.util.List;

/** This is the entry point of the program */
public class Main {
    public static void main(String[] args) {

        TrainerFactory trainerFactory = TrainerFactory.generateFactory();
        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();

        // TODO: Get number of testcases (number of .json files in the ./testcases dir)


//         GenerateTestcases.generate(10);


        List<Trainer> trainers = ImportTestcases.getTrainers(trainerFactory, pokemonFactory);
        if (trainers.isEmpty()) {
            System.out.println("Couldn't get the trainers");
            return;
        }

        Trainer firstTrainer  = trainers.get(0);
        Trainer secondTrainer = trainers.get(1);

        Arena arena = Arena.generateArena(firstTrainer, secondTrainer);
        System.out.println(arena);
        Arena.battle(arena);




//        System.out.println(firstTrainer);
//        System.out.println(secondTrainer);

//        Order is preserved
//        System.out.println(firstTrainer.getPokemonsOrder());
//        for(Pokemon pokemon: firstTrainer.getPokemons()) {
//            System.out.print(pokemon.getName() + "; ");
//        }
//        System.out.println();
//        System.out.println(secondTrainer.getPokemonsOrder());
//        for(Pokemon pokemon: secondTrainer.getPokemons()) {
//            System.out.print(pokemon.getName() + "; ");
//        }

        //

    }
}
