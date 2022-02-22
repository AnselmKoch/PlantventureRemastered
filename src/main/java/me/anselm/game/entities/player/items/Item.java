package me.anselm.game.entities.player.items;

import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public abstract class Item{
    String name;
    int id;
    private Texture texture;

    public Item(String name, int id, Texture texture) {
        this.name = name;
        this.id = id;
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
