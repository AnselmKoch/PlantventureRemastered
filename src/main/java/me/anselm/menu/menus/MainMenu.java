package me.anselm.menu.menus;

import me.anselm.Plantventure;
import me.anselm.game.Game;
import me.anselm.graphics.menu.MenuRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.menu.MenuManagar;
import me.anselm.menu.buttons.Button;
import me.anselm.menu.buttons.Clickable;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MainMenu extends Menu{

    public MainMenu() {
        super(new Vector3f(0.0f,0.0f,0.0f), 400.0f, 200.0f, 1.0f, AssetStorage.getTexture("background"), Position.BOTTOMLEFT, "Main Menu");

    }

    @Override
    public void init() {
        setStartButton();
        setExitButton();
    }

    private void setExitButton() {
        Button button = new Button("Exit Game", new Vector3f(200.0f,70.0f,1.0f),
                100.0f,20.0f,1.0f, AssetStorage.getTexture("button"), Position.CENTER, this);

        button.setClickable(new Clickable() {
            @Override
            public void click(Button button){
                Plantventure.running = false;
            }

            @Override
            public void hover(Button button) {
                button.setColor(new Vector4f(0.7f,0.7f,0.7f,1.0f));
                MenuRenderer.renderMesh.changeRenderable(button);
            }

            @Override
            public void unhover(Button button) {
                button.setColor(new Vector4f(1.0f,1.0f,1.0f,1.0f));
                MenuRenderer.renderMesh.changeRenderable(button);
            }
        });
        addButton(button);
    }

    private void setStartButton() {
        Button button = new Button("Start Game", new Vector3f(200.0f,100.0f,1.0f),
                100.0f,20.0f,1.0f, AssetStorage.getTexture("button"), Position.CENTER, this);

        button.setClickable(new Clickable() {
            @Override
            public void click(Button button) {
                Game.init();
                MenuRenderer.renderMesh.clear();
                MenuManagar.setCurrentMenu(null);
            }

            @Override
            public void hover(Button button) {
                button.setColor(new Vector4f(0.7f,0.7f,0.7f,1.0f));
                MenuRenderer.renderMesh.changeRenderable(button);
            }

            @Override
            public void unhover(Button button) {
                button.setColor(new Vector4f(1.0f,1.0f,1.0f,1.0f));
                MenuRenderer.renderMesh.changeRenderable(button);
            }
        });
        addButton(button);
    }
}
