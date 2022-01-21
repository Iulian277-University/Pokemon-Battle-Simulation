package entities;

import java.util.ArrayList;

public class Trainer {
    /** Attributes */
    private String name;
    private Integer age;
    private ArrayList<Pokemon> pokemons = new ArrayList<>(); // 3 pokemons

    /** Constructors */
    private Trainer(TrainerBuilder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.pokemons.addAll(builder.pokemons);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", pokemons=" + pokemons +
                '}';
    }

    // NO setters to provide immutability
    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    // Pattern: Builder
    public static class TrainerBuilder {
        /** Attributes */
        private String name;
        private Integer age;
        private ArrayList<Pokemon> pokemons = new ArrayList<>(); // 3 pokemons

        /** Constructors */
        public TrainerBuilder(String name) {
            this.name = name;
        }

        /** Complete Trainer fields */
        public TrainerBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public TrainerBuilder addPokemon(Pokemon pokemon) {
            if(this.pokemons.size() < 3) // TODO: Make constants
                this.pokemons.add(pokemon);
            return this;
        }

        public Trainer build() {
            Trainer trainer = new Trainer(this);
            // validate trainer
            return trainer;
        }

    }
}
