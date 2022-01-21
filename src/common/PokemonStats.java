package common;

import entities.Ability;

public final class PokemonStats {

    public static final String  PIKACHU_NAME = "Pikachu";
    public static final Integer PIKACHU_HP = 35;
    public static final Integer PIKACHU_NORMAL_ATTACK = null;
    public static final Integer PIKACHU_SPECIAL_ATTACK = 4;
    public static final Integer PIKACHU_NORMAL_DEFENSE = 2;
    public static final Integer PIKACHU_SPECIAL_DEFENSE = 3;
    public static final Ability PIKACHU_FIRST_ABILITY = new Ability.AbilityBuilder().damage(6).stun(false).dodge(false).cooldown(4).build();
    public static final Ability PIKACHU_SECOND_ABILITY = new Ability.AbilityBuilder().damage(4).stun(true).dodge(true).cooldown(5).build();

    // TODO: Populate for all the types
}
