package com.ld35;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.GameObject;
import com.ld35.engine.Tile;
import com.ld35.managers.GameObjectManager;
import com.ld35.managers.LevelManager;

public class Player extends GameObject {
	public enum Direction {
		LEFT, RIGHT, STOPPED, UP, DOWN
	}

	private Vector2f velocity;

	private float speed = 0.4f;
	private float jumpSpeed = 0.55f;
	private float gravity = 0.001f;
	private boolean canJump = true, inAir = false;

	private Direction direction = Direction.STOPPED;

	public Player(Vector2f position) {
		super(position);

		velocity = new Vector2f(0, 0);

		GameObjectManager.submit(this);
	}

	public void tick(GameContainer gc, int delta) {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_D)) {
			velocity.x = speed;
			direction = Direction.RIGHT;
		} else if (input.isKeyDown(Input.KEY_A)) {
			velocity.x = -speed;
			direction = Direction.LEFT;
		} else {
			velocity.x = 0;
			direction = Direction.STOPPED;
		}

		if (input.isKeyPressed(Input.KEY_SPACE)) {
			jump();
		}
		
		float oldPositionX = position.x;
		position.x += velocity.x * delta;
				
		if(isCollision()) {
			position.x = oldPositionX;
		}
		
		float oldPositionY = position.y;
		position.y += velocity.y * delta;
		
		if(isCollision()) {
			position.y = oldPositionY;
			if(velocity.y > 0) {
				canJump = true;
				inAir = false;
			} else {
				inAir = true;
			}
		}

		if (inAir) {
			velocity.y += gravity * delta;
		}
		if (position.y >= 480 - 32) {
			position.y = 480 - 32;
			inAir = false;
			canJump = true;
		}

	}
	
	private boolean isCollision() {
		boolean collision = false;
		
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 15; y++) {
				Tile platformTile = LevelManager.getCurrentLevel().getTileId(x,
						y, 1);
		
				Vector2f tilePos = platformTile.getPosition();
				tilePos.x *= 32;
				tilePos.y = tilePos.y * 32;
		
				if(platformTile.isSolid()) {
					boolean xCollision = (position.x < tilePos.x+32) && (position.x + 32 > tilePos.x);
					boolean yCollision = (position.y < tilePos.y+32) && (position.y + 32 > tilePos.y);
					if(xCollision&&yCollision)
						collision=true;
				}
			}
		}
		return collision;
	}
	
	private void jump() {
		if (canJump) {
			velocity.y = -jumpSpeed;
			canJump = false;
			inAir = true;
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		
		if(isCollision())
			g.setColor(Color.blue);
		g.fillRect(position.x, position.y, 32, 32);
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public Direction getDirection() {
		return direction;
	}
}
