package com.pwnscone.pewpew.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Misc {
	public static float RAD_TO_DEG = (float) (180.0 / Math.PI);
	public static float DEG_TO_RAD = (float) (Math.PI / 180.0);
	public static Vector3 T1 = new Vector3();
	public static Color FiftyPercentGray = new Color(.5f, .5f, .5f, 1.0f);

	private static float[] randoms;
	private static int randIndex;

	public static float random() {
		if (randIndex == 4096)
			randIndex = 0;
		return randoms[randIndex++];
	}

	static {
		randoms = new float[4096];
		for (int i = 0; i < 4096; i++) {
			randoms[i] = (float) Math.random();
		}
	}
}
