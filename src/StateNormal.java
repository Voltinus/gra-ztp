import java.awt.Point;
import java.util.HashMap;

public class StateNormal extends State {

	@Override
	public void shoot(Point shootPosition, HashMap<BulletExtState, Bullet> bullets, Point playerPosition, Enemy e, int time) {
		if(time % 1000 == 0) {
			double hpPercentage = e.hp / (double)e.maxHp;
			e.shootType.getBullets(shootPosition, bullets, playerPosition, hpPercentage);
			if(e instanceof Boss) {
				((Boss) e).switchShootType();
			}
		}
	}

}
