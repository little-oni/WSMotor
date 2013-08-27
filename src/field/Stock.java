package field;

import basicItems.Card;
import auxClasses.Chain;
import auxClasses.OutOfBoundsException;

public class Stock {
	Chain<Card> stock;
	public Stock(){
		stock = new Chain<Card>();
	}
	public int cardsInStock(){
		return stock.getIndex();
	}
	public void moveToStock(Card card){
		stock.add(card);
	}
	public Card payFirst() throws OutOfBoundsException{
		Card store = stock.getData(1);
		stock.remove(1);
		return store;
	}
	public Card payAny(int i) throws OutOfBoundsException{
		Card store = stock.getData(i);
		stock.remove(i);
		return store;
	}
	public Card getCard(int i) throws OutOfBoundsException{
		return stock.getData(i);
	}
	public String toString(){
		String res = "Cartas en Stock: " +cardsInStock()+"\n";
		for (int i = 1; i <= stock.getIndex(); i++) {
			try {
				res = res + "[" + getCard(i) + "]";
			} catch (OutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return res + "\n";
	}
	public Card[] getStock() throws OutOfBoundsException{
		Card[] array = new Card[stock.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = stock.getData(i + 1);
		}
		return array;
	}
}
