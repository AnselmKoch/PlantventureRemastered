package me.anselm.game.world.levels.layouts.layout.easy;

import me.anselm.game.entities.enemies.Beetle;
import me.anselm.game.world.levels.layouts.Difficulty;
import me.anselm.game.world.levels.layouts.LevelLayout;
import me.anselm.game.world.levels.layouts.SpawnInformation;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class EasyLeayout01 extends LevelLayout {

    public static final Difficulty difficulty = Difficulty.EASY;

    public EasyLeayout01() {
        super();
        this.getSpawnInformationList().add(new SpawnInformation(Beetle.class, new Vector3f(0.0f,0.0f,0.0f)));
        this.getSpawnInformationList().add(new SpawnInformation(Beetle.class, new Vector3f(400.0f,0.0f,0.0f)));
        this.getSpawnInformationList().add(new SpawnInformation(Beetle.class, new Vector3f(400.0f,200.0f,0.0f)));
        this.getSpawnInformationList().add(new SpawnInformation(Beetle.class, new Vector3f(0.0f,200.0f,0.0f)));
    }
}
