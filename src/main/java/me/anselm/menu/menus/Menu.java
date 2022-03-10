package me.anselm.menu.menus;

import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.menu.MenuRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.menu.buttons.Button;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu extends Renderable{

    private List<Button> buttonArrayList;

    private Button currentTarget;

    private String name;

    public Menu(Vector3f position, float width, float height, float size, Texture texture, Position orientation, String name) {
        super(position, width, height, size, texture, orientation);

        this.name = name;
        buttonArrayList = new ArrayList<>();
    }

    public Menu(Vector3f position, float width, float height, float size, Position orientation, Vector4f color, String name) {
        super(position, width, height, size, orientation, color);
        this.name = name;
        this.buttonArrayList = new ArrayList<>();
    }

    public void addButton(Button button) {
        this.buttonArrayList.add(button);
        MenuRenderer.renderMesh.addRenderable(button);
    }

    public abstract void init();

    public List<Button> getButtons() {
        return this.buttonArrayList;
    }

    public Button getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(Button currentTarget) {
        if(currentTarget == null) {
            if(this.currentTarget != null) {
                this.currentTarget.getClickable().unhover(this.currentTarget);
                this.currentTarget = null;
                return;
            }else{
                return;
            }
        }

        if(this.currentTarget != null) {
            this.currentTarget.getClickable().unhover(this.currentTarget);
        }

        this.currentTarget = currentTarget;
        this.currentTarget.getClickable().hover(this.currentTarget);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
