package field;

import basicItems.*;

public class Level {
	private Card[] level;

	public Level() { // Constructor genérico. Crea un level vacío
		level = new Card[4];
	}

	public int cardsInLevel() { // Devuelve el número de cartas en el level
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
											// carta "card" en la posición en la
											// que ésta estaba
		Card store = level[i-1];
		level[i-1] = card;
		return store;
	}

	public Card cardFromLevel(int i) {// Devuelve la carta en la posición i;
		return level[i-1];
	}

	public void levelUp(Card card) {// Añade la carta "card" al level en la
									// última posición
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
