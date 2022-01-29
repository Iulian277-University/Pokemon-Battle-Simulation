package game;

import common.Constants;
import entities.Pokemon;
import entities.Trainer;
import io.Logger;
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
    private static Logger logger;

    // Getters and private setters
    public Trainer getFirstTrainer() {
        return firstTrainer;
    }

    public Trainer getSecondTrainer() {
        return secondTrainer;
    }

    // Pattern: Singleton
    private static Arena arena;
    private Arena() {}

    public static Arena generateArena(Trainer firstTrainer, Trainer secondTrainer, Logger logger) {
        if (arena == null)
            arena = new Arena();
        // Set trainers and logger
        arena.firstTrainer  = firstTrainer;
        arena.secondTrainer = secondTrainer;
        Arena.logger = logger;
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
        Trainer firstTrainer  = arena.getFirstTrainer();
        Trainer secondTrainer = arena.getSecondTrainer();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Original pokemons (deep copy them)
        Pokemon neutrel1Orig = Constants.Neutrel1;
        Pokemon neutrel2Orig = Constants.Neutrel2;

        for (int i = 0; i < Math.min(firstTrainer.getPokemons().size(), secondTrainer.getPokemons().size()); ++i) {
            logger.print("********** POKEMONS INDEX - " + i + " **********");

            // References to the original trainers pokemons
            Pokemon firstPokemonOrig  = firstTrainer.getPokemons().get(i);
            Pokemon secondPokemonOrig = secondTrainer.getPokemons().get(i);

            // Pokemons vs Neutrels
            Constants.Events currEvent = pickRandomEvent();
            while (!currEvent.equals(Constants.Events.VERSUS_OPPONENT)) {
                currEvent = pokemonsVersusNeutrels(
                        executorService,
                        neutrel1Orig, neutrel2Orig,
                        firstPokemonOrig, secondPokemonOrig,
                        currEvent);
            }

            // Final battle: Pokemon1 vs Pokemon2
            finalBattle(executorService, firstPokemonOrig, secondPokemonOrig);
        }

        // [Best pokemon of trainer1] VS [Best pokemon of trainer2]
        int winnerIndex = bestOfTheBest(firstTrainer, secondTrainer, executorService);

        // Print the winner
        battleResults(firstTrainer, secondTrainer, winnerIndex);

        executorService.shutdown();
    }

    private static void battleResults(Trainer firstTrainer, Trainer secondTrainer, int winnerIndex) {
        logger.print("");
        if (winnerIndex == 0)
            logger.print("***** DRAW ****");
        else if (winnerIndex == 1) {
            logger.print("***** WINNER *****");
            logger.print(firstTrainer);
        }
        else if (winnerIndex == 2) {
            logger.print("***** WINNER *****");
            logger.print(secondTrainer);
        }
    }

    private static int bestOfTheBest(Trainer firstTrainer, Trainer secondTrainer, ExecutorService executorService) {
        Pokemon bestPokemonFirstTrainer  = firstTrainer.getPokemons().get(0);
        Pokemon bestPokemonSecondTrainer = secondTrainer.getPokemons().get(0);
        for (int i = 0; i < Math.min(firstTrainer.getPokemons().size(), secondTrainer.getPokemons().size()); ++i) {
            if (firstTrainer.getPokemons().get(i).getScore() > bestPokemonFirstTrainer.getScore())
                bestPokemonFirstTrainer = firstTrainer.getPokemons().get(i);
            if (secondTrainer.getPokemons().get(i).getScore() > bestPokemonSecondTrainer.getScore())
                bestPokemonSecondTrainer = secondTrainer.getPokemons().get(i);
        }

        for (int i = 0; i < 3; ++i) {
            logger.print(firstTrainer.getPokemons().get(i).getScore() + " | " +
                    secondTrainer.getPokemons().get(i).getScore());
        }

        logger.print("*** Best Pokemons - [BEFORE] - Final Battle ***");
        logger.print(bestPokemonFirstTrainer);
        logger.print(bestPokemonSecondTrainer);

        // Best of the best :)
        logger.print("---------- Best1 vs Best2 [START] ----------");
        int winnerIndex = individualBattle(executorService, bestPokemonFirstTrainer, bestPokemonSecondTrainer);
        logger.print("---------- Best1 vs Best2 [DONE] ----------");

        logger.print("*** Best Pokemons - [AFTER] - Final Battle ***");
        logger.print(bestPokemonFirstTrainer);
        logger.print(bestPokemonSecondTrainer);
        return winnerIndex;
    }

    private static void finalBattle(ExecutorService executorService, Pokemon firstPokemonOrig, Pokemon secondPokemonOrig) {
        logger.print(firstPokemonOrig);
        logger.print(secondPokemonOrig);
        logger.print("---------- Pokemon1 vs Pokemon2 [START] ----------");
        individualBattle(executorService, firstPokemonOrig, secondPokemonOrig);
        logger.print(firstPokemonOrig);
        logger.print(secondPokemonOrig);
        logger.print("---------- Pokemon1 vs Pokemon2 [DONE] ----------");
    }

    private static Constants.Events pokemonsVersusNeutrels(ExecutorService executorService,
                                                           Pokemon neutrel1Orig, Pokemon neutrel2Orig,
                                                           Pokemon firstPokemonOrig, Pokemon secondPokemonOrig,
                                                           Constants.Events currEvent) {
        // Battle: pok1 vs neutrel(1/2)
        Pokemon neutrel;
        if (currEvent.equals(Constants.Events.VERSUS_NEUTREL_1))
            neutrel = neutrel1Orig;
        else
            neutrel = neutrel2Orig;

        // Pokemon1 vs Neutrel
        logger.print("---------- Pokemon1 vs Neutrel [START] ----------");
        logger.print(firstPokemonOrig);
        individualBattle(executorService, firstPokemonOrig, neutrel);
        logger.print(firstPokemonOrig);
        logger.print("---------- Pokemon1 vs Neutrel [DONE] ----------");

        // Pokemon2 vs Neutrel
        logger.print("---------- Pokemon2 vs Neutrel [START] ----------");
        logger.print(secondPokemonOrig);
        individualBattle(executorService, secondPokemonOrig, neutrel);
        logger.print(secondPokemonOrig);
        logger.print("---------- Pokemon2 vs Neutrel [DONE] ----------");

        currEvent = pickRandomEvent();
        return currEvent;
    }

    private static int individualBattle(ExecutorService executorService, Pokemon firstPokemonOrig, Pokemon secondPokemonOrig) {
        Pokemon firstPokemon  = DeepCopy.deepCopy(firstPokemonOrig);
        Pokemon secondPokemon = DeepCopy.deepCopy(secondPokemonOrig);
        if (firstPokemon == null || secondPokemon == null) {
            System.err.println(Constants.ERROR_LOG + "Couldn't copy a pokemon");
            return -1;
        }

        Battle battle = Battle.generateBattle(firstPokemon, secondPokemon);
        firstPokemon.setBattle(battle);
        secondPokemon.setBattle(battle);
        Battle.setLogger(logger);

        // Run the battle
        while (firstPokemon.isAlive() && secondPokemon.isAlive()) {
            firstPokemon.run();
            secondPokemon.run();
        }

        // Update winner's stats
        int winnerIndex;
        if (firstPokemon.getHP() > secondPokemon.getHP()) {
            firstPokemonOrig.incrementStats();
            winnerIndex = 1;
        }
        else if (firstPokemon.getHP() < secondPokemon.getHP()) {
            secondPokemonOrig.incrementStats();
            winnerIndex = 2;
        } else
            winnerIndex = 0;

        return winnerIndex;
    }

    private static Constants.Events pickRandomEvent() {
        return Constants.Events.values()[new Random().nextInt(Constants.Events.values().length)];
    }
}