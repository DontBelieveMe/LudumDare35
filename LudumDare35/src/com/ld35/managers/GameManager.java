package com.ld35.managers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.Camera;
import com.ld35.Player;
import com.ld35.state.GameOver;
import com.ld35.state.MainMenu;
import com.ld35.state.Pause;

public class GameManager {
	public enum GameState {
		PLAYING, PAUSED, GAME_OVER, MAIN_MENU
	}

	private Player player;
	private Camera camera;

	public static LevelManager levelManager;
	private GameState state = GameState.MAIN_MENU;

	private Pause pauseState;
	private GameOver gameOverState;
	private MainMenu mainMenuState;
	
	public GameManager() {
		player = new Player(new Vector2f(0, 480 - 64));
		camera = new Camera(0, 0);

		levelManager = new LevelManager();
		AudioManager.create();
		
		pauseState = new Pause();
		gameOverState = new GameOver();
		mainMenuState = new MainMenu();
	}

	private void reset() {
		player.reset();
		camera.setPosition(new Vector2f(0, 0));
		gameOverState.requiresReset(false);
		state = GameState.PLAYING;
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
		
		if(gameOverState.requiresReset()) {
			reset();
		}
		
		if(mainMenuState.start()) {
			state = GameState.PLAYING;
			mainMenuState.setStart(false);
		}
		
		if(player.isDead()) {
			state = GameState.GAME_OVER;
		}
		
		
		switch (state) {
		case PLAYING:
			levelManager.tick(gc, delta);
			camera.tick(gc, delta, player);
			break;
		case PAUSED:
			pauseState.tick(gc, delta);
			break;
		case GAME_OVER:
			gameOverState.tick(gc, delta);
			break;
		case MAIN_MENU:
			mainMenuState.tick(gc, delta);
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
		case GAME_OVER:
			gameOverState.render(g);
			break;
		case MAIN_MENU:
			mainMenuState.render(g);
			break;
		default:
			break;
		}
	}

	public GameState getState() {
		return state;
	}
}
