import java.awt.Point;
import java.util.HashMap;

public class ShootTypeCircle implements ShootType {

	public GamePanel.DIFFICULTY difficulty = null;
	
	ShootTypeCircle(GamePanel.DIFFICULTY diff) {
		this.difficulty = diff;
	}
	
	
	int bulletCount = 12;
	
	public void getBullets(Point position, HashMap<Point, Bullet> bullets) {
		for(int i=0; i<bulletCount; i++) {
			Bullet b = null;
			switch(difficulty) {
				case EASY:   b = new EnemyEasyBullet(); break;
				case MEDIUM: b = new EnemyMediumBullet(); break;
				case HARD:   b = new EnemyHardBullet(); break;
			}
			b.dir = (Math.PI*2) / bulletCount * i;
			Point newPosition = new Point(position.x + (int)(10*Math.sin(b.dir)), position.y - (int)(10*Math.cos(b.dir)));
			bullets.put(newPosition, b);
		}
	}

}
