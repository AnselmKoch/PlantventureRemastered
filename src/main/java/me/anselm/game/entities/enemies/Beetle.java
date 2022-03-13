package me.anselm.game.entities.enemies;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.shaders.Shader;
import me.anselm.graphics.texture.Texture;
import me.anselm.menu.menus.Menu;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

public class Beetle extends Enemy {
    private static final Logger logger = LoggerUtils.getLogger(Beetle.class);

    private boolean isChildren = false;

    public Beetle(Vector3f position) {
        super(position, 10.0f, 10.0f, 1.0f, AssetStorage.getTexture("beetle"), Position.CENTER, false, 5);
        this.setDamage(3);
        this.setSpeed(1.0f);
        this.setInvincTime(20);
        this.setHealth(2);
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {
        momentum.normalize().mul(this.getSpeed());
        if(momentum.x > momentum.y) {
            if (momentum.x > 0) {
                this.rotate180();
            } else {
                this.rotate270();
            }
        }else{
            if (momentum.y > 0) {
                this.rotate90();
            } else {
                this.rotateStandart();
            }
        }
        this.addToPosition(momentum, 0.0f);
    }

    @Override
    public void tick() {

        this.setMomentum(this.calculateDirectionToPlayer());
        this.doCollition(-1.0f);

        move(this.getMomentum());

    }

    @Override
    public void die() {
        Game.levelManager.getCurrentLevel().getEnemyArrayList().remove(this);
        EntityRenderer.getRenderMesh().removeRenderable(this);
        this.getHealthbar().destroy();

        if(this.isChildren) {
            return;
        }

        for(int i = 0; i < 3; i++) {
            Vector3f childPos = new Vector3f().set(this.getPosition());
            switch (i) {
                case 0: childPos.x += 1.0f; break;
                case 1: childPos.y += 1.0f; childPos.x -= 1.0f; break;
                case 2: childPos.x -=1.0f; break;
            }
           Beetle entity = (Beetle) Game.levelManager.getCurrentLevel().spawnEntity(Beetle.class, childPos);
           entity.setWidth(7.0f);
           entity.setHeight(7.0f);
           entity.setChildren(true);
           entity.updateVertices();
        }
    }

    public boolean isChildren() {
        return isChildren;
    }

    public void setChildren(boolean children) {
        isChildren = children;
    }

    @Override
    public void attack() {

    }
}
