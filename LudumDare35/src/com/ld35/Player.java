package com.ld35;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.GameObject;
import com.ld35.managers.GameObjectManager;

public class Player extends GameObject {

	public Player(Vector2f position) {
		super(position);
		
		GameObjectManager.submit(this);
	}
	
	public void tick(GameContainer gc, int delta) {
		
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(position.x, position.y, 32, 32);
	}
}
