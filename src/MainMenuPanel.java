import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {
	private BufferedImage logo = null;
	
	MainMenuPanel() throws FileNotFoundException, ClassNotFoundException, IOException {
		try {
			this.logo = ImageIO.read(new File("img/logo.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować logo (img/logo.png)");
		}
		
		this.setLayout(null);
		
		JButton newGameEasy = new JButton();
		newGameEasy.setText("Nowa gra (łatwy)");
		newGameEasy.setFont(new Font(newGameEasy.getFont().getName(), Font.PLAIN, 20));
		newGameEasy.setMargin(new Insets(10, 20, 10, 20));
		newGameEasy.setLocation(50, 350);
		newGameEasy.setSize(new Dimension(300, 50));
		newGameEasy.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					GameFrame.reloadPanels(true, GamePanel.DIFFICULTY.EASY);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		    }
		});
		this.add(newGameEasy);
		
		JButton newGameMedium = new JButton();
		newGameMedium.setText("Nowa gra (średni)");
		newGameMedium.setFont(new Font(newGameMedium.getFont().getName(), Font.PLAIN, 20));
		newGameMedium.setMargin(new Insets(10, 20, 10, 20));
		newGameMedium.setLocation(50, 420);
		newGameMedium.setSize(new Dimension(300, 50));
		newGameMedium.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					GameFrame.reloadPanels(true, GamePanel.DIFFICULTY.MEDIUM);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		    }
		});
		this.add(newGameMedium);
		
		JButton newGameHard = new JButton();
		newGameHard.setText("Nowa gra (trudny)");
		newGameHard.setFont(new Font(newGameHard.getFont().getName(), Font.PLAIN, 20));
		newGameHard.setMargin(new Insets(10, 20, 10, 20));
		newGameHard.setLocation(50, 490);
		newGameHard.setSize(new Dimension(300, 50));
		newGameHard.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					GameFrame.reloadPanels(true, GamePanel.DIFFICULTY.HARD);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		    }
		});
		this.add(newGameHard);
		
		String[] columnNames = { "Lp.", "Nick" , "Wynik" };
		Ranking ranking = new RankingProxy();
		ArrayList<String[]> rankingArrayList = ranking.load();
		
		Object[][] array = new Object[rankingArrayList.size()][];
		for (int i = 0; i < rankingArrayList.size(); i++) {
		    Object[] row = rankingArrayList.get(i);
		    array[i] = new Object[]{ i+1, row[0], row[1] };
		}
		
		JTable scoreboard = new JTable(array, columnNames);
		scoreboard.setLocation(450, 380);
		scoreboard.setSize(new Dimension(300, 190));
		scoreboard.setBackground(Color.BLACK);
		scoreboard.setForeground(Color.WHITE);
		JTableHeader scoreboardHeader = scoreboard.getTableHeader();
		scoreboardHeader.setLocation(450, 350);
		scoreboardHeader.setSize(new Dimension(300, 30));
		scoreboardHeader.setBackground(Color.BLACK);
		scoreboardHeader.setForeground(Color.WHITE);
		TableColumnModel tcm = scoreboard.getColumnModel();
		tcm.getColumn(0).setMaxWidth(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		tcm.getColumn(2).setCellRenderer(rightRenderer);
		this.add(scoreboard);
		this.add(scoreboardHeader);
		
		this.setPreferredSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.logo, 0, 0, null);
	}
}
