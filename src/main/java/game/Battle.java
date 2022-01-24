package game;

import entities.Pokemon;
import java.lang.Thread;

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
        if (battleDone) {
            return;
        }

        try {
            semFirstMove.acquire();

            System.out.print("[" + Thread.currentThread().getId() + "]: ");
            // Call methods to attack (pok1 -> pok2)
            normalAttack(pokemon1);


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
        if (battleDone) {
            return;
        }

        try {
            semSecondMove.acquire();

            System.out.print("[" + Thread.currentThread().getId() + "]: ");
            // Call methods to attack (pok2 -> pok1)
            normalAttack(pokemon2);

            // TODO: Update HPs (check if dodge)
            // For now, ignore dodge
            pokemon1.setHP(pokemon1.getHP() + pokemon1.getDefense() - pokemon2.getAttack());
            pokemon2.setHP(pokemon2.getHP() + pokemon2.getDefense() - pokemon1.getAttack());

            printHPs(pokemon1, pokemon2);

            Thread.sleep(10);

            semFirstMove.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void normalAttack(Pokemon pokemon) {
        NormalAttack.makeMove(pokemon);
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

}
