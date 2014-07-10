package com.pwnscone.pewpew;

import graphics.Laser;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.pwnscone.pewpew.actor.Actor;
import com.pwnscone.pewpew.actor.Hub;
import com.pwnscone.pewpew.actor.Tetra;
import com.pwnscone.pewpew.actor.TetraSpawner;
import com.pwnscone.pewpew.actor.Trash;
import com.pwnscone.pewpew.util.Misc;
import com.pwnscone.pewpew.util.Pool;

public class Simulation {
	private final ArrayList<Pool<Actor>> mActorList;

	public final HashMap<Class, Pool<Actor>> mActorMap;
	public final Pool<Particle> mParticlePool;
	public final Pool<Spring> mSpringPool;
	public final Pool<Laser> mLaserPool;

	public Simulation() {
		mParticlePool = new Pool<Particle>(Particle.class);
		mSpringPool = new Pool<Spring>(Spring.class);
		mLaserPool = new Pool<Laser>(Laser.class);

		mActorMap = new HashMap<Class, Pool<Actor>>();
		mActorMap.put(Tetra.class, new Pool<Actor>(Tetra.class));
		mActorMap.put(Trash.class, new Pool<Actor>(Trash.class));
		mActorMap.put(Hub.class, new Pool<Actor>(Hub.class));
		mActorMap.put(TetraSpawner.class, new Pool<Actor>(TetraSpawner.class));

		mActorList = new ArrayList<Pool<Actor>>(mActorMap.values());
	}

	public void update() {
		mLaserPool.clear();

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
		for (int i = 0; i < mActorList.size(); i++) {
			Pool<Actor> actorPool = mActorList.get(i);
			for (int j = 0; j < actorPool.fill; j++) {
				Actor a = actorPool.get(j);
				if (a.health <= 0) {
					actorPool.remove(a);
					a.destroy();
					for (int k = 0; k < mActorList.size(); k++) {
						Pool<Actor> actorPool2 = mActorList.get(k);
						for (int m = 0; m < actorPool2.fill; m++) {
							actorPool2.get(m).forget(a);
						}
					}
				}
			}
		}

		if (false) {
			Pool<Actor> tetraPool = mActorMap.get(Tetra.class);
			Tetra t = (Tetra) tetraPool.add();
			t.create();
			t.setTransform(0, 0, (float) (Misc.random() * Math.PI * 2.0f));
			t.setVelocity(0.0f, 0.0f);
		}
	}

	public void create() {
		Pool<Actor> hubPool = mActorMap.get(Hub.class);
		Hub h = (Hub) hubPool.add();
		h.create();
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
		return addSpring(p0, p1, length2, Color.WHITE);
	}

	public Spring addSpring(Particle p0, Particle p1, float length2, Color color) {
		Spring spring = mSpringPool.add();
		spring.set(p0, p1, length2, color);
		return spring;
	}

	public Laser addLaser(float x0, float y0, float x1, float y1, Color color) {
		Laser laser = mLaserPool.add();
		laser.set(x0, y0, x1, y1, color);
		return laser;
	}
}
