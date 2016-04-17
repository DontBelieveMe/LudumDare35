package com.ld35.managers;

import org.newdawn.slick.GameContainer;

import com.ld35.Camera;
import com.ld35.Player;
import com.ld35.levels.Level;
import com.ld35.levels.LevelTwo;
import com.ld35.levels.TestLevel;

public class LevelManager {
	private Level[] levels = { new TestLevel(), new LevelTwo(), };

	private int index;
	public Level currentLevel;
	public static Level getCurrentLevel() {
		return GameManager.levelManager.currentLevel;
	}

	public LevelManager() {
		this.index = 0;
		currentLevel = levels[index];
	}

	public void gotoNextLevel(Player player, Camera camera) {
		if (index != levels.length - 1) {
			index += 1;
			//LudumDare35.gameManager.reset();
		}
	}

	public void gotoPreviousLevel() {
		if (index != 0) {
			index -= 1;
		}
	}

	public void renderCurrentLevel() {
		levels[index].draw();
	}

	public void tick(GameContainer gc, int delta) {
		levels[index].tick(gc, delta);
	}

	public Level[] getLevels() {
		return levels;
	}

	public int getLevelIndex() {
		return index;
	}

}
