package me.anselm.graphics.game.entity;

import me.anselm.graphics.game.Renderable;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Rectangle extends Renderable {

    public Rectangle(Vector3f position, float width, float height, float size, Position orientation, Vector4f color) {
        super(position, width, height, size, orientation, color);
    }
}
