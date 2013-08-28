package field;

import basicItems.Card;

/*
 * Highlight. Por aquí pasan las cartas que se muestran, los eventos/asistencias 
 * que se jueguen. También se puede hacer que por aquí pasen los clímax. 
 */
public class CheckArea {
	private Card card;

	public CheckArea() {
		card = new Card();
	}

	public void check(Card card) {
		this.card = card;
	}

	public String toString() {
		return card.toString();
	}

	public Card getCheck() {
		return card;
	}

}
