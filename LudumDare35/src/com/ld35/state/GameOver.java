package com.ld35.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.Utility;
import com.ld35.managers.AudioManager;

public class GameOver extends State{
	boolean requiresReset = false;
	
	public GameOver() {
		super(24);
		
	}
	
	public void tick(GameContainer gc, int delta) {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_R)) {
			AudioManager.playOnce(AudioManager.select);
			requiresReset = true;
		}
	}

	public void render(Graphics g) {
		Vector2f centre = Utility.getCenteredTextPos(slickFont, "You're Dead!");
		slickFont.drawString(centre.x, centre.y, "You're Dead!");
		
		centre = Utility.getCenteredTextPos(slickFont, "Press R to reset!");
		slickFont.drawString(centre.x, centre.y+64, "Press R to reset!");
	}

	public boolean requiresReset() {
		return requiresReset;
	}
	
	public void requiresReset(boolean reset) {
		this.requiresReset = reset;
	}
}
