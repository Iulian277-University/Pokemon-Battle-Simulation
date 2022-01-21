package main;

import common.PokemonStats;
import entities.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the entry point of the program
 */
public class Main {
    public static void main(String[] args) {

//        Pokemon pokemon1 = new Pokemon.PokemonBuilder("Nume puchemon")
//                .HP(50)
//                .attack(2)
//                .firstAbility(new Ability.AbilityBuilder()
//                        .damage(5)
//                        .cooldown(10)
//                        .build())
//                .build();
//
//        Item item1 = new Item.ItemBuilder("Nume aitam")
//                .HP(10)
//                .build();
//
//        System.out.println(pokemon1);
//        System.out.println(item1);


//        Trainer trainer = new Trainer.TrainerBuilder("Trainer Name")
//                .age(30)
//                .addPokemon(new Pokemon.PokemonBuilder("Pokemon1 Name")
//                        .HP(30)
//                        .firstAbility(new Ability.AbilityBuilder()
//                                .damage(5)
//                                .cooldown(3)
//                                .build())
//                        .build())
//                .build();
//
//        System.out.println(trainer.getPokemons().get(0).getFirstAbility());


        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();
        System.out.println(pokemonFactory);

        Pokemon pok = pokemonFactory.createPokemon("Pikachu");
        System.out.println(pok);
    }
}
