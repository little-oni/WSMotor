package basicItems;

import auxClasses.*;

public class Card {
	protected String image;
	private String name;
	private int copies;
	private int idcard;
	private int idthiscard;
	private String title;
	private TColors color;
	protected int trigger;
	private TField field;
	private static int cards = 0;

	public Card() {
		this.name = "nombre";
		this.copies = 4;
		this.idcard = 000000;
		this.idthiscard = cards;
		this.title = "title";
		this.color = TColors.DEFAULT;
		this.trigger = 0;
		this.field = TField.DECK;
		this.image = "/card2171.jpg";
		cards++;
	}

	public Card(String nombre, int identificador, String title, String color,
			int trigger, String ruta) {
		this.name = nombre;
		this.copies = 4;
		this.idcard = identificador;
		this.idthiscard = cards;
		this.title = title;
		switch (color) {
		case "amarillo":
			this.color = TColors.AMARILLO;
		case "verde":
			this.color = TColors.VERDE;
		case "rojo":
			this.color = TColors.ROJO;
		case "azul":
			this.color = TColors.AZUL;
		default:
			this.color = TColors.DEFAULT;
		}

		this.trigger = trigger;
		this.field = TField.DECK;
		this.image = ruta;
		cards++;
	}

	public Card(String nombre, int identificador, String title, String color,
			int trigger, String ruta, int nCopias) {
		this.name = nombre;
		this.copies = nCopias;
		this.idcard = identificador;
		this.idthiscard = cards;
		this.title = title;
		switch (color) {
		case "amarillo":
			this.color = TColors.AMARILLO;
		case "verde":
			this.color = TColors.VERDE;
		case "rojo":
			this.color = TColors.ROJO;
		case "azul":
			this.color = TColors.AZUL;
		default:
			this.color = TColors.DEFAULT;
		}
		this.trigger = trigger;
		this.field = TField.DECK;
		this.image = ruta;
		cards++;
	}



	public String getName() {
		return name;
	}

	public int getnCopies() {
		return copies;
	}

	public int getID() {
		return idcard;
	}

	public int getThisID() {
		return idthiscard;
	}

	public String getTitle() {
		return title;
	}

	public TColors getColor() {
		return color;
	}

	public int getTrigger() {
		return trigger;
	}


	public String toString() {
		return "" + this.idcard + "; " + this.idthiscard;
	}

	public boolean equals(Card card) {
		return this.idcard == card.getID()
				&& this.idthiscard == card.getThisID();
	}

	public boolean sameCard(Card card) {
		return this.idcard == card.getID();
	}

	public void trigger() {
		// TODO Auto-generated method stub

	}

	public void setField(TField zone) {
		this.field = zone;
	}
	public String getImage(){
		return this.image;
	}
}
