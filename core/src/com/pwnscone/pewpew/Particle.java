package com.pwnscone.pewpew;

public class Particle {
	
	public int index;
	
	public float x;
	public float y;
	public float z;
	public float ox;
	public float oy;
	public float oz;
	
	public float dampening = 0.95f;

	public void update() {
		float tempX = x;
		float tempY = y;
		float tempZ = z;

		x += (x - ox) * dampening;
		y += (y - oy) * dampening;
		z += (z - oz) * dampening;
		
		ox = tempX;
		oy = tempY;
		oz = tempZ;
	}

	public void setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.ox = x;
		this.oy = y;
		this.oz = z;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		this.z = 0.0f;
		this.ox = x;
		this.oy = y;
		this.oz = 0.0f;
	}

	public void setVelocity(float vx, float vy, float vz) {
		ox = x - vx;
		oy = y - vy;
		oz = z - vz;
	}

	public void setVelocity(float vx, float vy) {
		ox = x - vx;
		oy = y - vy;
		oz = z;
	}
}
