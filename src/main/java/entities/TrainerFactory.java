package entities;

import common.Constants;

import java.util.List;

/**
 * This class is used for instantiating a trainer
 * It allows you to sequentially add attributes
 */
public final class TrainerFactory {
    // Pattern: Singleton
    private static TrainerFactory factory;
    private TrainerFactory() {}
    public static TrainerFactory generateFactory() {
        if(factory == null)
            factory = new TrainerFactory();
        return factory;
    }

    // Pattern: Factory
    public Trainer createTrainer(String trainerName, Integer trainerAge, List<Pokemon> pokemons) {
        if (trainerName == null || trainerName.isEmpty()) {
            System.err.println(Constants.ERROR_LOG + "A trainer must have a name");
            return null;
        }
        if (pokemons == null || pokemons.isEmpty() || pokemons.size() > Constants.TRAINER_MAX_POKEMONS) {
            System.err.println(Constants.ERROR_LOG + "A trainer must have maximum " + Constants.TRAINER_MAX_POKEMONS + " pokemons");
            return null;
        }

        Trainer.TrainerBuilder trainerBuilder = new Trainer.TrainerBuilder(trainerName).age(trainerAge);
        for (Pokemon pokemon: pokemons) {
            trainerBuilder.addPokemon(pokemon);
        }

        return trainerBuilder.build();
    }
}
