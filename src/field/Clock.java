package field;

import auxClasses.Chain;
import auxClasses.OutOfBoundsException;
import auxClasses.TField;
import basicItems.Card;

public class Clock {
	private Card[] clockInUse;
	private Chain<Card> buffer;
	private Chain<Card> over7;

	public Clock() {
		clockInUse = new Card[7];
		buffer = new Chain<Card>();
		over7 = new Chain<Card>();
	}

	/*
	 * Devuelve el n�mero de cartas en el clock
	 */
	public int cardsInClock() {
		int i = 0;
		boolean stop = false;
		for (; i < clockInUse.length && !stop;) {
			stop = clockInUse[i] == null;
			if (!stop)
				i++;
		}
		return i;
	}

	/*
	 * DEBUG. Devuelve el n�mero de cartas en el b�fer.
	 */
	public int cardsInBuffer() {
		return this.buffer.getIndex();
	}

	/*
	 * DEBUG. Devuelve el n�mero de cartas en over7.
	 */
	public int cardsInOver7() {
		return this.over7.getIndex();
	}

	/*
	 * Coloca "card" en la �ltima posici�n del b�fer.
	 */
	public void bufferDamage(Card card) {
		buffer.add(card);
	}

	/*
	 * Vac�a el b�fer y elimina todos los elementos.
	 */
	public void clearBuffer() {
		buffer.clear();
	}

	/*
	 * Devuelve la primera carta del b�fer y la elimina.
	 */
	public Card unbuffer() {
		Card card = buffer.getData(1);
		buffer.remove(1);
		return card;
	}

	/*
	 * Vac�a el b�fer y transfiere las cartas al Clock. Si no es posible, las
	 * almacena en over7
	 */
	public void transferBuffer() {
		boolean stop = buffer.getHead() == null;
		for (int i = 0; i < clockInUse.length && !stop; i++) {
			if (clockInUse[i] == null && buffer.getIndex() != 0) {
				clockInUse[i] = this.unbuffer();
				clockInUse[i].setField(TField.CLOCK);
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

	/*
	 * Vac�a el clock. (useless)
	 */
	public void clearClock() {
		clockInUse = new Card[7];
	}

	/*
	 * Devuelve la carta con el �ndice encadenado "i" y la elimina del clock.
	 */
	public Card heal(int i) {
		Card card = clockInUse[i - 1];
		for (; i - 1 < clockInUse.length - 1; i++) {
			clockInUse[i - 1] = clockInUse[i];
		}
		clockInUse[6] = null;
		return card;
	}

	/*
	 * Borra el clock y transfiere las cartas que queden en over7 al b�fer, y
	 * luego las coloca en el clock.
	 */
	public void levelUp() {
		for (int i = 1; i <= over7.getIndex(); i++) {
			bufferDamage(over7.getData(i));
		}
		over7.clear();
		transferBuffer();
	}

	/*
	 * Devuelve la carta en la posici�n encadenada "i", sin borrarla
	 */
	public Card cardFromClock(int i) {
		return clockInUse[i - 1];
	}

	public String toString() {
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

	/*
	 * Esto es un sustituto del "toArray", no he podido imlementar ese m�todo en
	 * "Chain"
	 */
	public Card[] getBuffer() {
		Card[] array = new Card[buffer.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = buffer.getData(i + 1);
		}
		return array;
	}

	public Card[] getClock() {
		return clockInUse;
	}

	public Card[] getOver7() {
		Card[] array = new Card[over7.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = over7.getData(i + 1);
		}
		return array;
	}
}