package com.ld35.engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public abstract class GameObject {
	protected Vector2f position;
	protected boolean hasPhysics;
	
	public GameObject(Vector2f position) {
		this.position = position;
	}
	
	public GameObject(Vector2f position, boolean hasPhysics) {
		this.position = position;
		this.hasPhysics = hasPhysics;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public boolean hasPhysics() {
		return hasPhysics();
	}
	
	public void hasPhysics(boolean physics) {
		hasPhysics = physics;
	}
	
	public abstract void tick(GameContainer gc, int delta);
	public abstract void draw(Graphics g);
}
