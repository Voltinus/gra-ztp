import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener {
	boolean running = true;
	
	public static enum DIFFICULTY { EASY, MEDIUM, HARD }
	public DIFFICULTY difficulty;
	
	public int points = 0;
	
	Player player = Player.getInstance();
	HashMap<BulletExtState, Bullet> bullets = new HashMap<BulletExtState, Bullet>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Pickup> pickups = new ArrayList<Pickup>();
	Ranking ranking;
	
	
	void endGame(boolean isWin) throws AWTException, InterruptedException, ClassNotFoundException, IOException {
		running = false;
		timer.cancel();
		timer.purge();
		this.remove(statsLabel);
		this.setLayout(null);
		
		boolean isOnScoreboard = ranking.getScoreCount() < 10 || ranking.getLowestScore() < points;
		
		JLabel label = new JLabel();
		if(isWin) {
			label.setText("WYGRAŁEŚ");
			label.setForeground(Color.GREEN);
		} else {
			label.setText("PRZEGRAŁEŚ");
			label.setForeground(Color.RED);
		}
		label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 50));
		label.setLocation(250, 50);
		label.setSize(new Dimension(500, 50));
		this.add(label);
		
		if(isOnScoreboard) {
			JLabel score = new JLabel();
			score.setFont(new Font(score.getFont().getName(), Font.PLAIN, 30));
			score.setForeground(Color.WHITE);
			score.setLocation(100, 150);
			score.setSize(new Dimension(200, 30));
			score.setText("Wynik: " + points);
			this.add(score);
			
			JLabel nick = new JLabel();
			nick.setFont(new Font(score.getFont().getName(), Font.PLAIN, 30));
			nick.setForeground(Color.WHITE);
			nick.setLocation(100, 225);
			nick.setSize(new Dimension(200, 30));
			nick.setText("Nick:");
			this.add(nick);
			
			JTextField textbox = new JTextField();
			textbox.setLocation(100, 260);
			textbox.setSize(new Dimension(300, 35));
			textbox.setFont(new Font(textbox.getFont().getName(), Font.PLAIN, 30));
			textbox.setForeground(Color.WHITE);
			textbox.setBackground(Color.BLACK);
			this.add(textbox);
			
			JButton button = new JButton();
			button.setLocation(300, 400);
			button.setSize(new Dimension(200, 35));
			button.setFont(new Font(button.getFont().getName(), Font.PLAIN, 20));
			button.setForeground(Color.WHITE);
			button.setBackground(Color.BLACK);
			button.setText("Zapisz");
			button.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	try {
			    		ranking.save(textbox.getText(), points);
						GameFrame.reloadPanels(false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			    }
			});
			this.add(button);
		} else { // !isOnScoreboard
			JLabel score = new JLabel();
			score.setFont(new Font(score.getFont().getName(), Font.PLAIN, 30));
			score.setForeground(Color.WHITE);
			score.setLocation(100, 150);
			score.setSize(new Dimension(600, 30));
			score.setText("Nie pobito żadnego wyniku");
			this.add(score);
			
			JButton button = new JButton();
			button.setLocation(300, 400);
			button.setSize(new Dimension(200, 35));
			button.setFont(new Font(button.getFont().getName(), Font.PLAIN, 20));
			button.setForeground(Color.WHITE);
			button.setBackground(Color.BLACK);
			button.setText("Wróć do menu");
			button.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	try {
						GameFrame.reloadPanels(false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			    }
			});
			this.add(button);
		}
		
		

		this.setBackground(Color.BLACK);
	}
	
	
	int currentState = 0;
	
	private void spawnEnemies() throws AWTException, InterruptedException, ClassNotFoundException, IOException {
		if(currentState % 4 == 3) { // Boss
			Boss boss = null;
			switch(currentState / 4) {
				case 0: boss = new Boss(GamePanel.DIFFICULTY.EASY);   break;
				case 1: boss = new Boss(GamePanel.DIFFICULTY.MEDIUM); break;
				case 2: boss = new Boss(GamePanel.DIFFICULTY.HARD);   break;
			}

			enemies.add(boss);
		} else {
			if(currentState == 12) {
				endGame(true);
			}
			
			int enemiesCount = 0;
			
			switch(currentState / 4) {
				case 0: enemiesCount = 3; break;
				case 1: enemiesCount = 4; break;
				case 2: enemiesCount = 6; break;
			}
			
			for(int i=0; i<enemiesCount; i++) {
				EnemyFactory ef = null;
				
				switch(difficulty) {
					case EASY:   ef = new EnemyEasyFactory(); break;
					case MEDIUM: ef = new EnemyMediumFactory(); break;
					case HARD:   ef = new EnemyHardFactory(); break;
				}

				Enemy e = ef.createEnemy();
				enemies.add(e);
			}
		}
		
		currentState++;
	}
	

	JLabel statsLabel = new JLabel("HP: 100%");
	void updateStats() {
		if(this.player.hp <= 30) {
			statsLabel.setForeground(Color.RED);
		} else {
			statsLabel.setForeground(Color.WHITE);
		}
		statsLabel.setText("HP: " + this.player.hp + "%, POINTS: " + points);
	}
	
	GamePanel(GamePanel.DIFFICULTY diff) throws AWTException, InterruptedException, FileNotFoundException, ClassNotFoundException, IOException {
		statsLabel.setForeground(Color.WHITE);
		statsLabel.setFont(new Font(statsLabel.getFont().getName(), Font.PLAIN, 20));
		this.add(statsLabel);
		
		this.setPreferredSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		this.player.hp = this.player.maxHp;
		this.difficulty = diff;
		
		try {
			bg0 = ImageIO.read(new File("img/bg0.png"));
			bg1 = ImageIO.read(new File("img/bg1.png"));
			bg2 = ImageIO.read(new File("img/bg2.png"));
			bg0b = ImageIO.read(new File("img/bg0.png"));
			bg1b = ImageIO.read(new File("img/bg1.png"));
			bg2b = ImageIO.read(new File("img/bg2.png"));
		} catch(IOException e) {
			System.out.println("Nie udało się załadować obrazka dla pocisku gracza (img/laser.png)");
		}
		
		this.ranking = new RankingProxy();
		
		startGame();		
	}
	

	Timer timer = new Timer();
	static int tickTime = 20;
	
	public void startGame() throws AWTException {
		player.position = new Point(Game.SCREEN_WIDTH / 2 - player.size.x / 2, (int)(Game.SCREEN_HEIGHT * 0.9));

		Robot robot = new Robot();
		
		timer.schedule(new TimerTask() {

			long lastTime = System.currentTimeMillis();
			@Override
			public void run() {
				double delta = (System.currentTimeMillis() - lastTime) / 1000.0;
				lastTime = System.currentTimeMillis();
				
				if(running) {	
					try {
						tick(delta);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				repaint();
			    robot.mouseRelease(0);
			}
		}, 0, tickTime);
	}
	
	
	public static int tickNo = 0;
	
	public void tick(double delta) throws AWTException, InterruptedException, ClassNotFoundException, IOException {
		/// Player ///
		
		Point.Double velocity = new Point.Double(0, 0);

		if(pressed.get("A") || pressed.get("Left")) velocity.x -= 1;
		if(pressed.get("D") || pressed.get("Right")) velocity.x += 1;
		if(pressed.get("W") || pressed.get("Up")) velocity.y -= 1;
		if(pressed.get("S") || pressed.get("Down")) velocity.y += 1;
		
		if(pressed.get("Shift")) {
			velocity.x /= 2.0;
			velocity.y /= 2.0;
		}
		
		if(velocity.x != 0 && velocity.y != 0) {
			velocity.x /= Math.sqrt(2);
			velocity.y /= Math.sqrt(2);
		}
		
		player.position.translate((int)(velocity.x * player.speed * delta), (int)(velocity.y * player.speed * delta));

		player.position.x = Math.max(0, player.position.x);
		player.position.y = Math.max(0, player.position.y);
		player.position.x = Math.min(Game.SCREEN_WIDTH - player.size.x, player.position.x);
		player.position.y = Math.min(Game.SCREEN_HEIGHT - player.size.y, player.position.y);
		
		
		/// Bullets ///
		
		// Spawn player bullet
		if(tickNo*tickTime % 200 == 0) {
			PlayerBullet b = (PlayerBullet)BulletFactory.getBullet(BulletFactory.BULLET_TYPES.PLAYER_BULLET);
			int bulletDist = 20;
			for(int i=0; i<player.cannons; i++) {
				Point pos = new Point(player.position.x + player.size.x/2 - b.size.x/2 - ((player.cannons-1)*bulletDist/2) + (i*bulletDist), player.position.y - 5);
				bullets.put(new BulletExtState(pos, 0, null, 500), b);
			}
		}
		
		// Spawn enemy bullets
		if(tickNo*tickTime % 1000 == 0) {
			for(Enemy e : enemies) {
				Point bulletSize = BulletFactory.getBullet(BulletFactory.BULLET_TYPES.ENEMY_EASY_BULLET).size;
				Point shootPosition = new Point(e.position.x + e.size.x/2 - bulletSize.x/2, e.position.y + e.size.y/2 - bulletSize.y/2);
				e.state.shoot(shootPosition, bullets, player.position, e, tickNo*tickTime);
			}
		}
		
		// Move existing bullets
		for(Map.Entry<BulletExtState, Bullet> bullet : bullets.entrySet()) {
			int dx = (int)(bullet.getKey().speed * delta *  Math.sin(bullet.getKey().dir));
			int dy = (int)(bullet.getKey().speed * delta * -Math.cos(bullet.getKey().dir));
			bullet.getKey().position.translate(dx, dy);
		}
		
		// Remove off-screen bullets every second
		if(tickNo*tickTime % 1000 == 0) {
			HashMap<BulletExtState, Bullet> newBullets = new HashMap<BulletExtState, Bullet>();
			for(Map.Entry<BulletExtState, Bullet> bullet : bullets.entrySet()) {
				Point pos = bullet.getKey().position;
				Point size = bullet.getValue().size;
				if(pos.x + size.x < 0 || pos.y + size.y < 0 || pos.x > Game.SCREEN_WIDTH || pos.y > Game.SCREEN_HEIGHT) {
					continue;
				}
				newBullets.put(bullet.getKey(), bullet.getValue());
			}
			bullets = newBullets;
		}

		
		// Collision check
		
		// Player bullets and enemies
		ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();
		IteratorPlayerBullets iteratorPlayerBullets = new IteratorPlayerBullets(bullets);
		Iterator<Map.Entry<BulletExtState, Bullet>> itPlayer = iteratorPlayerBullets.iterator();
		while(itPlayer.hasNext()) {
			Map.Entry<BulletExtState, Bullet> entry = itPlayer.next();
			Point pos = entry.getKey().position;
			Bullet bullet = entry.getValue();
			
			for(Enemy e : enemies) {
				if (pos.x < e.position.x + e.size.x &&
				   pos.x + bullet.size.x > e.position.x &&
				   pos.y < e.position.y + e.size.y &&
				   pos.y + bullet.size.y > e.position.y) {
				    e.hp -= bullet.damage;
				    if(e.hp <= 0) {
				    	switch(difficulty) {
					    	case EASY:   points += 100; break;
					    	case MEDIUM: points += 200; break;
					    	case HARD:   points += 300; break;
				    	}
				    	updateStats();
				    	enemiesToRemove.add(e);
				    } else {
				    	switch(difficulty) {
					    	case EASY:   points += 10; break;
					    	case MEDIUM: points += 20; break;
					    	case HARD:   points += 30; break;
				    	}
				    }
			    	entry.getKey().position.translate(Game.SCREEN_WIDTH*100, Game.SCREEN_HEIGHT*100);
			    	break;
				}
			}
		}
		for(Enemy e : enemiesToRemove) {
			if(!enemies.contains(e)) continue;
			
			Random r = new Random();
			int invPickupChance = 0;
			
			switch(difficulty) {
				case EASY:   invPickupChance = 3; break;
				case MEDIUM: invPickupChance = 5; break;
				case HARD:   invPickupChance = 8; break;
			}
			
			int rand = r.nextInt(invPickupChance);
			
			if(rand == 0) {
				Pickup pickup = new Pickup();
				pickup.position = new Point(
						e.position.x + e.size.x/2 - pickup.size.x/2,
						e.position.y + e.size.y/2 - pickup.size.y/2
				);
				pickups.add(pickup);
			}
			
			enemies.remove(e);
		}
		
		// Enemy bullets and player
		IteratorEnemyBullets iteratorEnemyBullets = new IteratorEnemyBullets(bullets, player.position);
		Iterator<Map.Entry<BulletExtState, Bullet>> itEnemy = iteratorEnemyBullets.iterator();
		while(itEnemy.hasNext()) {
			Map.Entry<BulletExtState, Bullet> entry = itEnemy.next();
			Point pos = entry.getKey().position;
			Bullet bullet = entry.getValue();
			
			if (pos.x < player.position.x + player.size.x &&
			   pos.x + bullet.size.x > player.position.x &&
			   pos.y < player.position.y + player.size.y &&
			   pos.y + bullet.size.y > player.position.y) {
			    player.hp -= bullet.damage;
			    player.cannons = Math.max(1, player.cannons - 1);
			    updateStats();
			    if(player.hp <= 0) {
			    	timer.cancel();
			    	timer.purge();
					endGame(false);
			    }
		    	entry.getKey().position.translate(Game.SCREEN_WIDTH*100, Game.SCREEN_HEIGHT*100);
		    	break;
			}
		}
		
		
		/// Enemies ///
		
		// Spawn new enemies
		if(enemies.size() == 0) {
			spawnEnemies();
		}
		
		// Move enemies down
		for(Enemy e : enemies) {
			if(e.position.y < 100) e.position.y += 1;
		}
		
		
		/// Pickups
		
		// Move pickups down and collide with player
		for(Pickup p : pickups) {
			int speed = 2;
			switch(difficulty) {
				case EASY:   speed = 2; break;
				case MEDIUM: speed = 4; break;
				case HARD:   speed = 6; break;
			}
			p.position.y += speed;
			
			if(p.position.x < player.position.x + player.size.x &&
			   p.position.x + p.size.x > player.position.x &&
			   p.position.y < player.position.y + player.size.y &&
			   p.position.y + p.size.y > player.position.y) {
				p.onPickup(player);
		    	switch(difficulty) {
			    	case EASY:   points += 50; break;
			    	case MEDIUM: points += 100; break;
			    	case HARD:   points += 150; break;
		    	}
				updateStats();
				p.position.y = Game.SCREEN_HEIGHT;
			}
		}
		
		// Remove off-screen pickups
		if(tickNo*tickTime % 5000 == 0) {
			ArrayList<Pickup> newPickups = new ArrayList<Pickup>();
			for(Pickup p : pickups) {
				if(p.position.y < Game.SCREEN_HEIGHT) {
					newPickups.add(p);
				}
			}
			pickups = newPickups;
		}
		
		
		tickNo += 1;
		updateStats();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	BufferedImage bg0, bg1, bg2, bg0b, bg1b, bg2b;
	
	public void draw(Graphics g) {
		try {
			g.drawImage(bg0, 0, (0 + tickNo) % Game.SCREEN_HEIGHT, null);
			g.drawImage(bg1, 0, (0 + tickNo*3) % Game.SCREEN_HEIGHT, null);
			g.drawImage(bg2, 0, (0 + tickNo*5) % Game.SCREEN_HEIGHT, null);
			g.drawImage(bg0, 0, (0 + tickNo % Game.SCREEN_HEIGHT) - Game.SCREEN_HEIGHT, null);
			g.drawImage(bg1, 0, (0 + tickNo*3 % Game.SCREEN_HEIGHT) - Game.SCREEN_HEIGHT, null);
			g.drawImage(bg2, 0, (0 + tickNo*5 % Game.SCREEN_HEIGHT) - Game.SCREEN_HEIGHT, null);
			
			if(running) {
				for(Map.Entry<BulletExtState, Bullet> bullet : bullets.entrySet()) {
					g.drawImage(bullet.getValue().img, bullet.getKey().position.x, bullet.getKey().position.y, null);
				}
				
				for(Enemy e : enemies) {
					g.drawImage(e.img, e.position.x, e.position.y, null);
				}
				
				for(Pickup p : pickups) {
					g.drawImage(p.img, p.position.x, p.position.y, null);
				}
			
				g.drawImage(player.img, player.position.x, player.position.y, null);
			} else {
				
			}
		} catch(java.util.ConcurrentModificationException e) {
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {}
	
	
	public static HashMap<String, Boolean> pressed = new HashMap<String, Boolean>();

	public class MyKeyAdapter extends KeyAdapter {
		MyKeyAdapter() {
			String[] usedKeys = new String[] { "W", "A", "S", "D", "Up", "Left", "Down", "Right", "Shift", "Escape"};
			for(String s : usedKeys) {
				pressed.put(s, false);
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			pressed.put(KeyEvent.getKeyText(e.getKeyCode()), true);
			if(KeyEvent.getKeyText(e.getKeyCode()) == "Escape") {
				running = !running;
				try {
					timer.cancel();
					timer.purge();
					GameFrame.reloadPanels(running);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			pressed.put(KeyEvent.getKeyText(e.getKeyCode()), false);
		}
	}
}
