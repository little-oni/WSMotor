package field;

import basicItems.Card;
import dataHandlers.*;
import auxClasses.*;

public class TestingArea {

	public static void main(String[] args) throws OutOfBoundsException {
		Effect eff = new Effect();
		Deck deck = new Deck();
		int i=0;
		for(; i < 10; i++){
			deck.moveToTop(new Card("",i,"",0,0,"",eff));
		}
		System.out.println(deck.toString());
		deck.shuffleDeck();
		System.out.println(deck.toString());
		System.out.println("["+deck.draw()+"]"+"["+deck.draw()+"]"+"["+deck.draw()+"]"+"["+deck.draw()+"]");
		System.out.println(deck.toString());
		for(; i < 20; i++){
			deck.moveToTop(new Card("",i,"",0,0,"",eff));
		}
		deck.shuffleDeck();
		System.out.println(deck.toString());
		System.out.println(deck.getCard(6));
		System.out.println(deck.toString());
		System.out.println(deck.extractCard(6));
		System.out.println(deck.toString());
}}
