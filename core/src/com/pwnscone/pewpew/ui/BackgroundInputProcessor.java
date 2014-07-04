package com.pwnscone.pewpew.ui;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.pwnscone.pewpew.Game;
import com.pwnscone.pewpew.util.Util;

public class BackgroundInputProcessor implements InputProcessor {
	private float rotation;
	private Vector3 lastDown;
	private Vector3 lastMove;
	private Vector3 screenPivot;

	public BackgroundInputProcessor() {
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
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer == 0) {
			float angle = (float) (Math.atan2(screenPivot.y - lastMove.y, screenPivot.x
					- lastMove.x) - Math.atan2(screenPivot.y - screenY, screenPivot.x - screenX))
					* Util.RAD_TO_DEG;
			if (angle < -180) {
				angle += 360;
			} else if (angle > 180) {
				angle -= 360;
			}
			rotation += angle;
			lastMove.set(screenX, screenY, 0.0f);
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
		Camera camera = Game.get().getRenderer().getCamera();
		float delta = rotation * .25f;
		rotation -= delta;
		camera.rotateAround(Vector3.Zero, camera.direction, delta);
	}
}
