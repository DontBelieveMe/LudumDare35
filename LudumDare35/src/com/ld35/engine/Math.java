package com.ld35.engine;

public class Math {
	public static float clamp(float min, float max, float value) {
		if(value > max)
			value = max;
		else if(value < min)
			value = min;
		return value;
	}
}
