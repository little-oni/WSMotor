package field;

import basicItems.Card;
import auxClasses.Chain;
import auxClasses.OutOfBoundsException;
import auxClasses.TField;

public class Stock {
	Chain<Card> stock;

	public Stock() {
		stock = new Chain<Card>();
	}

	/*
	 * Devuelve el número de cartas en el Stock.
	 */
	public int cardsInStock() {
		return stock.getIndex();
	}

	/*
	 * Coloca la carta "card" como la última del Stock.
	 */
	public void moveToStock(Card card) {
		stock.add(card);
		card.setField(TField.STOCK);
	}

	/*
	 * Devuelve la primera carta del Stock y la elimina.
	 */
	public Card payFirst() {
		Card store = stock.getData(1);
		stock.remove(1);
		return store;
	}

	/*
	 * Devuelve la carta en la posición encadenada "i" y la elimina.
	 */
	public Card payAny(int i) {
		Card store = stock.getData(i);
		stock.remove(i);
		return store;
	}

	/*
	 * Devuelve, sin eliminar, la carta en la posición encadenada "i".
	 */
	public Card getCard(int i) {
		return stock.getData(i);
	}

	public String toString() {
		String res = "Cartas en Stock: " + cardsInStock() + "\n";
		for (int i = 1; i <= stock.getIndex(); i++) {
				res = res + "[" + getCard(i) + "]";
		}
		return res + "\n";
	}

	/*
	 * toArray().
	 */
	public Card[] getStock() {
		Card[] array = new Card[stock.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = stock.getData(i + 1);
		}
		return array;
	}
}
