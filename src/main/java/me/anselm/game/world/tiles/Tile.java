package me.anselm.game.world.tiles;

import me.anselm.game.world.Interactable;
import me.anselm.game.world.drops.LootTable;
import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public abstract class Tile extends Renderable implements Interactable {

    private LootTable lootTable;

    public Tile(Vector3f position, float width, float height, float size, Texture texture, Position center) {
        super(position, width, height, size, texture, center);
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
    }
}
