package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pwnscone.pewpew.Game;
import com.pwnscone.pewpew.Particle;
import com.pwnscone.pewpew.Simulation;
import com.pwnscone.pewpew.Spring;
import com.pwnscone.pewpew.util.Pool;

public class Renderer {
	private static final boolean DEBUG = true;

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

		Simulation sim = Game.get().getSimulation();

		Pool<Laser> laserPool = sim.mLaserPool;
		for (int i = 0; i < laserPool.fill; i++) {
			Laser laser = laserPool.get(i);
			lineRenderer.drawLine(laser.x0, laser.y0, laser.color, laser.x1, laser.y1, laser.color);
		}

		Pool<Spring> springPool = sim.mSpringPool;
		for (int i = 0; i < springPool.fill; i++) {
			Spring spring = springPool.get(i);
			Particle p0 = spring.p0;
			Particle p1 = spring.p1;
			lineRenderer.drawLine(p0.x, p0.y, spring.color, p1.x, p1.y, spring.color);
		}

		if (DEBUG) {
			Pool<Particle> particlePool = sim.mParticlePool;
			for (int i = 0; i < particlePool.fill; i++) {
				Particle p = particlePool.get(i);
				if (p.mark) {
					lineRenderer.drawLine(p.x - .03f, p.y - .03f, Color.GREEN, p.x + .03f,
							p.y + .03f, Color.GREEN);
					lineRenderer.drawLine(p.x + .03f, p.y - .03f, Color.GREEN, p.x + .03f,
							p.y - .03f, Color.GREEN);
				}
			}
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
		Gdx.gl.glLineWidth(2.0f);
	}
}
