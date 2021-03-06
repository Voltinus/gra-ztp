import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemyMediumBullet extends Bullet {
	EnemyMediumBullet() {
		damage = 10;
		size = new Point(13, 13);
		baseSpeed = 400;
		
		try {
			img = ImageIO.read(new File("img/laser_enemy_blue.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować obrazka dla pocisku przeciwnika (img/laser.png)");
		}
	}
}
