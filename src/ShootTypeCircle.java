import java.awt.Point;
import java.util.HashMap;

public class ShootTypeCircle implements ShootType {

	public GamePanel.DIFFICULTY difficulty = null;
	
	ShootTypeCircle(GamePanel.DIFFICULTY diff) {
		this.difficulty = diff;
	}
	
	
	int bulletCount = 12;
	
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
				
			double x = i * (2*Math.PI) / bulletCount;
			BulletExtState ext = new BulletExtState(new Point(position.x + (int)(10*Math.sin(x)), position.y - (int)(10*Math.cos(x))), (Math.PI*2) / bulletCount * i, null, speed);
			bullets.put(ext, b);
		}
	}

}
