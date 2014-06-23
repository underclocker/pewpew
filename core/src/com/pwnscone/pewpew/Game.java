package com.pwnscone.pewpew;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class Game extends ApplicationAdapter {

	private static Game GAME = null;

	private Renderer renderer;
	private Simulation simulation;
	private BackgroundInputProcessor backgroundInputProcessor;
	private InputMultiplexer inputMultiplexer;

	public static Game get() {
		return GAME;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Simulation getSimulation() {
		return simulation;
	}

	@Override
	public void create() {
		GAME = this;
		renderer = new Renderer();
		simulation = new Simulation();
		backgroundInputProcessor = new BackgroundInputProcessor();
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(backgroundInputProcessor);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render() {
		backgroundInputProcessor.update();
		simulation.update();
		renderer.update();
	}

	@Override
	public void resize(int width, int height) {
		renderer.reset();
	}
}
