package com.pwnscone.pewpew;

import com.badlogic.gdx.math.Vector3;
import com.pwnscone.pewpew.util.Poolable;
import com.pwnscone.pewpew.util.Util;

public class Particle extends Poolable {

	public boolean mark;

	// Current Position
	public Vector3 curPos;

	// Last Position
	public Vector3 oldPos;

	public float dampening = 0.9f;

	public Particle() {
		curPos = new Vector3();
		oldPos = new Vector3();
	}

	public void update() {
		Util.T1.set(curPos);
		Util.T2.set(curPos).sub(oldPos).scl(dampening);
		curPos.add(Util.T2);
		oldPos.set(Util.T1);
	}

	public void setPosition(float x, float y, float z) {
		curPos.set(x, y, z);
		oldPos.set(x, y, z);
	}

	public void setPosition(float x, float y) {
		setPosition(x, y, 0);
	}

	public void setPosition(Vector3 pos) {
		setPosition(pos.x, pos.y, pos.z);
	}

	public void setVelocity(float vx, float vy, float vz) {
		oldPos.set(curPos).sub(vx, vy, vz);
	}

	public void setVelocity(float vx, float vy) {
		setVelocity(vx, vy, 0);
	}

	public void setVelocity(Vector3 vel) {
		oldPos.set(curPos).sub(vel);
	}
}
