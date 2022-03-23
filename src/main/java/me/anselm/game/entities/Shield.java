package me.anselm.game.entities;

import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public class Shield extends Renderable {

    public Shield(Vector3f position, float width, float height) {
        super(position, width, height, 1.0f, AssetStorage.getTexture("shield"), Position.CENTER);
    }
}
