package me.anselm.menu.buttons;

import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Button extends Renderable {

    private Clickable clickable;
    private String text;

    public Button(String text, Vector3f position, float width, float height, float size, Texture texture, Position orientation) {
        super(position, width, height, size, texture, orientation);
        this.text = text;
    }


    public Clickable getClickable() {
        return clickable;
    }

    public void setClickable(Clickable clickable) {
        this.clickable = clickable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
