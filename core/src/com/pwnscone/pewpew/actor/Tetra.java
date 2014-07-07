package com.pwnscone.pewpew.actor;

import com.badlogic.gdx.graphics.Color;
import com.pwnscone.pewpew.Game;
import com.pwnscone.pewpew.Particle;

public class Tetra extends Actor {
	public static final float rightingSpeed = .25f;
	// relative to speed
	public static final float turnSpeed = .15f;
	public static final float minTurn = 0.5f;

	public float speed;

	public float targetX;
	public float targetY;
	public int shotTimer;
	public int shotDelay;
	public int shotHoldDelay;
	public int shotHoldTimer;
	public int shotRange2;

	public float damage;
	public float kickback;

	public Actor targetActor = null;
	public Particle targetParticle = null;

	public Tetra() {
		initFromMesh();
	}

	@Override
	public void update() {

		// Find Target
		if (targetActor == null) {
			targetActor = Game.get().getSimulation().mActorMap.get(Tetra.class).getRandom();
			targetParticle = targetActor.getRandomParticle();
			if (targetActor == this || true) {
				targetActor = null;
				targetActor = Game.get().getSimulation().mActorMap.get(TetraSpawner.class)
						.getRandom();
				if (targetActor != null) {
					targetParticle = targetActor.getRandomParticle();
				}
			}
		}

		if (targetActor != null) {
			targetX = targetParticle.x;
			targetY = targetParticle.y;
		} else {
			targetX = 0;
			targetY = 0;
		}
		float xDir = (particles[2].x - particles[1].x) + (particles[2].x - particles[3].x);
		float yDir = (particles[2].y - particles[1].y) + (particles[2].y - particles[3].y);
		xDir *= speed;
		yDir *= speed;

		particles[2].ox -= xDir;
		particles[2].oy -= yDir;

		xDir *= turnSpeed;
		yDir *= turnSpeed;

		float yDelta = (targetY - particles[0].y);
		float xDelta = (targetX - particles[0].x);

		float cross = 1000.0f * (xDir * yDelta - yDir * xDelta);
		float dot = xDelta * xDir + yDelta * yDir;

		shotTimer--;
		if (Math.abs(cross) < minTurn) {
			if (cross < 0) {
				cross = -minTurn;
			} else {
				cross = minTurn;
			}
			if (shotTimer < 0 && dot > 0) {
				float dist2 = xDelta * xDelta + yDelta * yDelta;
				if (dist2 < shotRange2 && dist2 > .5f && targetActor != null) {
					Game.get().getSimulation().addLaser(particles[2].x, particles[2].y, targetX,
							targetY, Color.GREEN);
					targetActor.damage(damage);
					dist2 *= kickback;
					targetActor.kick(xDelta * Math.abs(xDelta / dist2), yDelta
							* Math.abs(yDelta / dist2), targetParticle);

					shotHoldTimer--;
					if (shotHoldTimer < 0) {
						shotTimer = shotDelay;
						shotHoldTimer = shotHoldDelay;
					}
				}
			}
		}

		particles[0].ox += yDir * cross;
		particles[0].oy -= xDir * cross;
		particles[0].oz += speed * rightingSpeed;
		x = particles[0].x;
		y = particles[0].y;
	}

	public void create() {
		loadMesh();

		for (int i = 0; i < particles.length; i++) {
			particles[i].dampening = 0.95f;
		}

		speed = .01f;

		shotTimer = 0;
		shotDelay = 30;
		shotHoldTimer = 0;
		shotHoldDelay = 1;
		shotRange2 = 6 * 6;
		// higher is weaker
		kickback = 100f;

		damage = 10;
		health = 100;
	}

	@Override
	public void forget(Actor a) {
		if (targetActor == a) {
			targetActor = null;
			targetParticle = null;
		}
	}
}
