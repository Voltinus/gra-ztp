import java.awt.Point;
import java.util.HashMap;

public class ShootTypeWave implements ShootType {

	public GamePanel.DIFFICULTY difficulty = null;
	
	ShootTypeWave(GamePanel.DIFFICULTY diff) {
		this.difficulty = diff;
	}
	

	int bulletCount = 1;

	@Override
	public void getBullets(Point position, HashMap<Point, Bullet> bullets) {
		for(int i=0; i<bulletCount; i++) {
			Bullet b = null;
			switch(difficulty) {
				case EASY:   b = new EnemyEasyBullet(); break;
				case MEDIUM: b = new EnemyMediumBullet(); break;
				case HARD:   b = new EnemyHardBullet(); break;
			}
			b.dir = Math.PI;
			Point newPosition = new Point(position.x, position.y);
			bullets.put(newPosition, b);
		}
	}

}
