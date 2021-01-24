import java.awt.Point;
import java.util.HashMap;

public class ShootTypeCircle implements ShootType {

	public GamePanel.DIFFICULTY difficulty = null;
	
	ShootTypeCircle(GamePanel.DIFFICULTY diff) {
		this.difficulty = diff;
	}
	
	
	int bulletCount = 12;
	
	public void getBullets(Point position, HashMap<BulletExtState, Bullet> bullets, Point playerPosition) {
		for(int i=0; i<bulletCount; i++) {
			Bullet b = null;
			switch(difficulty) {
				case EASY:   b = new EnemyEasyBullet(); break;
				case MEDIUM: b = new EnemyMediumBullet(); break;
				case HARD:   b = new EnemyHardBullet(); break;
			}
			double x = i * (2*Math.PI) / bulletCount;
			BulletExtState ext = new BulletExtState(new Point(position.x + (int)(10*Math.sin(x)), position.y - (int)(10*Math.cos(x))), (Math.PI*2) / bulletCount * i);
			bullets.put(ext, b);
		}
	}

}
