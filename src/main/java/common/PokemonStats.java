package common;

import entities.Ability;

public final class PokemonStats {
    private PokemonStats() {}

    /**
     * To add a new pokemon, use the following format:
     * public static final <data_type> <pokemonName>_ATTRIBUTE, where ATTRIBUTE can be:
     * NAME, HP,
     * NORMAL_ATTACK, SPECIAL_ATTACK,
     * NORMAL_DEFENSE, SPECIAL_DEFENSE,
     * FIRST_ABILITY, SECOND_ABILITY
     * */

    // Neutrel1
    public static final String  NEUTREL1_NAME = "Neutrel1";
    public static final Integer NEUTREL1_HP = 10;
    public static final Integer NEUTREL1_NORMAL_ATTACK = 3;
    public static final Integer NEUTREL1_SPECIAL_ATTACK = null;
    public static final Integer NEUTREL1_NORMAL_DEFENSE = 1;
    public static final Integer NEUTREL1_SPECIAL_DEFENSE = 1;
    public static final Ability NEUTREL1_FIRST_ABILITY = null;
    public static final Ability NEUTREL1_SECOND_ABILITY = null;

    // Neutrel2
    public static final String  NEUTREL2_NAME = "Neutrel2";
    public static final Integer NEUTREL2_HP = 20;
    public static final Integer NEUTREL2_NORMAL_ATTACK = 4;
    public static final Integer NEUTREL2_SPECIAL_ATTACK = null;
    public static final Integer NEUTREL2_NORMAL_DEFENSE = 1;
    public static final Integer NEUTREL2_SPECIAL_DEFENSE = 1;
    public static final Ability NEUTREL2_FIRST_ABILITY = null;
    public static final Ability NEUTREL2_SECOND_ABILITY = null;

    // Pikachu
    public static final String  PIKACHU_NAME = "Pikachu";
    public static final Integer PIKACHU_HP = 35;
    public static final Integer PIKACHU_NORMAL_ATTACK = null;
    public static final Integer PIKACHU_SPECIAL_ATTACK = 4;
    public static final Integer PIKACHU_NORMAL_DEFENSE = 2;
    public static final Integer PIKACHU_SPECIAL_DEFENSE = 3;
    public static final Ability PIKACHU_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(6).stun(false).dodge(false).cooldown(4).build();
    public static final Ability PIKACHU_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(4).stun(true).dodge(true).cooldown(5).build();

    // Bulbasaur
    public static final String  BULBASAUR_NAME = "Bulbasaur";
    public static final Integer BULBASAUR_HP = 42;
    public static final Integer BULBASAUR_NORMAL_ATTACK = null;
    public static final Integer BULBASAUR_SPECIAL_ATTACK = 5;
    public static final Integer BULBASAUR_NORMAL_DEFENSE = 3;
    public static final Integer BULBASAUR_SPECIAL_DEFENSE = 1;
    public static final Ability BULBASAUR_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(6).stun(false).dodge(false).cooldown(4).build();
    public static final Ability BULBASAUR_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(5).stun(false).dodge(false).cooldown(3).build();

    // Charmander
    public static final String  CHARMANDER_NAME = "Charmander";
    public static final Integer CHARMANDER_HP = 50;
    public static final Integer CHARMANDER_NORMAL_ATTACK = 4;
    public static final Integer CHARMANDER_SPECIAL_ATTACK = null;
    public static final Integer CHARMANDER_NORMAL_DEFENSE = 3;
    public static final Integer CHARMANDER_SPECIAL_DEFENSE = 2;
    public static final Ability CHARMANDER_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(4).stun(true).dodge(false).cooldown(4).build();
    public static final Ability CHARMANDER_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(7).stun(false).dodge(false).cooldown(6).build();

    // Squirtle
    public static final String  SQUIRTLE_NAME = "Squirtle";
    public static final Integer SQUIRTLE_HP = 60;
    public static final Integer SQUIRTLE_NORMAL_ATTACK = null;
    public static final Integer SQUIRTLE_SPECIAL_ATTACK = 3;
    public static final Integer SQUIRTLE_NORMAL_DEFENSE = 5;
    public static final Integer SQUIRTLE_SPECIAL_DEFENSE = 5;
    public static final Ability SQUIRTLE_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(4).stun(false).dodge(false).cooldown(3).build();
    public static final Ability SQUIRTLE_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(2).stun(true).dodge(false).cooldown(2).build();

