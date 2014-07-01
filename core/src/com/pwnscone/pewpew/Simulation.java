package com.pwnscone.pewpew;

public class Simulation {
	private static final int MAX_PARTICLES = 8192;
	private static final int MAX_SPRINGS = 8192;

	public Particle[] particleArray;
	public int particles = 0;
	public Spring[] springArray;
	public int springs = 0;

	public Simulation() {
		particleArray = new Particle[MAX_PARTICLES];
		springArray = new Spring[MAX_SPRINGS];
	}

	public void update() {
		for (int i = 0; i < springs; i++) {
			springArray[i].update();
		}
		for (int i = 0; i < particles; i++) {
			particleArray[i].update();
		}
	}

	public void create() {
		Tetra t = new Tetra();
		t.create();
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

	public Spring addSpring(Particle p0, Particle p1) {
		float x = p0.x - p1.x;
		float y = p0.y - p1.y;
		float z = p0.z - p1.z;
		float length = (float) Math.sqrt(x * x + y * y + z * z);
		return addSpring(p0, p1, length);
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
