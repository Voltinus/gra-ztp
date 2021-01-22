import java.util.ArrayList;
import java.util.HashMap;

public class BulletFactory {
	private static HashMap<Integer, Bullet> bullets = new HashMap<Integer, Bullet>();
	
	public static Bullet getBullet(int type) {
		if(bullets.containsKey(type)) {
			return bullets.get(type);
		}
		
		Bullet b;
		
		switch(type) {
			case 0:	b = new PlayerBullet();      break;
			case 1:	b = new EnemyEasyBullet();   break;
			case 2:	b = new EnemyMediumBullet(); break;
			case 3:	b = new EnemyHardBullet();   break;
			default: b = new Bullet();
		}
		
		bullets.put(type, b);
		return b;
	}
}
