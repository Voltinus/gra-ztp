import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {
	private BufferedImage logo = null;
	
	MainMenuPanel() {
		try {
			this.logo = ImageIO.read(new File("img/logo.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować logo (img/logo.png)");
		}
		
		this.setLayout(null);//new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//this.setBorder(BorderFactory.createEmptyBorder(400, 330, 0, 0));
		
		JButton newGameEasy = new JButton();
		newGameEasy.setText("Nowa gra (łatwy)");
		newGameEasy.setFont(new Font(newGameEasy.getFont().getName(), Font.PLAIN, 20));
		newGameEasy.setMargin(new Insets(10, 20, 10, 20));
		newGameEasy.setLocation(250, 350);
		newGameEasy.setSize(new Dimension(300, 50));
		newGameEasy.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					GameFrame.reloadPanels(true, GamePanel.DIFFICULTY.EASY);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		this.add(newGameEasy);
		
		JButton newGameMedium = new JButton();
		newGameMedium.setText("Nowa gra (średni)");
		newGameMedium.setFont(new Font(newGameMedium.getFont().getName(), Font.PLAIN, 20));
		newGameMedium.setMargin(new Insets(10, 20, 10, 20));
		newGameMedium.setLocation(250, 420);
		newGameMedium.setSize(new Dimension(300, 50));
		newGameMedium.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					GameFrame.reloadPanels(true, GamePanel.DIFFICULTY.MEDIUM);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		this.add(newGameMedium);
		
		JButton newGameHard = new JButton();
		newGameHard.setText("Nowa gra (trudny)");
		newGameHard.setFont(new Font(newGameHard.getFont().getName(), Font.PLAIN, 20));
		newGameHard.setMargin(new Insets(10, 20, 10, 20));
		newGameHard.setLocation(250, 490);
		newGameHard.setSize(new Dimension(300, 50));
		newGameHard.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					GameFrame.reloadPanels(true, GamePanel.DIFFICULTY.HARD);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		this.add(newGameHard);
		
		JButton scoreboard = new JButton();
		
		this.setPreferredSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.logo, 0, 0, null);
	}
}
