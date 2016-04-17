package com.ld35.state;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public abstract class State {
	
	protected Font awtFont;
	protected TrueTypeFont slickFont;
	
	protected float fontSize;

	public State(float fontSize) {
		awtFont = Font.decode(Font.SERIF);
		awtFont = awtFont.deriveFont(fontSize);
		
		slickFont = new TrueTypeFont(awtFont, true);
	}
	
	public abstract void tick(GameContainer gc, int delta);
	public abstract void render(Graphics g);
	
}
