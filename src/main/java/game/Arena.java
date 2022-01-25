package game;

import entities.Pokemon;
import entities.Trainer;
import utils.DeepCopy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    // Arena (game) functionality
    public static void battle(Arena arena) {
        Trainer firstTrainer = arena.getFirstTrainer();
        Trainer secondTrainer = arena.getSecondTrainer();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        System.out.println("-----");
        // Make copies of the original pokemons in order to be able to add +1 to the winner
        // At the end, we need to update the stats of the winner (+1)
        Pokemon firstPokemon  = DeepCopy.deepCopy(firstTrainer.getPokemons().get(0));
        Pokemon secondPokemon = DeepCopy.deepCopy(secondTrainer.getPokemons().get(0));

        if (firstPokemon == null || secondPokemon == null) {
            System.out.println("Couldn't copy a pokemon");
            return;
        }

        Battle battle = Battle.generateBattle(firstPokemon, secondPokemon);
        firstPokemon.setBattle(battle);
        secondPokemon.setBattle(battle);

        while (firstPokemon.isAlive() && secondPokemon.isAlive()) {
            executorService.execute(firstPokemon);
            executorService.execute(secondPokemon);
        }

        System.out.println(firstPokemon.getName() + ":" + firstPokemon.getHP());
        System.out.println(secondPokemon.getName() + ":" + secondPokemon.getHP());
        System.out.println("-----");

        executorService.shutdown();

        System.out.println(firstPokemon.getHP());
        System.out.println(secondPokemon.getHP());
    }
}
