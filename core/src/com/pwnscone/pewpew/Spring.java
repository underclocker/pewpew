package com.pwnscone.pewpew;

import com.pwnscone.pewpew.util.Poolable;
import com.pwnscone.pewpew.util.Util;

public class Spring extends Poolable {

	public static final float dampening = 0.25f;

	public int index;

	public Particle p0;
	public Particle p1;
	public float length2;

	public void update() {
		Util.T1.set(p0.curPos).sub(p1.curPos);

		float diff = ((length2) / (Util.T1.len2() + length2) - 0.5f) * dampening;

		Util.T1.scl(diff);

		p0.oldPos.sub(Util.T1);
		p1.oldPos.add(Util.T1);
	}

	public void set(Particle p0, Particle p1, float length2) {
		this.p0 = p0;
		this.p1 = p1;
		this.length2 = length2;
	}
}
