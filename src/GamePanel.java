import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener {
	boolean running = true;
	
	public static enum DIFFICULTY { EASY, MEDIUM, HARD }
	public DIFFICULTY difficulty;
	
	Player player = Player.getInstance();
	HashMap<Point, Bullet> bullets = new HashMap<Point, Bullet>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	JLabel statsLabel = new JLabel("HP: 100%");
	void updateStats() {
		if(this.player.hp <= 30) {
			statsLabel.setForeground(Color.RED);
		}
		statsLabel.setText("HP: " + this.player.hp + "%");
	}
	
	GamePanel(GamePanel.DIFFICULTY diff) throws AWTException {
		statsLabel.setForeground(Color.WHITE);
		statsLabel.setFont(new Font(statsLabel.getFont().getName(), Font.PLAIN, 20));
		this.add(statsLabel);
		
		this.setPreferredSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		this.player.hp = this.player.maxHp;
		this.difficulty = diff;
		
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
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				repaint();
			    robot.mouseRelease(0);
			}
		}, 0, tickTime);
	}
	
	
	public static int tickNo = 0;
	
	public void tick(double delta) throws AWTException {
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
			bullets.put(new Point(player.position.x + player.size.x/2 - b.size.x/2, player.position.y - 5), b);
		}
		
		// Spawn enemy bullets
		if(tickNo*tickTime % 1000 == 0) {
			for(Enemy e : enemies) {
				Point bulletSize = BulletFactory.getBullet(BulletFactory.BULLET_TYPES.ENEMY_EASY_BULLET).size;
				Point shootPosition = new Point(e.position.x + e.size.x/2 - bulletSize.x/2, e.position.y + e.size.y/2 - bulletSize.y/2);
				e.shootType.getBullets(shootPosition, bullets);
			}
		}
		
		// Move existing bullets
		for(Map.Entry<Point, Bullet> bullet : bullets.entrySet()) {
			int dx = (int)(bullet.getValue().speed * delta *  Math.sin(bullet.getValue().dir));
			int dy = (int)(bullet.getValue().speed * delta * -Math.cos(bullet.getValue().dir));
			bullet.getKey().translate(dx, dy);
		}
		
		// Remove off-screen bullets every second
		if(tickNo*tickTime % 1000 == 0) {
			HashMap<Point, Bullet> newBullets = new HashMap<Point, Bullet>();
			for(Map.Entry<Point, Bullet> bullet : bullets.entrySet()) {
				Point pos = bullet.getKey();
				Point size = bullet.getValue().size;
				if(pos.x + size.x < 0 || pos.y + size.y < 0 || pos.x > Game.SCREEN_WIDTH || pos.y > Game.SCREEN_HEIGHT) {
					continue;
				}
				newBullets.put(pos, bullet.getValue());
			}
			bullets = newBullets;
		}

		
		// Collision check
		
		// Player bullets and enemies
		ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();
		IteratorPlayerBullets iteratorPlayerBullets = new IteratorPlayerBullets(bullets);
		Iterator<Map.Entry<Point, Bullet>> itPlayer = iteratorPlayerBullets.iterator();
		while(itPlayer.hasNext()) {
			Map.Entry<Point, Bullet> entry = itPlayer.next();
			Point pos = entry.getKey();
			Bullet bullet = entry.getValue();
			
			for(Enemy e : enemies) {
				if (pos.x < e.position.x + e.size.x &&
				   pos.x + bullet.size.x > e.position.x &&
				   pos.y < e.position.y + e.size.y &&
				   pos.y + bullet.size.y > e.position.y) {
				    e.hp -= bullet.damage;
				    if(e.hp <= 0) {
				    	enemiesToRemove.add(e);
				    }
			    	entry.getKey().translate(Game.SCREEN_WIDTH*100, Game.SCREEN_HEIGHT*100);
			    	break;
				}
			}
		}
		for(Enemy e : enemiesToRemove) {
			enemies.remove(e);
		}
		
		// Enemy bullets and player
		IteratorEnemyBullets iteratorEnemyBullets = new IteratorEnemyBullets(bullets, player.position);
		Iterator<Map.Entry<Point, Bullet>> itEnemy = iteratorEnemyBullets.iterator();
		while(itEnemy.hasNext()) {
			Map.Entry<Point, Bullet> entry = itEnemy.next();
			Point pos = entry.getKey();
			Bullet bullet = entry.getValue();
			
			if (pos.x < player.position.x + player.size.x &&
			   pos.x + bullet.size.x > player.position.x &&
			   pos.y < player.position.y + player.size.y &&
			   pos.y + bullet.size.y > player.position.y) {
			    player.hp -= bullet.damage;
			    updateStats();
			    if(player.hp <= 0) {
			    	timer.cancel();
			    	timer.purge();
			    	GameFrame.reloadPanels(false);
			    }
		    	entry.getKey().translate(Game.SCREEN_WIDTH*100, Game.SCREEN_HEIGHT*100);
		    	break;
			}
		}
		
		
		/// Enemies ///
		
		// Spawn new enemy
		if(tickNo*tickTime % 5000 == 0) {
			EnemyFactory ef = null;
			
			switch(difficulty) {
				case EASY:   ef = new EnemyEasyFactory(); break;
				case MEDIUM: ef = new EnemyMediumFactory(); break;
				case HARD:   ef = new EnemyHardFactory(); break;
			}

			Enemy e = ef.createEnemy();
			
			enemies.add(e);
		}
		
		// Move enemies down
		for(Enemy e : enemies) {
			if(e.position.y < 100) e.position.y += 1;
		}
		
		
		tickNo += 1;
		updateStats();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {
			for(Map.Entry<Point, Bullet> bullet : bullets.entrySet()) {
				g.drawImage(bullet.getValue().img, bullet.getKey().x, bullet.getKey().y, null);
			}
			
			for(Enemy e : enemies) {
				g.drawImage(e.img, e.position.x, e.position.y, null);
			}
		
			g.drawImage(player.img, player.position.x, player.position.y, null);
		} else {
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
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
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
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
