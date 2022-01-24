package entities;

//import java.io.Serializable;
// implements Serializable - NO NEED, because items are fixed

import java.io.Serializable;

public class Item implements Serializable {
    /** Attributes */
    private String name;
    private Integer HP;

    private Integer attack;
    private Integer specialAttack;

    private Integer defense;
    private Integer specialDefense;

    /** Constructors */
    private Item(ItemBuilder builder) {
        this.name = builder.name;
        this.HP = builder.HP;
        this.attack = builder.attack;
        this.specialAttack = builder.specialAttack;
        this.defense = builder.defense;
        this.specialDefense = builder.specialDefense;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", HP=" + HP +
                ", attack=" + attack +
                ", specialAttack=" + specialAttack +
                ", defense=" + defense +
                ", specialDefense=" + specialDefense +
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

    // Pattern: Builder
    public static class ItemBuilder {
        /** Attributes */
        private String name;
        private Integer HP;

        private Integer attack;
        private Integer specialAttack;

        private Integer defense;
        private Integer specialDefense;

        /** Constructors */
        public ItemBuilder(String name) {
            this.name = name;
        }

        public ItemBuilder HP(Integer HP) {
            this.HP = HP;
            return this;
        }

        public ItemBuilder attack(Integer attack) {
            this.attack = attack;
            return this;
        }

        public ItemBuilder specialAttack(Integer specialAttack) {
            this.specialAttack = specialAttack;
            return this;
        }

        public ItemBuilder defense(Integer defense) {
            this.defense = defense;
            return this;
        }

        public ItemBuilder specialDefense(Integer specialDefense) {
            this.specialDefense = specialDefense;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }

}
