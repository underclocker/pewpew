package com.pwnscone.pewpew.actor;

import com.badlogic.gdx.math.Vector3;
import com.pwnscone.pewpew.Game;
import com.pwnscone.pewpew.Mesh;
import com.pwnscone.pewpew.MeshManager;
import com.pwnscone.pewpew.Particle;
import com.pwnscone.pewpew.Simulation;
import com.pwnscone.pewpew.Spring;
import com.pwnscone.pewpew.util.Misc;
import com.pwnscone.pewpew.util.Pool;
import com.pwnscone.pewpew.util.Poolable;

public class Actor extends Poolable {
	protected Particle[] particles;
	protected Spring[] springs;

	// Each actor is responsible for keeping their properties up to date.
	public float x;
	public float y;

	public float health;

	protected void initFromMesh() {
		Mesh m = MeshManager.getMesh(this.getClass());
		particles = new Particle[m.particles.length];
		springs = new Spring[m.springs.length];
	}

	public void update() {
	}

	protected void loadMesh() {
		Mesh mesh = MeshManager.getMesh(this.getClass());
		Simulation sim = Game.get().getSimulation();
		for (int i = 0; i < mesh.particles.length; i++) {
			Particle meshParticle = mesh.particles[i];
			Particle particle = sim.addParticle();
			particle.setPosition(meshParticle.x, meshParticle.y, meshParticle.z);
			particles[i] = particle;
		}
		for (int i = 0; i < mesh.springs.length; i++) {
			Spring meshSpring = mesh.springs[i];
			Spring spring = sim.addSpring(particles[meshSpring.p0.index],
					particles[meshSpring.p1.index], meshSpring.length2);
			springs[i] = spring;
		}
	}

	public void setTransform(float x, float y, float theta) {
		Vector3 v = getCenter();
		float cx = v.x;
		float cy = v.y;

		float cos = (float) Math.cos(theta);
		float sin = (float) Math.sin(theta);

		for (int i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			p.x -= cx;
			p.y -= cy;

			float nx = p.x * cos - p.y * sin;
			p.y = p.x * sin + p.y * cos;
			p.x = nx;

			p.x += cx + x;
			p.y += cy + y;
		}
	}

	public void setVelocity(float x, float y) {
		for (int i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			p.ox = p.x - x;
			p.oy = p.y - y;
		}
	}

	protected Vector3 getCenter() {
		float x = 0, y = 0;

		for (int i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			x += p.x;
			y += p.y;
		}

		Misc.T1.set(x / particles.length, y / particles.length, 0.0f);
		return Misc.T1;
	}

	public void damage(float damage) {
		health -= damage;
	}

	public void kick(float x, float y, Particle p) {
		p.bump(.02f);
		p.ox -= x;
		p.oy -= y;
	}

	public void destroy() {
		Simulation sim = Game.get().getSimulation();
		Pool<Actor> trashPool = sim.mActorMap.get(Trash.class);
		for (int i = 0; i < springs.length; i++) {
			Spring spring = springs[i];
			Trash t = (Trash) trashPool.add();
			t.set(spring, sim);
			t.bump(.015f);
			sim.removeSpring(spring);
		}
		for (int i = 0; i < particles.length; i++) {
			sim.removeParticle(particles[i]);
		}
	}

	public void forget(Actor actor) {

	}

	public Particle getRandomParticle() {
		return particles[(int) Math.floor(Misc.random() * (particles.length))];
	}
}
