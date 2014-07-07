package com.pwnscone.pewpew.actor;

import com.pwnscone.pewpew.Particle;

public class Hub extends Actor {
	public static final float centering = .125f;
	public static final float spin = .0025f;

	public Hub() {
		initFromMesh();
	}

	@Override
	public void update() {
		Particle center = particles[42];
		Particle top = particles[11];
		Particle bottom = particles[0];
		Particle rot0 = particles[24];
		Particle rot1 = particles[25];
		Particle rot2 = particles[31];

		x = center.x;
		y = center.y;

		center.ox += center.x * centering;
		center.oy += center.y * centering;
		center.oz += center.z * centering;

		top.ox -= .0011f;
		top.oy -= .0011f;
		top.oz -= .0058f;
		bottom.ox += .0011f;
		bottom.oy += .0011f;
		bottom.oz += .0058f;

		float dX = (rot1.x - rot0.x) * spin;
		float dY = (rot1.y - rot0.y) * spin;
		float dZ = (rot1.z - rot0.z) * spin;

		rot0.x += dX;
		rot0.y += dY;
		rot0.z += dZ;

		rot2.x -= dX;
		rot2.y -= dY;
		rot2.z -= dZ;

	}

	public void create() {
		loadMesh();

		for (int i = 0; i < particles.length; i++) {
			particles[i].dampening = 0.95f;
		}

		health = 5000;
	}
}
