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
	 * Devuelve el n�mero de cartas en mano.
	 */
	public int cardsInHand() {
		return hand.getIndex();
	}

	/*
	 * Devuelve y elimina la carta con el �ndice encadenado "i".
	 */
	public Card discard(int i){
		Card card = hand.getData(i);
		hand.remove(i);
		return card;
	}

	/*
	 * A�ade "card" a la mano.
	 */
	public void addToHand(Card card) {
		hand.add(card);
		card.setField(TField.HAND);
	}

	/*
	 * Randomiza el orden de la mano.
	 */
	public void shuffle() {
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
	 * Devuelve, sin eliminar, la carta con el �ndice encadenado "i".
	 */
	public Card cardFromHand(int i) {
		return hand.getData(i);
	}

	public String toString() {
		String res = "Cartas en mano: " + cardsInHand() + "\n";
		for (int i = 1; i <= hand.getIndex(); i++) {
			
				res = res + "[" + cardFromHand(i) + "]";			
		}
		return res + "\n";
	}

	/*
	 * toArray().
	 */
	public Card[] getHand(){
		Card[] array = new Card[hand.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = hand.getData(i + 1);
		}
		return array;
	}
}
