package com.ld35.engine;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

public class Utility {
	public static Vector2f getCenteredTextPos(TrueTypeFont font, String string) {
		Vector2f vector = new Vector2f();
		vector.x = 640 / 2 - (font.getWidth(string) / 2);
		vector.y = 480 / 2 - (font.getHeight(string) / 2);
		return vector;
	}
}
