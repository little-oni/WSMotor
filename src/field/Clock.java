package field;

import auxClasses.Chain;
import auxClasses.OutOfBoundsException;
import basicItems.Card;

public class Clock {
	private Card[] clockInUse;
	private Chain<Card> buffer;
	private Chain<Card> over7;

	public Clock() {// Constructor gen�rico. Crea deck vac�o.
		clockInUse = new Card[7];
		buffer = new Chain<Card>();
		over7 = new Chain<Card>();
	}

	public int cardsInClock() {// Devuelve el n�mero de cartas en Clock.
		int i = 0;
		boolean stop = false;
		for (; i < clockInUse.length && !stop;) {
			stop = clockInUse[i] == null;
			if (!stop)
				i++;
		}
		return i;
	}

	public int cardsInBuffer() {// Devuelve el n�mero de cartas en el b�fer
		return this.buffer.getIndex();
	}

	public int cardsInOver7() {// Devuelve el n�mero de cartas por encima de las
								// 7 permitidas
		return this.over7.getIndex();
	}

	public void bufferDamage(Card card) {// Pone "card" en el b�fer para
											// evaluarla
		buffer.add(card);
	}

	public void clearBuffer() {// Vac�a el b�fer
		buffer.setHead(null);
	}

	public Card unbuffer() throws OutOfBoundsException {// Devuelve la primera
														// carta el b�fer y la
														// elimina de �ste
		Card card = buffer.getData(1);
		buffer.remove(1);
		return card;
	}

	public void transferBuffer() throws OutOfBoundsException {// Vac�a el b�fer
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

	public void clearClock() {// Vac�a el clock
		clockInUse = new Card[7];
	}

	public Card heal(int i) { // Devuelve la carta en el �ndice vectorial i y la
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
														// el b�fer a clock y
														// vuelve a comprobar el
														// estado
		clearClock();
		for (int i = 1; i <= over7.getIndex(); i++) {
			bufferDamage(over7.getData(i));
		}
		over7.clear();
		transferBuffer();
	}

	public Card cardFromClock(int i) {// Devuelve la carta en la posici�n
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
		res = res + "\n" + "Cartas en b�fer: " + buffer.getIndex() + "\n"
				+ "Cartas over7: " + over7.getIndex();
		return res;
	}

	public Card[] getBuffer() throws OutOfBoundsException {// Devuelve un array
															// con todos los
															// elementos del
															// b�fer
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