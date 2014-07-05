package com.pwnscone.pewpew.actor;

import com.pwnscone.pewpew.Game;
import com.pwnscone.pewpew.Particle;
import com.pwnscone.pewpew.Simulation;
import com.pwnscone.pewpew.Spring;
import com.pwnscone.pewpew.util.Misc;

public class Trash extends Actor {
	public static final float pickUpDist2 = 0.2f;

	public Trash() {
		particles = new Particle[2];
		springs = new Spring[1];
	}

	@Override
	public void update() {
		Particle p0 = particles[0];
		Particle p1 = particles[1];

		float d0 = p0.x * p0.x + p0.y * p0.y;

		if (d0 < pickUpDist2) {
			health = 0;
		} else {
			d0 = 0.0015f / d0;
			float d1 = p0.y * d0;
			d0 *= p0.x;
			p0.ox += d0;
			p0.oy += d1;
			p1.ox += d0;
			p1.oy += d1;
		}
	}

	public void set(Spring spring, Simulation sim) {
		health = 1.0f;

		Particle p0 = sim.addParticle();
		Particle p1 = sim.addParticle();
		Spring sprg = sim.addSpring(p0, p1, spring.length2);
		sprg.color = Misc.FiftyPercentGray;

		p0.cpy(spring.p0);
		p1.cpy(spring.p1);
		p0.dampening = .99f;
		p1.dampening = .99f;

		particles[0] = p0;
		particles[1] = p1;
		springs[0] = sprg;
	}

	public void bump(float strength) {
		particles[0].bump(strength);
		particles[1].bump(strength);
	}

	@Override
	public void destroy() {
		Simulation sim = Game.get().getSimulation();
		sim.removeSpring(springs[0]);
		sim.removeParticle(particles[0]);
		sim.removeParticle(particles[1]);
	}
}
