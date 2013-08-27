package field;

import game.*;
import basicItems.Card;
import dataHandlers.*;
import auxClasses.*;

public class TestingArea {

	public static void main(String[] args) throws OutOfBoundsException, GameOver {
		Effect eff = new Effect();
		Deck deck = new Deck();
		int i=0;
		for(; i < 20; i++){
			deck.moveToBottom(new Card("",i,"","",0,"",eff));
		}
		Player p = new Player(deck);
		Game game = new Game();
		game.takeDamage(p, 5);
		System.out.println(p.clock);
		System.out.println(deck);
		game.takeDamage(p, 5);
		System.out.println(p.clock);
		System.out.println(deck);
		System.out.println(p.level);
		System.out.println(p.waiting);
		for(int j = 0; j < 5; j++){
			game.draw(p);}
		System.out.println(p.hand);
		System.out.println(deck);
		game.topToStock(p);
		System.out.println(p.stock);
		System.out.println(deck);
		System.out.println(p.waiting);
		for(int j = 0; j < 4; j++){
			game.draw(p);}
		System.out.println(deck);
		System.out.println(p.waiting);
		System.out.println(p.clock);
	}}

