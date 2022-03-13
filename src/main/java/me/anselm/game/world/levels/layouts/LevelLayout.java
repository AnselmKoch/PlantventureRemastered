package me.anselm.game.world.levels.layouts;

import java.util.ArrayList;
import java.util.List;

public class LevelLayout {

    private List<SpawnInformation> spawnInformationList;

    private Difficulty difficulty;

    public LevelLayout() {
        spawnInformationList = new ArrayList<>();
    }

    public List<SpawnInformation> getSpawnInformationList() {
        return spawnInformationList;
    }

    public void setSpawnInformationList(List<SpawnInformation> spawnInformationList) {
        this.spawnInformationList = spawnInformationList;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
