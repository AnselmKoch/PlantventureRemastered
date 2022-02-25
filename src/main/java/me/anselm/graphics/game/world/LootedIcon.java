package me.anselm.graphics.game.world;

import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public class LootedIcon extends Renderable {

    public LootedIcon(Vector3f position, float width, float height, float size, Texture texture, Position orientation) {
        super(position, width, height, size, texture, orientation);
    }
}
