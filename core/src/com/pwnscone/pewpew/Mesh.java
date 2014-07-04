package com.pwnscone.pewpew;

import com.badlogic.gdx.files.FileHandle;

public class Mesh {
	public Particle[] particles;
	public Spring[] springs;

	public Mesh(FileHandle file) {
		String string = file.readString();
		string = string.replace("\n", " ");
		String[] words = string.split(" ");
		int v = 0;
		int l = 0;

		for (int i = 0; i < words.length; i++) {
			if (words[i].equals("v")) {
				v++;
			} else if (words[i].equals("l")) {
				l++;
			}
		}

		particles = new Particle[v];
		springs = new Spring[l];

		v = 0;
		l = 0;

		int index = 0;
		while (!words[index].equals("v")) {
			index++;
		}
		while (!words[index].equals("l")) {
			index++;
			float x = Float.parseFloat(words[index++]);
			float y = Float.parseFloat(words[index++]);
			float z = Float.parseFloat(words[index++]);
			particles[v] = new Particle();
			particles[v].setPosition(x, y, z);
			particles[v].index = v;
			v++;
		}
		while (index < words.length) {
			index++;
			Particle p0 = particles[Integer.parseInt(words[index++]) - 1];
			Particle p1 = particles[Integer.parseInt(words[index++]) - 1];
			springs[l] = new Spring();
			float x = p0.x - p1.x;
			float y = p0.y - p1.y;
			float z = p0.z - p1.z;
			springs[l].set(p0, p1, x * x + y * y + z * z);
			l++;
		}
	}
}
