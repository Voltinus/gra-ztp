import java.awt.Point;
import java.util.Random;

public class Pickup {
	public static enum TYPE {
		HP, CANNONS
	}
	public TYPE type;

	public Point position;
	public Point size;
	
	private Player playerInstance;
	
	
	Pickup(Player p) {
		playerInstance = p;
		
		Random r = new Random();
		TYPE allTypes[] = { TYPE.HP, TYPE.CANNONS };
		type = allTypes[r.nextInt(2)];
		
		switch(type) {
			case HP:
				size = new Point();
				position = new Point();
				break;
				
			case CANNONS:
				size = new Point();
				position = new Point();
				break;
		}
	}
	
	
}
