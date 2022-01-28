package entities;

import common.Constants;

import java.util.List;

public final class TrainerFactory {
    // Pattern: Singleton
    private static TrainerFactory factory;
    public TrainerFactory() {}
    public static TrainerFactory generateFactory() {
        if(factory == null)
            factory = new TrainerFactory();
        return factory;
    }

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
