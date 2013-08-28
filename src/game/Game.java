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

	/*
	 * El jugador "p" roba "i" cartas. Si el deck se acaba en algún momento, se
	 * ejecuta deckReset() y se hacen, al final, tantos daños como reseteos se
	 * hayan hecho.
	 */
	public void draw(Player p, int i) throws OutOfBoundsException, GameOver {
		int reset = 0;
		for (int j = 0; j < i;) {
			if (p.deck.cardsInDeck() != 0) {
				p.hand.addToHand(p.deck.draw());
				j++;
			} else {
				deckReset(p, false);
				reset++;
			}
		}
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}
		for (int j = 0; j < reset; j++) {
			topToClock(p);
		}

	}

	/*
	 * Las primeras "i" cartas del deck de "p" se ponen en su stock. Si el deck
	 * se acaba en algún momento, se ejecuta deckReset() y se hacen tantos daños
	 * como reseteos se hayan hecho.
	 */
	public void topToStock(Player p, int i) throws OutOfBoundsException,
			GameOver {
		int reset = 0;
		for (int j = 0; j < i;) {
			if (p.deck.cardsInDeck() != 0) {
				check.check(p.deck.draw());
				p.stock.moveToStock(check.getCheck());
				check.check(null);
				j++;
			} else {
				deckReset(p, false);
			}
		}
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}
		for (int j = 0; j < reset; j++) {
			topToClock(p);
		}
	}

	/*
	 * El jugador "p" recibe "i" daños desde el deck. Si en algún momento sale
	 * Clímax, se interrumpe la acción y se tiran todas lasa cartas sacadas al
	 * waiting. Si el deck se acaba en algún momento, se ejecuta deckReset() y
	 * se hacen tantos daños como reseteos se hayan hecho al final. Si el clock
	 * llega a 7 cartas en algún momento, se ejecuta levelUp().
	 */
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

	/*
	 * Selecciona una carta del clock y la pone en el level. Ejecuta
	 * clock.levelUp() para limpiar el clock y over7.
	 */
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
		if (p.clock.cardsInClock() == 7) {
			levelUp(p);
		}
	}

	/*
	 * Bucsa en "array" la carta "card" si la encuentra, devuelve su índice
	 * encadenado. En caso contrario devuelve 0.
	 */
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

	/*
	 * Si hay cartas en el waiting, resetea el deck. Si se ha pasado el
	 * parámetro "true", coloca la primera carta del deck en el clock. En caso
	 * de que no haya cartas en el waiting, pierde la partida.
	 */
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

	/*
	 * Coloca la primera carta del deck en el clock sin comprobar si es un
	 * Clímax. Si hay 7 cartas en el clock, ejecuta levelUp(). Si en algún
	 * momento el deck se acaba, ejecuta deckReset(true).
	 */
	public void topToClock(Player p) throws OutOfBoundsException, GameOver {
		p.clock.bufferDamage(p.deck.draw());
		p.clock.transferBuffer();
		if (p.clock.cardsInClock() == 7) {
			levelUp(p);
		}
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}
	}

	/*
	 * Coloca las primeras "i" cartas del stock en el waiting. ATENCION: no
	 * comprueba si hay "i" cartas.
	 */
	public void payStock(Player p, int i) throws OutOfBoundsException {
		for (int j = 0; j < i; j++) {
			p.waiting.trash(p.stock.payFirst());
		}
	}

	public void standPhase(Player p) {
		currentPhase = TPhases.STANDPH;
		for (int i = 0; i < p.stage.getStage().length; i++)
			p.stage.getStage()[i].stand();
		checkEffects();
	}

	public void clockPhase(Player p) {

	}

	public void checkEffects() {
		// TODO Auto-generated method stub

	}
}
