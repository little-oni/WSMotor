package dataHandlers;

import auxClasses.TTypes;

public class Skill {
	private TTypes type;
	private int stockCost;
	private String[] condition;
	private String[] effect;
	
	public TTypes getType() {
		return type;
	}

	public int getStockCost() {
		return stockCost;
	}

	public String[] getCondition() {
		return condition;
	}

	public String[] getEffect() {
		return effect;
	}
	public boolean checkCondition() {
		return true;
	}

}
