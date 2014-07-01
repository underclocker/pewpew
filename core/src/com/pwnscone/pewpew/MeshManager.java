package com.pwnscone.pewpew;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;

public class MeshManager {
	private static HashMap<Class, Mesh> MESH_MAP = new HashMap<Class, Mesh>();

	private static void load(String file, Class c) {
		MESH_MAP.put(c, new Mesh(Gdx.files.internal(file)));
	}

	public static Mesh getMesh(Class c) {
		return MESH_MAP.get(c);
	}

	public static void loadMeshes() {
		load("models/tetra.obj", Tetra.class);
	}
}
