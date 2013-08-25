package field;

import auxClasses.Chain;
import auxClasses.OutOfBoundsException;
import basicItems.Card;

public class Clock {
	private Card[] clockInUse;
	private Chain<Card> buffer;
	private Chain<Card> over7;

	public Clock() {// Constructor genérico. Crea deck vacío.
		clockInUse = new Card[7];
		buffer = new Chain<Card>();
		over7 = new Chain<Card>();
	}

	public int cardsInClock() {// Devuelve el número de cartas en Clock.
		int i = 0;
		boolean stop = false;
		for (; i < clockInUse.length && !stop;) {
			stop = clockInUse[i] == null;
			if (!stop)
				i++;
		}
		return i;
	}

	public int cardsInBuffer() {// Devuelve el número de cartas en el búfer
		return this.buffer.getIndex();
	}

	public int cardsInOver7() {// Devuelve el número de cartas por encima de las
								// 7 permitidas
		return this.over7.getIndex();
	}

	public void bufferDamage(Card card) {// Pone "card" en el búfer para
											// evaluarla
		buffer.add(card);
	}

	public void clearBuffer() {// Vacía el búfer
		buffer.setHead(null);
	}

	public Card unbuffer() throws OutOfBoundsException {// Devuelve la primera
														// carta el búfer y la
														// elimina de éste
		Card card = buffer.getData(1);
		buffer.remove(1);
		return card;
	}

	public void transferBuffer() throws OutOfBoundsException {// Vacía el búfer
																// y transfiere
																// todas las
																// cartas que
																// pueda a
																// clock. Las
																// restantes van
																// a over7
		boolean stop = buffer.getHead() == null;
		for (int i = 0; i < clockInUse.length && !stop; i++) {
			if (clockInUse[i] == null && buffer.getIndex() != 0) {
				clockInUse[i] = this.unbuffer();
				stop = buffer.getHead() == null;
			} else {
			}
		}
		if (buffer.getHead() != null) {
			int fixIndex = buffer.getIndex();
			for (int i = 1; i <= fixIndex; i++) {
				over7.add(this.unbuffer());
			}
		}
	}

	public void clearClock() {// Vacía el clock
		clockInUse = new Card[7];
	}

	public Card heal(int i) { // Devuelve la carta en el índice vectorial i y la
								// elimina del clock
		Card card = clockInUse[i];
		for (; i < clockInUse.length - 1; i++) {
			clockInUse[i] = clockInUse[i + 1];
		}
		clockInUse[6] = null;
		return card;
	}

	public void levelUp() throws OutOfBoundsException {// borra el clock,
														// transfire las cartas
														// que pueda desde over7
														// a bufer, transfiere
														// el búfer a clock y
														// vuelve a comprobar el
														// estado
		clearClock();
		for (int i = 1; i <= over7.getIndex(); i++) {
			bufferDamage(over7.getData(i));
		}
		over7.clear();
		transferBuffer();
	}

	public Card cardFromClock(int i) {// Devuelve la carta en la posición
										// vectorial i
		return clockInUse[i];
	}

	public String toString() {// toString
		String res = "Cartas en Clock: " + cardsInClock() + "\n";
		for (int i = 0; i < clockInUse.length; i++) {
			if (clockInUse[i] == null) {
				res = res + "[" + "null" + "]" + " ";
			} else {
				res = res + "[" + clockInUse[i].toString() + "]" + " ";
			}
		}
		res = res + "\n" + "Cartas en búfer: " + buffer.getIndex() + "\n"
				+ "Cartas over7: " + over7.getIndex();
		return res;
	}

	public Card[] getBuffer() throws OutOfBoundsException {// Devuelve un array
															// con todos los
															// elementos del
															// búfer
		Card[] array = new Card[buffer.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = buffer.getData(i + 1);
		}
		return array;
	}

	public Card[] getClock() {// Devuelve un array con todos los elementos del
								// clock
		return clockInUse;
	}

	public Card[] getOver7() throws OutOfBoundsException {// Devuelve un array
															// con todos los
															// elementos en
															// over7
		Card[] array = new Card[over7.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = over7.getData(i + 1);
		}
		return array;
	}
}