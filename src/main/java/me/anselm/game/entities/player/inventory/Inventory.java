package me.anselm.game.entities.player.inventory;

import me.anselm.game.entities.player.items.Item;
import me.anselm.graphics.game.hud.HUDRenderer;

import java.util.HashMap;

public class Inventory {

    private ItemStack[] itemStacks;
    private HashMap<Class, Integer> map;

    public Inventory() {
        this.itemStacks = new ItemStack[5];
        map = new HashMap<>();
    }

    public ItemStack[] getItemStacks() {
        return this.itemStacks;
    }

    public void removeItem(Class clazz) {
        int index = map.get(clazz);

        itemStacks[index].removeFromStack();
    }

    public void addItem(Class clazz, Item item) {
        if(!map.containsKey(clazz)) {
            int indexFree = findFreeStack();
            map.put(clazz, indexFree);

            if (indexFree == 99) {
                return;
            } else {
                itemStacks[indexFree] = new ItemStack(item);
                itemStacks[indexFree].addToSTack(item);

                HUDRenderer.addItemToRender(itemStacks[indexFree], indexFree);
            }
        }else{
            int index = map.get(item.getClass());
            if(itemStacks[index].getSize() != 99) {
                System.out.println("ADDED LIKE THIS");
                itemStacks[index].addToSTack(item);
            } else {

                int indexFree = findFreeStack();

                if (indexFree == 99) {
                    return;
                } else {
                    itemStacks[indexFree] = new ItemStack(item);
                    itemStacks[indexFree].addToSTack(item);
                    HUDRenderer.addItemToRender(itemStacks[indexFree], indexFree);
                }
            }
        }
    }

    private int findFreeStack() {
        for(int i = 0; i < itemStacks.length; i++) {
            if(itemStacks[i] == null  || itemStacks[i].getSize() != 99) {
                return i;
            }
        }
        return 99;
    }
}
