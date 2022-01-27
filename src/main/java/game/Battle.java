package game;

import common.Constants;
import entities.Pokemon;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


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

    // TODO: SEMAPHORES

//    private final Semaphore semFirstMove  = new Semaphore(1);
//    private final Semaphore semSecondMove = new Semaphore(0);

//    // Pokemon1 attacks Pokemon2
//    public void firstMove() {
//        checkEndGame();
//        if (battleDone) {
////            semFirstMove.release();
////            semSecondMove.release();
//            return;
//        }
//
//        try {
//            semFirstMove.acquire();
//
//            System.out.print("[" + Thread.currentThread().getId() + "]: ");
//            // Call methods to attack (pok1 -> pok2)
//            Constants.Moves generatedMove = generateRandomMove(pokemon1);
////            attack(pokemon1, pokemon2, generatedMove, false);
//
//            Thread.sleep(10);
//
//            semSecondMove.release();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            Thread.currentThread().interrupt();
//        }
//
//    }
//
//    // Pokemon2 attacks Pokemon1
//    public void secondMove() {
//        checkEndGame();
//        if (battleDone) {
////            semFirstMove.release();
////            semSecondMove.release();
//            return;
//        }
//
//        try {
//            semSecondMove.acquire();
//
//            System.out.print("[" + Thread.currentThread().getId() + "]: ");
//            // Call methods to attack (pok2 -> pok1)
//            Constants.Moves generatedMove = generateRandomMove(pokemon2);
//
////            attack(pokemon2, pokemon1, generatedMove, true);
//
//            // Dodge the defender itself at this moment
//            if (pokemon2.getCurrentMove() == Constants.Moves.ABILITY_1) {
//                if(Boolean.TRUE.equals(pokemon2.getFirstAbility().getDodge())) {
//                    pokemon2.setDodged(true);
//                }
//            } else if (pokemon2.getCurrentMove() == Constants.Moves.ABILITY_2) {
//                if(Boolean.TRUE.equals(pokemon2.getSecondAbility().getDodge())) {
//                    pokemon2.setDodged(true);
//                }
//            }
//
//            // Update HPs (check if dodge)
//            // For now, ignore dodge
////            updateHPs(pokemon1, pokemon2);
//            printHPs(pokemon1, pokemon2);
//
//            // Stun the attacker at the next moment
//            if (pokemon2.getCurrentMove() == Constants.Moves.ABILITY_1) {
//                if(Boolean.TRUE.equals(pokemon2.getFirstAbility().getStun())) {
//                    pokemon1.setStunned(true);
//                }
//            } else if (pokemon2.getCurrentMove() == Constants.Moves.ABILITY_2) {
//                if(Boolean.TRUE.equals(pokemon2.getSecondAbility().getStun())) {
//                    pokemon1.setStunned(true);
//                }
//            }
//
//            Thread.sleep(10);
//
//            semFirstMove.release();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            Thread.currentThread().interrupt();
//        }
//    }

    // TODO: LOCKS

    private Lock lock = new ReentrantLock();
    Condition firstMoveDoneCond  = lock.newCondition();
    Condition secondMoveDoneCond = lock.newCondition();

    private boolean doneFirstMove  = false;
    private boolean doneSecondMove = true;

    // Pokemon1 attacks Pokemon2
    public void firstMove() {
        checkEndGame();
        if (battleDone)
            return;

        try {
            lock.lock();
            while (!doneSecondMove)
                secondMoveDoneCond.await();

            doneFirstMove = false;
            // Call methods to attack (pok1 -> pok2)
            Constants.Moves generatedMove = generateRandomMove(pokemon1);
            attack(pokemon1, pokemon2, generatedMove, false);
            doneFirstMove = true;

            Thread.sleep(10);

            firstMoveDoneCond.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    // Pokemon2 attacks Pokemon1
    public void secondMove() {
        checkEndGame();
        if (battleDone)
            return;

        try {
            lock.lock();
            while (!doneFirstMove)
                firstMoveDoneCond.await();

            doneSecondMove = false;

            // Call methods to attack (pok2 -> pok1)
            Constants.Moves generatedMove = generateRandomMove(pokemon2);
            attack(pokemon2, pokemon1, generatedMove, true);

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
            updateHPs(pokemon1, pokemon2);
            printHPs(pokemon1, pokemon2);

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

            doneSecondMove = true;

            Thread.sleep(10);

            secondMoveDoneCond.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    private void attack(Pokemon attacker, Pokemon defender, Constants.Moves attackerMove, boolean defenderAttacks) {
        switch (attackerMove) {
            case NORMAL_ATTACK -> normalAttack(attacker, defender);
            case SPECIAL_ATTACK -> specialAttack(attacker, defender);
            case ABILITY_1 -> firstAbility(attacker, defender, defenderAttacks);
            case ABILITY_2 -> secondAbility(attacker, defender, defenderAttacks);
            case NOTHING -> nothing(attacker, defender);
        }
    }

    private void normalAttack(Pokemon attacker, Pokemon defender) {
        Attacks.normalAttack(attacker, defender);
    }

    private void specialAttack(Pokemon attacker, Pokemon defender) {
        Attacks.specialAttack(attacker, defender);
    }

    private void firstAbility(Pokemon attacker, Pokemon defender, boolean defenderAttacks) {
        Attacks.firstAbility(attacker, defender, defenderAttacks);
    }

    private void secondAbility(Pokemon attacker, Pokemon defender, boolean defenderAttacks) {
        Attacks.secondAbility(attacker, defender, defenderAttacks);
    }

    private void nothing(Pokemon attacker, Pokemon defender) {
        Attacks.nothing(attacker, defender);
    }

    private void updateHPs(Pokemon pokemon1, Pokemon pokemon2) {
        Constants.Moves currMovePok1 = pokemon1.getCurrentMove();
        Constants.Moves currMovePok2 = pokemon2.getCurrentMove();
        System.out.println(currMovePok1 + " | " + currMovePok2);

        calculateDamage(pokemon1, pokemon2, currMovePok1);
        calculateDamage(pokemon2, pokemon1, currMovePok2);
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
            case NORMAL_ATTACK -> damageToDefender = Math.max(attacker.getAttack() - defender.getDefense(), 0);
            case SPECIAL_ATTACK -> damageToDefender = Math.max(attacker.getSpecialAttack() - defender.getSpecialDefense(), 0);
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
        // Check if countdown of any ability is 0
        // First Ability
        if (pokemon.getFirstAbility() != null && pokemon.getFirstAbility().getCooldown() == 0) {
            pokemon.getFirstAbility().setAvailable(true);
            pokemon.getFirstAbility().setCooldown(pokemon.getFirstAbility().getOriginalCooldown());
        } else {
            if (pokemon.getFirstAbility() != null && !pokemon.getFirstAbility().isAvailable()) {
                pokemon.getFirstAbility().setCooldown(Math.max(pokemon.getFirstAbility().getCooldown() - 1, 0));
            }
        }

        // Second Ability
        if (pokemon.getSecondAbility() != null && pokemon.getSecondAbility().getCooldown() == 0) {
            pokemon.getSecondAbility().setAvailable(true);
            pokemon.getSecondAbility().setCooldown(pokemon.getSecondAbility().getOriginalCooldown());
        } else {
            if (pokemon.getSecondAbility() != null && !pokemon.getSecondAbility().isAvailable()) {
                pokemon.getSecondAbility().setCooldown(Math.max(pokemon.getSecondAbility().getCooldown() - 1, 0));
            }
        }

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

}
