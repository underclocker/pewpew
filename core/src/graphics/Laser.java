package graphics;

import com.badlogic.gdx.graphics.Color;
import com.pwnscone.pewpew.util.Poolable;

public class Laser extends Poolable {
	public Color color;
	public float x0;
	public float y0;
	public float x1;
	public float y1;

	public void set(float x0, float y0, float x1, float y1, Color color) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.color = color;
	}
}
