import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IteratorPlayerBullets implements Iterable<Map.Entry<BulletExtState, Bullet>> {

	HashMap<BulletExtState, Bullet> checkedBullets = new HashMap<BulletExtState, Bullet>();
	
	IteratorPlayerBullets(HashMap<BulletExtState, Bullet> bullets) {
		for(Map.Entry<BulletExtState, Bullet> bullet : bullets.entrySet()) {
			
			if(bullet.getValue() instanceof PlayerBullet && bullet.getKey().position.y < 150) {
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
