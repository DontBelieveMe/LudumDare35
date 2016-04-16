package com.ld35.engine;

import org.newdawn.slick.geom.Vector2f;

public class Tile {
	private int id;
	private boolean isSolid;
	private Vector2f position;
	
	public Tile(int id, boolean isSolid, Vector2f position) {
		this.id = id;
		this.isSolid = isSolid;
		this.position = position;
	}

	public boolean isSolid() {
		return isSolid;
	}

	public int getId() {
		return id;
	}
	
	public Vector2f getPosition() {
		return position;
	}

}
