package com.ld35.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.Utility;
import com.ld35.managers.AudioManager;


public class Pause extends State {
	
	public Pause() {
		super(24);
	}
	
	public void tick(GameContainer gc, int delta) {
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_E)) {
			AudioManager.playOnce(AudioManager.select);
			gc.exit();
		}
	}
	
	public void render(Graphics g) {
		Vector2f centre = Utility.getCenteredTextPos(slickFont, "PAUSED");
		slickFont.drawString(centre.x,
				centre.y, "PAUSED");
		
		centre = Utility.getCenteredTextPos(slickFont, "Press E to exit");
		slickFont.drawString(centre.x, centre.y + 64,
				"Press E to exit.");
	}
}
