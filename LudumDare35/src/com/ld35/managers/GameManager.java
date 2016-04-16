package com.ld35.managers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.Player;

public class GameManager {
	@SuppressWarnings("unused")
	private Player player;
	
	public static LevelManager levelManager;
	
	public GameManager() {
		player = new Player(new Vector2f(0, 480-32));
		
		levelManager = new LevelManager();
		System.out.println(LevelManager.getCurrentLevel().getTileId(14, 15, 1).getPosition());
	}
	
	public void tick(GameContainer gc, int delta) {
		levelManager.tick(gc, delta);
	
	}
	
	public void render(Graphics g) {
		levelManager.renderCurrentLevel();
	}
}
