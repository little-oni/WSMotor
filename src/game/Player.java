package game;

import field.*;

public class Player {
	protected Deck deck;
	public Hand hand;
	public Clock clock;
	public Level level;
	protected Memory memory;
	protected Stage stage;
	public Stock stock;
	public WaitingRoom waiting;
	
	public Player(){
		this.clock = new Clock();
		this.deck = new Deck();
		this.hand = new Hand();
		this.level = new Level();
		this.memory = new Memory();
		this.stage = new Stage();
		this.stock = new Stock();
		this.waiting = new WaitingRoom();
	}
	public Player(Deck deck){
		this.clock = new Clock();
		this.deck = deck;
		this.hand = new Hand();
		this.level = new Level();
		this.memory = new Memory();
		this.stage = new Stage();
		this.stock = new Stock();
		this.waiting = new WaitingRoom();
	}
	
}
