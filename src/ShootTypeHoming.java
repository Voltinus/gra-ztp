import java.awt.Point;
import java.util.HashMap;

public class ShootTypeHoming implements ShootType {

	public GamePanel.DIFFICULTY difficulty = null;
	
	ShootTypeHoming(GamePanel.DIFFICULTY diff) {
		this.difficulty = diff;
	}
	
	int bulletCount = 1;

	@Override
	public void getBullets(Point position, HashMap<BulletExtState, Bullet> bullets, Point playerPosition) {
		for(int i=0; i<bulletCount; i++) {
			Bullet b = null;
			switch(difficulty) {
				case EASY:   b = new EnemyEasyBullet(); break;
				case MEDIUM: b = new EnemyMediumBullet(); break;
				case HARD:   b = new EnemyHardBullet(); break;
			}
			Point.Double vec = new Point.Double(playerPosition.x - position.x, playerPosition.y - position.y);
			double vecLength = Math.sqrt(vec.x*vec.x + vec.y*vec.y);
			vec.x /= vecLength;
			vec.y /= vecLength;
			BulletExtState ext = new BulletExtState(new Point(position.x, position.y), Math.atan2(vec.x, -vec.y));
			bullets.put(ext, b);
		}
	}

}
