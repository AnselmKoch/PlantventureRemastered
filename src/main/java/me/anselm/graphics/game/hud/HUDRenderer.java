package me.anselm.graphics.game.hud;

import me.anselm.game.Game;
import me.anselm.game.entities.player.Player;
import me.anselm.game.entities.player.inventory.ItemStack;
import me.anselm.game.entities.player.items.Item;
import me.anselm.game.powerups.Powerup;
import me.anselm.game.world.hints.PlayerHeart;
import me.anselm.game.world.hints.PointingArrow;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.font.FontRenderer;
import me.anselm.graphics.game.items.ItemIcon;
import me.anselm.graphics.mesh.RenderMesh;
import me.anselm.graphics.shaders.Shader;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;


public class HUDRenderer {
    private static final Logger logger = LoggerUtils.getLogger(HUDRenderer.class);

    private static ItemContainer[] itemContainers;
    private static RenderMesh renderMesh;

    private static ItemContainer itemContainer;

    private static InformationRenderable informationPickUp;
    private static InformationRenderable informationLooted;
    private static InformationRenderable[] informationItem;

    private static PlayerHeart[] playerHearts;

    private static PointingArrow[] pointingArrows;

    private static boolean showPointingArrows;

    public static void init() {
        logger.info("Initializing HUD renderer...");

        showPointingArrows = false;

        renderMesh  = new RenderMesh(Shader.STANDART, 100);


        informationItem = new InformationRenderable[4];
        itemContainers = new ItemContainer[5];
        resetInventory();

        PointingArrow pointingArrow = new PointingArrow(new Vector3f(400 - 15f / 2f, Window.WORLDHEIGHT / 2, 0.0f),
                15.0f, 15.0f, 1.0f,AssetStorage.getTexture("arrow"), Position.CENTER);



       PointingArrow pointingArrow1 = new PointingArrow(new Vector3f(7.5f,100.0f, 0.0f),
                -15.0f, 15.0f, 1.0f,AssetStorage.getTexture("arrow"), Position.CENTER);

        HUDRenderer.getRenderMesh().addRenderable(pointingArrow1);
        HUDRenderer.getRenderMesh().addRenderable(pointingArrow);

        PointingArrow pointingArrow2 = new PointingArrow(new Vector3f(200.0f, 200f - 7.5f, 0.0f),
                15.0f, -15.0f, 1.0f, AssetStorage.getTexture("arrowup"), Position.CENTER);
        HUDRenderer.getRenderMesh().addRenderable(pointingArrow2);

        PointingArrow pointingArrow3 = new PointingArrow(new Vector3f(200.0f, 7.5f, 0.0f),
                15.0f, 15.0f, 1.0f, AssetStorage.getTexture("arrowup"), Position.CENTER);
        HUDRenderer.getRenderMesh().addRenderable(pointingArrow3);

        pointingArrows = new PointingArrow[] {
                pointingArrow, pointingArrow1,pointingArrow2,pointingArrow3
        };
    }

    public static void render() {
        logger.info("Rendering HUD...");

        renderMesh.render();

        FontRenderer.textFont.drawText("FPS:" + Window.fps +", UPS: " + Window.ups, new Vector3f(0.0f,10.0f,0.0f), 10,10,
                new Vector4f(1.0f,1.0f,1.0f,1.0f));

        FontRenderer.textFont.drawText("Kills:" + Game.monsterDeathCounter,
                new Vector3f(375.0f,200.0f,0.0f), 15.0f,10.0f, new Vector4f(1.0f,1.0f,1.0f,1.0f));

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

            if(i == Game.player.getInventory().getCurrentItemstack()) {
                FontRenderer.textFont.drawText(String.valueOf(itemContainer.getItemIcon().getItemStack().getSize()),
                        new Vector3f(pos.x + 5.0f, pos.y - 5.0f, 1.0f), 17.0f, 17.0f, new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
            }else{
                FontRenderer.textFont.drawText(String.valueOf(itemContainer.getItemIcon().getItemStack().getSize()),
                        new Vector3f(pos.x + 5.0f, pos.y - 5.0f, 1.0f), 17.0f, 17.0f, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
            }
        }

        float currY = 140.0f;
        for(int i = 0; i < informationItem.length; i++) {
            if(informationItem[i] == null) {
                continue;
            }

            InformationRenderable informationRenderable = informationItem[i];
            FontRenderer.textFont.drawText(informationRenderable.getText(),
                    new Vector3f(180.0f, currY, 1.0f), 8.0f,8.0f,
                    new Vector4f(0.0f,0.0f,0.0f,1.0f - 1.0f / informationRenderable.getTransparency()));

            currY += 12.0f;
        }

        if(informationPickUp != null) {
            InformationRenderable informationRenderable = informationPickUp;

            FontRenderer.textFont.drawText(informationRenderable.getText(),
                    new Vector3f(0.0f, 155.0f, 1.0f), 10.0f, 10.0f,
                    new Vector4f(0.0f, 0.0f, 0.0f, 1.0f - 1.0f / informationPickUp.getTransparency()));
        }

        if(informationLooted != null) {

            FontRenderer.textFont.drawText(informationLooted.getText(),
                    new Vector3f(0.0f, 145.0f, 1.0f), 10.0f, 10.0f,
                    new Vector4f(1.0f, 1.0f, 1.0f, 1.0f - 1.0f / informationLooted.getTransparency()));
        }
    }

    public static void tick() {

        for(InformationRenderable informationRenderable1 : informationItem) {
            if(informationRenderable1 == null) {
                continue;
            }

            informationRenderable1.setCurrentFrame(informationRenderable1.getCurrentFrame() +1);

            if (informationRenderable1.getCurrentFrame() >= informationRenderable1.getMaxFrames()) {
                informationRenderable1 = null;
            }


        }

        InformationRenderable informationRenderable = informationPickUp;



        if(informationRenderable != null) {

            informationRenderable.setCurrentFrame(informationRenderable.getCurrentFrame() + 1);
            informationRenderable.getItemIcon().setColor(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f - 1.0f / informationRenderable.getTransparency()));
            if (informationRenderable.getCurrentFrame() >= informationRenderable.getMaxFrames()) {
                renderMesh.removeRenderable(informationPickUp.getItemIcon());
                renderMesh.removeRenderable(itemContainer);
                informationPickUp = null;
                InformationRenderable.counter = 0;
                return;
            }
            renderMesh.changeRenderable(informationRenderable.getItemIcon());
            itemContainer.setColor(new Vector4f(1.0f,1.0f,1.0f,1.0f -1.0f / informationRenderable.getTransparency()));
            renderMesh.changeRenderable(itemContainer);
        }

        if(informationLooted != null) {
            informationLooted.setCurrentFrame(informationLooted.getCurrentFrame() + 1);
        }

    }

