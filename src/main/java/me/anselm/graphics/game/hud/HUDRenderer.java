package me.anselm.graphics.game.hud;

import me.anselm.game.entities.player.inventory.ItemStack;
import me.anselm.game.entities.player.items.Bullet;
import me.anselm.game.entities.player.items.Item;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.font.FontRenderer;
import me.anselm.graphics.game.items.ItemIcon;
import me.anselm.graphics.shaders.mesh.RenderMesh;
import me.anselm.graphics.shaders.Shader;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class HUDRenderer {
    private static final Logger logger = LoggerUtils.getLogger(HUDRenderer.class);

    private static ItemContainer[] itemContainers;
    private static RenderMesh renderMesh;

    private static InformationRenderable informationToRender;
    private static ItemContainer itemContainer;

    public static void init() {
        logger.info("Initializing HUD renderer...");

        renderMesh  = new RenderMesh(Shader.STANDART, 100);

        int itemWidth = 10;
        itemContainers = new ItemContainer[5];
        int currX = 0;

        for(int i = 0; i < itemContainers.length; i++) {
            itemContainers[i] = new ItemContainer(null,
                    new Vector3f(currX, 200.0f, 2.0f), 10.0f, 10.0f, 1.0f,
                    AssetStorage.getTexture("itemcontainer"), Position.TOPLEFT);
            renderMesh.addRenderable(itemContainers[i]);
            currX+=itemWidth;
        }
    }

    public static void render() {
        logger.info("Rendering HUD...");

        renderMesh.render();

        for(int i = 0; i < itemContainers.length; i++) {
            ItemContainer itemContainer = itemContainers[i];
            if(itemContainer.getItemIcon() == null) {
                continue;
            }

            if(itemContainer.getItemIcon().getItemStack().getSize() == 0) {
                renderMesh.removeRenderable(itemContainer.getItemIcon());
                itemContainer.setItemIcon(null);
                continue;
            }

            Vector3f pos = itemContainer.getPosition();
            FontRenderer.textFont.drawText(String.valueOf(itemContainer.getItemIcon().getItemStack().getSize()),
                    new Vector3f(pos.x + 5.0f, pos.y -5.0f, 1.0f), 17.0f,17.0f, new Vector4f(1.0f,1.0f,1.0f,1.0f));
        }

        if(informationToRender == null) {
            return;
        }
        InformationRenderable informationRenderable = informationToRender;

            if (informationRenderable.getCurrentFrame() >= informationRenderable.getMaxFrames()) {
                renderMesh.removeRenderable(informationToRender.getItemIcon());
                renderMesh.removeRenderable(itemContainer);
                informationToRender = null;
                InformationRenderable.counter = 0;
                return;
            }

            int maxAmountFrame = informationRenderable.getMaxFrames();
            int currentAmountFrame = informationRenderable.getCurrentFrame();
            float transparency = (float)maxAmountFrame / (float)currentAmountFrame;
            FontRenderer.textFont.drawText(informationRenderable.getText(),
                    new Vector3f(0.0f,155.0f, 1.0f), 10.0f, 10.0f,
                    new Vector4f(0.0f,0.0f,0.0f,1.0f - 1.0f / transparency));
            informationRenderable.getItemIcon().setColor(new Vector4f(1.0f,1.0f,1.0f,1.0f - 1.0f/transparency));
            itemContainer.setColor(informationRenderable.getItemIcon().getColor());

            renderMesh.changeRenderable(informationRenderable.getItemIcon());
            renderMesh.changeRenderable(itemContainer);
            informationRenderable.setCurrentFrame(currentAmountFrame+1);

        }

    public static void addItemToRender(ItemStack item, int index) {
        ItemIcon itemIcon = new ItemIcon(item, itemContainers[index].getPosition(), 10.0f,10.0f, 1.0f,
                Item.createInstanceFromItem(item.getItemClass()).getTexture(), Position.TOPLEFT);
        itemContainers[index].setItemIcon(itemIcon);
        renderMesh.addRenderable(itemIcon);
    }

    public static void drawInformation(int time, int amount, Class item) {
        ItemIcon itemIcon = new ItemIcon(new Vector3f(0, 155f, 1.0f), 10.0f,10.0f, 1.0f,
                Item.createInstanceFromItem(item).getTexture(), Position.BOTTOMLEFT);

        InformationRenderable.counter += amount;
        InformationRenderable informationRenderable = new InformationRenderable(itemIcon, time);

        if(informationToRender != null) {
            renderMesh.removeRenderable(informationToRender.getItemIcon());
            renderMesh.removeRenderable(itemContainer);
        }else{
           itemContainer = new ItemContainer(itemIcon,
                    new Vector3f(0f,155f,1.0f), 10f, 10.0f, 1.0f,  AssetStorage.getTexture("itemcontainer"), Position.BOTTOMLEFT);
        }

        renderMesh.addRenderable(itemContainer);
        informationToRender = informationRenderable;
        renderMesh.addRenderable(informationRenderable.getItemIcon());
    }

    public static void removeItemToRender(int index) {

    }

    public static ItemContainer[] getItemContainers() {
        return itemContainers;
    }

    public static RenderMesh getRenderMesh() {
        return renderMesh;
    }
}
