import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerBullet extends Bullet {
	PlayerBullet() {
		damage = 1;
		size = new Point(9, 54);
		speed = 500;
		dir = 0;
		
		try {
			img = ImageIO.read(new File("img/laser.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować obrazka dla pocisku gracza (img/laser.png)");
		}
	}
}