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


}