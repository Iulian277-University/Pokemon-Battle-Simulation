package game;

import common.Constants;
import entities.Pokemon;
import entities.Trainer;
import logger.Logger;
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

        // Original pokemons (deep copy them)
        Pokemon neutrel1Orig = Constants.Neutrel1;
        Pokemon neutrel2Orig = Constants.Neutrel2;

        StringBuilder outputBuffer = Logger.getOutputBuffer();

        for (int i = 0; i < Math.min(firstTrainer.getPokemons().size(), secondTrainer.getPokemons().size()); ++i) {
            outputBuffer.append("\n********** POKEMONS INDEX - ").append(i).append(" **********\n");

            // References to the original trainers pokemons
            Pokemon firstPokemonOrig  = firstTrainer.getPokemons().get(i);
            Pokemon secondPokemonOrig = secondTrainer.getPokemons().get(i);

            Constants.Events currEvent = pickRandomEvent();
            while (!currEvent.equals(Constants.Events.VERSUS_OPPONENT)) {
                // Battle: pok1 vs neutrel(1/2)
                Pokemon neutrel;
                if (currEvent.equals(Constants.Events.VERSUS_NEUTREL_1))
                    neutrel = neutrel1Orig;
                else
                    neutrel = neutrel2Orig;

                // pokemon1 vs neutrel
                outputBuffer.append("---------- Pokemon1 vs Neutrel [START] ----------\n");
                individualBattle(executorService, firstPokemonOrig, neutrel);
                outputBuffer.append("---------- Pokemon1 vs Neutrel [DONE] ----------\n");

                // pokemon2 vs neutrel
                outputBuffer.append("---------- Pokemon2 vs Neutrel [START] ----------\n");
                individualBattle(executorService, secondPokemonOrig, neutrel);
                outputBuffer.append("---------- Pokemon2 vs Neutrel [DONE] ----------\n");

                currEvent = pickRandomEvent();
            }

            // Final battle: pok1 vs pok2
            outputBuffer.append("---------- Pokemon1 vs Pokemon2 [START] ----------\n");
            individualBattle(executorService, firstPokemonOrig, secondPokemonOrig);
            outputBuffer.append("---------- Pokemon1 vs Pokemon2 [END] ----------\n");
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
            outputBuffer
                    .append(firstTrainer.getPokemons().get(i).getScore())
                    .append(" | ")
                    .append(secondTrainer.getPokemons().get(i).getScore())
                    .append("\n");
        }

        outputBuffer.append("*** Best Pokemons - [BEFORE] - Final Battle ***\n");
        outputBuffer.append(bestPokemonFirstTrainer).append("\n");
        outputBuffer.append(bestPokemonSecondTrainer).append("\n");

        // Best of the best :)
        outputBuffer.append("---------- Best1 vs Best2 [START] ----------\n");
        int winnerIndex = individualBattle(executorService, bestPokemonFirstTrainer, bestPokemonSecondTrainer);
        outputBuffer.append("---------- Best1 vs Best2 [END] ----------\n");

        outputBuffer.append("*** Best Pokemons - [AFTER] - Final Battle ***\n");
        outputBuffer.append(bestPokemonFirstTrainer);
        outputBuffer.append(bestPokemonSecondTrainer);

        outputBuffer.append("\n");
        if (winnerIndex == 0)
            outputBuffer.append("***** DRAW ****\n");
        else if (winnerIndex == 1)
            outputBuffer.append("***** WINNER: ").append(firstTrainer).append(" *****").append("\n");
        else if (winnerIndex == 2)
            outputBuffer.append("***** WINNER: ").append(secondTrainer).append(" *****").append("\n");

        executorService.shutdown();
    }

    private static int individualBattle(ExecutorService executorService, Pokemon firstPokemonOrig, Pokemon secondPokemonOrig) {
        Pokemon firstPokemon  = DeepCopy.deepCopy(firstPokemonOrig);
        Pokemon secondPokemon = DeepCopy.deepCopy(secondPokemonOrig);
        assert firstPokemon  != null;
        assert secondPokemon != null;

        Battle battle = Battle.generateBattle(firstPokemon, secondPokemon);
        firstPokemon.setBattle(battle);
        secondPokemon.setBattle(battle);

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
