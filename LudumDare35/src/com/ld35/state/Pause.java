package com.ld35.state;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.Utility;


public class Pause extends State{
	public void tick(GameContainer gc, int delta) {
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_E)) {
			gc.exit();
		}
	}
	
	public void render(Graphics g) {
		Font font2 = Font.decode(Font.SERIF);
		font2 = font2.deriveFont(24f);
		TrueTypeFont font = new TrueTypeFont(font2, true);
		Vector2f centre = Utility.getCenteredTextPos(font, "PAUSED");
		
		font.drawString(centre.x,
				centre.y, "PAUSED");
		centre = Utility.getCenteredTextPos(font, "Press E to exit");
		font.drawString(centre.x, centre.y,
				"Press E to exit.");
	}
}
