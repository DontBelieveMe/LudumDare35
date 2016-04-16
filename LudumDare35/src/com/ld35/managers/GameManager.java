package com.ld35.managers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.Camera;
import com.ld35.Player;

public class GameManager {
	private Player player;
	private Camera camera;
	
	public static LevelManager levelManager;
	
	public GameManager() {
		player = new Player(new Vector2f(0, 480-64));
		camera = new Camera(0, 0);
		
		levelManager = new LevelManager();
	}
	
	public void tick(GameContainer gc, int delta) {
		levelManager.tick(gc, delta);
		camera.tick(gc, delta, player);
	}
	
	public void render(Graphics g) {
		Vector2f camPos = camera.getPosition();
		g.translate(-camPos.x, -camPos.y);
		levelManager.renderCurrentLevel();
	}
}
