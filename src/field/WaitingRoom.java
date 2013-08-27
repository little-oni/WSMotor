package field;

import basicItems.Card;
import auxClasses.Chain;
import auxClasses.OutOfBoundsException;

public class WaitingRoom {
	private Chain<Card> waitingRoom;
	public WaitingRoom(){
		waitingRoom = new Chain<Card>();
	}
	public int cardsInWaiting(){
		return waitingRoom.getIndex();
	}
	public void trash(Card card){
		waitingRoom.add(card);
	}
	public Card extractCard(int i) throws OutOfBoundsException{
		Card store = waitingRoom.getData(i);
		waitingRoom.remove(i);
		return store;
	}
	public Card getCard(int i) throws OutOfBoundsException{
		return waitingRoom.getData(i);
	}
	public String toString(){
		String res = "Cartas en el Waiting Room: " +cardsInWaiting()+"\n";
		for (int i = 1; i <= waitingRoom.getIndex(); i++) {
			try {
				res = res + "[" + getCard(i) + "]";
			} catch (OutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return res + "\n";
	}
	public Card[] getWaitingRoom() throws OutOfBoundsException{
		Card[] array = new Card[waitingRoom.getIndex()];
		for (int i = 0; i < array.length; i++) {
			array[i] = waitingRoom.getData(i + 1);
		}
		return array;
	}
}
