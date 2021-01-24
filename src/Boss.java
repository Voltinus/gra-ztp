import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Boss extends Enemy {
	public Boss(GamePanel.DIFFICULTY diff) {
		this.size = new Point(98, 75);
		switch(diff) {
			case EASY:   this.maxHp = 50;  break;
			case MEDIUM: this.maxHp = 100; break;
			case HARD:   this.maxHp = 150; break;
		}
		this.hp = this.maxHp;
		
		try {
			switch(diff) {
				case EASY:   this.img = ImageIO.read(new File("img/boss1.png")); break;
				case MEDIUM: this.img = ImageIO.read(new File("img/boss2.png")); break;
				case HARD:   this.img = ImageIO.read(new File("img/boss3.png")); break;
			}
		} catch(IOException e) {
			System.out.println("Nie udało się załadować obrazka dla bossa");
		}
		
		Random r = new Random();
		this.position = new Point(400 - this.size.x/2, -this.size.y);
		
		switch(r.nextInt(3)) {
			case 0: this.shootType = new ShootTypeCircle(GamePanel.DIFFICULTY.HARD); break;
			case 1: this.shootType = new ShootTypeLine(GamePanel.DIFFICULTY.HARD); break;
			case 2: this.shootType = new ShootTypeHoming(GamePanel.DIFFICULTY.HARD); break;
		}
	}
	
	public void switchShootType() {
		Random r = new Random();
		switch(r.nextInt(3)) {
			case 0: this.shootType = new ShootTypeCircle(GamePanel.DIFFICULTY.HARD); break;
			case 1: this.shootType = new ShootTypeLine(GamePanel.DIFFICULTY.HARD); break;
			case 2: this.shootType = new ShootTypeHoming(GamePanel.DIFFICULTY.HARD); break;
		}
	}
}
