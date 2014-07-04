package com.pwnscone.pewpew.actor;

import com.pwnscone.pewpew.Game;
import com.pwnscone.pewpew.Mesh;
import com.pwnscone.pewpew.MeshManager;
import com.pwnscone.pewpew.Particle;
import com.pwnscone.pewpew.Simulation;
import com.pwnscone.pewpew.Spring;
import com.pwnscone.pewpew.util.Poolable;

public class Actor extends Poolable {
	protected Particle[] particles;
	protected Spring[] springs;

	public Actor() {
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
			particle.setPosition(meshParticle.curPos);
			particles[i] = particle;
		}
		for (int i = 0; i < mesh.springs.length; i++) {
			Spring meshSpring = mesh.springs[i];
			Spring spring = sim.addSpring(particles[meshSpring.p0.index],
					particles[meshSpring.p1.index], meshSpring.length2);
			springs[i] = spring;
		}
	}

	protected void setTransformation(float x, float y, float z, float theta) {

	}
}
