package game;

import common.Constants;
import entities.Pokemon;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;


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

    private boolean battleDone = false;

    private final Semaphore semFirstMove  = new Semaphore(1);
    private final Semaphore semSecondMove = new Semaphore(0);

    // Pokemon1 attacks Pokemon2
    public void firstMove() {
        checkEndGame();
        if (battleDone)
            return;

        try {
            semFirstMove.acquire();

            System.out.print("[" + Thread.currentThread().getId() + "]: ");
            // Call methods to attack (pok1 -> pok2)
            Constants.Moves generatedMove = generateRandomMove(pokemon1);
            attack(pokemon1, pokemon2, generatedMove);

            Thread.sleep(10);

            semSecondMove.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

    // Pokemon2 attacks Pokemon1
    public void secondMove() {
        checkEndGame();
        if (battleDone)
            return;

        try {
            semSecondMove.acquire();

            System.out.print("[" + Thread.currentThread().getId() + "]: ");
            // Call methods to attack (pok2 -> pok1)
            Constants.Moves generatedMove = generateRandomMove(pokemon2);
            attack(pokemon2, pokemon1, generatedMove);

            // Update HPs (check if dodge)
            // For now, ignore dodge
            updateHPs(pokemon1, pokemon2);
            printHPs(pokemon1, pokemon2);

            Thread.sleep(10);

            semFirstMove.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void attack(Pokemon attacker, Pokemon defender, Constants.Moves attackerMove) {
        switch (attackerMove) {
            case NORMAL_ATTACK -> normalAttack(attacker, defender);
            case SPECIAL_ATTACK -> specialAttack(attacker, defender);
            case ABILITY_1 -> firstAbility(attacker, defender);
            case ABILITY_2 -> secondAbility(attacker, defender);
        }
    }

    private void normalAttack(Pokemon attacker, Pokemon defender) {
        Attacks.normalAttack(attacker, defender);
    }

    private void specialAttack(Pokemon attacker, Pokemon defender) {
        Attacks.specialAttack(attacker, defender);
    }

    private void firstAbility(Pokemon attacker, Pokemon defender) {
        Attacks.firstAbility(attacker, defender);
    }

    private void secondAbility(Pokemon attacker, Pokemon defender) {
        Attacks.secondAbility(attacker, defender);
    }

    private void updateHPs(Pokemon pokemon1, Pokemon pokemon2) {
        Constants.Moves currMovePok1 = pokemon1.getCurrentMove();
        Constants.Moves currMovePok2 = pokemon2.getCurrentMove();
        System.out.println(currMovePok1 + " | " + currMovePok2);

        calculateDamage(pokemon1, pokemon2, currMovePok1);
        calculateDamage(pokemon2, pokemon1, currMovePok2);
    }

    private void calculateDamage(Pokemon attacker, Pokemon defender, Constants.Moves currMoveAtacker) {
        int damageToDefender = 0;
        switch (currMoveAtacker) {
            case NORMAL_ATTACK -> damageToDefender = attacker.getAttack() - defender.getDefense();
            case SPECIAL_ATTACK -> damageToDefender = attacker.getSpecialAttack() - defender.getSpecialDefense();
            case ABILITY_1 -> damageToDefender = attacker.getFirstAbility().getDamage();
            case ABILITY_2 -> damageToDefender = attacker.getSecondAbility().getDamage();
        }
        defender.setHP(Math.max(defender.getHP() - damageToDefender, 0));
    }


    private void printHPs(Pokemon pokemon1, Pokemon pokemon2) {
        System.out.print(pokemon1.getName() + " " + pokemon1.getHP() + "HP |");
        System.out.println(" " + pokemon2.getName() + " " + pokemon2.getHP() + "HP");
        System.out.println("---");
    }

    private void checkEndGame() {
        if(pokemon1.isDead() || pokemon2.isDead())
            battleDone = true;
    }

    private Constants.Moves generateRandomMove(Pokemon pokemon) {
        List<Constants.Moves> pokemonAvailableMoves = new ArrayList<>();
        if (pokemon.getAttack() != null)
            pokemonAvailableMoves.add(Constants.Moves.NORMAL_ATTACK);
        if (pokemon.getSpecialAttack() != null)
            pokemonAvailableMoves.add(Constants.Moves.SPECIAL_ATTACK);
        if (pokemon.getFirstAbility() != null)
            pokemonAvailableMoves.add(Constants.Moves.ABILITY_1);
        if (pokemon.getSecondAbility() != null)
            pokemonAvailableMoves.add(Constants.Moves.ABILITY_2);

        Constants.Moves randomMove = Constants.Moves.values()[new Random().nextInt(Constants.Moves.values().length)];
        while (!pokemonAvailableMoves.contains(randomMove)) {
            randomMove = Constants.Moves.values()[new Random().nextInt(Constants.Moves.values().length)];
        }

        return randomMove;
    }

}
