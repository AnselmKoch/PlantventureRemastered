package me.anselm.graphics.game.hud;

import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.game.items.ItemIcon;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public class ItemContainer extends Renderable {

    private ItemIcon itemIcon;

    public ItemContainer(ItemIcon itemIcon, Vector3f position, float width, float height, float size, Texture texture, Position center) {
        super(position, width, height, size, texture, center);
        this.itemIcon = itemIcon;
    }

    public ItemIcon getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(ItemIcon itemIcon) {
        this.itemIcon = itemIcon;
    }
}
