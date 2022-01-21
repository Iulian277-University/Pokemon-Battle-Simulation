package main;

import entities.*;

/**
 * This is the entry point of the program
 */
public class Main {
    public static void main(String[] args) {
        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();
        System.out.println(pokemonFactory);

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


        Trainer trainer = new Trainer.TrainerBuilder("Trainer Name")
                .age(30)
                .addPokemon(new Pokemon.PokemonBuilder("Pokemon1 Name")
                        .HP(30)
                        .firstAbility(new Ability.AbilityBuilder()
                                .damage(5)
                                .cooldown(3)
                                .build())
                        .build())
                .build();

        System.out.println(trainer);
    }
}
