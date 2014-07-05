package com.pwnscone.pewpew.actor;

import com.badlogic.gdx.graphics.Color;
import com.pwnscone.pewpew.Game;

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
	public int shotRange2;

	public float damage;
	public float kickback;

	public Actor target = null;

	public Tetra() {
		initFromMesh();
	}

	@Override
	public void update() {
		if (target != null) {
			targetX = target.x;
			targetY = target.y;
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
				if (dist2 < shotRange2 && target != null) {
					Game.get().getSimulation().addLaser(particles[2].x, particles[2].y, target.x,
							target.y, Color.GREEN);
					shotTimer = shotDelay;
					target.damage(damage);
					dist2 *= kickback;
					target.kick(xDelta * Math.abs(xDelta / dist2), yDelta
							* Math.abs(yDelta / dist2));
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

		speed = .01f;

		shotTimer = 0;
		shotDelay = 30;
		shotRange2 = 5 * 5;
		// higher is weaker
		kickback = 50f;

		damage = 15;
		health = 100;

		targetX = 0;
		targetY = 0;
	}

	@Override
	public void forget(Actor a) {
		if (target == a) {
			target = null;
		}
	}

	@Override
	public void kick(float x, float y) {
		particles[0].bump(.02f);
		particles[2].bump(.02f);
		particles[0].ox -= x;
		particles[0].oy -= y;
		particles[1].ox -= x;
		particles[1].oy -= y;
		particles[2].ox -= x;
		particles[2].oy -= y;
		particles[3].ox -= x;
		particles[3].oy -= y;
	}
}
