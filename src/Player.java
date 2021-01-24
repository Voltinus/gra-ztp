import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	
	public String nick; 
	public final int maxHp = 100;
	public int hp = maxHp;
	public BufferedImage img;
	public Point position;
	public int speed = 500;
	public Point size = new Point(50, 38);
	public int cannons = 1;
	
	private static Player instance = null;
	
	private Player() {
		try {
			img = ImageIO.read(new File("img/player.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować obrazka dla gracza (img/player.png)");
		}
		
		reset();
	}
	
	public static Player getInstance() {
		if(instance == null) instance = new Player();
		return instance;
	}
	
	public void reset() {
		hp = maxHp;
	}
}
