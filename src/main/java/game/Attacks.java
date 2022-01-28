package game;

import common.Constants;
import entities.Pokemon;

public final class Attacks {
    private Attacks() {}

    private static String info(Pokemon attacker, Pokemon defender, Constants.Moves attackType) {
        int attackHP = 0;
        int defenseHP = 0;
        switch (attackType) {
            case NORMAL_ATTACK -> {
                attackHP  = attacker.getAttack();
                defenseHP = defender.getDefense();
            }
            case SPECIAL_ATTACK -> {
                attackHP  = attacker.getSpecialAttack();
                defenseHP = defender.getSpecialDefense();
            }
            case ABILITY_1 -> attackHP  = attacker.getFirstAbility().getDamage();
            case ABILITY_2 -> attackHP  = attacker.getSecondAbility().getDamage();
        }

        return attacker.getName() + " " + attackType +
                " (A:"  + attackHP  +
                " | D:" + defenseHP + ")" +
                " [ab1-cooldown:"  + (attacker.getFirstAbility()  != null ? attacker.getFirstAbility().getCooldown()  : "") +
                " | ab2-cooldown:" + (attacker.getSecondAbility() != null ? attacker.getSecondAbility().getCooldown() : "") +
                "]";
    }

    public static String normalAttack(Pokemon attacker, Pokemon defender) {
        attacker.setCurrentMove(Constants.Moves.NORMAL_ATTACK);
        return info(attacker, defender, Constants.Moves.NORMAL_ATTACK);
    }

    public static String specialAttack(Pokemon attacker, Pokemon defender) {
        attacker.setCurrentMove(Constants.Moves.SPECIAL_ATTACK);
        return info(attacker, defender, Constants.Moves.SPECIAL_ATTACK);
    }


    public static String firstAbility(Pokemon attacker, Pokemon defender, boolean defenderAttacks) {
        attacker.setCurrentMove(Constants.Moves.ABILITY_1);
        attacker.getFirstAbility().setCooldown(attacker.getFirstAbility().getOriginalCooldown() - 1);
        if (Boolean.TRUE.equals(attacker.getFirstAbility().getDodge())) { // if the ability has dodge attribute
            if (!defenderAttacks)
                attacker.setDodged(true);
        } else {
            if (defenderAttacks)
                attacker.setDodged(false);
        }

        if (Boolean.TRUE.equals(attacker.getFirstAbility().getStun())) { // if the ability has stun attribute
            if (!defenderAttacks)
                defender.setStunned(true);
        } else {
            if (defenderAttacks)
                attacker.setDodged(false);
        }

        attacker.getFirstAbility().setAvailable(false);
        return info(attacker, defender, Constants.Moves.ABILITY_1);
    }

    public static String secondAbility(Pokemon attacker, Pokemon defender, boolean defenderAttacks) {
        attacker.setCurrentMove(Constants.Moves.ABILITY_2);
        attacker.getSecondAbility().setCooldown(attacker.getSecondAbility().getOriginalCooldown() - 1);
        if (Boolean.TRUE.equals(attacker.getSecondAbility().getDodge())) { // if the ability has dodge attribute
            if (!defenderAttacks) {
                attacker.setDodged(true);
            }
        }
        if (Boolean.TRUE.equals(attacker.getSecondAbility().getStun())) { // if the ability has stun attribute
            if (!defenderAttacks)
                defender.setStunned(true);
        }
        attacker.getSecondAbility().setAvailable(false);
        return info(attacker, defender, Constants.Moves.ABILITY_2);

    }

    public static String nothing(Pokemon attacker, Pokemon defender) {
        attacker.setCurrentMove(Constants.Moves.NOTHING);
        return info(attacker, defender, Constants.Moves.NOTHING);

    }
}
