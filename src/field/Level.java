package field;

import basicItems.*;

public class Level {
	private Card[] level;

	public Level() {
		level = new Card[4];
	}

	/*
	 * Devuelve el n�mero de cartas en el Level.
	 */
	public int cardsInLevel() {
		int i = 0;
		boolean stop = level[0] == null;
		for (; i < level.length && !stop;) {
			if (level[i] == null) {
				stop = true;

			} else
				i++;
		}
		return i;
	}

	/*
	 * Devuelve la carta con el �ndice encadenado "i" y la cambia por "card".
	 */
	public Card swap(Card card, int i) { // Devuelve la carta en i y coloca la
											// carta "card" en la posici�n en la
											// que �sta estaba
		Card store = level[i - 1];
		level[i - 1] = card;
		return store;
	}

	/*
	 * Devuelve, sin eliminar, la carta con el �ndice encadenado "i".
	 */
	public Card cardFromLevel(int i) {
		return level[i - 1];
	}

	/*
	 * A�ade "card" al Level en la �ltima posici�n.
	 */
	public void levelUp(Card card) {
		boolean stop = false;
		for (int i = 0; i < level.length && !stop; i++) {
			if (level[i] == null) {
				level[i] = card;
				stop = true;
			}
		}
	}

	public String toString() {
		String res = "Nivel: " + cardsInLevel();
		for (int i = 0; i < level.length; i++) {
			res = res + "[" + level[i] + "]";
		}
		return res;
	}

	/*
	 * toArray().
	 */
	public Card[] getLevel() {
		return level;
	}

}
