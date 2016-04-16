package com.ld35;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.GameObject;
import com.ld35.managers.GameObjectManager;

public class Player extends GameObject {
	private Vector2f velocity;
	private Vector2f acceleration;
	
	public Player(Vector2f position) {
		super(position);
		
		velocity = new Vector2f(0, 0);
		acceleration = new Vector2f(0, 0.01f);
		
		GameObjectManager.submit(this);
	}
	float dx = 0.001f;
	boolean canJump = true, onGround = true;
	public void tick(GameContainer gc, int delta) {
		Input input = gc.getInput();
		
		if(onGround) {
			velocity.x *= 0.9f;

			if(input.isKeyPressed(Input.KEY_SPACE)) {
				if(canJump) {
					System.out.println("HEllo World!");
					velocity.y = -0.1f;
					canJump = false;
				}
			}
		} else {
			canJump = true;
		}
		
		if(!onGround) {
			velocity.y += 0.01f;
		}
		
		if(!onGround && velocity.y > 0) {
			velocity.y -= 1f;
		}
		
		position.x += velocity.x * delta;
		position.y += velocity.y * delta;
		
		if(position.y + 32 > 480) {
			position.y = 480-32-1;
			velocity.y = 0;
			onGround = true;
		}
		
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(position.x, position.y, 32, 32);
	}
}
