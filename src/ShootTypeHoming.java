import java.awt.Point;
import java.util.HashMap;

public class ShootTypeHoming implements ShootType {

	public GamePanel.DIFFICULTY difficulty = null;
	
	ShootTypeHoming(GamePanel.DIFFICULTY diff) {
		this.difficulty = diff;
	}
	
	int bulletCount = 1;

	@Override
	public void getBullets(Point position, HashMap<BulletExtState, Bullet> bullets, Point playerPosition, double hpPercentage) {
		if(hpPercentage <= 0.2) return;
		
		for(int i=0; i<bulletCount; i++) {
			Bullet b = null;
			switch(difficulty) {
				case EASY:   b = BulletFactory.getBullet(BulletFactory.BULLET_TYPES.ENEMY_EASY_BULLET);   break;
				case MEDIUM: b = BulletFactory.getBullet(BulletFactory.BULLET_TYPES.ENEMY_MEDIUM_BULLET); break;
				case HARD:   b = BulletFactory.getBullet(BulletFactory.BULLET_TYPES.ENEMY_HARD_BULLET);   break;
			}
			
			int speed = 0;
			if(hpPercentage > 0.5) {
				speed = b.baseSpeed;
			} else if(hpPercentage > 0.2) {
				speed = b.baseSpeed / 2;
			}
			
			Point.Double vec = new Point.Double(playerPosition.x - position.x + 25, playerPosition.y - position.y + 19);
			double vecLength = Math.sqrt(vec.x*vec.x + vec.y*vec.y);
			vec.x /= vecLength;
			vec.y /= vecLength;
			BulletExtState ext = new BulletExtState(new Point(position.x, position.y), Math.atan2(vec.x, -vec.y), null, speed);
			bullets.put(ext, b);
		}
	}

}
