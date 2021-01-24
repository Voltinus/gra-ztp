import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IteratorEnemyBullets implements Iterable<Map.Entry<BulletExtState, Bullet>> {

	HashMap<BulletExtState, Bullet> checkedBullets = new HashMap<BulletExtState, Bullet>();
	
	IteratorEnemyBullets(HashMap<BulletExtState, Bullet> bullets, Point playerPosition) {
		for(Map.Entry<BulletExtState, Bullet> bullet : bullets.entrySet()) {
			Point bulletPosition = bullet.getKey().position;
			int dist = (int)Math.sqrt(Math.pow(playerPosition.x - bulletPosition.x, 2) + Math.pow(playerPosition.y - bulletPosition.y, 2));
			
			if(!(bullet.getValue() instanceof PlayerBullet) && dist < 100) {
				checkedBullets.put(bullet.getKey(), bullet.getValue());
			}
		}
	}
	
	@Override
	public Iterator<Map.Entry<BulletExtState, Bullet>> iterator() {
		Iterator<Map.Entry<BulletExtState, Bullet>> it = new Iterator<Map.Entry<BulletExtState, Bullet>>() {
			
			@Override
			public boolean hasNext() {
				for(Map.Entry<BulletExtState, Bullet> bullet : checkedBullets.entrySet()) {
					if(bullet.getValue() != null) return true;
				}
				return false;
			}

			@Override
			public Map.Entry<BulletExtState, Bullet> next() {
				for(Map.Entry<BulletExtState, Bullet> bullet : checkedBullets.entrySet()) {
					if(bullet.getValue() != null) {
						Map.Entry<BulletExtState, Bullet> ret = Map.entry(bullet.getKey(), bullet.getValue());
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
