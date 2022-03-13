package me.anselm.menu;

import me.anselm.game.Game;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.graphics.menu.MenuRenderer;
import me.anselm.menu.buttons.Button;
import me.anselm.menu.buttons.Clickable;
import me.anselm.menu.menus.DiedMenu;
import me.anselm.menu.menus.GamePauseMenu;
import me.anselm.menu.menus.MainMenu;
import me.anselm.menu.menus.Menu;
import me.anselm.menu.menus.PickupMenu;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

import java.util.HashMap;

public class MenuManagar {
    private static final Logger logger = LoggerUtils.getLogger(MenuManagar.class);

    public static HashMap<String, Menu> menuMap;
    public static final String MAIN_MENU = "MainMenu", GAME_PAUSE_MENU = "GamePauseMenu", DIED_MENU = "Died", PICK_MENU = "Pickup";

    private static Menu currentMenu;

    public static void init() {
        menuMap = new HashMap<>();
        menuMap.put(MAIN_MENU, new MainMenu());
        menuMap.put(GAME_PAUSE_MENU, new GamePauseMenu());
        menuMap.put(DIED_MENU, new DiedMenu());
        menuMap.put(PICK_MENU, new PickupMenu());

        Menu mainMenu = menuMap.get(MAIN_MENU);
        mainMenu.init();
        switchMenu(mainMenu);
    }

    public static void switchMenu(Menu menu) {

        if(menu == null) {
            MenuRenderer.renderMesh.clear();
            currentMenu = null;
            return;
        }

        MenuRenderer.renderMesh.clear();
        currentMenu = menu;
        MenuRenderer.renderMesh.addRenderable(currentMenu);

        for(Button button : currentMenu.getButtons()) {
            MenuRenderer.renderMesh.addRenderable(button);
        }
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
