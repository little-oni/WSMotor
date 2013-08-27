package field;

import basicItems.*;

public class Level {
	private Card[] level;

	public Level() { // Constructor gen�rico. Crea un level vac�o
		level = new Card[4];
	}

	public int cardsInLevel() { // Devuelve el n�mero de cartas en el level
		int i = 0;
		boolean stop = level[0]==null;
		for (; i < level.length && !stop;) {
			if (level[i] == null) {
				stop = true;
				
			}
			else
				i++;
		}
		return i;
	}

	public Card swap(Card card, int i) { // Devuelve la carta en i y coloca la
											// carta "card" en la posici�n en la
											// que �sta estaba
		Card store = level[i-1];
		level[i-1] = card;
		return store;
	}

	public Card cardFromLevel(int i) {// Devuelve la carta en la posici�n i;
		return level[i-1];
	}

	public void levelUp(Card card) {// A�ade la carta "card" al level en la
									// �ltima posici�n
		boolean stop = false;
		for (int i = 0; i < level.length && !stop; i++) {
			if (level[i] == null) {
				level[i] = card;
				stop = true;
			}
		}
	}

	public String toString() {// toString
		String res = "Nivel: " + cardsInLevel();
		for (int i = 0; i < level.length; i++) {
			res = res + "[" + level[i] + "]";
		}
		return res;
	}

	public Card[] getLevel() {// Devuelve un array con todos los elementos del
								// level.
		return level;
	}

}
