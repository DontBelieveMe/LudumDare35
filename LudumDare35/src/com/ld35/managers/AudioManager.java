package com.ld35.managers;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioManager {
	public static Sound jump;
	public static Sound damage;
	public static Sound shift;
	public static Sound nextLevel;
	public static Sound select;
	
	public static void create() {
		try {
			jump = new Sound("/res/jump.wav");
			damage = new Sound("/res/damage.wav");
			shift = new Sound("/res/shift.wav");
			nextLevel = new Sound("/res/levelTransition.wav");
			select = new Sound("/res/select.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void playOnce(Sound sound) {
		if(!sound.playing()) {
			sound.play();
		}
	}
}
