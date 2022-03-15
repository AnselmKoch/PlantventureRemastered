package me.anselm.game.world.levels.layouts.layout.easy;

import me.anselm.game.entities.enemies.Beetle;
import me.anselm.game.entities.enemies.Zombie;
import me.anselm.game.world.levels.layouts.LevelLayout;
import me.anselm.game.world.levels.layouts.SpawnInformation;
import org.joml.Vector3f;

public class EasyLayout02 extends LevelLayout {

    public EasyLayout02() {
        super();

        this.getSpawnInformationList().add(new SpawnInformation(Zombie.class, new Vector3f(0.0f,0.0f,0.0f)));
        this.getSpawnInformationList().add(new SpawnInformation(Zombie.class, new Vector3f(400.0f,0.0f,0.0f)));
        this.getSpawnInformationList().add(new SpawnInformation(Zombie.class, new Vector3f(400.0f,200.0f,0.0f)));
        this.getSpawnInformationList().add(new SpawnInformation(Zombie.class, new Vector3f(0.0f,200.0f,0.0f)));
    }
}
