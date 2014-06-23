package com.pwnscone.pewpew;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Renderer {
	public static final int HEIGHT = 16;
	public static final int WIDTH = 9;

	private OrthographicCamera camera;
	private LineRenderer lineRenderer;

	public OrthographicCamera getCamera() {
		return camera;
	}

	public Renderer() {
		camera = new OrthographicCamera();
		lineRenderer = new LineRenderer(camera);
		reset();
	}

	public void update() {
		camera.update();
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Simulation simulation = Game.get().getSimulation();
		Spring[] springArray = simulation.springArray;
		for (int i = 0; i < simulation.springs; i++) {
			Spring spring = springArray[i];
			Particle p0 = spring.p0;
			Particle p1 = spring.p1;
			lineRenderer.drawLine(p0.x, p0.y, Color.WHITE, p1.x, p1.y, Color.WHITE);
		}
		lineRenderer.render();
	}

	public void reset() {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		float ratio = ((float) width / height) / ((float) WIDTH / HEIGHT);
		if (ratio == 1.0) {
			camera.setToOrtho(false, WIDTH, HEIGHT);
		} else if (ratio > 1.0) {
			camera.setToOrtho(false, WIDTH * ratio, HEIGHT);
		} else if (ratio < 1.0) {
			camera.setToOrtho(false, WIDTH, HEIGHT / ratio);
		}
		camera.position.set(0.0f, 3.5f, 16.0f);
		camera.update();
		Gdx.gl.glDepthMask(false);
		Gdx.gl.glLineWidth(1.0f);
	}
}
