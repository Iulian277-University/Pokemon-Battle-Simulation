package entities;

import common.Constants;
import game.Battle;

import java.io.Serializable;
import java.util.ArrayList;

import common.Constants.Moves;

public class Pokemon implements Serializable, Runnable {
    /** Attributes */
    private String name;
    private Integer HP;

    private Integer attack;
    private Integer specialAttack;

    private Integer defense;
    private Integer specialDefense;

    private Ability firstAbility;
    private Ability secondAbility;

    private ArrayList<Item> items; // 3 items

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

        this.items = builder.items;
        updateStatsWithItems();
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
                ", items=" + items +
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

    public ArrayList<Item> getItems() {
        return items;
    }

    public void incrementStats() {
        this.HP++;
        if (this.attack != null)
            this.attack++;
        else if (this.specialAttack != null)
            this.specialAttack++;
        this.defense++;
        this.specialDefense++;

        // Reset stun/dodge
        this.isStunned = false;
        this.isDodged  = false;
    }

    // Public methods
    public boolean isAlive() {
        return this.HP > 0;
    }

    public boolean isDead() {
        return this.HP <= 0;
    }

    // Validate a pokemon
    public boolean validate() {
        return (this.getAttack() == null && this.getSpecialAttack() != null) ||
                (this.getAttack() != null && this.getSpecialAttack() == null);
    }

    // Add items to pokemon (Update pokemon's stats)
    private void updateStatsWithItems() {
        for (Item item: this.items) {
            this.HP += item.getHP();

            if (this.getAttack() != null)
                this.attack += item.getAttack();
            else if (this.getSpecialAttack() != null)
                this.specialAttack += item.getSpecialAttack();

            this.defense += item.getDefense();
            this.specialDefense += item.getSpecialDefense();
        }
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

        private ArrayList<Item> items = new ArrayList<>(); // 3 items

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

        public PokemonBuilder addItem(Item item) {
            if (item == null)
                return this;

            if(this.items.size() < Constants.POKEMON_MAX_ITEMS)
                this.items.add(item);
            else
                System.out.println("Couldn't add the Item '" +
                        item.getName() + "' because the pokemon's capacity is full " +
                        "[Max "+ Constants.POKEMON_MAX_ITEMS + " items]");

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

    // Game functionality
    private transient Battle battle; // transient?
    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    private boolean isAttacker = false; // pokemon1
    public void isAttacker(boolean attacker) {
        isAttacker = attacker;
    }

    @Override
    public void run() {
        if (isAttacker)
            battle.firstMove();
        else
            battle.secondMove();
    }

    private Moves currentMove;
    public Moves getCurrentMove() {
        return currentMove;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHP(Integer HP) {
        this.HP = HP;
    }

    public void setCurrentMove(Moves currentMove) {
        this.currentMove = currentMove;
    }


    private boolean isStunned = false;
    public boolean isStunned() {
        return isStunned;
    }
    public void setStunned(boolean isStunned) {
        this.isStunned = isStunned;
    }

    private boolean isDodged = false;
    public boolean isDodged() {
        return isDodged;
    }
    public void setDodged(boolean isDodged) {
        this.isDodged = isDodged;
    }

    public int getScore() {
        int score = this.HP + this.defense + this.specialDefense;
        if (this.attack != null)
            score += this.attack;
        if (this.specialAttack != null)
            score += this.specialAttack;

        return score;
    }
}
