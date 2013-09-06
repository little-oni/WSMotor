package basicItems;

public class Event extends Card {
	private int level;
	private int cost;
	public Event() {
		super();
		this.image = "/images/card2320.jpg";
		level = 2;
		cost = 0;
	}
	public Event(String nombre, int identificador, String title, String color,
			int trigger, String ruta, int nCopias) {
		super(nombre, identificador, title, color, trigger, ruta, nCopias);
		this.level = level;
		this.cost = cost;
	}
	public Event(String nombre, int identificador, String title, String color,
			int trigger, String ruta) {
		super(nombre, identificador, title, color, trigger, ruta);
		this.level = level;
		this.cost = cost;
	}
	

}
