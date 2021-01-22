import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToolBar;


public class GamePanel extends JPanel implements ActionListener {
	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 600; 
	
	static boolean running = true;
	
	static Player player = Player.getInstance();
	static HashMap<Point, Bullet> bullets = new HashMap<Point, Bullet>();

	static JLabel statsLabel = new JLabel("HP: 100%");
	static void updateStats() {
		statsLabel.setText("HP: " + player.hp + "%");
	}
	
	GamePanel() throws AWTException {
		statsLabel.setForeground(Color.WHITE);
		statsLabel.setFont(new Font(statsLabel.getFont().getName(), Font.PLAIN, 20));
		this.add(statsLabel);
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		startGame();
	}
	

	Timer timer = new Timer();
	static int tickTime = 20;
	
	public void startGame() throws AWTException {
		player.position = new Point(SCREEN_WIDTH / 2 - player.size.x / 2, (int)(SCREEN_HEIGHT * 0.9));

		Robot robot = new Robot();
		
		timer.schedule(new TimerTask() {

			long lastTime = System.currentTimeMillis();
			@Override
			public void run() {
				if(running) {
					double delta = (System.currentTimeMillis() - lastTime) / 1000.0;
					lastTime = System.currentTimeMillis();
					
					tick(delta);
					repaint();

				    robot.mouseRelease(0);
				}
			}
		}, 0, tickTime);
	}
	
	
	public static int tickNo = 0;
	
	public void tick(double delta) {
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
		player.position.x = Math.min(SCREEN_WIDTH - player.size.x, player.position.x);
		player.position.y = Math.min(SCREEN_HEIGHT - player.size.y, player.position.y);
		
		
		/// Bullets ///
		
		// Spawn player bullet
		if(tickNo*tickTime % 200 == 0) {
			PlayerBullet b = (PlayerBullet)BulletFactory.getBullet(0);
			bullets.put(new Point(player.position.x + player.size.x/2 - b.size.x/2, player.position.y - 5), b);
		}
		
		// Move existing bullets
		for(Map.Entry<Point, Bullet> bullet : bullets.entrySet()) {
			int dx = (int)(bullet.getValue().speed * delta *  Math.sin(bullet.getValue().dir));
			int dy = (int)(bullet.getValue().speed * delta * -Math.cos(bullet.getValue().dir));
			bullet.getKey().translate(dx, dy);
		}
		
		// Remove off-screen bullets every 3 seconds
		if(tickNo*tickTime % 3000 == 0) {
			HashMap<Point, Bullet> newBullets = new HashMap<Point, Bullet>();
			for(Map.Entry<Point, Bullet> bullet : bullets.entrySet()) {
				Point pos = bullet.getKey();
				Point size = bullet.getValue().size;
				if(pos.x + size.x < 0 || pos.y - size.y < 0 || pos.x > SCREEN_WIDTH || pos.y > SCREEN_HEIGHT) {
					continue;
				}
				newBullets.put(pos, bullet.getValue());
			}
			bullets = newBullets;
		}
		
		
		tickNo += 1;
		updateStats();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		for(Map.Entry<Point, Bullet> bullet : bullets.entrySet()) {
			g.drawImage(bullet.getValue().img, bullet.getKey().x, bullet.getKey().y, null);
		}
		
		g.drawImage(player.img, player.position.x, player.position.y, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static HashMap<String, Boolean> pressed = new HashMap<String, Boolean>();

	public class MyKeyAdapter extends KeyAdapter {
		MyKeyAdapter() {
			String[] usedKeys = new String[] { "W", "A", "S", "D", "Up", "Left", "Down", "Right", "Shift"};
			for(String s : usedKeys) {
				pressed.put(s, false);
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			pressed.put(KeyEvent.getKeyText(e.getKeyCode()), true);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			pressed.put(KeyEvent.getKeyText(e.getKeyCode()), false);
		}
	}
}
