package com.ld35;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.ld35.managers.GameManager;
import com.ld35.managers.GameManager.GameState;
import com.ld35.managers.GameObjectManager;

public class LudumDare35 extends BasicGame{
	private GameManager gameManager;
	private boolean debug = false;
	
	public LudumDare35(String title) {
		super(title);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		gameManager.render(g);
		if(gameManager.getState() == GameState.PLAYING)
			GameObjectManager.instance.draw(g);
	}

	public void init(GameContainer gc) throws SlickException {
		gameManager = new GameManager();
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		gameManager.tick(gc, delta);
		if(gameManager.getState() == GameState.PLAYING)
			GameObjectManager.instance.tick(gc, delta);
		if(gc.getInput().isKeyPressed(Input.KEY_F5))
			debug = !debug;
		
		gc.setShowFPS(debug);
	}
	
	public static void main(String args []) {
		try {
			AppGameContainer app = new AppGameContainer(new LudumDare35("Ludum Dare 35"));
			app.setDisplayMode(640, 480, false);
			app.setVerbose(false);
			app.setShowFPS(false);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
