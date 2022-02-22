package me.anselm.graphics.game.hud;

import me.anselm.game.Game;
import me.anselm.game.entities.player.inventory.Inventory;
import me.anselm.game.entities.player.inventory.ItemStack;
import me.anselm.game.entities.player.items.Bullet;
import me.anselm.game.entities.player.items.Item;
import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.game.font.FontRenderer;
import me.anselm.graphics.game.items.ItemIcon;
import me.anselm.graphics.mesh.RenderMesh;
import me.anselm.graphics.shaders.Shader;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import me.anselm.utils.font.TextFont;
import org.joml.Vector3f;
import org.slf4j.Logger;


public class HUDRenderer {
    private static final Logger logger = LoggerUtils.getLogger(HUDRenderer.class);

    private static Texture itemContainerTex;
    private static ItemContainer[] itemContainers;
    private static RenderMesh renderMesh;

    public static void init() {
        logger.info("Initializing HUD renderer...");

        renderMesh  = new RenderMesh(Shader.STANDART);

        int itemWidth = 10;
        itemContainers = new ItemContainer[5];
        int currX = 0;

        itemContainerTex = new Texture("HUD/ItemContainer");
        for(int i = 0; i < itemContainers.length; i++) {
            itemContainers[i] = new ItemContainer(null, new Vector3f(currX, 200.0f, 2.0f), 10.0f, 10.0f, 1.0f, itemContainerTex, Position.TOPLEFT);
            renderMesh.addRenderable(itemContainers[i]);
            currX+=itemWidth;
        }
    }

    public static void render() {
        logger.info("Rendering HUD...");

        int currX = 5;

        renderMesh.render();

        for(int i = 0; i < itemContainers.length; i++) {
            ItemContainer itemContainer = itemContainers[i];

            if(itemContainer.getItemIcon() == null) {
                continue;
            }
            Vector3f pos = itemContainer.getPosition();
            logger.info(itemContainer.getItemIcon().getItemStack().getSize() + "!!");
            FontRenderer.textFont.drawText(String.valueOf(itemContainer.getItemIcon().getItemStack().getSize()), new Vector3f(pos.x + 5.0f, pos.y -10.0f, 1.0f), 20.0f,20.0f);
        }

    }

    public static void addItemToRender(ItemStack item, int index) {
        ItemIcon itemIcon = new ItemIcon(item, itemContainers[index].getPosition(), 10.0f,10.0f, 1.0f, item.getItem().getTexture(), Position.TOPLEFT);
        itemContainers[index].setItemIcon(itemIcon);
        renderMesh.addRenderable(itemIcon);

    }

    public static void removeItemToRender(int index) {

    }

    public static RenderMesh getRenderMesh() {
        return renderMesh;
    }
}
