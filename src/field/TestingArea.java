package field;

import basicItems.Card;
import dataHandlers.*;
import auxClasses.*;

public class TestingArea {

	public static void main(String[] args) throws OutOfBoundsException {
		Effect eff = new Effect();
		Clock clock = new Clock();
		int i=0;
		for(; i < 5; i++){
			clock.bufferDamage(new Card("",i,"",0,0,"",eff));
		}
		System.out.println(clock.toString());
		System.out.println(clock.cardsInBuffer());
		clock.transferBuffer();
		System.out.println(clock.toString());
		for(;i<10; i++){
			clock.bufferDamage(new Card("",i,"",0,0,"",eff));
		}
		System.out.println(clock.toString());
		clock.transferBuffer();
		System.out.println(clock.toString());
		clock.levelUp();
		System.out.println(clock.toString());
		for(;i<25; i++){
			clock.bufferDamage(new Card("",i,"",0,0,"",eff));
		}
		clock.transferBuffer();
		System.out.println(clock.toString());
		clock.levelUp();
		System.out.println(clock.toString());
		clock.levelUp();
		System.out.println(clock.toString());
		System.out.println(clock.heal(0));
		System.out.println(clock.toString());
	}

}
