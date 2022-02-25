package me.anselm.game.world.drops;

import me.anselm.game.entities.player.items.Item;

import java.util.HashMap;
import java.util.Random;

public class LootTable {

    private int percentage;
    private Class item;
    private int maxAmount;
    private Random random;

    public LootTable(Class loot, int chance, int maximum) {
        this.item = loot;
        this.percentage = chance;
        this.maxAmount = maximum;
        this.random = new Random();
    }



    public int loot() {

        if(random.nextInt(100) <= percentage) {
            return random.nextInt(maxAmount);
        }

        return 0;
    }

    public Class getItem() {
        return item;
    }

    public void setItem(Class item) {
        this.item = item;
    }
}
