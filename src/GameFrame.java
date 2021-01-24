import java.awt.AWTException;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private static GameFrame instance;

	private static GamePanel gamePanel = null;
	private static MainMenuPanel mainMenuPanel = null;
	
	public static void reloadPanels(boolean running, GamePanel.DIFFICULTY diff) throws AWTException {
		if(gamePanel != null) {
			instance.remove(gamePanel);
			gamePanel = null;
		}
		if(mainMenuPanel != null) {
			instance.remove(mainMenuPanel);
			mainMenuPanel = null;
		}
		
		if(running) {
			gamePanel = new GamePanel(diff);
			instance.add(gamePanel);
			gamePanel.requestFocus();
		} else {
			mainMenuPanel = new MainMenuPanel();
			instance.add(mainMenuPanel);
		}

		instance.pack();
		instance.setTitle("Space Shooter");
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instance.setResizable(false);
		instance.setVisible(true);
		instance.setLocationRelativeTo(null);
	}
	
	public static void reloadPanels(boolean running) throws AWTException {
		reloadPanels(running, GamePanel.DIFFICULTY.EASY);
	}
	
	GameFrame() throws AWTException {
		GameFrame.instance = this;
		reloadPanels(false);
	}
	
}
