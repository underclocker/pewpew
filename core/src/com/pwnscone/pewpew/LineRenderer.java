package com.pwnscone.pewpew;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

public class LineRenderer {
	private static final int MAX_VERTS = 1024;
	private static final int POSITION_COMPONENTS = 3;
	private static final int COLOR_COMPONENTS = 4;
	private static final int TOTAL_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;

	public static final String VERT_SHADER = "attribute vec3 a_position;\n"
			+ "attribute vec4 a_color;\n" + "uniform mat4 u_projTrans;\n"
			+ "varying vec4 vColor;\n" + "void main() {\n" + "	vColor = a_color;\n"
			+ "	gl_Position =  u_projTrans * vec4(a_position.xyz, 1.0);\n" + "}";

	public static final String FRAG_SHADER = "#ifdef GL_ES\n" + "precision mediump float;\n"
			+ "#endif\n" + "varying vec4 vColor;\n" + "void main() {\n"
			+ "	gl_FragColor = vColor;\n" + "}";

	private Mesh mesh;
	private ShaderProgram shader;
	private Camera camera;

	private float[] verts = new float[MAX_VERTS * TOTAL_COMPONENTS];
	private int index = 0;

	public LineRenderer(Camera camera) {
		mesh = new Mesh(true, MAX_VERTS, 0, new VertexAttribute(Usage.Position,
				POSITION_COMPONENTS, "a_position"), new VertexAttribute(Usage.Color,
				COLOR_COMPONENTS, "a_color"));
		shader = new ShaderProgram(VERT_SHADER, FRAG_SHADER);
		this.camera = camera;
	}

	public void drawLine(Vector3 v0, Color color0, Vector3 v1, Color color1) {
		if (index == verts.length)
			render();

		verts[index++] = v0.x;
		verts[index++] = v0.y;
		verts[index++] = v0.z;
		verts[index++] = color0.r;
		verts[index++] = color0.g;
		verts[index++] = color0.b;
		verts[index++] = color0.a;

		verts[index++] = v1.x;
		verts[index++] = v1.y;
		verts[index++] = v1.z;
		verts[index++] = color1.r;
		verts[index++] = color1.g;
		verts[index++] = color1.b;
		verts[index++] = color1.a;
	}

	public void render() {
		if (index <= 0)
			return;

		mesh.setVertices(verts);
		int vertNum = index / TOTAL_COMPONENTS;

		shader.begin();
		shader.setUniformMatrix("u_projTrans", camera.combined);

		mesh.render(shader, GL20.GL_LINES, 0, vertNum);

		shader.end();

		index = 0;
	}
}
