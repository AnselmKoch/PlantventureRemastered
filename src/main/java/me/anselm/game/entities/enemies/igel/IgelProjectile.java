package me.anselm.game.entities.enemies.igel;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.entities.enemies.Igel;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class IgelProjectile extends Entity {
    private static final Logger logger = LoggerUtils.getLogger(IgelProjectile.class);

    private float rotation;
    private Igel parent;

    public IgelProjectile(Vector3f position, float rotation, Igel parent) {
        super(position, 4.0f, 7.0f, 1.0f, AssetStorage.getTexture("igelproj"), Position.CENTER, false, 0);
        this.rotation = rotation;
        this.parent = parent;
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum, 0.0f);

    }

    @Override
    public void tick() {

        if(this.checkIfOutOfBounds()) {
            EntityRenderer.getRenderMesh().removeRenderable(this);
            Game.levelManager.getCurrentLevel().getProjectileList().remove(this);
            return;
        }

        this.move(this.getMomentum());
        this.rotateZ(rotation);
        EntityRenderer.getRenderMesh().changeRenderable(this);

        if(CollitionDetector.colides(this, Game.player )) {
            Game.player.onDamage((int)parent.getDamage(), null);
            EntityRenderer.getRenderMesh().removeRenderable(this);
            Game.levelManager.getCurrentLevel().getProjectileList().remove(this);
        }
    }

    @Override
    public void die() {

    }
}
