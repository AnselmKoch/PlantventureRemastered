package me.anselm.game.entities.player.inventory;

import me.anselm.game.entities.player.items.BasicBullet;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.utils.LoggerUtils;
import org.slf4j.Logger;

import java.util.HashMap;

public class Inventory {
    private static final Logger logger = LoggerUtils.getLogger(Inventory.class);

    private ItemStack[] itemStacks;
    private HashMap<Class, Integer> map;

    private int currentItemstack;

    public Inventory() {
        this.itemStacks = new ItemStack[5];
        map = new HashMap<>();
        this.currentItemstack = 0;
    }

    public ItemStack[] getItemStacks() {
        return this.itemStacks;
    }

    public void removeItem(Class clazz) {

        if(itemStacks[currentItemstack] != null) {
            itemStacks[currentItemstack].removeFromStack();
            if(itemStacks[currentItemstack].getSize() == 0) {
                map.remove(itemStacks[currentItemstack].getItemClass());
                itemStacks[currentItemstack] = null;
            }
        }else{

        int index = map.get(clazz);

        itemStacks[index].removeFromStack();

        if(itemStacks[index].getSize() == 0) {
            itemStacks[index] = null;
            map.remove(clazz);

            for (int i = 0; i < itemStacks.length; i++) {
                ItemStack itemStack = itemStacks[i];
                if (itemStack == null) {
                    continue;
                }

                if (itemStack.getItemClass().equals(clazz)) {
                    map.put(clazz, i);
                    break;
                }
            }
        }
        }
    }

    public ItemStack getStackForItem(Class clazz) {
        return itemStacks[map.get(clazz)];
    }

    public boolean containsInInventory(Class clazz) {
        if(map.get(clazz) == null) {
            return false;
        }else{
            return true;
        }
    }
    public void addItem(Class clazz) {
        logger.info(clazz.getName());
        if(!map.containsKey(clazz)) {
            int indexFree = findFreeStack();

            if (indexFree == 99) {
                return;
            } else {
                map.put(clazz, indexFree);
                itemStacks[indexFree] = new ItemStack(clazz);
                itemStacks[indexFree].addToSTack();

                HUDRenderer.addItemToRender(itemStacks[indexFree], indexFree);
            }
        }else{
            int index = map.get(clazz);
            if(itemStacks[index].getSize() != 99) {
                itemStacks[index].addToSTack();
            } else {
                int indexFree = findFreeStack();

                if (indexFree == 99) {
                    return;
                } else {
                    itemStacks[indexFree] = new ItemStack(clazz);
                    itemStacks[indexFree].addToSTack();
                    map.remove(clazz);
                    map.put(clazz, indexFree);
                    HUDRenderer.addItemToRender(itemStacks[indexFree], indexFree);
                }
            }
        }
    }

    private int findFreeStack() {
        for(int i = 0; i < itemStacks.length; i++) {
            if(itemStacks[i] == null) {
                return i;
            }
        }
        return 99;
    }

    public int getCurrentItemstack() {
        return currentItemstack;
    }

    public void setCurrentItemstack(int currentItemstack) {
        this.currentItemstack = currentItemstack;
    }
}
