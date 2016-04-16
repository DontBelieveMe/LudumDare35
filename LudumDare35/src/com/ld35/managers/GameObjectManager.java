package com.ld35.managers;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.ld35.engine.GameObject;

public class GameObjectManager {
	public static GameObjectManager instance = new GameObjectManager();
	
	private static List<GameObject> gameObjects = new ArrayList<GameObject>();
	
	public static void submit(GameObject obj) {
		gameObjects.add(obj);
	}
	
	public void draw(Graphics g) {
		for(GameObject obj : gameObjects) {
			obj.draw(g);
		}
	}
	
	public void tick(GameContainer gc, int delta) {
		for(GameObject obj : gameObjects) {
			obj.tick(gc, delta);
		}
	}
}
