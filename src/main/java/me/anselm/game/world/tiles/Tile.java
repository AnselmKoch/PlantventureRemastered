package me.anselm.game.world.tiles;

import me.anselm.game.powerups.Powerup;
import me.anselm.game.world.Interactable;
import me.anselm.game.world.drops.LootTable;
import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public abstract class Tile extends Renderable implements Interactable {

    private LootTable lootTable;
    private boolean isInteractable;
    private Powerup powerup;

    public Tile(Vector3f position, float width, float height, float size, Texture texture, Position center) {
        super(position, width, height, size, texture, center);
        this.isInteractable = true;
    }

    public Powerup getPowerup() {
        return powerup;
    }

    public void setPowerup(Powerup powerup) {
        this.powerup = powerup;
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
    }

    public boolean isLooted() {
        return isInteractable;
    }

    public void setLooted(boolean looted) {
        isInteractable = looted;
    }
}
