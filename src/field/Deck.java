package field;

import basicItems.Card;
import auxClasses.*;

import java.util.Collections;
import java.util.Vector;

public class Deck {
	private Chain<Card> deck;
	
	public Deck(){
		deck= new Chain<Card>();
	}
	public int cardsInDeck(){
		return deck.getIndex();
	}
	public void moveToBottom(Card card){
		deck.add(card);
	}
	public void moveToTop(Card card) throws OutOfBoundsException{
		deck.insert(card, 1);
	}
	public Card draw() throws OutOfBoundsException{
		Card card = deck.getData(1);
		deck.remove(1);
		return card;
	}
	public Card getCard(int i) throws OutOfBoundsException{
		Card card = deck.getData(i);
		deck.remove(i);
		return card;
	}
	public void shuffleDeck() throws OutOfBoundsException{
		Vector<Card> list = new Vector<Card>(1, 1);
		int fix = cardsInDeck();
		for(int i = 1; i<=fix; i++){
			list.add(getCard(i));}
		Collections.shuffle(list);
		for(int i = 0; i<list.toArray().length; i++){
			moveToTop(list.get(i));
		}
	}
	
}
