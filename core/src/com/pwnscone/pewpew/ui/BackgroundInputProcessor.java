package com.pwnscone.pewpew.ui;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.pwnscone.pewpew.Game;
import com.pwnscone.pewpew.util.Misc;

public class BackgroundInputProcessor implements InputProcessor {
	private float rotation;
	private float zoom;
	private Vector3 lastDown;
	private Vector3 lastMove;
	private Vector3 screenPivot;

	public BackgroundInputProcessor() {
		rotation = 0;
		zoom = 1;
		lastDown = new Vector3();
		lastMove = new Vector3();
		screenPivot = new Vector3();
		screenPivot.set(0.0f, 7.0f, 0.0f);
		Camera camera = Game.get().getRenderer().getCamera();
		camera.project(screenPivot);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pointer == 0) {
			rotation = 0;
			lastDown.set(screenX, screenY, 0.0f);
			lastMove.set(lastDown);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer == 0) {
			rotation = 0;
			zoom = 1;
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer == 0) {
			float angle = (float) (Math.atan2(screenPivot.y - lastMove.y, screenPivot.x
					- lastMove.x) - Math.atan2(screenPivot.y - screenY, screenPivot.x - screenX))
					* Misc.RAD_TO_DEG;
			if (angle < -180) {
				angle += 360;
			} else if (angle > 180) {
				angle -= 360;
			}
			rotation += angle;
			lastMove.set(screenX, screenY, 0.0f);

			if (false) {
				float dx = screenPivot.x - lastDown.x;
				float dy = screenPivot.y - lastDown.y;
				float dist = (float) Math.sqrt(dx * dx + dy * dy);

				dx = screenPivot.x - screenX;
				dy = screenPivot.y - screenY;
				float dist2 = (float) Math.sqrt(dx * dx + dy * dy);

				zoom = dist2 / dist;
				if (zoom > 1.35f) {
					zoom -= .35f;
					zoom = (1 + zoom) / 2f;
					zoom = (1 + zoom) / 2f;
				} else if (zoom < .75f) {
					zoom += .25f;
				} else {
					zoom = 1;
				}
				zoom = (1 + zoom) / 2f;
				zoom = (1 + zoom) / 2f;
				zoom = (1 + zoom) / 2f;
			}
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public void update() {
		OrthographicCamera camera = Game.get().getRenderer().getCamera();

		float delta = rotation * .25f;
		rotation -= delta;
		camera.rotateAround(Vector3.Zero, camera.direction, delta);

		if ((camera.zoom < 3 && zoom > 1) || (camera.zoom > .75f && zoom < 1)) {
			camera.zoom *= zoom;
			camera.position.x *= zoom;
			camera.position.y *= zoom;
		}
	}
}
