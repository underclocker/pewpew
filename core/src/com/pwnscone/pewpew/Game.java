package com.pwnscone.pewpew;

import com.badlogic.gdx.ApplicationAdapter;

public class Game extends ApplicationAdapter {
	private Renderer renderer;
	private Simulation simulation;

	@Override
	public void create () {
		renderer = new Renderer();
		simulation = new Simulation();
		renderer.setSimulation(simulation);
	}

	@Override
	public void render () {
		simulation.update();
		renderer.update();
	}
}
