package visual;

import field.Deck;
import game.Game;
import visual.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import auxClasses.*;
import basicItems.*;
import basicItems.Character;

public class Main extends JPanel implements KeyListener, MouseListener{
	//Ventana del juego
	public JFrame jp1;
	private int width = 1024;
	private int height = 600;
	private static Point mouse;
	private static int mouseX = 0;
	private	static int mouseY = 0;
	private static boolean update = false;
	private Game game = new Game();
	
	
	public Main () throws OutOfBoundsException, GameOver{
		Deck deck1 = new Deck();
		Deck deck2 = new Deck();
		for (int i = 0; i < 8; i++) {
			deck1.moveToTop(new Climax());
		}
		for (int i = 0; i < 12; i++) {
			deck1.moveToTop(new Event());
		}
		for (int i = 0; i < 30; i++) {
			deck1.moveToTop(new Character());
		}
		for (int i = 0; i < 8; i++) {
			deck2.moveToTop(new Climax());
		}
		for (int i = 0; i < 12; i++) {
			deck2.moveToTop(new Event());
		}
		for (int i = 0; i < 30; i++) {
			deck2.moveToTop(new Character());
		}
		deck1.shuffleDeck();
		deck2.shuffleDeck();
		game = new Game(deck1, deck2);
		
		jp1 = new JFrame();
		//Dimensiones de tu ventana
		jp1.setSize(width, height);
		//Añade los listener de teclado y raton
		jp1.addMouseListener(this);
		jp1.addKeyListener(this);
		jp1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Añade lo visual
		jp1.getContentPane().add(game.getVisual());
		
		//NECESARIO
		jp1.setVisible(true);
		game.draw(game.getP1(), 5);
		game.draw(game.getP2(), 5);
		while (game.active) {
			game.standPhase();
			game.drawPhase();
			game.clockPhase();
			System.out.println(game.getCurrentPlayer().hand);
			System.out.println(game.getCurrentPlayer().clock);
			System.out.println(game.getCurrentPlayer().hand);
			game.mainPhase();
			game.climaxPhase();
			game.atackPhase();
			game.encoreStep();
			game.endPhase();
		}
		
	}
	
	
	
	
	public void mouseClicked(MouseEvent arg0) {
		mouse = arg0.getPoint();
		mouseX = mouse.x;
		mouseY = mouse.y;
		System.out.println("clicked on " + mouseX + ", "+mouseY);
		update = true;
	}

	public void mouseEntered(MouseEvent arg0) {	}

	public void mouseExited(MouseEvent arg0) {	}

	public void mousePressed(MouseEvent arg0) {	}

	public void mouseReleased(MouseEvent arg0) {  }

	public void keyPressed(KeyEvent arg0) {	}

	public void keyReleased(KeyEvent arg0) { }

	public void keyTyped(KeyEvent arg0) {
	}
	
	public static void updated(){
		update = false;
	}
	public static int getMouseX(){
		return mouseX;
	}
	public static int getMouseY(){
		return mouseY;
	}
	public static boolean getUpdate(){
		return update;
	}

	public static void main(String[] ashidaGay) throws OutOfBoundsException, GameOver {
		// Pro Main
		new Main();

	}
}
