import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemyEasyBullet extends Bullet {
	EnemyEasyBullet() {
		damage = 2;
		size = new Point(13, 13);
		speed = 300;
		dir = Math.PI;
		
		try {
			img = ImageIO.read(new File("img/laser_enemy.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować obrazka dla pocisku przeciwnika (img/laser.png)");
		}
	}
}
