import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IteratorEnemyBullets implements Iterable<Map.Entry<Point, Bullet>> {

	HashMap<Point, Bullet> checkedBullets = new HashMap<Point, Bullet>();
	
	IteratorEnemyBullets(HashMap<Point, Bullet> bullets, Point playerPosition) {
		for(Map.Entry<Point, Bullet> bullet : bullets.entrySet()) {
			Point bulletPosition = bullet.getKey();
			int dist = (int)Math.sqrt(Math.pow(playerPosition.x - bulletPosition.x, 2) + Math.pow(playerPosition.y - bulletPosition.y, 2));
			
			if(!(bullet.getValue() instanceof PlayerBullet) && dist < 100) {
				checkedBullets.put(bullet.getKey(), bullet.getValue());
			}
		}
	}
	
	@Override
	public Iterator<Map.Entry<Point, Bullet>> iterator() {
		Iterator<Map.Entry<Point, Bullet>> it = new Iterator<Map.Entry<Point, Bullet>>() {
			
			@Override
			public boolean hasNext() {
				for(Map.Entry<Point, Bullet> bullet : checkedBullets.entrySet()) {
					if(bullet.getValue() != null) return true;
				}
				return false;
			}

			@Override
			public Map.Entry<Point, Bullet> next() {
				for(Map.Entry<Point, Bullet> bullet : checkedBullets.entrySet()) {
					if(bullet.getValue() != null) {
						Map.Entry<Point, Bullet> ret = Map.entry(bullet.getKey(), bullet.getValue());
						checkedBullets.put(bullet.getKey(), null);
						return ret;
					}
				}
				return null;
			}
			
		};
		return it;
	}

}
