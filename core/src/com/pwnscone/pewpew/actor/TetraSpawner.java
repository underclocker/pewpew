package com.pwnscone.pewpew.actor;

import com.pwnscone.pewpew.Particle;

public class TetraSpawner extends Actor {
	public static final float centering = .0025f;

	public TetraSpawner() {
		initFromMesh();
	}

	@Override
	public void update() {
		Particle center = particles[0];

		center.ox -= center.x * centering;
		center.oy -= center.y * centering;
		center.oz -= center.z * centering;

		x = center.x;
		y = center.y;
	}

	public void create() {
		loadMesh();

		for (int i = 0; i < particles.length; i++) {
			particles[i].dampening = 0.95f;
		}

		health = 2000;
	}
}
