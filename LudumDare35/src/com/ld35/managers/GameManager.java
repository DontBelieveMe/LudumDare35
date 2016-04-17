package com.ld35.managers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.Camera;
import com.ld35.Player;
import com.ld35.state.Pause;
import com.ld35.state.State;

public class GameManager {
	public enum GameState {
		PLAYING, PAUSED
	}

	private Player player;
	private Camera camera;

	public static LevelManager levelManager;
	private GameState state = GameState.PLAYING;

	private State pauseState = new Pause();
	
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
			pauseState.tick(gc, delta);
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
			pauseState.render(g);

			break;
		default:
			break;
		}
	}

	public GameState getState() {
		return state;
	}
}
