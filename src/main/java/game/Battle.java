package game;

import entities.Pokemon;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Battle {
    private Pokemon pokemon1;
    private Pokemon pokemon2;

    public Battle(Pokemon pokemon1, Pokemon pokemon2) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.pokemon1.isAttacker(true);
    }

    private Lock lock = new ReentrantLock();
    private Condition notFirstMoveDone = lock.newCondition();
    private Condition notSecondMoveDone = lock.newCondition();

    private boolean makingFirstMove = false;
    private boolean makingSecondMove = false;

    // TODO: Problem - The attacks end up on the same thread
    //  or there are multiple attacks in a row

    // Pokemon1 attacks Pokemon2
    public void firstMove() {
        try {
            lock.lock();
            while (makingSecondMove)
                notFirstMoveDone.await();

            makingFirstMove = true;
            // Call methods to attack (pok1 -> pok2)
            Thread.sleep(100);
            System.out.println("[" + Thread.currentThread().getId() + "]: " + pokemon1.getName() + " attacks " + pokemon2.getName());
            makingFirstMove = false;

            notSecondMoveDone.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    // Pokemon2 attacks Pokemon1
   public void secondMove() {
        try {
            lock.lock();
            while (makingFirstMove)
                notSecondMoveDone.await();

            makingSecondMove = true;
            // Call methods to attack (pok2 -> pok1)
            Thread.sleep(100);
            System.out.println("[" + Thread.currentThread().getId() + "]: " + pokemon2.getName() + " attacks " + pokemon1.getName());
            makingSecondMove = false;

            notFirstMoveDone.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

}
