package com.pwnscone.pewpew;

public class Simulation {
	private static final int MAX_PARTICLES = 1024;

	public Particle[] particleArray;
	public int particles = 0;

	public Simulation() {
		particleArray = new Particle[MAX_PARTICLES];
		removeParticle(addParticle());
		addParticle();
		addParticle();
	}

	public void update() {
		for (int i = 0; i < particles; i++) {
			particleArray[i].update();
			System.out.println(particleArray[i].x);
		}
	}

	public Particle addParticle() {
		if (particleArray[particles] == null)
			particleArray[particles] = new Particle();
		particleArray[particles].index = particles++;
		particleArray[particles - 1].ox -= .1;
		particleArray[particles - 1].oy -= .2;
		return particleArray[particles - 1];
	}

	public void removeParticle(Particle particle) {
		Particle oldLast = particleArray[particles - 1];
		particleArray[particle.index] = oldLast;
		particleArray[particles - 1] = particle;
		oldLast.index = particle.index;
		particle.index = particles - 1;
	}
}
