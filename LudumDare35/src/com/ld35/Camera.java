package com.ld35;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

public class Camera {
	private Vector2f position;
	private Vector2f velocity;
	
	public Camera(float x, float y) {
		this.position = new Vector2f(position.x, position.y);
		this.velocity = new Vector2f(0, 0);
	}
	
	public void tick(GameContainer gc, int delta) {
	}
	
	public void move() {
		
	}
}
