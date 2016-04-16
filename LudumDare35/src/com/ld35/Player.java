package com.ld35;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.GameObject;
import com.ld35.managers.GameObjectManager;

public class Player extends GameObject {
	public enum Direction {
		LEFT, RIGHT, STOPPED
	}
	
	private Vector2f velocity;
	private Vector2f acceleration;
	
	private float speed = 0.9f;

	private boolean onGround;
	private boolean jumping;
	
	private Direction direction = Direction.STOPPED;
	
	public Player(Vector2f position) {
		super(position);
		
		velocity = new Vector2f(0, 0);
		acceleration = new Vector2f(0, 0.01f);
		
		GameObjectManager.submit(this);
	}
	
	public void tick(GameContainer gc, int delta) {
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_D)) {
			velocity.x = speed;
			direction = Direction.RIGHT;
		} else if(input.isKeyDown(Input.KEY_A)) {
			velocity.x = -speed;
			direction = Direction.LEFT;
		} else {
			velocity.x = 0;
			direction = Direction.STOPPED;
		}
		
		position.x += velocity.x * delta;
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(position.x, position.y, 32, 32);
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}

	public Vector2f getAcceleration() {
		return acceleration;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public boolean isJumping() {
		return jumping;
	}
	
	public Direction getDirection() {
		return direction;
	}
}
