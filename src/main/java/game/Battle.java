package game;

import entities.Pokemon;

import java.util.concurrent.Semaphore;

public final class Battle {
    private Pokemon pokemon1;
    private Pokemon pokemon2;

    public Battle(Pokemon pokemon1, Pokemon pokemon2) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.pokemon1.isAttacker(true);
    }

    private Semaphore semFirstMove  = new Semaphore(1);
    private Semaphore semSecondMove = new Semaphore(0);

    // Pokemon1 attacks Pokemon2
    public void firstMove() {
        try {
            semFirstMove.acquire();

            // Call methods to attack (pok1 -> pok2)
            System.out.println("[" + Thread.currentThread().getId() + "]: " + pokemon1.getName() + " attacks " + pokemon2.getName());
            Thread.sleep(1000);

            semSecondMove.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

    // Pokemon2 attacks Pokemon1
    public void secondMove() {
        try {
            semSecondMove.acquire();

            // Call methods to attack (pok2 -> pok1)
            System.out.println("[" + Thread.currentThread().getId() + "]: " + pokemon2.getName() + " attacks " + pokemon1.getName());
            Thread.sleep(1000);

            semFirstMove.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
