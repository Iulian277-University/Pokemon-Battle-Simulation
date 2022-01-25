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
                " | D:" + defender.getDefense() + ")");
    }

    // attacker -> defender
    public static void specialAttack(Pokemon attacker, Pokemon defender) {
        attacker.setCurrentMove(Constants.Moves.SPECIAL_ATTACK);
        System.out.println(attacker.getName() + " special attack " +
                "(A:" + attacker.getSpecialAttack() +
                " | D:" + defender.getSpecialDefense() + ")");
    }

    public static void firstAbility(Pokemon attacker, Pokemon defender) {
        attacker.setCurrentMove(Constants.Moves.ABILITY_1);
        System.out.println(attacker.getName() + " ability 1 " +
                "(A:" + attacker.getFirstAbility().getDamage() +
                " | D:" +  "0)");
    }

    public static void secondAbility(Pokemon attacker, Pokemon defender) {
        attacker.setCurrentMove(Constants.Moves.ABILITY_2);
        System.out.println(attacker.getName() + " ability 2 " +
                "(A:" + attacker.getSecondAbility().getDamage() +
                " | D:" + "0)");
    }

}
