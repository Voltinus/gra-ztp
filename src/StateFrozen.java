import java.awt.Point;
import java.util.HashMap;

public class StateFrozen extends State {

	@Override
	public void shoot(Point shootPosition, HashMap<BulletExtState, Bullet> bullets, Point playerPosition, Enemy e, int time) {
		return;
	}

}
