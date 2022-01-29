package entities;

import common.Constants;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
    // Attributes
    private final String name;
    private final Integer age;
    private final List<Pokemon> pokemons;
    private final List<String> pokemonsOrder;

    // Constructors
    private Trainer(TrainerBuilder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.pokemons = builder.pokemons;

        pokemonsOrder = new ArrayList<>();
        for (Pokemon pokemon: pokemons) {
           pokemonsOrder.add(pokemon.getName());
        }
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", pokemons=" + pokemons +
                ", pokemonsOrder=" + pokemonsOrder +
                '}';
    }

    // NO setters to provide immutability
    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public List<String> getPokemonsOrder() {
        return pokemonsOrder;
    }

    // Pattern: Builder
    public static class TrainerBuilder {
        // Attributes
        private final String name;
        private Integer age;
        private final List<Pokemon> pokemons = new ArrayList<>();

        // Constructors
        public TrainerBuilder(String name) {
            this.name = name;
        }

        // Complete Trainer fields
        public TrainerBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public TrainerBuilder addPokemon(Pokemon pokemon) {
            if(pokemon == null)
                return this;

            if(this.pokemons.size() < Constants.TRAINER_MAX_POKEMONS)
                this.pokemons.add(pokemon);
            else
                System.err.println(Constants.ERROR_LOG +
                        "Couldn't add the Pokemon '" +
                        pokemon.getName() + "' because the trainer's capacity is full " +
                        "[Max "+ Constants.TRAINER_MAX_POKEMONS + " pokemons]");
            return this;
        }

        public Trainer build() {
            return new Trainer(this);
        }
    }
}
