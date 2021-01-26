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
	public void getBullets(Point position, HashMap<BulletExtState, Bullet> bullets, Point player, double hpPercentage) {
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
			
			BulletExtState ext = new BulletExtState(new Point(position.x - (bulletCount/2)*20 + i*20, position.y), Math.PI, null, speed);
			bullets.put(ext, b);
		}
	}
	
}
