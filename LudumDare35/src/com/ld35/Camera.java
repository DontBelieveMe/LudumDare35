package com.ld35;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.Player.Direction;
import com.ld35.levels.Level;
import com.ld35.managers.LevelManager;

public class Camera {
	private Vector2f position;

	private Vector2f velocity;

	public Camera(float x, float y) {
		this.position = new Vector2f(x, y);
		this.velocity = new Vector2f(0, 0);
	}

	public void tick(GameContainer gc, int delta, Player player) {
		moveIfNessersary(player);

		this.position.x += velocity.x * delta;
	}

	public void moveIfNessersary(Player player) {
		Vector2f playerPosition = player.getPosition();
		float offset = 64;
		
		if (playerPosition.x > (this.position.x + 480 - offset)) {
			velocity.x = 0.4f;
		} else if (playerPosition.x < this.position.x + offset
				) {
			System.out.println("Hello!");
			velocity.x = -0.4f;
		} else {
			velocity.x = 0;
		}
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getVelocity() {
		return velocity;
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public void setVelocity(float d) {
		velocity.x = d;
		velocity.y = d;
	}
}
