package field;

import game.*;
import basicItems.Card;
import dataHandlers.*;
import auxClasses.*;

public class TestingArea {

	public static void main(String[] args) throws OutOfBoundsException,
			GameOver {
		Deck deck1 = new Deck();
		Deck deck2 = new Deck();
		for (int i = 0; i < 20; i++) {
			deck1.moveToTop(new Card("nombre", 20 + i, "title", "default", 0,
					"texto"));
		}
		for (int i = 0; i < 20; i++) {
			deck2.moveToTop(new Card("nombre", 20 + i, "title", "default", 0,
					"texto"));
		}
		deck1.shuffleDeck();
		deck2.shuffleDeck();
		Game game = new Game(deck1, deck2);
		game.draw(game.getP1(), 5);
		game.draw(game.getP2(), 5);
		while (game.active) {
			game.standPhase();
			game.drawPhase();
			game.clockPhase();
			System.out.println(game.getP1().hand);
			System.out.println(game.getP1().clock);
			game.mainPhase();
			game.climaxPhase();
			game.atackPhase();
			game.encoreStep();
			game.endPhase();
		}
	}
}
