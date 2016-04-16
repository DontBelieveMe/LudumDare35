package com.ld35.levels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.ld35.engine.Tile;

public abstract class Level {
	protected TiledMap map;
	
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
	
	public Tile getTileId(int x, int y, int tileLayer) {
		int id = map.getTileId(x, y, tileLayer);
		System.out.println(id);
		String solid = map.getTileProperty(id, "isSolid", "not-set");
		boolean isSolid = Boolean.parseBoolean(solid);
		
		return new Tile(id, isSolid, new Vector2f(x, y));
	}
	
	public int getWidthInTiles() {
		return map.getWidth();
	}
	
	public int getHeightInTiles() {
		return map.getHeight();
	}
	
	public int getRealWidthInPixels() {
		return map.getWidth() * 32;
	}
	
	public int getRealHeightInPixels() {
		return map.getHeight() * 32;
	}
	
	
}
