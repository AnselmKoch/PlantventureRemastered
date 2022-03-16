package me.anselm.game.entities.enemies;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.entity.HealthbarRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class Zombie extends Enemy {
    private static final Logger logger = LoggerUtils.getLogger(Zombie.class);

    public  Texture[] textures = new Texture[] {
            AssetStorage.getTexture("zombie"), AssetStorage.getTexture("zombie1"), AssetStorage.getTexture("zombie2")
    };

    public float animationIndex = 0;

    public Zombie(Vector3f position) {
        super(position, 20.0f, 20.0f, 1.0f, AssetStorage.getTexture("zombie"), Position.CENTER,  false, 5);
        this.setDamage(3);
        this.setSpeed(1.5f);
        this.setInvincTime(20);

        this.setAnimationDelay(10);

        this.setTextures(textures);
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum.normalize().mul(this.getSpeed()),0.0f);

        if(momentum.x  < 0) {
            this.rotateY(0);
        }else{
            this.rotateY(160);
        }
    }

    @Override
    public void tick() {


        this.setMomentum(this.calculateDirectionToPlayer());
        this.doCollition(-0.25f);

        move(this.getMomentum());

    }


    @Override
    public void die() {
        Game.monsterDeathCounter++;
        Game.levelManager.getCurrentLevel().getEnemyArrayList().remove(this);
        EntityRenderer.getRenderMesh().removeRenderable(this);
        this.getHealthbar().destroy();
    }

    @Override
    public void attack() {

    }
}
