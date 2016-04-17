package com.ld35.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class State {
	public abstract void tick(GameContainer gc, int delta);
	public abstract void render(Graphics g);
}
