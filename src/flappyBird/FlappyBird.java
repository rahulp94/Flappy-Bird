package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener,MouseListener, KeyListener {

	public static FlappyBird flappyBird;

	public static int WIDTH = 800, HEIGHT = 800;

	public Rendering rendering;
	public Rectangle bird;
	public ArrayList<Rectangle> columns;
	public Random rand;
	public int ticks, yMotion,score;
	public boolean gameOver,gameStart;

	public FlappyBird(){
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20,this);

		rendering = new Rendering();
		rand = new Random();

		jframe.add(rendering);
		jframe.setTitle("Flappy Bird Custom");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);

		columns = new ArrayList<>();
		bird = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);

		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);

		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ticks++;
		int speed = 10;

		if(gameStart){
		for(int i = 0; i <columns.size();i++){
			Rectangle rect = columns.get(i);
			rect.x -= speed;
		}

		for (int i = 0; i < columns.size(); i++){
			Rectangle col = columns.get(i);

			if (col.x + col.width < 0){
				columns.remove(col);

				if (col.y == 0){
					addColumn(false);
				}
			}
		}

		if(ticks%2 == 0 && yMotion<15){
			yMotion+=2;
		}
		bird.y += yMotion;

		for(Rectangle col : columns){
			if (col.y == 0 && bird.x + bird.width / 2 > col.x + col.width / 2 - 10
					&& bird.x + bird.width / 2 < col.x + col.width / 2 + 10){
				score++;
			}
			if(col.intersects(bird)){
				gameOver = true;
				bird.x = col.x - bird.width;
			}
		}

		if(bird.y > HEIGHT - 120 || bird.y < 0){
			gameOver = true;
		}

		if(bird.y + yMotion >= HEIGHT-120){
			bird.y = HEIGHT - 120 - bird.height;
		}
	}
		rendering.repaint();
	}

	public void jump(){
		if(!gameStart){
			gameStart = true;
		}
		else if(!gameOver){
			if(yMotion >0){
				yMotion = 0;
			}

			yMotion -= 10;
		}

		if(gameOver){
			bird = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
			columns.clear();
			yMotion = 0;
			score = 0;

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);

			gameOver = false;

		}
	}

	public void addColumn(boolean start)
	{
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);

		if (start)
		{
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		else
		{
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}

	public void paintColumn(Graphics g, Rectangle col){
		g.setColor(Color.green.darker());
		g.fillRect(col.x, col.y, col.width, col.height);
	}

	public void repaint(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT-120, WIDTH, 120);

		g.setColor(Color.yellow);
		g.fillRect(0, HEIGHT-120, WIDTH, 20);

		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);

		for (Rectangle column : columns)
		{
			paintColumn(g, column);
		}

		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));

		if(!gameStart){
			g.drawString("Start Game",100,HEIGHT/2 - 50);
		}

		if(gameOver){
			g.drawString("Game Over",100,HEIGHT/2 - 50);
		}

		if (!gameOver && gameStart){
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}

	public static void main(String[] args){
		flappyBird = new FlappyBird();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		jump();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_SPACE){
			jump();
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
