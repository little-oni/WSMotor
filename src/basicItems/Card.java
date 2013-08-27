package basicItems;

import auxClasses.*;
import dataHandlers.Effect;

public class Card {
	private String nombre;
	private int nCopias;
	private int identificador;
	private String title;
	private TColors color;
	protected int trigger; // otro tontiswitch que se pondrá según el caso. Para
							// personajes y eventos me lo ahorro y lo pongo como
							// número
	private String texto; // en principio, esto es independiente del efecto,
							// sólo para funciones estéticas dentro del
							// programa. Posiblemente no haga falta. Ni lo pongo
							// en el constructor.
	private Effect efecto;
	public Card(){
		this.nombre = "nombre";
		this.nCopias = 4;
		this.identificador = 000000;
		this.title = "title";
		this.color = TColors.DEFAULT;
		this.trigger = 0;
		this.texto = "texto";
		this.efecto = new Effect();
	}
	public Card(String nombre, int identificador, String title, String color,
			int trigger, String texto, Effect efecto) {
		this.nombre = nombre;
		this.nCopias = 4;
		this.identificador = identificador;
		this.title = title;
		switch (color){
		case "amarillo":
			this.color = TColors.AMARILLO;
		case  "verde":
			this.color = TColors.VERDE;
		case "rojo":
			this.color = TColors.ROJO;
		case "azul":
			this.color=TColors.AZUL;
		default:
			this.color = TColors.DEFAULT;
		}
		
		this.trigger = trigger;
		this.texto = texto;
		this.efecto = efecto;
	}

	public Card(String nombre, int identificador, String title, String color,
			int trigger, String texto, Effect efecto, int nCopias) {
		this.nombre = nombre;
		this.nCopias = nCopias;
		this.identificador = identificador;
		this.title = title;
		switch (color){
		case "amarillo":
			this.color = TColors.AMARILLO;
		case  "verde":
			this.color = TColors.VERDE;
		case "rojo":
			this.color = TColors.ROJO;
		case "azul":
			this.color=TColors.AZUL;
		default:
			this.color = TColors.DEFAULT;
		}
		this.trigger = trigger;
		this.texto = texto;
		this.efecto = efecto;
	}

	// Getters y Setters. No pongo setters para nada excepto el texto, ya que
	// no es probable que se modifique.
	public Effect getEfecto() {
		return efecto;
	}

	public void setEfecto(Effect efecto) {
		this.efecto = efecto;
	}

	public String getNombre() {
		return nombre;
	}

	public int getnCopias() {
		return nCopias;
	}

	public int getIdentificador() {
		return identificador;
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

	public String getTexto() {
		return texto;
	}

	public boolean playable() throws OutOfBoundsException {
		// TODO : comprobar la situación del campo para casos generales (nivel,
		// color, stock, etc)
		boolean check = true;
		for(int i = 1; (i <= this.efecto.getIndex())&&check ; i++){
			if (this.efecto.getSkills().getData(i).getType()==TTypes.CONDICION){
				check = this.efecto.getSkills().getData(i).checkCondition();
			}
		}
		return check;
	}
	public String toString(){
		return "" +identificador;
	}
	public boolean equals(Card card){//TODO
		return true;
	}
}
