package entities;

import common.Constants;

import java.util.ArrayList;
import java.util.List;

/** This class is used for creating an item */
public class PokemonBuilder {
    // Pattern: Builder
    // Attributes
    private final String name;
    private Integer HP;

    private Integer attack;
    private Integer specialAttack;

    private Integer defense;
    private Integer specialDefense;

    private Ability firstAbility;
    private Ability secondAbility;

    private final List<Item> items = new ArrayList<>(); // 3 items

    // Constructors
    public PokemonBuilder(String name) {
        this.name = name;
    }

    // Complete Pokemon's fields
    public entities.PokemonBuilder HP(Integer HP) {
        this.HP = HP;
        return this;
    }

    public entities.PokemonBuilder attack(Integer attack) {
        this.attack = attack;
        return this;
    }

    public entities.PokemonBuilder specialAttack(Integer specialAttack) {
        this.specialAttack = specialAttack;
        return this;
    }

    public entities.PokemonBuilder defense(Integer defense) {
        this.defense = defense;
        return this;
    }

    public entities.PokemonBuilder specialDefense(Integer specialDefense) {
        this.specialDefense = specialDefense;
        return this;
    }

    public entities.PokemonBuilder firstAbility(Ability ability) {
        this.firstAbility = ability;
        return this;
    }

    public entities.PokemonBuilder secondAbility(Ability ability) {
        this.secondAbility = ability;
        return this;
    }

    public entities.PokemonBuilder addItem(Item item) {
        if (item == null)
            return this;

        if(this.items.size() < Constants.POKEMON_MAX_ITEMS)
            this.items.add(item);
        else
            System.err.println(Constants.ERROR_LOG + "Couldn't add the Item '" +
                    item.getName() + "' because the pokemon's capacity is full " +
                    "[Max "+ Constants.POKEMON_MAX_ITEMS + " items]");

        return this;
    }

    public Pokemon build() {
        Pokemon pokemon = new Pokemon(this);
        if (pokemon.validate())
            return pokemon;
        else {
            System.err.println(Constants.ERROR_LOG +
                    "Couldn't create the pokemon '" + pokemon.getName() +
                    "' because the pokemon isn't a valid one " +
                    "[Pokemons are not allowed to have 2 types of attack " +
                    "(NORMAL_ATTACK and SPECIAL_ATTACK)]");
            return null;
        }
    }

    // Getters
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

    public List<Item> getItems() {
        return items;
    }
}