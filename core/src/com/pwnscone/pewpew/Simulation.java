package com.pwnscone.pewpew;

public class Simulation {
	private static final int MAX_PARTICLES = 8192 * 16;
	private static final int MAX_SPRINGS = 8192 * 16;

	public Particle[] particleArray;
	public int particles = 0;
	public Spring[] springArray;
	public int springs = 0;

	public Simulation() {
		particleArray = new Particle[MAX_PARTICLES];
		springArray = new Spring[MAX_SPRINGS];

		for (int i = 0; i < 3000; i++) {
			Particle p0 = addParticle();
			p0.setPosition((float) (9.0 * Math.random()), (float) (16.0 * Math.random()));
			Particle p1 = addParticle();
			p1.setPosition((float) (9.0 * Math.random()), (float) (16.0 * Math.random()));
			Particle p2 = addParticle();
			p2.setPosition((float) (9.0 * Math.random()), (float) (16.0 * Math.random()));
			Particle p3 = addParticle();
			p3.setPosition((float) (9.0 * Math.random()), (float) (16.0 * Math.random()), 1.0f);
			addSpring(p0, p1, .5f);
			addSpring(p1, p2, .5f);
			addSpring(p2, p0, .5f);
			addSpring(p3, p0, .5f);
			addSpring(p3, p1, .5f);
			addSpring(p3, p2, .5f);
		}
	}

	public void update() {
		for (int i = 0; i < springs; i++) {
			springArray[i].update();
		}
		for (int i = 0; i < particles; i++) {
			particleArray[i].update();
		}
	}

	public Particle addParticle() {
		if (particleArray[particles] == null)
			particleArray[particles] = new Particle();
		particleArray[particles].index = particles++;
		return particleArray[particles - 1];
	}

	public void removeParticle(Particle particle) {
		Particle oldLast = particleArray[particles - 1];
		particleArray[particle.index] = oldLast;
		particleArray[particles - 1] = particle;
		oldLast.index = particle.index;
		particle.index = particles - 1;
	}

	public Spring addSpring(Particle p0, Particle p1, float length) {
		if (springArray[springs] == null) {
			springArray[springs] = new Spring();
		}
		Spring spring = springArray[springs];
		spring.set(p0, p1, length);
		spring.index = springs++;
		return spring;
	}

	public void removeSpring(Spring spring) {
		Spring oldLast = springArray[springs - 1];
		springArray[spring.index] = oldLast;
		springArray[springs - 1] = spring;
		oldLast.index = spring.index;
		spring.index = springs - 1;
	}
}
