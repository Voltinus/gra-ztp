import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class EnemyEasy extends Enemy {
	public EnemyEasy() {
		this.state = new StateNormal();
		this.size = new Point(52, 42);
		this.maxHp = 7;
		this.hp = this.maxHp;
		
		try {
			this.img = ImageIO.read(new File("img/enemy_easy.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować obrazka dla wroga łatwego (img/enemy_easy.png)");
		}
		
		Random r = new Random();
		this.position = new Point(r.nextInt(600) + 100 - this.size.x/2, -this.size.y);
		
		switch(r.nextInt(3)) {
			case 0: this.shootType = new ShootTypeCircle(GamePanel.DIFFICULTY.EASY); break;
			case 1: this.shootType = new ShootTypeLine(GamePanel.DIFFICULTY.EASY); break;
			case 2: this.shootType = new ShootTypeHoming(GamePanel.DIFFICULTY.EASY); break;
		}
	}
}
