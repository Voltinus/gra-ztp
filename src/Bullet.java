import java.awt.Point;
import java.awt.image.BufferedImage;

public class Bullet {
	public BufferedImage img;
	public Point size;
	public double dir; // kierunek w radianach, 0 = góra, 1/2PI = prawo, itd.
	public int speed;  // szybkośc pocisku w pikselach na sekundę
	public int damage;
}