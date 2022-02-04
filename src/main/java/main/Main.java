package main;

import entities.*;
import io.OutputStream;

/**
 * This is the entry point of the program
 *
 * Patterns
 * - Singleton: Everywhere when the object needs to be one-time instantiated
 * - Adapter:   I created a pipeline which looks like this: Input -> Adapter -> Application -> Adapter -> Output
 * - Builder:   Used for sequentially adding attributes (or different combinations of them) to objects
 * - Factory:   User only provide the name of the object and the backend implementation does the filtering and
 *
 * In the project, you may find combinations of Builder and Factory patterns for a better approach of the problem
 * You can look for comments like `// Pattern: ` to see where each pattern was used to
 */
public class Main {
    public static void main(String[] args) {

        TrainerFactory trainerFactory = TrainerFactory.generateFactory();
        PokemonFactory pokemonFactory = PokemonFactory.generateFactory();

        // GenerateTestcases.generate(10);

        // TODO: Battle on threads

        OutputStream.selectStream();

        Test.runTestcases(trainerFactory, pokemonFactory);
    }
}
