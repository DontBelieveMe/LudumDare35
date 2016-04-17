package com.ld35;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.GameObject;
import com.ld35.engine.Tile;
import com.ld35.levels.Level;
import com.ld35.managers.GameManager;
import com.ld35.managers.GameObjectManager;
import com.ld35.managers.LevelManager;

public class Player extends GameObject {
	public enum Direction {
		LEFT, RIGHT, STOPPED, UP, DOWN
	}

	private Vector2f velocity;
	private Image image;

	private float speed = 0.4f;
	private float jumpSpeed = 1.6f;
	private float gravity = 0.01f;
	private boolean canJump = true, inAir = false;

	private final float collisionOffset = 3;

	private Direction direction = Direction.STOPPED;
	private PlayerState state = PlayerState.HUMAN;

	public static final int MAX_HEALTH = 100;
	private int health = MAX_HEALTH;
	private boolean dead = false;
	
	
	private float timeSinceTick = 0;
	float healthDepleteInterval = 400;

	
	public Player(Vector2f position) {
		super(position);

		velocity = new Vector2f(0, 0);

		try {
			image = new Image("/res/Characters.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		GameObjectManager.submit(this);
	}


	public void tick(GameContainer gc, int delta) {
		timeSinceTick += delta;
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_1))
			state = PlayerState.HUMAN;
		else if (input.isKeyPressed(Input.KEY_2))
			state = PlayerState.BIRD;

		switch (state) {
		case HUMAN:
			tickHuman(gc, delta);
			break;
		case BIRD:
			tickBird(gc, delta);
			break;
		default:
			break;
		}
		
		checkLevelBounds();
		
		if(health <= 0) {
			health = 0;
			dead = true;
		}
	}

	private void tickBird(GameContainer gc, int delta) {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_D)) {
			velocity.x = speed;
			direction = Direction.RIGHT;
		} else if (input.isKeyDown(Input.KEY_A)) {
			velocity.x = -speed;
			direction = Direction.LEFT;
		} else {
			velocity.x = 0;
			direction = Direction.STOPPED;
		}

		if (input.isKeyDown(Input.KEY_SPACE)) {
			direction = Direction.UP;
			flyUp();
		}

		float oldPositionX = position.x;
		position.x += velocity.x * delta;

		checkXCollisions(oldPositionX);

		float oldPositionY = position.y;
		position.y += velocity.y * delta;

		if (velocity.y > 0.5f)
			velocity.y = 0.5f;

		checkYCollisions(oldPositionY);

		if (inAir) {
			velocity.y += gravity * delta;
		}

		if (position.y >= 480 - 32) {
			position.y = 480 - 32;
			inAir = false;
		}
		
		
	}

	private void tickHuman(GameContainer gc, int delta) {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_D)) {
			velocity.x = speed;
			direction = Direction.RIGHT;
		} else if (input.isKeyDown(Input.KEY_A)) {
			velocity.x = -speed;
			direction = Direction.LEFT;
		} else {
			velocity.x = 0;
			direction = Direction.STOPPED;
		}

		if (input.isKeyPressed(Input.KEY_SPACE)) {
			jump();
		}

		float oldPositionX = position.x;
		position.x += velocity.x * delta;

		checkXCollisions(oldPositionX);

		float oldPositionY = position.y;
		position.y += velocity.y * delta;

		if (velocity.y > 0.5f)
			velocity.y = 0.5f;

		checkYCollisions(oldPositionY);

		if (inAir) {
			velocity.y += gravity * delta;
		}

		if (position.y >= 480 - 32) {
			position.y = 480 - 32;
			inAir = false;
			canJump = true;
		}

	}

	private void checkYCollisions(float oldY) {
		if (isCollision()) {
			if (velocity.y > 0) {
				canJump = true;
				inAir = false;
			}
			position.y = oldY;
		}
	}

	private void checkXCollisions(float oldX) {
		if (isCollision()) {
			position.x = oldX;
		}
	}

	private void jump() {
		if (canJump) {
			velocity.y = -jumpSpeed;
			canJump = false;
			inAir = true;
		}
	}

	private void flyUp() {
		velocity.y = -jumpSpeed / 4f;
		canJump = false;
		inAir = true;
	}

	private void checkLevelBounds() {
		Level level = LevelManager.getCurrentLevel();
		if (position.x < 0) {
			position.x = 0;
		} else if (position.x + 32 > level.getRealWidthInPixels()) {
			position.x = level.getRealWidthInPixels() - 32;
		}

		if (position.y < 0) {
			position.y = 0;
		}
	}

	private boolean isCollision() {
		boolean collision = false;
		Level level = LevelManager.getCurrentLevel();

		for (int x = 0; x < level.getWidthInTiles(); x++) {
			for (int y = 0; y < level.getHeightInTiles(); y++) {
				Tile platformTile = LevelManager.getCurrentLevel().getTileId(x,
						y, 1);

				Vector2f tilePos = platformTile.getPosition();
				tilePos.x *= 32;
				tilePos.y *= 32;

				boolean xCollision = (position.x < tilePos.x + 32
						- collisionOffset)
						&& (position.x + 32 > tilePos.x + collisionOffset);

				boolean yCollision;
				if (inAir && platformTile.collidesWith(PlayerState.BIRD)) {
					yCollision = (position.y < tilePos.y - collisionOffset)
							&& (position.y + 32 > tilePos.y + collisionOffset);
				} else {
					yCollision = (position.y < tilePos.y + 32 - collisionOffset)
							&& (position.y + 32 > tilePos.y + collisionOffset);
				}
				if (formSpecificCollisions(xCollision && yCollision,
						platformTile))
					collision = true;
			}
		}

		return collision;
	}

	private boolean formSpecificCollisions(boolean xAndY, Tile platformTile) {
		switch (state) {
		case HUMAN:
			if (platformTile.collidesWith(PlayerState.HUMAN)
					|| platformTile.isSolid() || platformTile.hurtsHumans()) {
				if (xAndY) {
					if(platformTile.isWarp()) {
						System.out.println("NEXT!");
						GameManager.levelManager.gotoNextLevel();
					}
					if (timeSinceTick >= healthDepleteInterval 
							&& platformTile.hurtsHumans()) {
						speed /= 3;
						health -= 25;
						timeSinceTick = 0;
					}
					return true;
				}
			}
			break;
		case BIRD:
			if (platformTile.collidesWith(PlayerState.BIRD)
					|| platformTile.isSolid() || platformTile.hurtsBirds()) {
				if (xAndY) {
					if(timeSinceTick >= healthDepleteInterval && platformTile.hurtsBirds()) {
						speed /= 3;
						health -= 25;
						timeSinceTick = 0;
					}
					return true;
				}
			}
			break;
		default:
			break;
		}
		return false;
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		switch (state) {
		case HUMAN:
			g.drawImage(image, position.x, position.y);
			break;
		case BIRD:
			g.fillRect(position.x + 8, position.y + 8, 16, 16);
		default:
			break;
		}
		
		g.resetTransform();
		g.drawString("Health: " + Integer.toString(health), 100, 10);
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getHealth() {
		return health;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setSpeed(float speed) {
		this.speed = 0.4f;
	}
	
	public void reset() {
		this.position.x = 0;
		this.position.y = 480 - 64;
		
		this.health = MAX_HEALTH;
		this.dead = false;
		this.speed = 0.4f;
	}
}

