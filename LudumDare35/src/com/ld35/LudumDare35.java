package com.ld35;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class LudumDare35 extends BasicGame{

	public LudumDare35(String title) {
		super(title);

	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		
	}

	public void init(GameContainer gc) throws SlickException {
		
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		
	}
	
	public static void main(String args []) {
		try {
			AppGameContainer app = new AppGameContainer(new LudumDare35("Ludum Dare 35"));
			app.setDisplayMode(640, 480, false);
			app.setVerbose(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
