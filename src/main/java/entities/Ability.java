package entities;

import java.io.Serializable;

/** Special ability */
public class Ability implements Serializable {
    /** Attributes */
    private Integer damage;
    private Boolean stun;
    private Boolean dodge;
    private Integer cooldown;
    private Integer originalCooldown;

    /** Constructors */
    private Ability(AbilityBuilder builder) {
        this.damage = builder.damage;
        this.stun = builder.stun;
        this.dodge = builder.dodge;
        this.cooldown = builder.cooldown;
        this.originalCooldown = this.cooldown;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "damage=" + damage +
                ", stun=" + stun +
                ", dodge=" + dodge +
                ", cooldown=" + cooldown +
                '}';
    }

    // NO setters to provide immutability
    public Integer getDamage() {
        return damage;
    }

    public Boolean getStun() {
        return stun;
    }

    public Boolean getDodge() {
        return dodge;
    }

    public Integer getCooldown() {
        return cooldown;
    }

    public Integer getOriginalCooldown() {
        return originalCooldown;
    }

    // Pattern: Builder
    public static class AbilityBuilder {
        /** Attributes */
        private Integer damage;
        private Boolean stun;
        private Boolean dodge;
        private Integer cooldown;

        public AbilityBuilder damage(Integer damage) {
            this.damage = damage;
            return this;
        }

        public AbilityBuilder stun(Boolean stun) {
            this.stun = stun;
            return this;
        }

        public AbilityBuilder dodge(Boolean dodge) {
            this.dodge = dodge;
            return this;
        }

        public AbilityBuilder cooldown(Integer cooldown) {
            this.cooldown = cooldown;
            return this;
        }

        public Ability build() {
            return new Ability(this);
        }

    }

    // Game functionality
    private boolean isAvailable = true;
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setCooldown(Integer cooldown) {
        this.cooldown = cooldown;
    }
}
