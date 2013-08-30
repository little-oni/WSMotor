package game;

import field.*;
import auxClasses.*;
import basicItems.*;
import basicItems.Character;

import java.util.Scanner;

public class Game {
	private Player p1;
	private Player p2;
	private TPhases currentPhase;
	private Player currentPlayer;
	private Player inactivePlayer;
	private CheckArea check;
	private boolean active;
	private Character atacker;
	private Character defender;

	public Game() {
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
	 * El jugador "p" roba "i" cartas. Si el deck se acaba en algún momento, se
	 * ejecuta deckReset() y se hacen, al final, tantos daños como reseteos se
	 * hayan hecho.
	 */
	public void draw(Player p, int i) throws OutOfBoundsException, GameOver {
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
	 * se acaba en algún momento, se ejecuta deckReset() y se hacen tantos daños
	 * como reseteos se hayan hecho.
	 */
	public void topToStock(Player p, int i) throws OutOfBoundsException,
			GameOver {
		int reset = 0;
		for (int j = 0; j < i;) {
			if (p.deck.cardsInDeck() != 0) {
				check.check(p.deck.draw());
				p.stock.moveToStock(check.getCheck());
				check.check(null);
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
	 * El jugador "p" recibe "i" daños desde el deck. Si en algún momento sale
	 * Clímax, se interrumpe la acción y se tiran todas lasa cartas sacadas al
	 * waiting. Si el deck se acaba en algún momento, se ejecuta deckReset() y
	 * se hacen tantos daños como reseteos se hayan hecho al final. Si el clock
	 * llega a 7 cartas en algún momento, se ejecuta levelUp().
	 */
	public void takeDamage(Player p, int i) throws OutOfBoundsException,
			GameOver {
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
					check.check(null);
					cancel = true;
				} else {
					p.clock.bufferDamage(check.getCheck());
					check.check(null);
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
	public void levelUp(Player p) throws OutOfBoundsException {
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
	 * Bucsa en "array" la carta "card" si la encuentra, devuelve su índice
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
	 * parámetro "true", coloca la primera carta del deck en el clock. En caso
	 * de que no haya cartas en el waiting, pierde la partida.
	 */
	public void deckReset(Player p, boolean clock) throws OutOfBoundsException,
			GameOver {
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
	public void topToClock(Player p) throws OutOfBoundsException, GameOver {
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
	public void payStock(Player p, int i) throws OutOfBoundsException {
		for (int j = 0; j < i; j++) {
			p.waiting.trash(p.stock.payFirst());
		}
	}

	/*
	 * Pone los personajes en Stand. Comprueba efectos DESPUES de hacerlo. Si
	 * hay algún personaje que no se ponga en Stand por algo, durante la
	 * comprobación se pondrian en Rest (y no ejecutria una nueva comprobacion).
	 */
	public void standPhase() {
		currentPhase = TPhases.STANDPH;
		for (int i = 0; i < currentPlayer.stage.getStage().length; i++)
			currentPlayer.stage.getStage()[i].stand();
		checkEffects();
	}

	/*
	 * Roba y comprueba efectos ANTES Y DESPUES de robar.
	 */
	public void drawPhase() throws OutOfBoundsException, GameOver {
		currentPhase = TPhases.DRAWPH;
		checkEffects();
		draw(currentPlayer, 1);
		checkEffects();
	}

	/*
	 * Pregunta si se quiere poner una carta al clock. En caso afirmativo, lo
	 * hace y roba dos. Comprueba efectos ANTES Y DESPUÉS.
	 */
	public void clockPhase() throws OutOfBoundsException, GameOver {
		currentPhase = TPhases.CLOCKPH;
		checkEffects();
		Scanner input = new Scanner(System.in);
		System.out.println("¿Desea colocar una carta en el clock?");
		int n = input.nextInt();
		if (n == 1) {
			System.out.println("Seleccione la carta a poner en clock");
			int i = input.nextInt();
			currentPlayer.clock.bufferDamage(currentPlayer.hand.discard(i));
			currentPlayer.clock.transferBuffer();
			draw(currentPlayer, 2);
		}
		checkEffects();
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
			Scanner input = new Scanner(System.in);
			System.out.println("¿Acabar main phase? (0 = no, 1 = sí)");
			int in = input.nextInt();
			if (in == 1) {
				main = false;
			}
		}
		checkEffects();
	}

	/*
	 * Nuevamente, fase generica, sólo pregunta si se puede hacer algo.
	 */
	public void climaxPhase() {
		currentPhase = TPhases.CLIMAXPH;
		checkEffects();
	}

	/*
	 * Y viene la battle, explicada paso a paso.
	 */
	public void atackPhase() throws OutOfBoundsException, GameOver {
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
				if (currentPlayer.stage.frontRow()[i].standing()) {
					charStanding++;
				}
			}
			if (charStanding != 0) {
				/*
				 * En caso de que haya personajes en Stand, pregunta si se
				 * quiere atacar. En caso negativo, procede a acabar la batlle.
				 */
				Scanner input = new Scanner(System.in);
				System.out.println("¿Declarar un ataque? (0 = no, 1 = sí)");
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
					 * comprobación se hace cada vez que se declara un ataque,
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
							check.check(null);
							checkEffects();
							/*
							 * Entramos en Counter Step. Solo pregunta por los
							 * efectos.
							 */
							currentPhase = TPhases.COUNTERST;
							checkEffects();
							/*
							 * Entramos en Damage. El jugador defensor recibe
							 * los daños que le corresponden y DESPUES se
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
						 * pero calculando el daño de la manera correspondiente.
						 * En el caso de los Ghost, habra que hacer que en este
						 * momento ganen Soul igual al que perderian por el Side
						 * Atack, sin refrescar la pantalla.
						 */
						else {
							atacker.rest();
							checkEffects();
							currentPhase = TPhases.TRIGGERST;
							check.check(currentPlayer.deck.draw());
							check.getCheck().trigger();
							currentPlayer.stock.moveToStock(check.getCheck());
							check.check(null);
							checkEffects();
							currentPhase = TPhases.DAMAGEST;
							takeDamage(inactivePlayer, atacker.getCurrentSoul()
									- defender.getLevel());
							checkEffects();
						}
					}
					/*
					 * En caso de Direct Atack, se procede omitiendo las fases
					 * correspondientes y aplicando el modificador de daño.
					 */
					else {
						atacker.rest();
						checkEffects();
						currentPhase = TPhases.TRIGGERST;
						check.check(currentPlayer.deck.draw());
						check.getCheck().trigger();
						currentPlayer.stock.moveToStock(check.getCheck());
						check.check(null);
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
		Player store = currentPlayer;
		currentPlayer = inactivePlayer;
		inactivePlayer = store;
	}

	public void checkEffects() {
		// TODO Auto-generated method stub

	}
}
