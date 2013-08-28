package field;

import java.util.Collections;
import java.util.Vector;

import auxClasses.*;
import basicItems.*;

public class Hand {
	private Chain<Card> hand;

	public Hand() {
		hand = new Chain<Card>();
	}

	/*
	 * Devuelve el número de cartas en mano.
	 */
	public int cardsInHand() {
		return hand.getIndex();
	}

	/*
	 * Devuelve y elimina la carta con el índice encadenado "i".
	 */
	public Card discard(int i) throws OutOfBoundsException {
		Card card = hand.getData(i);
		hand.remove(i);
		return card;
	}

	/*
	 * Añade "card" a la mano.
	 */
	public void addToHand(Card card) {
		hand.add(card);
	}

	/*
	 * Randomiza el orden de la mano.
	 */
	public void shuffle() throws OutOfBoundsException {
		Vector<Card> list = new Vector<Card>(1, 1);
		int fix = cardsInHand();
		for (int i = 1; i <= fix; i++) {
			list.add(discard(1));
		}
		Collections.shuffle(list);
		for (int i = 0; i < list.toArray().length; i++) {
			addToHand(list.get(i));
		}
	}

	/*
	 * Devuelve, sin eliminar, la carta con el índice encadenado "i".
	 */
	public Card cardFromHand(int i) throws OutOfBoundsException {
		return hand.getData(i);
	}

	public String toString() {
		String res = "Cartas en mano: " + cardsInHand() + "\n";
		for (int i = 1; i <= hand.getIndex(); i++) {
			try {
				res = res + "[" + cardFromHand(i) + "]";
			} catch (OutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return res + "\n";
	}

	/*
	 * toArray().
	 */
	public Card[] getHand() throws OutOfBoundsException {
		Card[] array = new Card[hand.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = hand.getData(i + 1);
		}
		return array;
	}
}
