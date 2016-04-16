package com.ld35.engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;



public abstract class GameObject {
	protected Vector2f position;

	public GameObject(Vector2f position) {
		this.position = position;
	}	
	
	public Vector2f getPosition() {
		return position;
	}
	
	public abstract void tick(GameContainer gc, int delta);
	public abstract void draw(Graphics g);
}
