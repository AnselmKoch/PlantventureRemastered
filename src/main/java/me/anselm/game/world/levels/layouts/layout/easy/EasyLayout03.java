package me.anselm.game.world.levels.layouts.layout.easy;

import me.anselm.game.entities.enemies.Brain;
import me.anselm.game.entities.enemies.Zombie;
import me.anselm.game.world.levels.layouts.LevelLayout;
import me.anselm.game.world.levels.layouts.SpawnInformation;
import org.joml.Vector3f;

public class EasyLayout03 extends LevelLayout {

    public EasyLayout03() {
        super();

        this.getSpawnInformationList().add(new SpawnInformation(Brain.class, new Vector3f(30.0f,15.0f,0.0f)));
        this.getSpawnInformationList().add(new SpawnInformation(Brain.class, new Vector3f(370.0f,15.0f,0.0f)));
        this.getSpawnInformationList().add(new SpawnInformation(Brain.class, new Vector3f(370.0f,185.0f,0.0f)));
        this.getSpawnInformationList().add(new SpawnInformation(Brain.class, new Vector3f(30.0f,185.0f,0.0f)));
    }
}
