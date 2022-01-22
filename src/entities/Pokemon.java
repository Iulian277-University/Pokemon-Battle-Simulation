package entities;

public class Pokemon {
    /** Attributes */
    private String name;
    private Integer HP;

    private Integer attack;
    private Integer specialAttack;

    private Integer defense;
    private Integer specialDefense;

    private Ability firstAbility;
    private Ability secondAbility;

    /** Constructors */
    private Pokemon(PokemonBuilder builder) {
        this.name = builder.name;
        this.HP = builder.HP;
        this.attack = builder.attack;
        this.specialAttack = builder.specialAttack;
        this.defense = builder.defense;
        this.specialDefense = builder.specialDefense;
        this.firstAbility = builder.firstAbility;
        this.secondAbility = builder.secondAbility;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", HP=" + HP +
                ", attack=" + attack +
                ", specialAttack=" + specialAttack +
                ", defense=" + defense +
                ", specialDefense=" + specialDefense +
                ", firstAbility=" + firstAbility +
                ", secondAbility=" + secondAbility +
                '}';
    }

    // NO setters to provide immutability
    public String getName() {
        return name;
    }

    public Integer getHP() {
        return HP;
    }

    public Integer getAttack() {
        return attack;
    }

    public Integer getSpecialAttack() {
        return specialAttack;
    }

    public Integer getDefense() {
        return defense;
    }

    public Integer getSpecialDefense() {
        return specialDefense;
    }

    public Ability getFirstAbility() {
        return firstAbility;
    }

    public Ability getSecondAbility() {
        return secondAbility;
    }

    // Validate a pokemon
    public boolean validate() {
        return this.getAttack() == null || this.getSpecialAttack() == null;
    }

    // Pattern: Builder
    public static class PokemonBuilder {
        /** Attributes */
        private String name;
        private Integer HP;

        private Integer attack;
        private Integer specialAttack;

        private Integer defense;
        private Integer specialDefense;

        private Ability firstAbility;
        private Ability secondAbility;

        /** Constructors */
        public PokemonBuilder(String name) {
            this.name = name;
        }

        /** Complete Pokemon fields */
        public PokemonBuilder HP(Integer HP) {
            this.HP = HP;
            return this;
        }

        public PokemonBuilder attack(Integer attack) {
            this.attack = attack;
            return this;
        }

        public PokemonBuilder specialAttack(Integer specialAttack) {
            this.specialAttack = specialAttack;
            return this;
        }

        public PokemonBuilder defense(Integer defense) {
            this.defense = defense;
            return this;
        }

        public PokemonBuilder specialDefense(Integer specialDefense) {
            this.specialDefense = specialDefense;
            return this;
        }

        public PokemonBuilder firstAbility(Ability ability) {
            this.firstAbility = ability;
            return this;
        }

        public PokemonBuilder secondAbility(Ability ability) {
            this.secondAbility = ability;
            return this;
        }

        public Pokemon build() {
            Pokemon pokemon = new Pokemon(this);
            if (pokemon.validate())
                return pokemon;
            else {
                System.out.println("Couldn't create the pokemon '" + pokemon.getName() +
                        "' because the pokemon isn't a valid one " +
                        "[Pokemons are not allowed to have 2 types of attack " +
                        "(NORMAL_ATTACK and SPECIAL_ATTACK)]");
                return null;
            }
        }
    }
}
