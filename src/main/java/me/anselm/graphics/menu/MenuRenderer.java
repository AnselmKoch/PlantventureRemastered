package me.anselm.graphics.menu;

import me.anselm.graphics.game.font.FontRenderer;
import me.anselm.graphics.shaders.Shader;
import me.anselm.graphics.mesh.RenderMesh;
import me.anselm.menu.MenuManagar;
import me.anselm.menu.buttons.Button;
import me.anselm.menu.menus.PickupMenu;
import me.anselm.utils.LoggerUtils;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

public class MenuRenderer {
    private final static Logger logger = LoggerUtils.getLogger(MenuRenderer.class);

    public static RenderMesh renderMesh;

    public static void render() {
        renderMesh.render();

        if(MenuManagar.getCurrentMenu() == null) {
            return;
        }

        for(Button button : MenuManagar.getCurrentMenu().getButtons()) {
            FontRenderer.textFont.drawTextCentered(button.getText(), new Vector3f(button.getPosition().x + 3.0f, button.getPosition().y + 5.5f, 1.0f)
                    , button.width, button.height, 0.15f, new Vector4f(1.0f,1.0f,1.0f,1.0f));
        }

        FontRenderer.textFont.drawTextCentered(MenuManagar.getCurrentMenu().getName(), new Vector3f(200f - 40.0f,175f, 0.0f),
                1.0f,0.5f, 0.5f, new Vector4f(0.0f,0.3f,0.0f,1.0f));


        if(!(MenuManagar.getCurrentMenu() == MenuManagar.menuMap.get(MenuManagar.PICK_MENU))) {
            return;
        }

        PickupMenu pickupMenu = (PickupMenu) MenuManagar.getCurrentMenu();
        FontRenderer.textFont.drawText("+" + pickupMenu.getCrtPowerup().getDamage() + " damage",
                new Vector3f(100.0f, 120.0f, 0.0f), 5.0f,5.0f,new Vector4f(1.0f,1.0f,1.0f,1.0f));
        FontRenderer.textFont.drawText("+" + pickupMenu.getCrtPowerup().getHealth() + " health",
                new Vector3f(100.0f, 100.0f, 0.0f), 5.0f,5.0f,new Vector4f(1.0f,1.0f,1.0f,1.0f));
        FontRenderer.textFont.drawText("+" + pickupMenu.getCrtPowerup().getSpeed() + " speed",
                new Vector3f(220.0f, 120.0f, 0.0f), 5.0f,5.0f,new Vector4f(1.0f,1.0f,1.0f,1.0f));
        FontRenderer.textFont.drawText("+" + pickupMenu.getCrtPowerup().getShotSpeed() + " shot speed",
                new Vector3f(220.0f, 100.0f, 0.0f), 5.0f,5.0f,new Vector4f(1.0f,1.0f,1.0f,1.0f));

    }

    public static void init() {
        renderMesh = new RenderMesh(Shader.MENU, 150);


    }
}
