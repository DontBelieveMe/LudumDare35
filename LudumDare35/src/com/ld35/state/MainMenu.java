package com.ld35.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.ld35.engine.Utility;

public class MainMenu extends State{
	private Image title;
	private Image bgTile;
	
	private boolean start;
	
	public MainMenu() {
		super(24f);
		
		this.start = false;
		
		try {
			title = new Image("/res/Title.png");
			bgTile = new Image("/res/MenuBackgroundTile.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void tick(GameContainer gc, int delta) {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			start = true;
		}
	}

	public void render(Graphics g) {
		for(int x = 0; x < 640; x += 32) {
			for(int y = 0; y < 480; y += 32) {
				g.drawImage(bgTile, x, y);
			}
		}
		g.drawImage(this.title, 640/2-title.getWidth()/2, 480/2-title.getHeight()/2-96);
		g.setFont(slickFont);
		
		Vector2f centre = Utility.getCenteredTextPos(slickFont, "Press [Space] To Start!");
		g.setColor(Color.white);
		g.drawString("Press [Space] To Start!", centre.x, centre.y+64);
	}
	
	public boolean start() {
		return start;
	}
	
	public void setStart(boolean start) {
		this.start = start;
	}

}
