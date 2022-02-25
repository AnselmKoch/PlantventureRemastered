package me.anselm.game.world.hints;

import me.anselm.game.entities.player.Player;
import me.anselm.game.world.Interactable;
import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public class PointingArrow extends Renderable implements Interactable {

    public PointingArrow(Vector3f position, float width, float height, float size,Texture texture, Position orientation) {
        super(position, width, height, size, texture, orientation);
    }

    @Override
    public void setInteractable(boolean interactable) {

    }

    @Override
    public boolean isInteractable() {
        return false;
    }

    @Override
    public void onInteract(Player player) {

    }
}
