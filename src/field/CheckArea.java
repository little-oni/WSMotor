package field;

import basicItems.Card;

public class CheckArea {
	private Card card;
	public CheckArea(){
		card = new Card();
	}
	public void check(Card card){
		this.card = card;
	}
	public String toString(){
		return card.toString();
	}
	public Card getCheck(){
		return card;
	}

}
