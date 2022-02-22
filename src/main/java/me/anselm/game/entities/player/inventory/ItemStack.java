package me.anselm.game.entities.player.inventory;

import me.anselm.game.entities.player.items.Item;

import java.util.Stack;

public class ItemStack {

    private Item item;
    private Stack<Item> stack;
    private int size;

    public ItemStack(Item item) {
        this.item = item;
        this.stack = new Stack<>();
    }

    public boolean addToSTack(Item item) {
        if(stack.size() < 100) {
            stack.push(item);
            size++;
            return true;
        }else{
            return false;
        }
    }

    public boolean removeFromStack() {
        stack.pop();
        size--;
        return true;
    }

    public Item getItem() {
        return this.item;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
