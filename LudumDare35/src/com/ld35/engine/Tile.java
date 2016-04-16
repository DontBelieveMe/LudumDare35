package com.ld35.engine;

public class Tile {
	private int id;
	private boolean isSolid;
	
	public Tile(int id, boolean isSolid) {
		this.id = id;
		this.isSolid = isSolid;
	}

	public boolean isSolid() {
		return isSolid;
	}

	public int getId() {
		return id;
	}

	
	
}
