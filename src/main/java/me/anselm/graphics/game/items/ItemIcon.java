package me.anselm.graphics.game.items;

import me.anselm.game.entities.player.inventory.ItemStack;
import me.anselm.game.entities.player.items.Item;
import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public class ItemIcon extends Renderable {

    private ItemStack itemStack;

    public ItemIcon(ItemStack item, Vector3f position, float width, float height, float size, Texture texture, Position orientation) {
        super(position, width, height, size, texture, orientation);
        this.itemStack = item;
    }


    public ItemIcon( Vector3f position, float width, float height, float size, Texture texture, Position orientation) {
        super(position, width, height, size, texture, orientation);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
