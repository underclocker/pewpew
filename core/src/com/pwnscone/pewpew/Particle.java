package com.pwnscone.pewpew;

import com.pwnscone.pewpew.util.Misc;
import com.pwnscone.pewpew.util.Poolable;

public class Particle extends Poolable {

	public boolean mark = false;

	// Current Position
	public float x;
	public float y;
	public float z;

	// Last Position
	public float ox;
	public float oy;
	public float oz;

	public float dampening = 0.95f;

	public void update() {
		float tx = x;
		float ty = y;
		float tz = z;

		x += (x - ox) * dampening;
		y += (y - oy) * dampening;
		z += (z - oz) * dampening;

		ox = tx;
		oy = ty;
		oz = tz;
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

	public void cpy(Particle p) {
		x = p.x;
		y = p.y;
		z = p.z;

		ox = p.ox;
		oy = p.oy;
		oz = p.oz;

		dampening = p.dampening;
	}

	public void bump(float strength) {
		ox += strength * (Misc.random() - .5f);
		oy += strength * (Misc.random() - .5f);
		oz += strength * (Misc.random() - .5f);
	}
}