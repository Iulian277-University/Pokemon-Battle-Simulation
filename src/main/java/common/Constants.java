package common;

import entities.Pokemon;

// TODO: Delete asserts from the entire project

public final class Constants {
    private Constants() {}

    public static final Integer TRAINER_MAX_POKEMONS = 3;
    public static final Integer POKEMON_MAX_ITEMS = 3;

    public static final String  TESTCASES_DIR_PATH = "./testcases/";
    public static final Integer JSON_INDENTATION_FACTOR = 4;

    public enum Moves {
        NORMAL_ATTACK,
        SPECIAL_ATTACK,
        ABILITY_1,
        ABILITY_2,
        NOTHING,
    }

    public enum Events {
        VERSUS_NEUTREL_1,
        VERSUS_NEUTREL_2,
        VERSUS_OPPONENT,
    }

    public static final Pokemon Neutrel1 = new Pokemon.PokemonBuilder(PokemonStats.NEUTREL1_NAME)
            .HP(PokemonStats.NEUTREL1_HP)
            .attack(PokemonStats.NEUTREL1_NORMAL_ATTACK)
            .defense(PokemonStats.NEUTREL1_NORMAL_DEFENSE)
            .specialDefense(PokemonStats.NEUTREL1_SPECIAL_DEFENSE)
            .build();

    public static final Pokemon Neutrel2 = new Pokemon.PokemonBuilder(PokemonStats.NEUTREL2_NAME)
            .HP(PokemonStats.NEUTREL2_HP)
            .attack(PokemonStats.NEUTREL2_NORMAL_ATTACK)
            .defense(PokemonStats.NEUTREL2_NORMAL_DEFENSE)
            .specialDefense(PokemonStats.NEUTREL2_SPECIAL_DEFENSE)
            .build();

}
