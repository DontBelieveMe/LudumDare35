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
		Level level = LevelManager.getCurrentLevel();
		
		
		if (playerPosition.x > (this.position.x + 480 - offset)) {
			velocity.x = 0.2f;
		} else if (playerPosition.x < this.position.x + offset
				&& player.getDirection() == Direction.LEFT) {
			velocity.x = -0.2f;
		} else {
			velocity.x = 0;
		}
		if (this.position.x <= 0) {
			this.position.x = 0;
		} else if(this.position.x + 480 >= level.getRealWidthInPixels()) 
			this.position.x = level.getRealWidthInPixels() - 480;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getVelocity() {
		return velocity;
	}
}
