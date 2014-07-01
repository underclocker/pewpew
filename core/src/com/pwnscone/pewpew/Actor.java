package com.pwnscone.pewpew;

public class Actor {
	protected Particle[] particles;
	protected Spring[] springs;

	public Actor() {
		Mesh m = MeshManager.getMesh(this.getClass());
		particles = new Particle[m.particles.length];
		springs = new Spring[m.springs.length];
	}

	private void update() {

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
}
