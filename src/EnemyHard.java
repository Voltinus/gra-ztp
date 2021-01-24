import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class EnemyHard extends Enemy {
	public EnemyHard() {
		this.size = new Point(93, 84);
		this.maxHp = 23;
		this.hp = this.maxHp;
		
		try {
			this.img = ImageIO.read(new File("img/enemy_hard.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować obrazka dla wroga trudnego (img/enemy_hard.png)");
		}
		
		Random r = new Random();
		this.position = new Point(r.nextInt(600) + 100 - this.size.x/2, -this.size.y);
		
		switch(r.nextInt(3)) {
			case 0: this.shootType = new ShootTypeCircle(GamePanel.DIFFICULTY.HARD); break;
			case 1: this.shootType = new ShootTypeLine(GamePanel.DIFFICULTY.HARD); break;
			case 2: this.shootType = new ShootTypeWave(GamePanel.DIFFICULTY.HARD); break;
		}
	}
}
