import java.awt.Color;
import java.awt.Point;

public class BulletExtState {
	public Point position;
	public double dir;
	public Color color;
	
	BulletExtState(Point position, double dir, Color color) {
		this.position = position;
		this.dir = dir;
		this.color = color;
	}
	
	BulletExtState(Point position, double dir) {
		this(position, dir, null);
	}
	
	BulletExtState(Point position) {
		this(position, 0, null);
	}
}