    // Snorlax
    public static final String  SNORLAX_NAME = "Snorlax";
    public static final Integer SNORLAX_HP = 62;
    public static final Integer SNORLAX_NORMAL_ATTACK = 3;
    public static final Integer SNORLAX_SPECIAL_ATTACK = null;
    public static final Integer SNORLAX_NORMAL_DEFENSE = 6;
    public static final Integer SNORLAX_SPECIAL_DEFENSE = 4;
    public static final Ability SNORLAX_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(4).stun(true).dodge(false).cooldown(5).build();
    public static final Ability SNORLAX_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(0).stun(false).dodge(true).cooldown(5).build();

    // Vulpix
    public static final String  VULPIX_NAME = "Vulpix";
    public static final Integer VULPIX_HP = 36;
    public static final Integer VULPIX_NORMAL_ATTACK = 5;
    public static final Integer VULPIX_SPECIAL_ATTACK = null;
    public static final Integer VULPIX_NORMAL_DEFENSE = 2;
    public static final Integer VULPIX_SPECIAL_DEFENSE = 4;
    public static final Ability VULPIX_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(8).stun(true).dodge(false).cooldown(6).build();
    public static final Ability VULPIX_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(2).stun(false).dodge(true).cooldown(7).build();

    // Eevee
    public static final String  EEVEE_NAME = "Eevee";
    public static final Integer EEVEE_HP = 39;
    public static final Integer EEVEE_NORMAL_ATTACK = null;
    public static final Integer EEVEE_SPECIAL_ATTACK = 4;
    public static final Integer EEVEE_NORMAL_DEFENSE = 3;
    public static final Integer EEVEE_SPECIAL_DEFENSE = 3;
    public static final Ability EEVEE_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(5).stun(false).dodge(false).cooldown(3).build();
    public static final Ability EEVEE_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(3).stun(true).dodge(false).cooldown(3).build();

    // Jigglypuff
    public static final String  JIGGLYPUFF_NAME = "Jigglypuff";
    public static final Integer JIGGLYPUFF_HP = 34;
    public static final Integer JIGGLYPUFF_NORMAL_ATTACK = 4;
    public static final Integer JIGGLYPUFF_SPECIAL_ATTACK = null;
    public static final Integer JIGGLYPUFF_NORMAL_DEFENSE = 2;
    public static final Integer JIGGLYPUFF_SPECIAL_DEFENSE = 3;
    public static final Ability JIGGLYPUFF_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(4).stun(true).dodge(false).cooldown(4).build();
    public static final Ability JIGGLYPUFF_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(3).stun(true).dodge(false).cooldown(4).build();

    // Meowth
    public static final String  MEOWTH_NAME = "Meowth";
    public static final Integer MEOWTH_HP = 41;
    public static final Integer MEOWTH_NORMAL_ATTACK = 3;
    public static final Integer MEOWTH_SPECIAL_ATTACK = null;
    public static final Integer MEOWTH_NORMAL_DEFENSE = 4;
    public static final Integer MEOWTH_SPECIAL_DEFENSE = 2;
    public static final Ability MEOWTH_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(5).stun(false).dodge(true).cooldown(4).build();
    public static final Ability MEOWTH_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(1).stun(false).dodge(true).cooldown(3).build();

    // Psyduck
    public static final String  PSYDUCK_NAME = "Psyduck";
    public static final Integer PSYDUCK_HP = 43;
    public static final Integer PSYDUCK_NORMAL_ATTACK = 3;
    public static final Integer PSYDUCK_SPECIAL_ATTACK = null;
    public static final Integer PSYDUCK_NORMAL_DEFENSE = 3;
    public static final Integer PSYDUCK_SPECIAL_DEFENSE = 3;
    public static final Ability PSYDUCK_FIRST_ABILITY =
            new Ability.AbilityBuilder().damage(2).stun(false).dodge(false).cooldown(4).build();
    public static final Ability PSYDUCK_SECOND_ABILITY =
            new Ability.AbilityBuilder().damage(2).stun(true).dodge(false).cooldown(5).build();

}
