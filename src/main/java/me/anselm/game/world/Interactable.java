package me.anselm.game.world;

import me.anselm.game.entities.player.Player;

public interface Interactable {

    void onInteract(Player player);
}
