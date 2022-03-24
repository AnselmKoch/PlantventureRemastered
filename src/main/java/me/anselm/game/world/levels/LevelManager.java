package me.anselm.game.world.levels;

import me.anselm.game.Game;
import me.anselm.game.world.levels.layouts.Difficulty;
import me.anselm.game.world.levels.layouts.LevelLayout;
import me.anselm.game.world.levels.layouts.layout.easy.EasyLayout02;
import me.anselm.game.world.levels.layouts.layout.easy.EasyLayout03;
import me.anselm.game.world.levels.layouts.layout.easy.EasyLeayout01;
import me.anselm.game.world.levels.layouts.layout.peaceful.PeaceFulLayout00;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelManager {

    private Level currentLevel;

    public static Vector2i levelIndex;

    public static Set<String> levelSet = new HashSet<>();

    public static HashMap<Difficulty, List<LevelLayout>> levelLayouts;

    public LevelManager() {


        levelLayouts = new HashMap<>();

        levelLayouts.put(Difficulty.EASY, new ArrayList<>());
        levelLayouts.put(Difficulty.MEDIUM, new ArrayList<>());
        levelLayouts.put(Difficulty.PEACEFUL, new ArrayList<>());
        levelLayouts.put(Difficulty.HARD, new ArrayList<>());

        levelLayouts.get(Difficulty.PEACEFUL).add(new PeaceFulLayout00());
        levelLayouts.get(Difficulty.EASY).add(new EasyLeayout01());
        levelLayouts.get(Difficulty.EASY).add(new EasyLayout02());
        levelLayouts.get(Difficulty.EASY).add(new EasyLayout03());

        levelIndex = new Vector2i(0,0);
        currentLevel = new Level(Game.difficultyLevel);
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }


}
