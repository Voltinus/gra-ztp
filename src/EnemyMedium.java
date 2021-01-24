import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class EnemyMedium extends Enemy {
	public EnemyMedium() {
		this.size = new Point(78, 63);
		this.maxHp = 15;
		this.hp = this.maxHp;
		
		try {
			this.img = ImageIO.read(new File("img/enemy_medium.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować obrazka dla wroga średniego (img/enemy_medium.png)");
		}
		
		Random r = new Random();
		this.position = new Point(r.nextInt(600) + 100 - this.size.x/2, -this.size.y);
		
		switch(r.nextInt(3)) {
			case 0: this.shootType = new ShootTypeCircle(GamePanel.DIFFICULTY.MEDIUM); break;
			case 1: this.shootType = new ShootTypeLine(GamePanel.DIFFICULTY.MEDIUM); break;
			case 2: this.shootType = new ShootTypeWave(GamePanel.DIFFICULTY.MEDIUM); break;
		}
	}
}
