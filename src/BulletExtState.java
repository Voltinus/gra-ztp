import java.awt.Color;
import java.awt.Point;

public class BulletExtState {
	public Point position;
	public double dir;
	public Color color;
	public int speed;
	
	BulletExtState(Point position, double dir, Color color, int speed) {
		this.position = position;
		this.dir = dir;
		this.color = color;
		this.speed = speed;
	}
	
	BulletExtState(Point position, double dir, Color color) {
		this(position, dir, color, 300);
	}
	
	BulletExtState(Point position, double dir) {
		this(position, dir, null, 300);
	}
	
	BulletExtState(Point position) {
		this(position, 0, null, 300);
	}
}
