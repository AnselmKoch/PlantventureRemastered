package me.anselm.game.entities.player.inventory;

import me.anselm.game.Game;
import me.anselm.game.entities.player.items.BasicBullet;
import me.anselm.game.entities.player.items.Item;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.utils.LoggerUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory {
    private static final Logger logger = LoggerUtils.getLogger(Inventory.class);

    private ItemStack[] itemStacks;
    private int currentItemstack;
    public Inventory() {
        itemStacks = new ItemStack[5];
        this.currentItemstack = 0;
    }

    public void removeItem(Class clazz) {
        if(itemStacks[currentItemstack] == null) {
            return;
        }

        itemStacks[currentItemstack].removeFromStack();
        if(itemStacks[currentItemstack].getSize() == 0) {
            itemStacks[currentItemstack] = null;

            if(findFreeStack(clazz) != 99) {
                currentItemstack = findFreeStack(clazz);
            }
        }
    }

    public boolean containsInInventory(Class clazz) {
        for(ItemStack itemStack : itemStacks) {
            if(itemStack == null) {
                continue;
            }
            if(itemStack.getItemClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    private List<ItemStack> getItemStacksFromClass(Class clazz) {
        List<ItemStack> itemStacks = new ArrayList<>();

        for(ItemStack itemStack : this.itemStacks) {
            if(itemStack == null) {
                continue;
            }

            if(itemStack.getItemClass().equals(clazz)) {
                itemStacks.add(itemStack);
            }
        }

        return itemStacks;
    }

    public void addItem(Class clazz) {
        int freeStack = findFreeStack(clazz);

        int nullStack = findNullStack();

        if(freeStack == 99) {
            if(nullStack == 99) {
                return;
            }else{
                itemStacks[nullStack] = new ItemStack(clazz);
                HUDRenderer.addItemToRender(itemStacks[nullStack], nullStack);
                itemStacks[nullStack].addToSTack();

                if(Game.player.getCurrentBullet() == null)  {
                    Game.player.setCurrentBullet(clazz);
                }
                return;
            }
        }

        itemStacks[freeStack].addToSTack();
    }

    private int findNullStack() {
        for(int i = 0; i < itemStacks.length; i++) {
            if(itemStacks[i] == null) {
                return i;
            }
        }
        return 99;
    }

    private int findFreeStack(Class clazz) {
        for(int i = 0; i < itemStacks.length; i++) {
            if(itemStacks[i] == null) {
                continue;
            }

            if(itemStacks[i].getSize() != 99 && itemStacks[i].getItemClass().equals(clazz)) {
                return i;
            }
        }

        return 99;
    }

    public ItemStack getItemStack(int key) {
        return this.itemStacks[key];
    }

    public int getCurrentItemstack() {
        return currentItemstack;
    }

    public void setCurrentItemstack(int currentItemstack) {
        this.currentItemstack = currentItemstack;
    }

}
