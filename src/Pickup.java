import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Pickup {
	public static enum TYPE {
		HP, CANNONS
	}
	public TYPE type;

	public Point position;
	public Point size;
	
	public BufferedImage img;
	
	
	Pickup() {
		Random r = new Random();
		TYPE allTypes[] = { TYPE.HP, TYPE.CANNONS };
		type = allTypes[r.nextInt(2)];
		
		switch(type) {
			case HP:
				size = new Point(19, 30);
				try {
					this.img = ImageIO.read(new File("img/power.png"));
				} catch(IOException e) {
					System.out.println("Nie udało się załadować obrazka dla pickupu 'power' (img/power.png)");
				}
				break;
				
			case CANNONS:
				size = new Point(32, 32);
				try {
					this.img = ImageIO.read(new File("img/cannons.png"));
				} catch(IOException e) {
					System.out.println("Nie udało się załadować obrazka dla pickupu 'cannons' (img/cannons.png)");
				}
				break;
		}
	}
	
	public void onPickup(Player p) {
		switch(type) {
			case HP:
				p.hp = Math.min(p.maxHp, p.hp + 30);
				break;
				
			case CANNONS:
				p.cannons = Math.min(3, p.cannons + 1);
				break;
		}
	}
}
