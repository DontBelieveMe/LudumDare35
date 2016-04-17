package com.ld35.managers;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.Camera;
import com.ld35.Player;
import com.ld35.engine.Utility;

public class GameManager {
	public enum GameState {
		PLAYING, PAUSED
	}

	private Player player;
	private Camera camera;

	public static LevelManager levelManager;
	private GameState state = GameState.PLAYING;

	public GameManager() {
		player = new Player(new Vector2f(0, 480 - 64));
		camera = new Camera(0, 0);

		levelManager = new LevelManager();
	}

	public void tick(GameContainer gc, int delta) {
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			if (state == GameState.PLAYING) {
				state = GameState.PAUSED;
			} else if (state == GameState.PAUSED) {
				state = GameState.PLAYING;
			}
		}

		switch (state) {
		case PLAYING:
			levelManager.tick(gc, delta);
			camera.tick(gc, delta, player);
			break;
		case PAUSED:
			if (input.isKeyPressed(Input.KEY_E)) {
				gc.exit();
			}
			break;
		default:
			break;
		}
	}

	public void render(Graphics g) {
		switch (state) {
		case PLAYING:
			Vector2f camPos = camera.getPosition();
			g.translate(-camPos.x, -camPos.y);
			levelManager.renderCurrentLevel();
			break;
		case PAUSED:
			Font font2 = Font.decode(Font.SERIF);
			font2 = font2.deriveFont(24f);
			TrueTypeFont font = new TrueTypeFont(font2, true);
			Vector2f centre = Utility.getCenteredTextPos(font, "PAUSED");
			
			font.drawString(centre.x,
					centre.y, "PAUSED");
			centre = Utility.getCenteredTextPos(font, "Press E to exit");
			font.drawString(centre.x, centre.y,
					"Press E to exit.");

			break;
		default:
			break;
		}
	}

	public GameState getState() {
		return state;
	}
}
