package field;

import basicItems.Card;
import auxClasses.*;

import java.util.Collections;
import java.util.Vector;

public class Deck {
	private Chain<Card> deck;

	public Deck() {// Constructor genérico, devuelve un deck vacío
		deck = new Chain<Card>();
	}

	public int cardsInDeck() {// Devuelve el número de cartas en deck
		return deck.getIndex();
	}

	public void moveToBottom(Card card) {// Coloca "card" como última carta del
											// deck
		deck.add(card);
	}

	public void moveToTop(Card card) throws OutOfBoundsException {// Coloca
																	// "card"
																	// como
																	// primera
																	// carta del
																	// deck
		deck.insert(card, 1);
	}

	public Card draw() throws OutOfBoundsException {// devuelve y elimina la
													// primera carta del deck
		Card card = deck.getData(1);
		deck.remove(1);
		return card;
	}

	public Card extractCard(int i) throws OutOfBoundsException {// devuelve y
																// elimina la
																// carta con el
																// índice
																// encadenado i
		Card card = deck.getData(i);
		deck.remove(i);
		return card;
	}

	public void shuffleDeck() throws OutOfBoundsException {// randomiza el orden
															// del deck
		Vector<Card> list = new Vector<Card>(1, 1);
		int fix = cardsInDeck();
		for (int i = 1; i <= fix; i++) {
			list.add(draw());
		}
		Collections.shuffle(list);
		for (int i = 0; i < list.toArray().length; i++) {
			moveToTop(list.get(i));
		}
	}

	public Card getCard(int i) throws OutOfBoundsException {// devuelve, sin
															// eliminar la carta
															// con el índice
															// encadenado i
		return deck.getData(i);
	}

	public String toString() {// toString
		String res = "Cartas en deck: " + cardsInDeck() + "\n";
		for (int i = 1; i <= deck.getIndex(); i++) {
			try {
				res = res + "[" + getCard(i) + "]";
			} catch (OutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return res + "\n";
	}

	public Card[] getDeck() throws OutOfBoundsException {// devuelve el array
															// con todos los
															// elementos del
															// deck
		Card[] array = new Card[deck.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = deck.getData(i + 1);
		}
		return array;
	}

}
