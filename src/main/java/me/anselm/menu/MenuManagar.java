package me.anselm.menu;

import me.anselm.game.Game;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.graphics.menu.MenuRenderer;
import me.anselm.menu.buttons.Button;
import me.anselm.menu.buttons.Clickable;
import me.anselm.menu.menus.Menu;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

public class MenuManagar {
    private static final Logger logger = LoggerUtils.getLogger(MenuManagar.class);

    private static Menu currentMenu;

    public static void init() {
        currentMenu = new Menu();
        Button button = new Button("Start Game", new Vector3f(200.0f,100.0f,1.0f),
                100.0f,20.0f,1.0f, AssetStorage.getTexture("button"), Position.CENTER);

        button.setClickable(new Clickable() {
            @Override
            public void click(Button button) {
                Game.init();
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
        currentMenu.addButton(button);
        MenuRenderer.renderMesh.addRenderable(currentMenu);
        MenuRenderer.renderMesh.addRenderable(currentMenu.getButtons().get(0));
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        MenuManagar.currentMenu = currentMenu;
    }

    public static void handleMouse(Vector3f mousePos) {
        for(Button button : currentMenu.getButtons()) {

            float xMin = button.getCenter().x - button.width / 2;
            float xMax = button.getCenter().x + button.width / 2;
            float yMin = button.getCenter().y -  button.height / 2;
            float yMax = button.getCenter().y + button.height / 2;


            if(mousePos.x < xMax && mousePos.x > xMin) {
                if(mousePos.y  < yMax && mousePos.y > yMin) {
                    currentMenu.setCurrentTarget(button);
                    return;
                }
            }

            currentMenu.setCurrentTarget(null);
        }
    }

    public static void handleMouseClick() {
        Button button = currentMenu.getCurrentTarget();

        if(button == null) {
            return;
        }

        button.getClickable().click(button);
    }
}
