package game;

import entities.Pokemon;
import entities.Trainer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * There is a unique arena in the game
 * Only one game can be played at a time
 */
public final class Arena {
    private Trainer firstTrainer;
    private Trainer secondTrainer;

    // Getters and private setters
    public Trainer getFirstTrainer() {
        return firstTrainer;
    }

    public Trainer getSecondTrainer() {
        return secondTrainer;
    }

    private void setFirstTrainer(Trainer firstTrainer) {
        this.firstTrainer = firstTrainer;
    }

    private void setSecondTrainer(Trainer secondTrainer) {
        this.secondTrainer = secondTrainer;
    }

    // Pattern: Singleton
    private static Arena arena;

    private Arena(Trainer firstTrainer, Trainer secondTrainer) {
        this.firstTrainer = firstTrainer;
        this.secondTrainer = secondTrainer;
    }

    public static Arena generateArena(Trainer firstTrainer, Trainer secondTrainer) {
        if (arena == null)
            arena = new Arena(firstTrainer, secondTrainer);
        // Update trainers if queried
        arena.setFirstTrainer(firstTrainer);
        arena.setSecondTrainer(secondTrainer);
        return arena;
    }

    @Override
    public String toString() {
        return "Arena{" +
                "firstTrainer=" + firstTrainer +
                ", secondTrainer=" + secondTrainer +
                '}';
    }

    // TODO: Arena (game) functionality

    public static void battle(Arena arena) {
        Trainer firstTrainer  = arena.getFirstTrainer();
        Trainer secondTrainer = arena.getSecondTrainer();

        int numberOfPokemonsFirst = firstTrainer.getPokemons().size();
        int numberOfPokemonsSecond = secondTrainer.getPokemons().size();

        for (int i = 0; i < Math.min(numberOfPokemonsFirst, numberOfPokemonsSecond); ++i) {
            Pokemon firstPokemon = firstTrainer.getPokemons().get(i);
            Pokemon secondPokemon = secondTrainer.getPokemons().get(i);

            // Deep copy the pokemons before the individual battle
            // At the end, we need to update the stats of the winner (+1)

            if (i > 0)
                break;

            Battle battle = new Battle(firstPokemon, secondPokemon);
            firstPokemon.setBattle(battle);
            secondPokemon.setBattle(battle);

            ExecutorService executorService = Executors.newFixedThreadPool(2);

            while (firstPokemon.isAlive() && secondPokemon.isAlive()) {
                executorService.execute(firstPokemon);
                executorService.execute(secondPokemon);
            }

            executorService.shutdown();
        }
    }

}
