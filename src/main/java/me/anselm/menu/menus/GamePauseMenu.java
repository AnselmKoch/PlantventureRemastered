package me.anselm.menu.menus;

import me.anselm.game.Game;
import me.anselm.graphics.menu.MenuRenderer;
import me.anselm.menu.MenuManagar;
import me.anselm.menu.buttons.Button;
import me.anselm.menu.buttons.Clickable;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

public class GamePauseMenu extends Menu{

    public GamePauseMenu() {
        super(new Vector3f(0.0f,0.0f,0.0f), 400.0f, 200.0f, 1.0f, Position.BOTTOMLEFT,
                new Vector4f(0.0f,0.0f,0.0f,0.5f), "Game Paused");

        setupResumeButton();
        setupResetButton();
        setupBackButton();

    }

    @Override
    public void init() {
    }

    private void setupBackButton() {
        Button button = new Button("Back to Menu", new Vector3f(200.0f,40.0f,1.0f),
                100.0f,20.0f,1.0f, AssetStorage.getTexture("button"), Position.CENTER, this);
        addButton(button);

        Clickable clickable = new Clickable() {
            @Override
            public void click(Button button) {
                Game.reset();
                MenuManagar.switchMenu(MenuManagar.menuMap.get(MenuManagar.MAIN_MENU));
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
        };

        button.setClickable(clickable);
    }

    private void setupResetButton() {
        Button button = new Button("Reset Run", new Vector3f(200.0f,70.0f,1.0f),
                100.0f,20.0f,1.0f, AssetStorage.getTexture("button"), Position.CENTER, this);
        addButton(button);

        Clickable clickable = new Clickable() {
            @Override
            public void click(Button button) {
                Game.reset();
                Game.init();
                MenuManagar.switchMenu(null);
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
        };

        button.setClickable(clickable);
    }
    private void setupResumeButton() {

        Button button = new Button("Back To Game", new Vector3f(200.0f,100.0f,1.0f),
                100.0f,20.0f,1.0f, AssetStorage.getTexture("button"), Position.CENTER, this);
        addButton(button);

        Clickable clickable = new Clickable() {
            @Override
            public void click(Button button) {
                MenuManagar.switchMenu(null);
                Game.ticking = true;
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
        };

        button.setClickable(clickable);
    }
}
