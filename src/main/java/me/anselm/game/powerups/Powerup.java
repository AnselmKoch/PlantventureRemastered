package me.anselm.game.powerups;

import me.anselm.graphics.powerups.PowerupIcon;

public abstract class Powerup {

    private final int health;
    private final float speed;
    private final float shotSpeed;
    private final int damage;
    private final PowerupIcon powerupIcon;

    public Powerup(int upHealth, float upSpeed, float upShotspeed, int upDamage, PowerupIcon powerupIcon) {
        this.health = upHealth;
        this.damage = upDamage;
        this.speed = upSpeed;
        this.shotSpeed = upShotspeed;
        this.powerupIcon = powerupIcon;
    }

    public PowerupIcon getPowerupIcon() {
        return this.powerupIcon;
    }

    public int getHealth() {
        return health;
    }

    public float getSpeed() {
        return speed;
    }

    public float getShotSpeed() {
        return shotSpeed;
    }

    public int getDamage() {
        return damage;
    }
}
