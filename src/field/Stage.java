package field;

import auxClasses.TField;
import basicItems.Character;

public class Stage {
	private Character[] stage;

	public Stage() {
		stage = new Character[5];
	}

	/*
	 * Devuelve el número de personajes en el Stage.
	 */
	public int charactersOnStage() {
		int num = 0;
		for (int i = 0; i < stage.length; i++) {
			if (stage[i] != null) {
				num++;
			}
		}
		return num;
	}

	/*
	 * Coloca "chara" en la posición encadenada "i".
	 */
	public void entry(Character chara, int i) {
		stage[i - 1] = chara;
		switch (i) {
		case 1:
			chara.setField(TField.STAGELF);
		case 2:
			chara.setField(TField.STAGECF);
		case 3:
			chara.setField(TField.STAGERF);
		case 4:
			chara.setField(TField.STAGELB);
		case 5:
			chara.setField(TField.STAGERB);
		}

	}

	/*
	 * Devuelve, sin eliminar, el personaje en la posición encadenada "i".
	 */
	public Character getChara(int i) {
		return stage[i];
	}

	/*
	 * Devuelve y elimina el personaje en la posición encadenada "i".
	 */
	public Character retire(int i) {
		Character store = stage[i - 1];
		stage[i - 1] = null;
		return store;
	}

	/*
	 * Devuelve un array con los peronajes en el Front Row.
	 */
	public Character[] frontRow() {
		Character[] store = new Character[3];
		for (int i = 0; i < 3; i++) {
			store[i] = stage[i];
		}
		return store;
	}

	/*
	 * Devuelve un array con los personajes en el BackRow.
	 */
	public Character[] backRow() {
		Character[] store = new Character[2];
		for (int i = 0; i < 2; i++) {
			store[i] = stage[i + 3];
		}
		return store;
	}

	/*
	 * Intercambia el contenido de las posiciones encadenadas "i" y "j".
	 */
	public void move(int i, int j) {
		Character store = stage[i - 1];
		stage[i - 1] = stage[j - 1];
		stage[j - 1] = store;
		switch (i) {
		case 1:
			stage[i - 1].setField(TField.STAGELF);
		case 2:
			stage[i - 1].setField(TField.STAGECF);
		case 3:
			stage[i - 1].setField(TField.STAGERF);
		case 4:
			stage[i - 1].setField(TField.STAGELB);
		case 5:
			stage[i - 1].setField(TField.STAGERB);
		}
		switch (j) {
		case 1:
			stage[j - 1].setField(TField.STAGELF);
		case 2:
			stage[j - 1].setField(TField.STAGECF);
		case 3:
			stage[j - 1].setField(TField.STAGERF);
		case 4:
			stage[j - 1].setField(TField.STAGELB);
		case 5:
			stage[j - 1].setField(TField.STAGERB);
		}
	}

	public String toString() {
		String res = "";
		for (int i = 0; i < 3; i++) {
			res = res + "[" + stage[i] + "]";
		}
		res = res + "\n" + " ";
		for (int i = 3; i < 5; i++) {
			res = res + "[" + stage[i] + "]";
		}
		return res;
	}

	/*
	 * toArray().
	 */
	public Character[] getStage() {
		return stage;
	}
}
