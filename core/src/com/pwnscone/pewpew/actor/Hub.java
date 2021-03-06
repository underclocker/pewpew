package com.pwnscone.pewpew.actor;

import java.util.ArrayList;
import java.util.HashMap;

import com.pwnscone.pewpew.Game;
import com.pwnscone.pewpew.Particle;
import com.pwnscone.pewpew.Simulation;
import com.pwnscone.pewpew.Spring;
import com.pwnscone.pewpew.util.Pool;

public class Hub extends Actor {
	public static final float centering = .125f;
	public static final float spin = .0025f;

	public ArrayList<Actor> modules;
	public HashMap<Actor, Spring> umbilicalSpringMap;
	public ArrayList<Spring> umbilicalSpringList;
	public HashMap<Actor, Particle> dockingParticleMap;
	public ArrayList<Particle> dockingParticleList;
	public ArrayList<Particle> valenceParticleList;

	public Hub() {
		initFromMesh();
		modules = new ArrayList<Actor>();
		umbilicalSpringMap = new HashMap<Actor, Spring>();
		umbilicalSpringList = new ArrayList<Spring>();
		dockingParticleMap = new HashMap<Actor, Particle>();
		dockingParticleList = new ArrayList<Particle>();
	}

	@Override
	public void update() {
		Particle center = particles[42];
		Particle top = particles[11];
		Particle bottom = particles[0];

		x = center.x;
		y = center.y;

		center.ox += center.x * centering;
		center.oy += center.y * centering;
		center.oz += center.z * centering;

		float spin = (1.0f + modules.size()) * .5f;
		top.ox -= .0011f * spin;
		top.oy -= .0011f * spin;
		top.oz -= .0058f * spin;
		bottom.ox += .0011f * spin;
		bottom.oy += .0011f * spin;
		bottom.oz += .0058f * spin;
		spin *= 2 * 0.0375f * Hub.spin;

		for (int i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			p.ox += p.y * spin;
			p.oy -= p.x * spin;
		}

		for (int i = 0; i < umbilicalSpringList.size(); i++) {
			Spring s = umbilicalSpringList.get(i);
			if (s.length2 > .00025f) {
				s.length2 *= 0.99f;
			}
		}
	}

	public void create() {
		loadMesh();

		for (int i = 0; i < particles.length; i++) {
			particles[i].dampening = 0.95f;
		}
		umbilicalSpringMap.clear();
		umbilicalSpringList.clear();

		dockingParticleList.add(particles[22]);
		dockingParticleList.add(particles[31]);
		dockingParticleList.add(particles[26]);
		dockingParticleList.add(particles[24]);
		dockingParticleList.add(particles[23]);
		dockingParticleList.add(particles[28]);
		dockingParticleList.add(particles[27]);
		dockingParticleList.add(particles[25]);
		dockingParticleList.add(particles[30]);
		dockingParticleList.add(particles[29]);

		health = 5000;
	}

	public void spawn(Class clazz) {
		if (dockingParticleList.size() > 0) {
			Simulation sim = Game.get().getSimulation();
			Pool<Actor> pool = sim.mActorMap.get(clazz);
			Actor actor = pool.add();
			actor.create();
			modules.add(actor);
			Particle docPart = dockingParticleList.remove(0);
			dockingParticleMap.put(actor, docPart);
			float bestDist = 0;
			Particle closest = actor.particles[0];
			bestDist = -Float.MAX_VALUE;
			for (int i = 0; i < actor.particles.length; i++) {
				Particle p = actor.particles[i];
				float dx = p.x - docPart.x;
				float dy = p.y - docPart.y;
				float dist2 = dx * dx + dy * dy;
				if (dist2 > bestDist) {
					closest = p;
					bestDist = dist2;
				}
			}
			actor.setTransform(docPart.x - closest.x, docPart.y - closest.y, 0);
			actor.setVelocity(0.0f, 0.0f);
			Spring spring = sim.addSpring(docPart, closest);
			umbilicalSpringMap.put(actor, spring);
			umbilicalSpringList.add(spring);
		}
	}

	@Override
	public void forget(Actor actor) {
		if (modules.contains(actor)) {
			Simulation sim = Game.get().getSimulation();
			modules.remove(actor);
			Spring s = umbilicalSpringMap.remove(actor);
			umbilicalSpringList.remove(s);
			sim.removeSpring(s);
			dockingParticleList.add(dockingParticleMap.remove(actor));
		}
	}
}
