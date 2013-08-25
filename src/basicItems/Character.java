package basicItems;
public class Character extends Card {
	private int level;
	private int state; // estado de un personaje: stand, rest o reverse (sólo
						// relevante en campo)
	private boolean backup; // bloque de boleanos que designan skills que
							// permiten a un personaje activarse fuera del campo
							// (no está estudiado, habría que ver si renta más
							// con un sistema switch que decida luego qué tipo
							// de personaje es según el efecto
	private boolean shift;
	private int power;
	private int soul;

	public Character(String nombre, int id, String title, int coste,
			String color, int nCopias, int level, String timing, int power,
			int soul, int trigger) {
		super(nombre, id, title, coste, color, nCopias);
		this.level = level;
		this.power = power;
		this.soul = soul;
		this.trigger = trigger;
		switch (timing) {
		case "backup":
			this.backup = true;
			this.shift = false;
		case "shift":
			this.shift = true;
			this.backup = false;
		default:
			this.backup = false;
			this.shift = false;
		}
	}
	public Character(String nombre, int id, String title, int coste,
			String color, int level, String timing, int power, int soul,
			int trigger) {
		super(nombre, id, title, coste, color);
		this.level = level;
		this.power = power;
		this.soul = soul;
		this.trigger = trigger;
		switch (timing) {
		case "backup":
			this.backup = true;
			this.shift = false;
		case "shift":
			this.shift = true;
			this.backup = false;
		default:
			this.backup = false;
			this.shift = false;
		}
	}

	public void play() {
		dataHandlers.EffectHandler.playCard();
	}


}
