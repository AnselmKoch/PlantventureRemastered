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

public class Menu extends Renderable{

    private List<Button> buttonArrayList;

    private Button currentTarget;

    public Menu() {
        super(new Vector3f(0.0f,0.0f,0.0f), 400.0f, 200.0f, 1.0f, AssetStorage.getTexture("background"), Position.BOTTOMLEFT);
        buttonArrayList = new ArrayList<>();
    }


    public void addButton(Button button) {
        this.buttonArrayList.add(button);
        MenuRenderer.renderMesh.addRenderable(button);
    }

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
}
