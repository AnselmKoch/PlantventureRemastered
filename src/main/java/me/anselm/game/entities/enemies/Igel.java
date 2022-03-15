package me.anselm.game.entities.enemies;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.entities.enemies.igel.IgelProjectile;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Igel extends Enemy{
    private static final Logger logger = LoggerUtils.getLogger(Igel.class);


    public Igel(Vector3f position) {
        super(position, 22.0f, 18f, 1.0f, AssetStorage.getTexture("igel"), Position.CENTER, false, 10);

        this.setHealth(1);
        this.setSpeed(1.0f);
        this.setCooldown(0.75f);
        this.setDamage(1.0f);
    }

    @Override
    public void onRender() {

        this.rotateZ(180);
    }

    @Override
    public void move(Vector2f momentum) {
        momentum.normalize().mul(this.getSpeed());
        this.addToPosition(momentum, 0.0f);
        if(momentum.x > 0) {
           this.rotateY(210f);
        }else{
           this.rotateY(0);
        }
    }

    @Override
    public void tick() {

        move(this.calculateDirectionToPlayer());
        this.doDamageColor();

    }

    @Override
    public void die() {
        EntityRenderer.getRenderMesh().removeRenderable(this);
        this.getHealthbar().destroy();
        Game.levelManager.getCurrentLevel().getEnemyArrayList().remove(this);
    }

    @Override
    public void attack() {

        for(int i = 0; i < 9; i++) {

            int rotation = (360 / 9) * i;
            IgelProjectile igelProjectile = new IgelProjectile(new Vector3f().set(this.getPosition()), rotation + 30, this);
            Vector3f momentum = new Vector3f((float)Math.cos(rotation),(float)Math.sin(rotation),0.0f);
            momentum.normalize();
            igelProjectile.setMomentumTotal(new Vector2f(momentum.x, momentum.y));
            Game.levelManager.getCurrentLevel().getProjectileList().add(igelProjectile);
            EntityRenderer.getRenderMesh().addRenderable(igelProjectile);
        }
    }
}
