package com.ld35.managers;

import org.newdawn.slick.geom.Vector2f;

import com.ld35.Player;

public class GameManager {
	private Player player;
	
	public GameManager() {
		player = new Player(new Vector2f(0, 480-32));
	}
	
	
}
