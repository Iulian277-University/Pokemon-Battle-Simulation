package common;

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
}
