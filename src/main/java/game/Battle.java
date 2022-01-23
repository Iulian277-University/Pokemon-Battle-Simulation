package game;

import entities.Pokemon;
import entities.Trainer;

public final class Battle {
    private Battle() {}

    public static void battle(Arena arena) {
        Trainer firstTrainer  = arena.getFirstTrainer();
        Trainer secondTrainer = arena.getSecondTrainer();

        int numberOfPokemonsFirst = firstTrainer.getPokemons().size();
        int numberOfPokemonsSecond = secondTrainer.getPokemons().size();

        for (int i = 0; i < Math.min(numberOfPokemonsFirst, numberOfPokemonsSecond); ++i) {
            Pokemon firstPokemon = firstTrainer.getPokemons().get(i);
            Pokemon secondPokemon = secondTrainer.getPokemons().get(i);

            individualBattle(firstPokemon, secondPokemon);
        }

    }

    private static void individualBattle(Pokemon pokemon1, Pokemon pokemon2) {
        // Battle
        // Update stats at the end
    }

}
