package com.ld35.engine;

import org.newdawn.slick.geom.Vector2f;

import com.ld35.PlayerState;

public class Tile {
	private int id;
	private boolean isSolid;
	private Vector2f position;
	private PlayerState collidesWith;

	public Tile(int id, boolean isSolid, Vector2f position, String collidesWith) {
		this.id = id;
		this.isSolid = isSolid;
		this.position = position;

		if (collidesWith.equalsIgnoreCase("bird"))
			this.collidesWith = PlayerState.BIRD;

		System.out.println(this.collidesWith);
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

	public boolean collidesWith(PlayerState state) {
		return collidesWith == state;
	}
}
