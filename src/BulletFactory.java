import java.util.HashMap;

public class BulletFactory {
	public static enum BULLET_TYPES {
		PLAYER_BULLET,
		ENEMY_EASY_BULLET,
		ENEMY_MEDIUM_BULLET,
		ENEMY_HARD_BULLET
	};
	
	private static HashMap<BULLET_TYPES, Bullet> bullets = new HashMap<BULLET_TYPES, Bullet>();
	
	public static Bullet getBullet(BulletFactory.BULLET_TYPES type) {
		if(bullets.containsKey(type)) {
			return bullets.get(type);
		}
		
		Bullet b;
		
		switch(type) {
			case PLAYER_BULLET:       b = new PlayerBullet();      break;
			case ENEMY_EASY_BULLET:   b = new EnemyEasyBullet();   break;
			case ENEMY_MEDIUM_BULLET: b = new EnemyMediumBullet(); break;
			case ENEMY_HARD_BULLET:	  b = new EnemyHardBullet();   break;
			default: b = new Bullet();
		}
		
		bullets.put(type, b);
		return b;
	}
	
	
}
