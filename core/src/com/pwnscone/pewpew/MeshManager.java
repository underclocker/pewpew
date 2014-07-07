package com.pwnscone.pewpew;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.pwnscone.pewpew.actor.Hub;
import com.pwnscone.pewpew.actor.Tetra;
import com.pwnscone.pewpew.actor.TetraSpawner;

public class MeshManager {
	private static HashMap<Class, Mesh> MESH_MAP = new HashMap<Class, Mesh>();

	private static void load(String file, Class c) {
		MESH_MAP.put(c, new Mesh(Gdx.files.internal(file)));
	}

	public static Mesh getMesh(Class c) {
		return MESH_MAP.get(c);
	}

	static {
		load("models/tetra.obj", Tetra.class);
		load("models/hub.obj", Hub.class);
		load("models/tetraSpawner.obj", TetraSpawner.class);
	}
}
