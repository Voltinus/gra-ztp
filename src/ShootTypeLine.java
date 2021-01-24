import java.awt.Point;
import java.util.HashMap;
import java.lang.UnsupportedOperationException;

public class ShootTypeLine implements ShootType {

	public GamePanel.DIFFICULTY difficulty = null;
	
	ShootTypeLine(GamePanel.DIFFICULTY diff) {
		this.difficulty = diff;
	}
	

	int bulletCount = 5;
	
	@Override
	public void getBullets(Point position, HashMap<BulletExtState, Bullet> bullets, Point player) {
		for(int i=0; i<bulletCount; i++) {
			Bullet b = null;
			switch(difficulty) {
				case EASY:   b = new EnemyEasyBullet(); break;
				case MEDIUM: b = new EnemyMediumBullet(); break;
				case HARD:   b = new EnemyHardBullet(); break;
			}
			BulletExtState ext = new BulletExtState(new Point(position.x - (bulletCount/2)*20 + i*20, position.y), Math.PI);
			bullets.put(ext, b);
		}
	}
	
}
