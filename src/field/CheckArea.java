package field;

import auxClasses.TField;
import basicItems.Card;

/*
 * Highlight. Por aqu� pasan las cartas que se muestran, los eventos/asistencias 
 * que se jueguen. Tambi�n se puede hacer que por aqu� pasen los cl�max. 
 */
public class CheckArea {
	private Card card;

	public CheckArea() {
		card = new Card();
	}

	public void check(Card card) {
		this.card = card;
		card.setField(TField.CHECK);
	}

	public String toString() {
		return card.toString();
	}
	
	public void clear(){
		this.card = null;
	}

	public Card getCheck() {
		return card;
	}

}
