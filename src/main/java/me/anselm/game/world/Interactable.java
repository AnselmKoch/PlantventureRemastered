package me.anselm.game.world;

import me.anselm.game.entities.player.Player;

public interface Interactable {

    void setInteractable(boolean interactable);

    boolean isInteractable();
    void onInteract(Player player);
}
