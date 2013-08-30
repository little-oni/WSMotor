package basicItems;

import dataHandlers.Effect;

public class Character extends Card {

	public Character() {
		super();
	}

	public Character(String nombre, int identificador, String title,
			String color, int trigger, String texto, Effect efecto, int nCopias) {
		super(nombre, identificador, title, color, trigger, texto, efecto, nCopias);
	}

	public Character(String nombre, int identificador, String title,
			String color, int trigger, String texto, Effect efecto) {
		super(nombre, identificador, title, color, trigger, texto, efecto);
	}

	public void stand() {
		// TODO Auto-generated method stub
		
	}

	public boolean standing() {
		// TODO Auto-generated method stub
		return false;
	}

	public void rest() {
		// TODO Auto-generated method stub
		
	}

	public int getCurrentSoul() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCurrentPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void reverse() {
		// TODO Auto-generated method stub
		
	}

	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean reversed() {
		// TODO Auto-generated method stub
		return false;
	}


}