    public static void toggleArrows() {
        for(PointingArrow pointingArrow : pointingArrows) {
            if(showPointingArrows) {
                pointingArrow.setColor(new Vector4f(1.0f,1.0f,1.0f,1.0f));
            }else{
                pointingArrow.setColor(new Vector4f(0.2f,0.2f,0.2f,1.0f));
            }

            renderMesh.changeRenderable(pointingArrow);
        }
    }

    public static void addItemToRender(ItemStack item, int index) {
        ItemIcon itemIcon = new ItemIcon(item, itemContainers[index].getPosition(), 10.0f,10.0f, 1.0f,
                Item.createInstanceFromItem(item.getItemClass()).getTexture(), Position.TOPLEFT);
        itemContainers[index].setItemIcon(itemIcon);
        renderMesh.addRenderable(itemIcon);
    }

    public static void drawInformation(int time, String text) {
        informationLooted = new InformationRenderable(text, time);
    }

    public static void drawItemPickUp(int time, Powerup powerup) {
        informationItem[0] = new InformationRenderable("+" + powerup.getDamage() + " damage", time);
        informationItem[1] = new InformationRenderable("+" + powerup.getHealth() + " health", time);
        informationItem[2] = new InformationRenderable("+" + powerup.getShotSpeed() + " shot speed", time);
        informationItem[3] = new InformationRenderable("+" + powerup.getSpeed() + " speed", time);
    }

    public static void resetInventory() {
        int itemWidth = 10;
        int currX = 0;

        for(int i = 0; i < itemContainers.length; i++) {
            if(itemContainers[i] != null) {
                renderMesh.removeRenderable(itemContainers[i]);
            }
            itemContainers[i] = new ItemContainer(null,
                    new Vector3f(currX, 200.0f, 2.0f), 10.0f, 10.0f, 1.0f,
                    AssetStorage.getTexture("itemcontainer"), Position.TOPLEFT);
            renderMesh.addRenderable(itemContainers[i]);
            currX+=itemWidth;
        }
    }

    public static void updatePlayerHearts() {
        Player player = Game.player;

        if(playerHearts == null) {
            playerHearts = new PlayerHeart[Player.MAX_HEALTH];
        }else{
            for(PlayerHeart playerHeart : playerHearts) {
                renderMesh.removeRenderable(playerHeart);
            }
        }

        int currX = 0;
        int currY = 180;

        for(int i = 0; i < Player.MAX_HEALTH; i++) {

            Texture texture;

            if (i >= Game.player.getHealth()) {
                texture = AssetStorage.getTexture("emptyheart");

            } else {
                texture = AssetStorage.getTexture("fullheart");
            }

            playerHearts[i] = new PlayerHeart(new Vector3f(currX, currY, 1.0f), 7.0f, 7.0f, 1.0f,
                   texture, Position.TOPLEFT);

            renderMesh.addRenderable(playerHearts[i]);

            currX += 8;
        }
    }
    public static void drawInformation(int time, int amount, Class item) {
        ItemIcon itemIcon = new ItemIcon(new Vector3f(0, 155f, 1.0f), 10.0f,10.0f, 1.0f,
                Item.createInstanceFromItem(item).getTexture(), Position.BOTTOMLEFT);

        InformationRenderable.counter += amount;
        InformationRenderable informationRenderable = new InformationRenderable(itemIcon, time);

        if(informationPickUp != null) {
            renderMesh.removeRenderable(informationPickUp.getItemIcon());
            renderMesh.removeRenderable(itemContainer);
        }else{
            itemContainer = new ItemContainer(itemIcon,
                    new Vector3f(0f,155f,1.0f), 10f, 10.0f, 1.0f,  AssetStorage.getTexture("itemcontainer"), Position.BOTTOMLEFT);
        }

        renderMesh.addRenderable(itemContainer);
        informationPickUp = informationRenderable;
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

    public static void setShowPointingArrows(boolean show) {
        showPointingArrows = show;
    }
}