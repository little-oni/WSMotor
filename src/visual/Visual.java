package visual;

import game.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import auxClasses.OutOfBoundsException;

public class Visual extends JPanel {
	private int scroll;
	private Game game;
	private boolean hand;
	private boolean yn;
	private boolean updateClock;

	// no necesitas nada para la clase
	public Visual(Game game) {
		scroll = 0;
		this.game = game;
		hand = false;
		yn = false;
		updateClock = false;

	}

	public void paint(Graphics g) {
		g.setColor(Color.pink);
		g.fillRect(0, 0, 1024, 600);
		g.setColor(Color.black);
		Image lol = new ImageIcon(this.getClass().getResource(
				"/images/playmat_english.png")).getImage();
		g.drawImage(lol, 0, 0, 1024, 600, null);
		if (hand) {
			g.setColor(Color.white);
			g.fillRect(0, 300, 1024, 300);
			g.setColor(Color.black);
			g.drawRect(0, 300, 1024, 300);
			Image card1 = null;
			Image card2 = null;
			Image card3 = null;
			if (3 * scroll < game.getCurrentPlayer().hand.getHand().length)
				card1 = new ImageIcon(this.getClass().getResource(
						game.getCurrentPlayer().hand.getHand()[0 + 3 * scroll]
								.getImage())).getImage();
			else
				card1 = new ImageIcon(this.getClass().getResource(
						"/images/nullcard.png")).getImage();
			if (1 + 3 * scroll < game.getCurrentPlayer().hand.getHand().length)
				card2 = new ImageIcon(this.getClass().getResource(
						game.getCurrentPlayer().hand.getHand()[1 + 3 * scroll]
								.getImage())).getImage();
			else
				card2 = new ImageIcon(this.getClass().getResource(
						"/images/nullcard.png")).getImage();
			if (2 + 3 * scroll < game.getCurrentPlayer().hand.getHand().length)
				card3 = new ImageIcon(this.getClass().getResource(
						game.getCurrentPlayer().hand.getHand()[2 + 3 * scroll]
								.getImage())).getImage();
			else
				card3 = new ImageIcon(this.getClass().getResource(
						"/images/nullcard.png")).getImage();
			g.drawImage(card1, 150, 300, 224, 300, null);
			g.drawImage(card2, 150 + 224 + 50, 300, 224, 300, null);
			g.drawImage(card3, 150 + 224 + 50 + 224 + 50, 300, 224, 300, null);
		}
		if (yn) {
			g.setColor(Color.white);
			g.fillRect(412, 250, 200, 100);
			g.setColor(Color.black);
			g.drawRect(412, 250, 100, 100);
			g.drawRect(512, 250, 100, 100);
			g.drawString("Si", 457, 305);
			g.drawString("No", 557, 305);
		}
		if (updateClock) {
			Image[] clock = new Image[7];
			for (int i = 0; i < clock.length; i++) {
				if (game.getCurrentPlayer().clock.getClock()[i] != null){
					clock[i] = new ImageIcon(this.getClass().getResource(
							game.getCurrentPlayer().clock.getClock()[i]
									.getImage())).getImage();}
				else
					clock[i] = new ImageIcon(this.getClass().getResource(
							"/images/nullcard.png")).getImage();
			}
			for (int i = 0; i < clock.length; i++){
				g.drawImage(clock[i], 345+50*i, 504, 100, 146, null);
			}
		}
	}

	public void showHand() {
		hand = !hand;
	}

	public void showYN() {
		yn = !yn;
	}

	public void setScroll(int i) {
		scroll = i;
	}

	public int getScroll() {
		return scroll;
	}
	public void updateClock(){
		updateClock=!updateClock;
	}
}
