package com.pwnscone.pewpew;

public class Spring {

	public static final float dampening = 0.35f;

	public int index;

	public Particle p0;
	public Particle p1;
	public float length2;

	public void update() {

		float deltaX = p0.x - p1.x + ((p0.x - p0.ox) - (p1.x - p1.ox)) * dampening;
		float deltaY = p0.y - p1.y + ((p0.y - p0.oy) - (p1.y - p1.oy)) * dampening;
		float deltaZ = p0.z - p1.z + ((p0.z - p0.oz) - (p1.z - p1.oz)) * dampening;

		float dist2 = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

		float diff = (length2) / (dist2 + length2) - 0.5f;

		deltaX *= diff;
		deltaY *= diff;
		deltaZ *= diff;

		p0.ox -= deltaX;
		p1.ox += deltaX;
		p0.oy -= deltaY;
		p1.oy += deltaY;
		p0.oz -= deltaZ;
		p1.oz += deltaZ;
	}

	public void set(Particle p0, Particle p1, float length) {
		this.p0 = p0;
		this.p1 = p1;
		this.length2 = length * length;
	}
}
