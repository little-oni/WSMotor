package field;

import auxClasses.*;
import basicItems.*;

public class Memory {
	private Chain<Card> memory;

	public Memory() {
		memory = new Chain<Card>();
	}

	/*
	 * Devuelve el número de cartas en el Memory.
	 */
	public int cardsInMemory() {
		return memory.getIndex();
	}

	/*
	 * Coloca "card" en la última posición.
	 */
	public void sendToMemory(Card card) {
		memory.add(card);
	}

	/*
	 * Devuelve y elimina la carta con el índice encadenado "i".
	 */
	public Card extractFromMemory(int i) throws OutOfBoundsException {
		Card store = memory.getData(i);
		memory.remove(i);
		return store;
	}

	/*
	 * Devuelve, sin eliminar, la carta con el índice encadenado "i".
	 */
	public Card getCard(int i) throws OutOfBoundsException {
		return memory.getData(i);
	}

	public String toString() {
		String res = "";
		for (int i = 1; i <= memory.getIndex(); i++) {
			try {
				res = res + "[" + memory.getData(i) + "]";
			} catch (OutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	/*
	 * toArray()
	 */
	public Card[] getMemory() throws OutOfBoundsException {
		Card[] array = new Card[memory.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = memory.getData(i + 1);
		}
		return array;
	}
}
