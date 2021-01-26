import java.awt.Point;
import java.util.HashMap;

public abstract class State {
	public abstract void shoot(Point shootPosition, HashMap<BulletExtState, Bullet> bullets, Point playerPosition, Enemy e, int time);
}
