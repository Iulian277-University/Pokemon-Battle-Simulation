package game;

import common.Constants;
import entities.Ability;
import entities.Pokemon;
import io.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used for setting a battle with 2 pokemons
 * It contains some helper methods for updating the stats of the pokemons
 */
public final class Battle {
    private Pokemon pokemon1;
    private Pokemon pokemon2;

    // Pattern: Singleton
    private static Battle battle;
    public Battle() {}
    public static Battle generateBattle(Pokemon pokemon1, Pokemon pokemon2) {
        if (battle == null) {
            battle = new Battle(pokemon1, pokemon2);
        }
        // Update pokemons if queried
        battle.setPokemon1(pokemon1);
        battle.setPokemon2(pokemon2);
        pokemon1.isAttacker(true);
        return battle;
    }

    private Battle(Pokemon pokemon1, Pokemon pokemon2) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.pokemon1.isAttacker(true);
    }

    public void setPokemon1(Pokemon pokemon1) {
        this.pokemon1 = pokemon1;
    }
    public void setPokemon2(Pokemon pokemon2) {
        this.pokemon2 = pokemon2;
    }

    private static Logger logger;
    public static void setLogger(Logger logger) {
        Battle.logger = logger;
    }

    private boolean battleDone = false;

    // Pokemon1 attacks Pokemon2
    public void firstMove(Constants.Moves move) {
        checkEndGame();
        if (battleDone)
            return;

        // Call methods to attack (pok1 -> pok2)
        attack(pokemon1, pokemon2, move, false);
    }

    // Pokemon2 attacks Pokemon1
    public void secondMove(Constants.Moves move) {
        checkEndGame();
        if (battleDone)
            return;

        // Call methods to attack (pok2 -> pok1)
        attack(pokemon2, pokemon1, move, true);

        // Dodge the defender itself at this moment
        if (pokemon2.getCurrentMove() == Constants.Moves.ABILITY_1) {
            if(Boolean.TRUE.equals(pokemon2.getFirstAbility().getDodge())) {
                pokemon2.setDodged(true);
            }
        } else if (pokemon2.getCurrentMove() == Constants.Moves.ABILITY_2) {
            if(Boolean.TRUE.equals(pokemon2.getSecondAbility().getDodge())) {
                pokemon2.setDodged(true);
            }
        }

        // Update HPs
        logger.print(updateHPs(pokemon1, pokemon2));
        logger.print(displayHPs(pokemon1, pokemon2));

        // Stun the attacker at the next moment
        if (pokemon2.getCurrentMove() == Constants.Moves.ABILITY_1) {
            if(Boolean.TRUE.equals(pokemon2.getFirstAbility().getStun())) {
                pokemon1.setStunned(true);
            }
        } else if (pokemon2.getCurrentMove() == Constants.Moves.ABILITY_2) {
            if(Boolean.TRUE.equals(pokemon2.getSecondAbility().getStun())) {
                pokemon1.setStunned(true);
            }
        }
    }


    private void attack(Pokemon attacker, Pokemon defender, Constants.Moves attackerMove, boolean defenderAttacks) {
        switch (attackerMove) {
            case NORMAL_ATTACK  -> logger.print(Attacks.normalAttack(attacker, defender));
            case SPECIAL_ATTACK -> logger.print(Attacks.specialAttack(attacker, defender));
            case ABILITY_1      -> logger.print(Attacks.firstAbility(attacker, defender, defenderAttacks));
            case ABILITY_2      -> logger.print(Attacks.secondAbility(attacker, defender, defenderAttacks));
            case NOTHING        -> logger.print(Attacks.nothing(attacker, defender));
        }
    }

    private String updateHPs(Pokemon pokemon1, Pokemon pokemon2) {
        Constants.Moves currMovePok1 = pokemon1.getCurrentMove();
        Constants.Moves currMovePok2 = pokemon2.getCurrentMove();
        String currMoves = currMovePok1 + " | " + currMovePok2;

        calculateDamage(pokemon1, pokemon2, currMovePok1);
        calculateDamage(pokemon2, pokemon1, currMovePok2);

        return currMoves;
    }

    private void calculateDamage(Pokemon attacker, Pokemon defender, Constants.Moves currMoveAttacker) {
        if (defender.isDodged()) {
            defender.setDodged(false);
            return;
        }

        if (attacker.isStunned())
            attacker.setStunned(false);

        int damageToDefender = 0;
        switch (currMoveAttacker) {
            case NORMAL_ATTACK  -> damageToDefender = Math.max(attacker.getAttack() - defender.getDefense(), 0);
            case SPECIAL_ATTACK -> damageToDefender = Math.max(attacker.getSpecialAttack() - defender.getSpecialDefense(), 0);
            case ABILITY_1      -> damageToDefender = attacker.getFirstAbility().getDamage();
            case ABILITY_2      -> damageToDefender = attacker.getSecondAbility().getDamage();
        }
        defender.setHP(Math.max(defender.getHP() - damageToDefender, 0));
    }


    private String displayHPs(Pokemon pokemon1, Pokemon pokemon2) {
        String outputHPs = "";
        outputHPs += pokemon1.getName() + " " + pokemon1.getHP() + "HP |";
        outputHPs += " " + pokemon2.getName() + " " + pokemon2.getHP() + "HP\n";
        outputHPs += "---";
        return outputHPs;
    }

    private void checkEndGame() {
        if(pokemon1.isDead() || pokemon2.isDead())
            battleDone = true;
    }

    public Constants.Moves generateRandomMove(Pokemon pokemon) {
        // Check if countdown of any ability is 0
        countDownAbility(pokemon.getFirstAbility());
        countDownAbility(pokemon.getSecondAbility());

        // Check if stunned
        if (pokemon.isStunned())
            return Constants.Moves.NOTHING;

        List<Constants.Moves> pokemonAvailableMoves = new ArrayList<>();
        if (pokemon.getAttack() != null)
            pokemonAvailableMoves.add(Constants.Moves.NORMAL_ATTACK);
        if (pokemon.getSpecialAttack() != null)
            pokemonAvailableMoves.add(Constants.Moves.SPECIAL_ATTACK);
        if (pokemon.getFirstAbility() != null && pokemon.getFirstAbility().isAvailable())
            pokemonAvailableMoves.add(Constants.Moves.ABILITY_1);
        if (pokemon.getSecondAbility() != null && pokemon.getSecondAbility().isAvailable())
            pokemonAvailableMoves.add(Constants.Moves.ABILITY_2);

        Constants.Moves randomMove = Constants.Moves.values()[new Random().nextInt(Constants.Moves.values().length)];
        while (!pokemonAvailableMoves.contains(randomMove)) {
            randomMove = Constants.Moves.values()[new Random().nextInt(Constants.Moves.values().length)];
        }

        return randomMove;
    }

    private void countDownAbility(Ability ability) {
        if (ability != null && ability.getCooldown() == 0) {
            ability.setAvailable(true);
            ability.setCooldown(ability.getOriginalCooldown());
        } else {
            if (ability != null && !ability.isAvailable()) {
                ability.setCooldown(Math.max(ability.getCooldown() - 1, 0));
            }
        }
    }
}
