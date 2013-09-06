package game;

import field.*;
import auxClasses.*;
import basicItems.*;
import basicItems.Character;
import visual.*;

import java.util.Scanner;

public class Game {
	private Visual visual;
	private Player p1;
	private Player p2;
	private TPhases currentPhase;
	private Player currentPlayer;
	private Player inactivePlayer;
	private CheckArea check;
	public boolean active;
	private Character atacker;
	private Character defender;

	public Game() {
		visual = new Visual(this);
		p1 = new Player();
		p2 = new Player();
		currentPhase = TPhases.STANDPH;
		currentPlayer = p1;
		inactivePlayer = p2;
		check = new CheckArea();
		active = true;
		atacker = new Character();
		defender = new Character();
	}

	public Game(Deck deck1, Deck deck2) {
		visual = new Visual(this);
		p1 = new Player(deck1);
		p2 = new Player(deck2);
		currentPhase = TPhases.STANDPH;
		currentPlayer = p1;
		inactivePlayer = p2;
		check = new CheckArea();
		active = true;
		atacker = new Character();
		defender = new Character();
	}

	/*
	 * El jugador "p" roba "i" cartas. Si el deck se acaba en alg�n momento,
	 * se ejecuta deckReset() y se hacen, al final, tantos da�os como reseteos
	 * se hayan hecho.
	 */
	public void draw(Player p, int i) throws GameOver {
		int reset = 0;
		for (int j = 0; j < i;) {
			if (p.deck.cardsInDeck() != 0) {
				p.hand.addToHand(p.deck.draw());
				j++;
			} else {
				deckReset(p, false);
				reset++;
			}
		}
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}
		for (int j = 0; j < reset; j++) {
			topToClock(p);
		}

	}

	/*
	 * Las primeras "i" cartas del deck de "p" se ponen en su stock. Si el deck
	 * se acaba en alg�n momento, se ejecuta deckReset() y se hacen tantos
	 * da�os como reseteos se hayan hecho.
	 */
	public void topToStock(Player p, int i) throws GameOver {
		int reset = 0;
		for (int j = 0; j < i;) {
			if (p.deck.cardsInDeck() != 0) {
				check.check(p.deck.draw());
				p.stock.moveToStock(check.getCheck());
				check.clear();
				j++;
			} else {
				deckReset(p, false);
			}
		}
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}
		for (int j = 0; j < reset; j++) {
			topToClock(p);
		}
	}

	/*
	 * El jugador "p" recibe "i" da�os desde el deck. Si en alg�n momento
	 * sale Cl�max, se interrumpe la acci�n y se tiran todas lasa cartas
	 * sacadas al waiting. Si el deck se acaba en alg�n momento, se ejecuta
	 * deckReset() y se hacen tantos da�os como reseteos se hayan hecho al
	 * final. Si el clock llega a 7 cartas en alg�n momento, se ejecuta
	 * levelUp().
	 */
	public void takeDamage(Player p, int i) throws GameOver {
		boolean cancel = false;
		int reset = 0;
		for (int j = 0; j < i && !cancel;) {
			if (p.deck.cardsInDeck() > 0) {
				check.check(p.deck.draw());
				if (check.getCheck() instanceof Climax) {
					int fix = p.clock.cardsInBuffer();
					for (int n = 0; n < fix; n++) {
						p.waiting.trash(p.clock.unbuffer());
					}
					p.waiting.trash(check.getCheck());
					check.clear();
					cancel = true;
				} else {
					p.clock.bufferDamage(check.getCheck());
					check.clear();
					j++;
				}
			} else {
				deckReset(p, false);
				reset++;
			}

		}
		if (!cancel) {
			while (p.clock.cardsInBuffer() != 0) {

				p.clock.transferBuffer();

				if (p.clock.cardsInClock() == 7) {
					levelUp(p);
				}
			}
			p.clock.transferBuffer();
		}
		for (int n = 0; n < reset; n++) {
			topToClock(p);
		}
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}

	}

	/*
	 * Selecciona una carta del clock y la pone en el level. Ejecuta
	 * clock.levelUp() para limpiar el clock y over7.
	 */
	public void levelUp(Player p) {
		Scanner input = new Scanner(System.in);
		System.out
				.println(p.clock + "\n Selecciona la carta a poner en Level:");
		p.level.levelUp(p.clock.heal(input.nextInt()));
		for (int i = 0; i < 6; i++) {
			p.waiting.trash(p.clock.heal(0));
		}
		p.clock.levelUp();
		checkEffects();
		active = p.level.cardsInLevel() == 4;
		if (p.clock.cardsInClock() == 7) {
			levelUp(p);
		}
	}

	/*
	 * Bucsa en "array" la carta "card" si la encuentra, devuelve su �ndice
	 * encadenado. En caso contrario devuelve 0.
	 */
	public int search(Card[] array, Card card) {
		int i = 0;
		boolean stop = false;
		for (; i < array.length;) {
			if (card.equals(array[i])) {
				stop = true;
			} else
				i++;
		}
		if (stop)
			return i + 1;
		else
			return 0;
	}

	/*
	 * Si hay cartas en el waiting, resetea el deck. Si se ha pasado el
	 * par�metro "true", coloca la primera carta del deck en el clock. En caso
	 * de que no haya cartas en el waiting, pierde la partida.
	 */
	public void deckReset(Player p, boolean clock) throws GameOver {
		if (p.waiting.cardsInWaiting() != 0) {
			int fix = p.waiting.cardsInWaiting();
			for (int i = 0; i < fix; i++) {
				p.deck.moveToBottom(p.waiting.extractCard(1));
			}
			p.deck.shuffleDeck();
			if (clock)
				topToClock(p);
		} else {
			active = false;
			throw new GameOver();
		}
	}

	/*
	 * Coloca la primera carta del deck en el clock sin comprobar si es un
	 * Climax. Si hay 7 cartas en el clock, ejecuta levelUp(). Si en algun
	 * momento el deck se acaba, ejecuta deckReset(true).
	 */
	public void topToClock(Player p) throws GameOver {
		p.clock.bufferDamage(p.deck.draw());
		p.clock.transferBuffer();
		if (p.clock.cardsInClock() == 7) {
			levelUp(p);
		}
		if (p.deck.cardsInDeck() == 0) {
			deckReset(p, true);
		}
	}

	/*
	 * Coloca las primeras "i" cartas del stock en el waiting. ATENCION: no
	 * comprueba si hay "i" cartas.
	 */
	public void payStock(Player p, int i) {
		for (int j = 0; j < i; j++) {
			p.waiting.trash(p.stock.payFirst());
		}
	}

	/*
	 * Pone los personajes en Stand. Comprueba efectos DESPUES de hacerlo. Si
	 * hay alg�n personaje que no se ponga en Stand por algo, durante la
	 * comprobaci�n se pondrian en Rest (y no ejecutria una nueva
	 * comprobacion).
	 */
	public void standPhase() {
		currentPhase = TPhases.STANDPH;
		for (int i = 0; i < currentPlayer.stage.getStage().length; i++)
			if (currentPlayer.stage.getStage()[i] != null) {
				currentPlayer.stage.getStage()[i].stand();
			}
		checkEffects();
	}

	/*
	 * Roba y comprueba efectos ANTES Y DESPUES de robar.
	 */
	public void drawPhase() throws GameOver {
		currentPhase = TPhases.DRAWPH;
		checkEffects();
		draw(currentPlayer, 1);
		checkEffects();
	}

	/*
	 * Pregunta si se quiere poner una carta al clock. En caso afirmativo, lo
	 * hace y roba dos. Comprueba efectos ANTES Y DESPU�S.
	 */
	public void clockPhase() throws GameOver {
		currentPhase = TPhases.CLOCKPH;
		checkEffects();
		visual.showYN();
		visual.repaint();
		Hitbox yes = new Hitbox(412, 250, 100, 100);
		Hitbox no = new Hitbox(512, 250, 100, 100);
		int n = 0;
		while (!Main.getUpdate()) {
		}
		if (yes.inside(Main.getMouseX(), Main.getMouseY())) {
			n = 1;
			Main.updated();
		} else if (no.inside(Main.getMouseX(), Main.getMouseY())) {
			n = 0;
			Main.updated();
		}
		visual.showYN();
		if (n == 1) {
			int i = selectHand(1, currentPlayer)[0];
			visual.showHand();
			visual.repaint();
			currentPlayer.clock.bufferDamage(currentPlayer.hand.discard(i));
			currentPlayer.clock.transferBuffer();
			draw(currentPlayer, 2);
			visual.updateClock();
			visual.repaint();
		}
		else{
			visual.updateClock();
			visual.repaint();
		}
		checkEffects();
	}

	public TPhases getCurrentPhase() {
		return currentPhase;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getInactivePlayer() {
		return inactivePlayer;
	}

	public CheckArea getCheck() {
		return check;
	}

	public boolean isActive() {
		return active;
	}

	public Character getAtacker() {
		return atacker;
	}

	public Character getDefender() {
		return defender;
	}

	/*
	 * Fase generica. Lo unico que hace es preguntar si hay efectos que hacer /
	 * cartas que jugar.
	 */
	public void mainPhase() {
		currentPhase = TPhases.MAINPH;
		boolean main = true;
		while (main) {
			checkEffects();
			Hitbox showHand = new Hitbox(0, 520, 100, 80);
			while (!Main.getUpdate()) {
			}
			if (showHand.inside(Main.getMouseX(), Main.getMouseY())) {
				Main.updated();
				visual.updateClock();
				selectHand(0, currentPlayer);
				visual.updateClock();
			}
		}
			main = false;
		}


	/*
	 * Nuevamente, fase generica, s�lo pregunta si se puede hacer algo.
	 */
	public void climaxPhase() {
		currentPhase = TPhases.CLIMAXPH;
		checkEffects();
	}

	/*
	 * Y viene la battle, explicada paso a paso.
	 */
	public void atackPhase() throws GameOver {
		currentPhase = TPhases.ATTACKPH;
		checkEffects();
		boolean atack = true;
		while (atack) {
			/*
			 * Cuenta los personajes en Stand. En caso de que no haya, no
			 * procede a atacar.
			 */
			int charStanding = 0;
			for (int i = 0; i < currentPlayer.stage.frontRow().length; i++) {
				if (currentPlayer.stage.frontRow()[i] != null
						&& currentPlayer.stage.frontRow()[i].standing()) {
					charStanding++;
				}
			}
			if (charStanding != 0) {
				/*
				 * En caso de que haya personajes en Stand, pregunta si se
				 * quiere atacar. En caso negativo, procede a acabar la batlle.
				 */
				Scanner input = new Scanner(System.in);
				System.out.println("�Declarar un ataque? (0 = no, 1 = s�)");
				int conf = input.nextInt();
				if (conf == 1) {
					/*
					 * En caso de que quiera atacar, se entra en Atack Step. (En
					 * este punto NO se comprueban efectos por razones que ahora
					 * se veran).
					 */
					currentPhase = TPhases.ATTACKST;
					/*
					 * Cuenta los personajes en Stand y te da la opcion de
					 * elegir con cual atacar.
					 */
					int[] standingChar = new int[3];
					int n = 0;
					for (int i = 0; i < currentPlayer.stage.frontRow().length; i++) {
						if (currentPlayer.stage.frontRow()[i] != null
								&& currentPlayer.stage.frontRow()[i].standing()) {
							standingChar[n] = i + 1;
							n++;
						}
					}
					String standingChars = "";
					for (int i = 0; i < standingChar.length; i++) {
						if (standingChar[i] != 0) {
							standingChars = standingChars + standingChar[i]
									+ ", ";
						}
					}
					System.out
							.println("Seleccione personaje: " + standingChars);
					int j = input.nextInt();
					/*
					 * Asigna atacante y defensor segun el personaje
					 * seleccionado. En ESTE momento se comprueban los efectos.
					 * La razon son los personajes que desvian el ataque (te
					 * estoy hablando a ti, guardaespaldas). Dado que esta
					 * comprobaci�n se hace cada vez que se declara un ataque,
					 * en caso de que las condiciones de desvio no se den, el
					 * defensor no se reasigna y punto.
					 */
					this.atacker = currentPlayer.stage.frontRow()[j - 1];
					this.defender = inactivePlayer.stage.frontRow()[j - 1];
					checkEffects();
					/*
					 * Comprueba si hay o no Direct Atack
					 */
					if (defender != null) {
						/*
						 * En caso de que no haya, pregunta por el tipo de
						 * ataque a realizar
						 */
						System.out
								.println("Seleccione tipo de ataque. (0 = front, 1 = side)");
						int tatk = input.nextInt();
						/*
						 * En caso de front atack:
						 */
						if (tatk == 0) {
							/*
							 * El personaje se pone en Rest. Se comprueban
							 * efectos de "cuando esta carta ataque/sea atacada"
							 */
							atacker.rest();
							checkEffects();
							/*
							 * Entramos en Trigger Step. Se comprueba el trigger
							 * y los efectos
							 */
							currentPhase = TPhases.TRIGGERST;
							check.check(currentPlayer.deck.draw());
							check.getCheck().trigger();
							currentPlayer.stock.moveToStock(check.getCheck());
							check.clear();
							checkEffects();
							/*
							 * Entramos en Counter Step. Solo pregunta por los
							 * efectos.
							 */
							currentPhase = TPhases.COUNTERST;
							checkEffects();
							/*
							 * Entramos en Damage. El jugador defensor recibe
							 * los da�os que le corresponden y DESPUES se
							 * comprueban los efectos.
							 */
							currentPhase = TPhases.DAMAGEST;
							takeDamage(inactivePlayer, atacker.getCurrentSoul());
							checkEffects();
							/*
							 * Entramos en Battle Step. Segun el resultado de la
							 * batalla, se ponen los personajes en Reverse y
							 * DESPUES se comprueban los efectos
							 */
							currentPhase = TPhases.BATTLEST;
							if (atacker.getCurrentPower() > defender
									.getCurrentPower()) {
								defender.reverse();
								checkEffects();
							} else if (atacker.getCurrentPower() < defender
									.getCurrentPower()) {
								atacker.reverse();
								checkEffects();
							} else {
								atacker.reverse();
								defender.reverse();
								checkEffects();
							}
						}
						/*
						 * En caso de Side Atack, se procede de manera similar,
						 * pero calculando el da�o de la manera
						 * correspondiente. En el caso de los Ghost, habra que
						 * hacer que en este momento ganen Soul igual al que
						 * perderian por el Side Atack, sin refrescar la
						 * pantalla.
						 */
						else {
							atacker.rest();
							checkEffects();
							currentPhase = TPhases.TRIGGERST;
							check.check(currentPlayer.deck.draw());
							check.getCheck().trigger();
							currentPlayer.stock.moveToStock(check.getCheck());
							check.clear();
							checkEffects();
							currentPhase = TPhases.DAMAGEST;
							takeDamage(inactivePlayer, atacker.getCurrentSoul()
									- defender.getLevel());
							checkEffects();
						}
					}
					/*
					 * En caso de Direct Atack, se procede omitiendo las fases
					 * correspondientes y aplicando el modificador de da�o.
					 */
					else {
						atacker.rest();
						checkEffects();
						currentPhase = TPhases.TRIGGERST;
						check.check(currentPlayer.deck.draw());
						check.getCheck().trigger();
						currentPlayer.stock.moveToStock(check.getCheck());
						check.clear();
						checkEffects();
						currentPhase = TPhases.DAMAGEST;
						takeDamage(inactivePlayer, atacker.getCurrentSoul() + 1);
						checkEffects();
					}
				} else {
					atack = false;
				}
			} else {
				atack = false;
			}
			/*
			 * Se reinician los roles de atacante y defensor. Notese que esta
			 * parte del bucle solo es alcanzable si ha habido ataque.
			 */
			atacker = new Character();
			defender = new Character();
		}
		/*
		 * Se comprueban los efectos al final de la battle.
		 */
		checkEffects();
	}

	/*
	 * Encore Step, se retiran los personajes en reverse. Se comprueban los
	 * efectos ANTES, DESPUES y cuando se retiran los personajes.
	 */
	public void encoreStep() {
		currentPhase = TPhases.ENCOREST;
		checkEffects();
		for (int i = 0; i < currentPlayer.stage.frontRow().length; i++) {
			if (currentPlayer.stage.frontRow()[i] != null
					&& currentPlayer.stage.frontRow()[i].reversed()) {
				currentPlayer.waiting.trash(currentPlayer.stage.retire(i + 1));
				checkEffects();

			}
			if (inactivePlayer.stage.frontRow()[i] != null
					&& inactivePlayer.stage.frontRow()[i].reversed()) {
				inactivePlayer.waiting
						.trash(inactivePlayer.stage.retire(i + 1));
				checkEffects();
			}
		}
		checkEffects();
	}

	/*
	 * End Phase. Comprueba efectos (en este momento los efectos que se acaban
	 * desaparecen) y cede el control al otro jugador
	 */
	public void endPhase() {
		currentPhase = TPhases.ENDPH;
		checkEffects();
		Scanner input = new Scanner(System.in);
		if (currentPlayer.hand.cardsInHand() > 7) {
			System.out.println("Seleccione cartas a descartar");
			System.out.println(currentPlayer.hand);
			int[] store = new int[currentPlayer.hand.cardsInHand() - 7];
			for (int i = 0; i < store.length; i++) {
				store[i] = input.nextInt();
			}
			for (int i = 0; i < store.length; i++) {
				currentPlayer.waiting.trash(currentPlayer.hand
						.discard(store[i]));
			}

		}
		Player tmp = currentPlayer;
		currentPlayer = inactivePlayer;
		inactivePlayer = tmp;
	}

	public void checkEffects() {
		// TODO Auto-generated method stub

	}

	public Player getP1() {
		return currentPlayer;
	}

	public Player getP2() {
		return inactivePlayer;
	}

	public Visual getVisual() {
		return visual;
	}

	public TPhases getPhase() {
		return currentPhase;
	}

	public int[] selectHand(int i, Player p) {
		if (i == 0) {
			visual.showHand();
			visual.repaint();
			boolean stop = false;
			while (!Main.getUpdate()) {
			}
			int scrolls = 0;
			Hitbox exit = new Hitbox(100,260,250,100);
			Hitbox scrollBack = new Hitbox(0, 300, 100, 300);
			Hitbox scroll = new Hitbox(150 + 224 + 50 + 224 + 50 + 224, 300,
					150, 300);
			while (!stop) {
				visual.repaint();
				while (!Main.getUpdate()) {
				}
				if (scrollBack.inside(Main.getMouseX(), Main.getMouseY())) {
					if (visual.getScroll() > 0) {
						scrolls--;
						visual.setScroll(visual.getScroll() - 1);
					}
					visual.repaint();
					Main.updated();
				}

				if (scroll.inside(Main.getMouseX(), Main.getMouseY())) {
					scrolls++;
					visual.setScroll(visual.getScroll() + 1);
					visual.repaint();
					Main.updated();
				}
				if (exit.inside(Main.getMouseX(), Main.getMouseY())){
					Main.updated();
					visual.showHand();
					visual.repaint();
					stop = true;
				}
					
			}
			return new int[0];
		} else {
			int[] vector = new int[i];
			visual.showHand();
			visual.repaint();
			for (int m = 0; m < vector.length; m++) {
				int n = 0;
				boolean stop = false;
				while (!Main.getUpdate()) {
				}
				int scrolls = 0;
				Hitbox scrollBack = new Hitbox(0, 300, 100, 300);
				Hitbox card1 = new Hitbox(150, 300, 224, 300);
				Hitbox card2 = new Hitbox(150 + 224 + 50, 300, 224, 300);
				Hitbox card3 = new Hitbox(150 + 224 + 50 + 224 + 50, 300, 224,
						300);
				Hitbox scroll = new Hitbox(150 + 224 + 50 + 224 + 50 + 224,
						300, 150, 300);
				while (!stop) {
					visual.repaint();
					while (!Main.getUpdate()) {
					}
					if (scrollBack.inside(Main.getMouseX(), Main.getMouseY())) {
						if (visual.getScroll() > 0) {
							scrolls--;
							visual.setScroll(visual.getScroll() - 1);
						}
						visual.repaint();
						Main.updated();
					} else if (card1.inside(Main.getMouseX(), Main.getMouseY())) {
						n = 1 + 3 * scrolls;
						Main.updated();
						stop = true;
					} else if (card2.inside(Main.getMouseX(), Main.getMouseY())) {
						n = 2 + 3 * scrolls;
						Main.updated();
						stop = true;
					} else if (card3.inside(Main.getMouseX(), Main.getMouseY())) {
						n = 3 + 3 * scrolls;
						Main.updated();
						stop = true;
					}
					if (scroll.inside(Main.getMouseX(), Main.getMouseY())) {
						scrolls++;
						visual.setScroll(visual.getScroll() + 1);
						visual.repaint();
						Main.updated();
					}
				}
				vector[m] = n;
			}
			return vector;
		}
	}
}
