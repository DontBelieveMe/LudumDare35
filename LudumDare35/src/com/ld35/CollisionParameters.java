package com.ld35;

public class CollisionParameters<T> {
	private T type;
	private int specialCollision;

	public CollisionParameters(T type, int specialCollision) {
		this.type = type;
		this.specialCollision = specialCollision;
	}

	public T getType() {
		return type;
	}

	public int getSpecialCollision() {
		return specialCollision;
	}
	
	
}
