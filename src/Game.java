import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;


public class Game extends Canvas implements Runnable,KeyListener{
	
	public Node[] nodeSnake = new Node[30];
	
	public boolean left,right,up,down;
	
	public int score = 0;
	
	public int appleX = 0, appleY = 0;
	
	public int speed = 4;

	public Game() {
		this.setPreferredSize(new Dimension(800,800));
		for(int i = 0; i < nodeSnake.length; i++) {
			nodeSnake[i] = new Node(0,0);
		}
		this.addKeyListener(this);
	}
	
	public void tick() {
		
		for(int i = nodeSnake.length - 1; i > 0; i--) {
			nodeSnake[i].x = nodeSnake[i-1].x;
			nodeSnake[i].y = nodeSnake[i-1].y;
		}
		
		if(nodeSnake[0].x + 10 < 0) {
			nodeSnake[0].x = 800;
		} else if(nodeSnake[0].x >= 800){
			nodeSnake[0].x = -10;
		}
		
		if(nodeSnake[0].y + 10 < 0) {
			nodeSnake[0].y = 800;
		} else if(nodeSnake[0].y >= 800){
			nodeSnake[0].y = -10;
		}
		
		if(right) {
			nodeSnake[0].x+=speed;
		} else if (up) {
			nodeSnake[0].y-=speed;
		} else if(down) {
			nodeSnake[0].y+=speed;
		} else if(left) {
			nodeSnake[0].x-=speed;
		}
		
		if(new Rectangle(nodeSnake[0].x, nodeSnake[0].y,10,10).intersects(new Rectangle(appleX, appleY,10,10))) {
			appleX = new Random().nextInt(800 - 10);
			appleY = new Random().nextInt(800 - 10);
			score++;
			speed++;
			System.out.println("Pontos: " + score);
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		requestFocus();
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0,800,800);
		for(int i = 0; i < nodeSnake.length; i++) {
			g.setColor(Color.blue);
			g.fillRect(nodeSnake[i].x, nodeSnake[i].y,10,10);
		}
		
		g.setColor(Color.red);
		g.fillRect(appleX, appleY, 10, 10);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		JFrame frame = new JFrame("Snake");
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			up = false;
			down = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
			up = false;
			down = false;
			right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
			right = false;
			left = false;
			down = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
			right = false;
			left = false;
			up = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
 		
	}
}
