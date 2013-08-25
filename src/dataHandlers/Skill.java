package dataHandlers;

public class Skill {
	private int type; // 0: condición de uso de la carta, 1: skill auto o evento
						// / climax, 2 skill de main phase o evento de main, 3
						// skill continua
	private int stockCost;
	public int getType() {
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

	private String[] condition;
	private String[] effect;

	public boolean checkCondition() {
		return true;
	}

}
