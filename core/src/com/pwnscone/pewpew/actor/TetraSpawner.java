package com.pwnscone.pewpew.actor;

import java.util.HashSet;

import com.pwnscone.pewpew.Game;
import com.pwnscone.pewpew.Particle;
import com.pwnscone.pewpew.Simulation;
import com.pwnscone.pewpew.Spring;
import com.pwnscone.pewpew.util.Pool;

public class TetraSpawner extends Actor {
	public static final float centering = .0025f;

	public int spawnCap;
	public int spawnTimer;
	public int spawnDelay;
	public float scale;
	public float scalespeed;

	public HashSet<Actor> spawned;

	public TetraSpawner() {
		initFromMesh();
		spawned = new HashSet<Actor>();
	}

	@Override
	public void update() {
		Particle center = particles[0];

		center.ox -= center.x * centering;
		center.oy -= center.y * centering;
		center.oz -= center.z * centering;

		x = center.x;
		y = center.y;
		if (spawned.size() < spawnCap) {
			if (spawnTimer-- <= 0) {
				spawn(Tetra.class);
				spawnTimer = spawnDelay;
			}
		}

		if (scale < 1) {
			scalespeed += .0001f;
			scale *= scalespeed;
			float scale2 = scalespeed * scalespeed;
			for (int i = 0; i < springs.length; i++) {
				Spring s = springs[i];
				s.length2 *= scale2;
			}
		}
	}

	@Override
	public void create() {
		loadMesh();
		spawnCap = 50;
		spawnTimer = 300;
		spawnDelay = 45;
		scale = 0.1f;
		scalespeed = 1.00f;

		for (int i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			p.dampening = 0.95f;
			p.x *= scale;
			p.y *= scale;
			p.z *= scale;
			p.ox *= scale;
			p.oy *= scale;
			p.oz *= scale;
		}
		float scale2 = scale * scale;
		for (int i = 0; i < springs.length; i++) {
			Spring s = springs[i];
			s.length2 *= scale2;
		}
		spawned.clear();

		health = 2000;
	}

	public void spawn(Class clazz) {
		Simulation sim = Game.get().getSimulation();
		Pool<Actor> pool = sim.mActorMap.get(clazz);
		Actor actor = pool.add();
		actor.create();
		actor.setTransform(x, y, (float) Math.atan2(y, x));
		actor.setVelocity(x * .05f, y * .05f);
		spawned.add(actor);
	}

	@Override
	public void forget(Actor actor) {
		if (spawned.contains(actor)) {
			spawned.remove(actor);
		}
	}
}
