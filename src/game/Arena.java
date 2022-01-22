package game;

import entities.Trainer;

/**
 * There is a unique arena in the game
 * Only one game can be played at a time
 */
public final class Arena {
    private Trainer firstTrainer;
    private Trainer secondTrainer;

    // Pattern: Singleton
    private static Arena arena;

    private Arena(Trainer firstTrainer, Trainer secondTrainer) {
        this.firstTrainer = firstTrainer;
        this.secondTrainer = secondTrainer;
    }

    public static Arena generateArena(Trainer firstTrainer, Trainer secondTrainer) {
        if (arena == null)
            arena = new Arena(firstTrainer, secondTrainer);
        return arena;
    }

    @Override
    public String toString() {
        return "Arena{" +
                "firstTrainer=" + firstTrainer +
                ", secondTrainer=" + secondTrainer +
                '}';
    }

    // TODO: Arena (game) functionality

}
