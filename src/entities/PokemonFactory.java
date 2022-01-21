package entities;

public final class PokemonFactory {
    // Pattern: Singleton
    private static PokemonFactory factory;
    public PokemonFactory() {}
    public static PokemonFactory generateFactory() {
        if(factory == null)
            factory = new PokemonFactory();
        return factory;
    }


}
