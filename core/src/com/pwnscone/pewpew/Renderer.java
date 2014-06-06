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
		lineRenderer = new LineRenderer();
		reset();
	}

	public void update() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		lineRenderer.drawLine(0, 0, Color.GREEN, 4, 4, Color.RED);
		lineRenderer.drawLine(0, 0, Color.BLUE, 8, 4, Color.GREEN);
		lineRenderer.render(camera);
	}

	private void reset() {
		camera.setToOrtho(false, WIDTH, HEIGHT);
		Gdx.gl.glDepthMask(false);
		Gdx.gl.glLineWidth(5);
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
}
