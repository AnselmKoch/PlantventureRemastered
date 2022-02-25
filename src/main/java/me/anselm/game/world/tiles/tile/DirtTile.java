package me.anselm.game.world.tiles.tile;

import me.anselm.game.entities.player.Player;
import me.anselm.game.entities.player.items.StoneBullet;
import me.anselm.game.world.drops.LootTable;
import me.anselm.game.world.tiles.Tile;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public class DirtTile extends Tile {

    public DirtTile(Vector3f position, float width, float height, float size, Position center) {
        super(position, width, height, size, AssetStorage.getTexture("dirt"), center);

        this.setLootTable(new LootTable(StoneBullet.class, 30, 5));
    }

    @Override
    public void setInteractable(boolean interactable) {
        this.setLooted(interactable);
    }

    @Override
    public boolean isInteractable() {
        return this.isLooted();
    }

    @Override
    public void onInteract(Player player) {

        if(!this.isInteractable()) {
            HUDRenderer.drawInformation(100, "already looted");
            return;
        }


        this.setInteractable(false);

        int lootedAmount = this.getLootTable().loot();
        Class lootItem = this.getLootTable().getItem();

        if(lootedAmount == 0) {
            return;
        }

       for(int i = 0; i < lootedAmount; i++) {
           player.getInventory().addItem(lootItem);
       }
        HUDRenderer.drawInformation(100, lootedAmount, lootItem);
    }
}
