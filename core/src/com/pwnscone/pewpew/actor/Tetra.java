package com.pwnscone.pewpew.actor;

public class Tetra extends Actor {
	public float speed = .02f;
	public static final float rightingSpeed = .125f;
	// relative to speed
	public static final float turnSpeed = .1f;

	public static final float minTurn = 1.0f;

	public float targetX;
	public float targetY;

	public Actor target = null;

	public Tetra() {
	}

	@Override
	public void update() {
		if (target != null) {
			targetX = target.x;
			targetY = target.y;
		}
		float xDir = (particles[2].x - particles[1].x) + (particles[2].x - particles[3].x);
		float yDir = (particles[2].y - particles[1].y) + (particles[2].y - particles[3].y);
		xDir *= speed;
		yDir *= speed;

		particles[2].ox -= xDir;
		particles[2].oy -= yDir;

		xDir *= turnSpeed;
		yDir *= turnSpeed;

		float cross = 500.0f * (xDir * (targetY - particles[0].y) - yDir
				* (targetX - particles[0].x));

		if (Math.abs(cross) < minTurn) {
			if (cross < 0) {
				cross = -minTurn;
			} else {
				cross = minTurn;
			}
		}
		particles[0].ox += yDir * cross;
		particles[0].oy -= xDir * cross;
		particles[0].oz += speed * rightingSpeed;
		x = particles[0].x;
		y = particles[0].y;
	}

	public Tetra create() {
		loadMesh();
		targetX = 0;
		targetY = 0;
		return this;
	}
}
