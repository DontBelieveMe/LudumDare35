package com.ld35.engine;

import org.newdawn.slick.geom.Vector2f;

import com.ld35.PlayerState;

public class Tile {
	private int id;
	private boolean isSolid;
	private Vector2f position;
	private PlayerState collidesWith;

	private boolean hurtsHumans;
	private boolean hurtsBirds;

	public Tile(int id, boolean isSolid, Vector2f position, String collidesWith, String harmfulTo) {
		this.id = id;
		this.isSolid = isSolid;
		this.position = position;

		if (collidesWith.equalsIgnoreCase("bird"))
			this.collidesWith = PlayerState.BIRD;
		else if(collidesWith.equalsIgnoreCase("human"))
			this.collidesWith = PlayerState.HUMAN;
		
		if(harmfulTo.equalsIgnoreCase("human")) {
			this.hurtsHumans = true;
			this.hurtsBirds = false;
		} else if(harmfulTo.equalsIgnoreCase("bird")) {
			this.hurtsBirds = true;
			this.hurtsHumans = false;
		}
		
		System.out.println(this.hurtsHumans);
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

	public PlayerState getCollidesWith() {
		return collidesWith;
	}

	public boolean hurtsHumans() {
		return hurtsHumans;
	}

	public boolean hurtsBirds() {
		return hurtsBirds;
	}
	
	
}
