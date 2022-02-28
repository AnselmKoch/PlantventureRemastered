package me.anselm.graphics.menu;

import me.anselm.graphics.game.font.FontRenderer;
import me.anselm.graphics.shaders.Shader;
import me.anselm.graphics.shaders.mesh.RenderMesh;
import me.anselm.menu.MenuManagar;
import me.anselm.menu.buttons.Button;
import me.anselm.menu.menus.Menu;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MenuRenderer {

    public static RenderMesh renderMesh;

    public static void render() {
        renderMesh.render();
        for(Button button : MenuManagar.getCurrentMenu().getButtons()) {
            FontRenderer.textFont.drawTextCentered(button.getText(), new Vector3f(button.getPosition().x + 3.0f, button.getPosition().y + 5.5f, 1.0f)
                    , button.width, button.height, 0.15f, new Vector4f(1.0f,1.0f,1.0f,1.0f));
        }
    }

    public static void init() {
        renderMesh = new RenderMesh(Shader.MENU, 150);


    }
}
