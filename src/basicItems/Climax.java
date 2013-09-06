package basicItems;

public class Climax extends Card {

	public Climax() {
		super();
		this.image = "/images/card4347.jpg";
	}

	public Climax(String nombre, int identificador, String title, String color,
			int trigger, String ruta, int nCopias) {
		super(nombre, identificador, title, color, trigger, ruta, nCopias);
	}

	public Climax(String nombre, int identificador, String title, String color,
			int trigger, String ruta) {
		super(nombre, identificador, title, color, trigger, ruta);
	}
}
