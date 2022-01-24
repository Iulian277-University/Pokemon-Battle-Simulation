package game;

import common.Constants;
import entities.Pokemon;

/** Normal Attack */
public final class NormalAttack {
    private NormalAttack() {}

    // attacker -> defender
    public static void makeMove(Pokemon attacker) {
        attacker.setCurrentMove(Constants.Moves.NORMAL_ATTACK);
        System.out.println(attacker.getName() + " normal attack");
    }

}
