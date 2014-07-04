package com.pwnscone.pewpew;

import java.util.ArrayList;
import java.util.HashMap;

import com.pwnscone.pewpew.actor.Actor;
import com.pwnscone.pewpew.actor.Tetra;
import com.pwnscone.pewpew.util.Pool;

public class Simulation {
	private final HashMap<Class, Pool<Actor>> mActorMap;
	private final ArrayList<Pool<Actor>> mActorList;

	public final Pool<Particle> mParticlePool;
	public final Pool<Spring> mSpringPool;

	public Simulation() {
		mParticlePool = new Pool<Particle>(Particle.class);
		mSpringPool = new Pool<Spring>(Spring.class);
		mActorMap = new HashMap<Class, Pool<Actor>>();
		mActorMap.put(Tetra.class, new Pool<Actor>(Tetra.class));
		mActorList = new ArrayList<Pool<Actor>>(mActorMap.values());
	}

	public void update() {
		for (int i = 0; i < mSpringPool.fill; i++) {
			mSpringPool.get(i).update();
		}
		for (int i = 0; i < mParticlePool.fill; i++) {
			mParticlePool.get(i).update();
		}
		for (int i = 0; i < mActorList.size(); i++) {
			Pool<Actor> actorPool = mActorList.get(i);
			for (int j = 0; j < actorPool.fill; j++) {
				actorPool.get(j).update();
			}
		}
	}

	public void create() {
		for (int i = 0; i < 2000; i++) {
			Tetra t = ((Tetra) mActorMap.get(Tetra.class).add()).create();
			t.setTransform((float) (6.0f * Math.random() - 3.0f),
					(float) (6.0f * Math.random() - 3.0f), (float) (Math.random() * Math.PI * 2.0f));
			t.setVelocity(0.0f, 0.0f);
		}
	}

	public Particle addParticle() {
		return mParticlePool.add();
	}

	public void removeParticle(Particle particle) {
		mParticlePool.remove(particle);
	}

	public void removeSpring(Spring spring) {
		mSpringPool.remove(spring);
	}

	public Spring addSpring(Particle p0, Particle p1) {
		float x = p0.x - p1.x;
		float y = p0.y - p1.y;
		float z = p0.z - p1.z;
		return addSpring(p0, p1, x * x + y * y + z * z);
	}

	public Spring addSpring(Particle p0, Particle p1, float length2) {
		Spring spring = mSpringPool.add();
		spring.set(p0, p1, length2);
		return spring;
	}
}
