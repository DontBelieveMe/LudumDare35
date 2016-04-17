package com.ld35.managers;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioManager {
	public static Sound jump;
	public static Sound damage;
	public static Sound shift;
	
	public static void create() {
		try {
			jump = new Sound("/res/jump.wav");
			damage = new Sound("/res/damage.wav");
			shift = new Sound("/res/shift.wav");
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
