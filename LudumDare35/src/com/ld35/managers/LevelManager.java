package com.ld35.managers;

import org.newdawn.slick.GameContainer;

import com.ld35.levels.Level;
import com.ld35.levels.TestLevel;

public class LevelManager {
	private Level [] levels = {
			new TestLevel()
	};
	
	private int index;
	
	public static Level getCurrentLevel() {
		int index = GameManager.levelManager.getLevelIndex();
		return GameManager.levelManager.getLevels()[index];
	}
	
	public LevelManager() {
		this.index = 0;
	}
	
	public void gotoNextLevel() {
		if(index != levels.length-1) {
			index += 1;
		}
	}
	
	public void gotoPreviousLevel() {
		if(index != 0) {
			index -= 1;
		}
	}
	
	public void renderCurrentLevel() {
		levels[index].draw();
	}
	
	public void tick(GameContainer gc, int delta) {
		levels[index].tick(gc, delta);
	}
	
	public Level [] getLevels() {
		return levels;
	}
	
	public int getLevelIndex() {
		return index;
	}
	
}
