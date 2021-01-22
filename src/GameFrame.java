import java.awt.AWTException;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	GameFrame() throws AWTException {
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.setTitle("Space Shooter");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
