package game;

import common.Constants;
import common.PokemonStats;
import entities.Pokemon;
import entities.Trainer;
import utils.DeepCopy;

import java.util.Random;
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

    // Arena (game) functionality
    public static void battle(Arena arena) {
        Trainer firstTrainer = arena.getFirstTrainer();
        Trainer secondTrainer = arena.getSecondTrainer();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Original pokemons [DeepCopy them]
        Pokemon neutrel1Orig = Constants.Neutrel1;
        Pokemon neutrel2Orig = Constants.Neutrel2;

        if (neutrel1Orig == null || neutrel2Orig == null) {
            System.out.println("Couldn't copy a neutrel");
            return;
        }

        for (int i = 0; i < Math.min(firstTrainer.getPokemons().size(), secondTrainer.getPokemons().size()); ++i) {
            System.out.println("********** POKEMONS INDEX - " + i + " **********");

            // References to the original trainers pokemons
            Pokemon firstPokemonOrig = firstTrainer.getPokemons().get(i);
            Pokemon secondPokemonOrig = secondTrainer.getPokemons().get(i);

            if (firstPokemonOrig == null || secondPokemonOrig == null) {
                System.out.println("Couldn't copy a pokemon");
                return;
            }

            // TODO: Remove DeepCopy functionality

            Constants.Events currEvent = pickRandomEvent();
            while (!currEvent.equals(Constants.Events.VERSUS_OPPONENT)) {
                // Battle: pok1 vs neutrel(1/2)
                Pokemon neutrelOrig;
                if (currEvent.equals(Constants.Events.VERSUS_NEUTREL_1))
                    neutrelOrig = neutrel1Orig;
                else
                    neutrelOrig = neutrel2Orig;

                // pokemon1 vs neutrel
                System.out.println("---------- Pok1 vs Neutrel [START] ----------");
                individualBattle(executorService, firstPokemonOrig, neutrelOrig);
                System.out.println("---------- Pok1 vs Neutrel [DONE] ----------");

                // pokemon2 vs neutrel
                System.out.println("---------- Pok2 vs Neutrel [START] ----------");
                individualBattle(executorService, secondPokemonOrig, neutrelOrig);
                System.out.println("---------- Pok2 vs Neutrel [DONE] ----------");

                currEvent = pickRandomEvent();
            }

            // Final battle: pok1 vs pok2
            System.out.println("---------- Pok1 vs Pok2 [START] ----------");
            individualBattle(executorService, firstPokemonOrig, secondPokemonOrig);
            System.out.println("---------- Pok1 vs Pok2 [END] ----------");
            System.out.println("********************");
        }

        // [Best pokemon of trainer1] VS [Best pokemon of trainer2]
        Pokemon bestPokemonFirstTrainer  = firstTrainer.getPokemons().get(0);
        Pokemon bestPokemonSecondTrainer = secondTrainer.getPokemons().get(0);
        for (int i = 0; i < Math.min(firstTrainer.getPokemons().size(), secondTrainer.getPokemons().size()); ++i) {
            if (firstTrainer.getPokemons().get(i).getScore() > bestPokemonFirstTrainer.getScore())
                bestPokemonFirstTrainer = firstTrainer.getPokemons().get(i);
            if (secondTrainer.getPokemons().get(i).getScore() > bestPokemonSecondTrainer.getScore())
                bestPokemonSecondTrainer = secondTrainer.getPokemons().get(i);
        }

        for (int i = 0; i < 3; ++i) {
            System.out.println(firstTrainer.getPokemons().get(i).getScore() + " | " +
                    secondTrainer.getPokemons().get(i).getScore());
        }

        // TODO: Check unlocking threads after they finish
        // The process gets slower towards the end


        // Best of the best :)
        System.out.println("---------- Best1 vs Best2 [START] ----------");
        individualBattle(executorService, bestPokemonFirstTrainer, bestPokemonSecondTrainer);
        System.out.println("---------- Best1 vs Best2 [END] ----------");

//        System.out.println(bestPokemonFirstTrainer);
//        System.out.println(bestPokemonSecondTrainer);

        executorService.shutdown();
    }

    private static void individualBattle(ExecutorService executorService, Pokemon firstPokemonOrig, Pokemon secondPokemonOrig) {
        assert firstPokemonOrig  != null;
        assert secondPokemonOrig != null;

        Battle battle = Battle.generateBattle(firstPokemonOrig, secondPokemonOrig);
        firstPokemonOrig.setBattle(battle);
        secondPokemonOrig.setBattle(battle);

        while (firstPokemonOrig.isAlive() && secondPokemonOrig.isAlive()) {
            executorService.execute(firstPokemonOrig);
            executorService.execute(secondPokemonOrig);
        }

        // Update winner's stats
        if (firstPokemonOrig.getHP() > secondPokemonOrig.getHP()) {
            firstPokemonOrig.incrementStats(); // increment the winner's stats
            secondPokemonOrig.setHP(secondPokemonOrig.getOriginalHP()); // reset the loser to the original HP
        } else {
            secondPokemonOrig.incrementStats(); // increment the winner's stats
            firstPokemonOrig.setHP(firstPokemonOrig.getOriginalHP()); // reset the loser to the original HP
        }

    }

    private static Constants.Events pickRandomEvent() {
        return Constants.Events.values()[new Random().nextInt(Constants.Events.values().length)];
    }

}
