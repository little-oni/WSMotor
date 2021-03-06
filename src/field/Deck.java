package field;

import basicItems.Card;
import auxClasses.*;

import java.util.Collections;
import java.util.Vector;

public class Deck {
	private Chain<Card> deck;

	public Deck() {
		deck = new Chain<Card>();
	}

	/*
	 * Devuelve el n�mero de cartas en Deck.
	 */
	public int cardsInDeck() {
		return deck.getIndex();
	}

	/*
	 * Coloca "card" al fondo del deck.
	 */
	public void moveToBottom(Card card) {
		deck.add(card);
		card.setField(TField.DECK);
	}

	/*
	 * Coloca "card" en la parte superior del deck.
	 */
	public void moveToTop(Card card) {
		deck.insert(card, 1);
		card.setField(TField.DECK);
	}

	/*
	 * Devuelve y elimina la primera carta del deck.
	 */
	public Card draw() {
		Card card = deck.getData(1);
		deck.remove(1);
		return card;
	}

	/*
	 * Devuelve y elimina la carta con el �ndice encadenado "i".
	 */
	public Card extractCard(int i) {
		Card card = deck.getData(i);
		deck.remove(i);
		return card;
	}

	/*
	 * Randomiza el orden del Deck.
	 */
	public void shuffleDeck() {
		Vector<Card> list = new Vector<Card>(1, 1);
		int fix = cardsInDeck();
		for (int i = 0; i < fix; i++) {
			list.add(draw());
		}
		Collections.shuffle(list);
		for (int i = 0; i < list.toArray().length; i++) {
			moveToTop(list.get(i));
		}
	}

	/*
	 * Devuelve, sin eliminar, la carta con el �ndice encadenado "i".
	 */
	public Card getCard(int i){
		return deck.getData(i);
	}

	public String toString() {
		String res = "Cartas en deck: " + cardsInDeck() + "\n";
		for (int i = 1; i <= deck.getIndex(); i++) {
				res = res + "[" + getCard(i) + "]";
			}
		return res + "\n";
	}

	/*
	 * toArray().
	 */
	public Card[] getDeck() {
		Card[] array = new Card[deck.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = deck.getData(i + 1);
		}
		return array;
	}

}
