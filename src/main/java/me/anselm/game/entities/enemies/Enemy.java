package me.anselm.game.entities.enemies;

import me.anselm.game.entities.Entity;
import me.anselm.graphics.game.entity.Healthbar;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.slf4j.Logger;

public abstract class Enemy extends Entity {
    private static final Logger logger = LoggerUtils.getLogger(Enemy.class);

    public Enemy(Vector3f position, float width, float height, float size, Texture texture, Position center, boolean transparency, int health) {
        super(position, width, height, size, texture, center, transparency, health);

        this.setHealthBarPos(new Vector3f().set(this.getPosition()));

       this.setHealthbar(new Healthbar(this.getHealthBarPos(), width, 2.0f, this.getHealth(), this));

    }

    public abstract void attack();
}
