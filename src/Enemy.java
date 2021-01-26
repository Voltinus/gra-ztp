import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class Enemy {
	public int hp;
	public int maxHp;
	
	public BufferedImage img;
	
	public Point size;
	public Point position;
	
	public ShootType shootType;
	public State state;
}
