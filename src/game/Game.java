package game;

import field.*;
import auxClasses.*;
import basicItems.*;

import java.util.Scanner;

public class Game {
	private TPhases currentPhase;
	private boolean currentPlayer;
	private CheckArea check;
	private boolean active;

	public Game() {
		currentPhase = TPhases.STANDPH;
		currentPlayer = true;
		check = new CheckArea();
		active = true;
	}

	public void draw(Player p) throws OutOfBoundsException, GameOver {
		p.hand.addToHand(p.deck.draw());
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}
	}

	public void topToStock(Player p) throws OutOfBoundsException, GameOver {
		check.check(p.deck.draw());
		p.stock.moveToStock(check.getCheck());
		check.check(null);
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}
	}

	public void takeDamage(Player p, int i) throws OutOfBoundsException,
			GameOver {
		boolean cancel = false;
		int reset = 0;
		for (int j = 0; j < i && !cancel;) {
			if (p.deck.cardsInDeck() > 0) {
				check.check(p.deck.draw());
				if (check.getCheck() instanceof Climax) {
					int fix = p.clock.cardsInBuffer();
					for (int n = 0; n < fix; n++) {
						p.waiting.trash(p.clock.unbuffer());
					}
					p.waiting.trash(check.getCheck());
					check.check(null);
					cancel = true;
				} else {
					p.clock.bufferDamage(check.getCheck());
					check.check(null);
					j++;
				}
			} else {
				deckReset(p, false);
				reset++;
			}

		}
		if (!cancel) {
			while (p.clock.cardsInBuffer() != 0) {

				p.clock.transferBuffer();

				if (p.clock.cardsInClock() == 7) {
					levelUp(p);
				}
			}
			p.clock.transferBuffer();
		}
		for (int n = 0; n < reset; n++) {
			topToClock(p);
		}
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}

	}

	public void levelUp(Player p) throws OutOfBoundsException {
		Scanner input = new Scanner(System.in);
		System.out
				.println(p.clock + "\n Selecciona la carta a poner en Level:");
		p.level.levelUp(p.clock.heal(input.nextInt()));
		for (int i = 0; i < 6; i++) {
			p.waiting.trash(p.clock.heal(0));
		}
		p.clock.levelUp();
		active = p.level.cardsInLevel() == 4;
	}

	public int search(Card[] array, Card card) {
		int i = 0;
		boolean stop = false;
		for (; i < array.length;) {
			if (card.equals(array[i])) {
				stop = true;
			} else
				i++;
		}
		if (stop)
			return i + 1;
		else
			return 0;
	}

	public void deckReset(Player p, boolean clock) throws OutOfBoundsException,
			GameOver {
		if (p.waiting.cardsInWaiting() != 0) {
			int fix = p.waiting.cardsInWaiting();
			for (int i = 0; i < fix; i++) {
				p.deck.moveToBottom(p.waiting.extractCard(1));
			}
			p.deck.shuffleDeck();
			if (clock)
				topToClock(p);
		} else {
			active = false;
			throw new GameOver();
		}
	}

	public void topToClock(Player p) throws OutOfBoundsException {
		p.clock.bufferDamage(p.deck.draw());
		p.clock.transferBuffer();
	}

}
