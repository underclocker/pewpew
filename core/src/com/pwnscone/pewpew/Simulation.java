package com.pwnscone.pewpew;

import java.util.ArrayList;
import java.util.HashMap;

import com.pwnscone.pewpew.actor.Actor;
import com.pwnscone.pewpew.actor.Tetra;
import com.pwnscone.pewpew.util.Pool;
import com.pwnscone.pewpew.util.Util;

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

		for (int i = 0; i < 3000; i++) {
			Particle p0 = addParticle();
			p0.setPosition((float) (32.0 * Math.random() - 16), (float) (32.0 * Math.random() - 16));
			Particle p1 = addParticle();
			p1.setPosition((float) (32.0 * Math.random() - 16), (float) (32.0 * Math.random() - 16));
			Particle p2 = addParticle();
			p2.setPosition((float) (32.0 * Math.random() - 16), (float) (32.0 * Math.random() - 16));
			Particle p3 = addParticle();
			p3.setPosition((float) (32.0 * Math.random() - 16),
					(float) (32.0 * Math.random() - 16), 1.0f);
			addSpring(p0, p1, .015f);
			addSpring(p1, p2, .015f);
			addSpring(p2, p0, .015f);
			addSpring(p3, p0, .015f);
			addSpring(p3, p1, .015f);
			addSpring(p3, p2, .015f);
		}
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
				actorPool.get(i).update();
			}
		}
	}

	public void create() {
		((Tetra) mActorMap.get(Tetra.class).add()).create();
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
		return addSpring(p0, p1, Util.T1.set(p0.curPos).sub(p1.curPos).len2());
	}

	public Spring addSpring(Particle p0, Particle p1, float length2) {
		Spring spring = mSpringPool.add();
		spring.set(p0, p1, length2);
		return spring;
	}
}
