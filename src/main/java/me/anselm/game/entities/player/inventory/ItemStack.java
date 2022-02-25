package me.anselm.game.entities.player.inventory;

import me.anselm.game.entities.player.items.Item;

import java.util.Stack;

public class ItemStack {

    private final Class itemClass;
    private int size;

    public ItemStack(Class itemClass) {
            this.itemClass = itemClass;
            size = 0;
    }

    public boolean addToSTack() {
        if(size < 100) {
            size++;
            return true;
        }else{
            return false;
        }
    }

    public boolean removeFromStack() {
        size--;
        return true;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Class getItemClass() {
        return itemClass;
    }
}
