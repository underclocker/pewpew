package com.pwnscone.pewpew.actor;

public class Tetra extends Actor {
	public float speed = .01f;
	public float rightingSpeed = .125f;
	// relative to speed
	public float turnSpeed = .1f;

	public Tetra() {
	}

	@Override
	public void update() {
		float xDir = (particles[2].x - particles[1].x) + (particles[2].x - particles[3].x);
		float yDir = (particles[2].y - particles[1].y) + (particles[2].y - particles[3].y);
		xDir *= speed;
		yDir *= speed;
		particles[2].ox -= xDir;
		particles[2].oy -= yDir;
		xDir *= turnSpeed;
		yDir *= turnSpeed;
		particles[0].ox += yDir;
		particles[0].oy -= xDir;
		particles[0].oz += speed * rightingSpeed;
	}

	public Tetra create() {
		loadMesh();
		rightingSpeed *= 1 + Math.random();
		return this;
	}
}
