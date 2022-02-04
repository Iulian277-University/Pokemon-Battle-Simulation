package entities;

import game.Battle;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

import common.Constants.Moves;

/**
 * This class is used for creating a pokemon
 * and setting the game functionalities
 */
public class Pokemon implements Serializable, Callable<Moves> {
    // Attributes
    private final String name;
    private Integer HP;
    private Integer attack;
    private Integer specialAttack;
    private Integer defense;
    private Integer specialDefense;
    private final Ability firstAbility;
    private final Ability secondAbility;
    private final List<Item> items;

    // Constructors
    public Pokemon(PokemonBuilder builder) {
        this.name = builder.getName();
        this.HP = builder.getHP();

        this.attack = builder.getAttack();
        this.specialAttack = builder.getSpecialAttack();

        this.defense = builder.getDefense();
        this.specialDefense = builder.getSpecialDefense();

        this.firstAbility = builder.getFirstAbility();
        this.secondAbility = builder.getSecondAbility();

        this.items = builder.getItems();
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

    public List<Item> getItems() {
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

    // Game functionality
    private transient Battle battle;
    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    @Override
    public Moves call() {
        return battle.generateRandomMove(this);
    }

    private boolean isAttacker = false; // pokemon1
    public void isAttacker(boolean attacker) {
        isAttacker = attacker;
    }

    public void run(Moves move) {
        if (isAttacker)
            battle.firstMove(move);
        else
            battle.secondMove(move);
    }

    private Moves currentMove;
    public  Moves getCurrentMove() {
        return currentMove;
    }
    public  void  setCurrentMove(Moves currentMove) {
        this.currentMove = currentMove;
    }

    public  void setHP(Integer HP) {
        this.HP = HP;
    }

    private boolean isStunned = false;
    public  boolean isStunned() {
        return isStunned;
    }
    public  void setStunned(boolean isStunned) {
        this.isStunned = isStunned;
    }

    private boolean isDodged = false;
    public  boolean isDodged() {
        return isDodged;
    }
    public  void setDodged(boolean isDodged) {
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
