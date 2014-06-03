package com.pwnscone.pewpew;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class Game extends ApplicationAdapter {
	FrameBuffer fbo = null;
	TextureRegion tr = null;
	SpriteBatch sb = null;

	@Override
	public void create () {
		fbo = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		tr = new TextureRegion(fbo.getColorBufferTexture());
		sb = new SpriteBatch();
		sb.enableBlending();
		Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void render () {
		fbo.begin();
		Gdx.gl.glClearColor(0, 0, 1, 0.05f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		fbo.end();

		//Gdx.gl.glClearColor(0, 0, 1, 0.5f);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		sb.begin();
		sb.draw(tr, 0, 0);
		sb.end();
	}
}
