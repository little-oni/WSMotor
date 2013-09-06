package field;

import basicItems.Card;
import auxClasses.Chain;
import auxClasses.OutOfBoundsException;
import auxClasses.TField;

public class WaitingRoom {
	private Chain<Card> waitingRoom;

	public WaitingRoom() {
		waitingRoom = new Chain<Card>();
	}

	/*
	 * Devuelve el número de cartas en el WaitingRoom.
	 */
	public int cardsInWaiting() {
		return waitingRoom.getIndex();
	}

	/*
	 * Coloca "card" en la última posición del waiting room.
	 */
	public void trash(Card card) {
		waitingRoom.add(card);
		card.setField(TField.WAITINGROOM);
	}

	/*
	 * Devuelve, y elimina, la carta en la posición encadenada "i".
	 */
	public Card extractCard(int i)  {
		Card store = waitingRoom.getData(i);
		waitingRoom.remove(i);
		return store;
	}

	/*
	 * Devuelve, sin eliminar, la carta en la posición encadenada "i".
	 */
	public Card getCard(int i)  {
		return waitingRoom.getData(i);
	}

	public String toString() {
		String res = "Cartas en el Waiting Room: " + cardsInWaiting() + "\n";
		for (int i = 1; i <= waitingRoom.getIndex(); i++) {

				res = res + "[" + getCard(i) + "]";

		}
		return res + "\n";
	}

	/*
	 * toArray().
	 */
	public Card[] getWaitingRoom()  {
		Card[] array = new Card[waitingRoom.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = waitingRoom.getData(i + 1);
		}
		return array;
	}
}
