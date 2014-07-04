package com.pwnscone.pewpew.actor;

public class Tetra extends Actor {
	public float speed = .01f;

	public Tetra() {
	}

	@Override
	public void update() {
		// float xDir = (particles[2].x - particles[1].x) + (particles[2].x -
		// particles[3].x);
		// float yDir = (particles[2].y - particles[1].y) + (particles[2].y -
		// particles[3].y);
		// particles[2].ox -= xDir * speed;
		// particles[2].oy -= yDir * speed;
	}

	public Tetra create() {
		loadMesh();
		return this;
	}
}
