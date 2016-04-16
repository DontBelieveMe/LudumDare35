package com.ld35.levels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public abstract class Level {
	private TiledMap map;
	
	public Level(String path) {
		try {
			map = new TiledMap(path);
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public void draw() {
		map.render(0, 0);
	}
	
	public abstract void tick(GameContainer gc, int delta);
}
