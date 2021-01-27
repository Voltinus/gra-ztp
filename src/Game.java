import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {

	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	public static void main(String[] args) throws AWTException, InterruptedException, FileNotFoundException, ClassNotFoundException, IOException {
		@SuppressWarnings("unused")
		GameFrame frame = new GameFrame();
	}
}
