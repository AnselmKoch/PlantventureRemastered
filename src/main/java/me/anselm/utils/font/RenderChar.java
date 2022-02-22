package me.anselm.utils.font;

import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class RenderChar extends Renderable {

    private final char renderChar;

    public RenderChar(char renderChar, Vector3f position, Vector2f[] texCords, float width, float height, float size, Texture texture, Position orientation) {
        super(position, texCords, width, height, size, texture, orientation);
        this.renderChar = renderChar;
    }
}
