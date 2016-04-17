package com.ld35;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.GameObject;
import com.ld35.engine.Tile;
import com.ld35.levels.Level;
import com.ld35.managers.AudioManager;
import com.ld35.managers.GameManager;
import com.ld35.managers.GameObjectManager;
import com.ld35.managers.LevelManager;

public class Player extends GameObject {
	public enum Direction {
		LEFT, RIGHT, STOPPED, UP, DOWN
	}
	
	private Image healthBar;

	SpriteSheet player;
	Image[] images = new Image[2];

	private Vector2f velocity;
	Animation animation;

	private float speed = 0.4f;
	private float jumpSpeed = 1.6f;
	private float gravity = 0.01f;
	private boolean canJump = true, inAir = false;

	private final float collisionOffset = 3;

	private Direction direction = Direction.STOPPED;
	private PlayerState state = PlayerState.HUMAN;

	public static final int MAX_HEALTH = 100;
	private float health = MAX_HEALTH;
	private boolean dead = false;

	private float platformHealthTick = 0;
	private float regenerationTick = 0;
	float healthDepleteInterval = 400;
	float shiftHealthDepleteInterval = 300;

	public Player(Vector2f position) {
		super(position);

		velocity = new Vector2f(0, 0);
		try {
			player = new SpriteSheet("/res/Characters.png", 32, 32);
			healthBar = new Image("/res/HealthBar.png");
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		images[0] = player.getSprite(0, 1);
		images[1] = player.getSprite(1, 1);
		animation = new Animation(images, 250);
		
		GameObjectManager.submit(this);
	}

	public void tick(GameContainer gc, int delta) {
		platformHealthTick += delta;
		regenerationTick += delta;
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_1)) {
			AudioManager.playOnce(AudioManager.shift);
			if(state == PlayerState.HUMAN) {
				state = PlayerState.BIRD;
			} else {
				state = PlayerState.HUMAN;
			}
		} 
		
		if(!isCollision() && speed != 0.4f)
			speed = 0.4f;
		
		switch (state) {
		case HUMAN:
			if(regenerationTick >= shiftHealthDepleteInterval && health < Player.MAX_HEALTH && !isCollision()) {
				health += 0.5f;
				regenerationTick = 0;
			}
			tickHuman(gc, delta);
			break;
		case BIRD:
			if(regenerationTick >= shiftHealthDepleteInterval && !isCollision()) {
				health -= 3;
				regenerationTick = 0;
			}
			tickBird(gc, delta);
			break;
		default:
			break;
		}

		checkLevelBounds();

		if (health <= 0) {
			health = 0;
			dead = true;
		}
	}

	private boolean jumpDeathStart = false;

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
			if (jumpDeathStart)
				jumpDeathStart = false;
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
			AudioManager.playOnce(AudioManager.jump);
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
					|| platformTile.isSolid() || platformTile.hurtsHumans()
					|| platformTile.isWarp()) {
				if (xAndY) {
					if (platformTile.isWarp()) {
						AudioManager.playOnce(AudioManager.nextLevel);
						GameManager.levelManager.gotoNextLevel();
					}
					if (platformHealthTick >= healthDepleteInterval
							&& platformTile.hurtsHumans()) {
						System.out.println("Hello!");
						AudioManager.playOnce(AudioManager.damage);
						speed /= 3;
						health -= 50;
						platformHealthTick = 0;
					}
					return true;
				}
			}
			break;
		case BIRD:
			if (platformTile.collidesWith(PlayerState.BIRD)
					|| platformTile.isSolid() || platformTile.hurtsBirds()) {
				if (xAndY) {
					if (platformTile.isWarp()) {
						AudioManager.playOnce(AudioManager.nextLevel);
						GameManager.levelManager.gotoNextLevel();
					}
					if (platformHealthTick >= healthDepleteInterval
							&& platformTile.hurtsBirds()) {
						speed /= 3;
						health -= 50;
						platformHealthTick = 0;
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
		switch (state) {
		case HUMAN:
			if (direction == Direction.STOPPED)
				g.drawImage(player.getSprite(0, 0), position.x, position.y);
			else
				g.drawAnimation(animation, position.x, position.y);
			break;
		case BIRD:
			g.fillRect(position.x + 8, position.y + 8, 16, 16);
		default:
			break;
		}

		g.resetTransform();
		g.setColor(Color.red);
		g.fillRect(640 / 2 - 100, 480 - 32+5, health*2, 20);
		g.setColor(Color.black);
		g.drawImage(healthBar, 640 / 2 - 100, 480 - 32+5);
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public Direction getDirection() {
		return direction;
	}

	public float getHealth() {
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
