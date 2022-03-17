package me.anselm.game.entities.enemies;

import me.anselm.game.Game;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.entity.Rectangle;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Brain extends Enemy{

    private Rectangle rectangle;
    private Vector2f momentum;

    public Brain(Vector3f position) {
        super(position, 15, 15, 1.0f, AssetStorage.getTexture("brain"), Position.CENTER, false, 10);
        this.setCooldown(1.0f);
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {

    }

    @Override
    public void tick() {
        if(rectangle == null) {
            System.out.println("IS NULL RECTANLGE!!!!");
            return;
        }
    }

    @Override
    public void die() {

    }

    @Override
    public void attack() {
        Vector2f direction = this.calculateDirectionToPlayer();

        momentum = direction;

        float angle = this.getPosition().angle(direction);

        angle = (float) Math.toDegrees(angle);

        System.out.println(angle + "ANGLE");


        if (this.rectangle == null) {
            Rectangle rectangle = new Rectangle(new Vector3f().set(this.getPosition()), 1.0f, 1000.0f,
                    1.0f, Position.CENTER, new Vector4f(0.0f, 0.0f, 1.0f, 1.0f));
            rectangle.rotateZ(angle);
            rectangle.setTexture(AssetStorage.getTexture("empty"));
            EntityRenderer.getRenderMesh().addRenderable(rectangle);
            this.rectangle = rectangle;

        }else{
            this.rectangle.updateVertices();
            this.rectangle.rotateZ(angle);
        }

        EntityRenderer.getRenderMesh().changeRenderable(this.rectangle);
    }
}
