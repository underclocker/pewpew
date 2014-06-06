package com.pwnscone.pewpew;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Renderer {
	public static final int HEIGHT = 16;
	public static final int WIDTH = 9;

	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private LineRenderer lineRenderer;

	private Simulation simulation;

	public Renderer() {
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		lineRenderer = new LineRenderer(camera);
		reset();
	}

	public void update() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		float ratio =((float) width / height) / ((float) WIDTH / HEIGHT);
		if (ratio == 1.0) {
			camera.setToOrtho(false, WIDTH, HEIGHT);
		} else if (ratio > 1.0){
			camera.setToOrtho(false, WIDTH * ratio, HEIGHT);
		} else if (ratio < 1.0){
			camera.setToOrtho(false, WIDTH , HEIGHT / ratio);
		}
		Gdx.gl.glDepthMask(false);
		Gdx.gl.glLineWidth(1.0f);
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
}
