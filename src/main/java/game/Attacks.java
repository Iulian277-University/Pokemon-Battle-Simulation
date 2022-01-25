package game;

import common.Constants;
import entities.Pokemon;

public final class Attacks {
    private Attacks() {}

    // attacker -> defender
    public static void normalAttack(Pokemon attacker, Pokemon defender) {
        attacker.setCurrentMove(Constants.Moves.NORMAL_ATTACK);
        System.out.println(attacker.getName() + " normal attack " +
                "(A:" + attacker.getAttack() +
                " | D:" + defender.getDefense() + ")" +
                " [ab1-cooldown:" + (attacker.getFirstAbility() != null ? attacker.getFirstAbility().getCooldown() : "") +
                " | ab2-cooldown:" + (attacker.getSecondAbility() != null ? attacker.getSecondAbility().getCooldown() : "") + "]");
    }

    // attacker -> defender
    public static void specialAttack(Pokemon attacker, Pokemon defender) {
        attacker.setCurrentMove(Constants.Moves.SPECIAL_ATTACK);
        System.out.println(attacker.getName() + " special attack " +
                "(A:" + attacker.getSpecialAttack() +
                " | D:" + defender.getSpecialDefense() + ")" +
                " [ab1-cooldown:" + (attacker.getFirstAbility() != null ? attacker.getFirstAbility().getCooldown() : "") +
                " | ab2-cooldown:" + (attacker.getSecondAbility() != null ? attacker.getSecondAbility().getCooldown() : "") + "]");
    }

    public static void firstAbility(Pokemon attacker, Pokemon defender, boolean defenderAttacks) {
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
        System.out.println(attacker.getName() + " ability 1 " +
                "(A:" + attacker.getFirstAbility().getDamage() +
                " | D:" +  "0)" +
                " [ab1-cooldown:" + (attacker.getFirstAbility() != null ? attacker.getFirstAbility().getCooldown() : "") +
                " | ab2-cooldown:" + (attacker.getSecondAbility() != null ? attacker.getSecondAbility().getCooldown() : "") + "]");
    }

    public static void secondAbility(Pokemon attacker, Pokemon defender, boolean defenderAttacks) {
        attacker.setCurrentMove(Constants.Moves.ABILITY_2);
        attacker.getSecondAbility().setCooldown(attacker.getSecondAbility().getOriginalCooldown() - 1);
        if (Boolean.TRUE.equals(attacker.getSecondAbility().getDodge())) { // if the ability has dodge attribute
            if (!defenderAttacks)
                attacker.setDodged(true);
        }
        if (Boolean.TRUE.equals(attacker.getSecondAbility().getStun())) { // if the ability has stun attribute
            if (!defenderAttacks)
                defender.setStunned(true);
        }
        attacker.getSecondAbility().setAvailable(false);
        System.out.println(attacker.getName() + " ability 2 " +
                "(A:" + attacker.getSecondAbility().getDamage() +
                " | D:" +  "0)" +
                " [ab1-cooldown:" + (attacker.getFirstAbility() != null ? attacker.getFirstAbility().getCooldown() : "") +
                " | ab2-cooldown:" + (attacker.getSecondAbility() != null ? attacker.getSecondAbility().getCooldown() : "") + "]");
    }

    public static void nothing(Pokemon attacker, Pokemon defender) {
        attacker.setCurrentMove(Constants.Moves.NOTHING);
        System.out.println(attacker.getName() + " nothing " +
                "(A:" + attacker.getAttack() +
                " | D:" + defender.getDefense() + ")" +
                " [ab1-cooldown:" + (attacker.getFirstAbility() != null ? attacker.getFirstAbility().getCooldown() : "") +
                " | ab2-cooldown:" + (attacker.getSecondAbility() != null ? attacker.getSecondAbility().getCooldown() : "") + "]");
    }
}
