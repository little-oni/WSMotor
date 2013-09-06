package basicItems;

import auxClasses.*;

public class Character extends Card {
	private int level;
	private int cost;
	private int power;
	private int soul;
	private String[] traits;
	private TState state;
	private int currentLevel;
	private int currentCost;
	private int currentPower;
	private int currentSoul;

	public Character() {
		super();
		this.image = "/images/card2171.jpg";
		this.cost = 0;
		this.level = 1;
		this.power = 4500;
		this.soul = 1;
		this.traits = new String[3];
		this.traits[0] = "Animal";
		this.traits[1] = "Science";
		this.traits[2] = "";
		this.state = TState.STAND;
		this.currentCost = cost;
		this.currentLevel = level;
		this.currentPower = power;
		this.currentSoul = soul;

	}

	public Character(String nombre, int identificador, String title,
			String color, int trigger, String ruta, int nCopias, int level,
			int cost, int power, int soul, String trait1, String trait2) {
		super(nombre, identificador, title, color, trigger, ruta, nCopias);
		this.cost = cost;
		this.level = level;
		this.power = power;
		this.soul = soul;
		this.traits = new String[3];
		this.traits[0] = trait1;
		this.traits[1] = trait2;
		this.traits[2] = "";
		this.state = TState.STAND;
		this.currentCost = this.cost;
		this.currentLevel = this.level;
		this.currentPower = this.power;
		this.currentSoul = this.soul;
	}

	public Character(String nombre, int identificador, String title,
			String color, int trigger, String ruta, int level, int cost,
			int power, int soul, String trait1, String trait2) {
		super(nombre, identificador, title, color, trigger, ruta);
		this.cost = cost;
		this.level = level;
		this.power = power;
		this.soul = soul;
		this.traits = new String[3];
		this.traits[0] = trait1;
		this.traits[1] = trait2;
		this.traits[2] = "";
		this.state = TState.STAND;
		this.currentCost = this.cost;
		this.currentLevel = this.level;
		this.currentPower = this.power;
		this.currentSoul = this.soul;
	}

	public void stand() {
		this.state = TState.STAND;

	}

	public boolean standing() {
		return this.state == TState.STAND;
	}

	public void rest() {
		this.state = TState.REST;

	}

	public int getCurrentSoul() {
		return this.currentCost;
	}

	public int getCurrentPower() {
		return this.currentPower;
	}

	public void reverse() {
		this.state = TState.REVERSE;

	}

	public int getLevel() {
		return this.currentLevel;
	}

	public boolean reversed() {
		return this.state == TState.REVERSE;
	}

}