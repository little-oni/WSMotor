package field;

import auxClasses.*;
import basicItems.*;

public class Memory {
	private Chain<Card> memory;

	public Memory() {
		memory = new Chain<Card>();
	}

	/*
	 * Devuelve el n�mero de cartas en el Memory.
	 */
	public int cardsInMemory() {
		return memory.getIndex();
	}

	/*
	 * Coloca "card" en la �ltima posici�n.
	 */
	public void sendToMemory(Card card) {
		memory.add(card);
		card.setField(TField.MEMORY);
	}

	/*
	 * Devuelve y elimina la carta con el �ndice encadenado "i".
	 */
	public Card extractFromMemory(int i) {
		Card store = memory.getData(i);
		memory.remove(i);
		return store;
	}

	/*
	 * Devuelve, sin eliminar, la carta con el �ndice encadenado "i".
	 */
	public Card getCard(int i) {
		return memory.getData(i);
	}

	public String toString() {
		String res = "";
		for (int i = 1; i <= memory.getIndex(); i++) {
			
				res = res + "[" + memory.getData(i) + "]";
		
		}
		return res;
	}

	/*
	 * toArray()
	 */
	public Card[] getMemory() {
		Card[] array = new Card[memory.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = memory.getData(i + 1);
		}
		return array;
	}
}
