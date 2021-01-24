import java.awt.Point;
import java.util.HashMap;

public interface ShootType {
	public void getBullets(Point position, HashMap<Point, Bullet> bullets);
}
