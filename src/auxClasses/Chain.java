package auxClasses;

import basicItems.Card;

/* Array indefinido de elementos de tipo T. Puede almacenar cualquier cantidad de datos
 * y el espacio en memoria se va asignando según el número varía. 
 * La función "getIndex()" funciona como el "length" de los array.
 * ATENCION: a la hora de buscar elementos, funciona con índices encadenados, 
 * no vectoriales: empieza en 1 y acaba en Index.
 */
public class Chain<T> {
	private Link<T> head;
	private int index;

	public Chain() {
		head = new Link<T>(null);
		index = 0;
	}

	/*
	 * Asigna un Link como cabeza. En principio, no es útil en la mayoría de
	 * casos pero puede encontrar usu utilidad.
	 */
	public void setHead(Link<T> head) {
		this.head = head;
	}

	/*
	 * Coloca "info" como último elemento de la cadena. Info no es un Link, sino
	 * el datoque se coloca en el link.
	 */
	public void add(T info) {
		if (this.head == null) {
			this.head = new Link<T>(info);
		} else {
			Link<T> onUse = this.head;
			Link<T> addedLink = new Link<T>(info);
			while (onUse.getNext() != null) {
				onUse = onUse.getNext();
			}
			onUse.setNext(addedLink);
		}
		this.index++;
	}

	/*
	 * Inserta "info" en la posición i. "i" se cuenta desde 1, no desde 0, ya
	 * que es un indice encadenado.
	 */
	public void insert(T info, int i) throws OutOfBoundsException {
		Link<T> newLink = new Link<T>(info);
		if (i == 1) {
			newLink.setNext(this.head.getNext());
			this.head.setNext(newLink);
			index++;
		} else {
			Link<T> onUse = this.head;
			for (int j = 0; j < i - 1; j++) {
				onUse = onUse.getNext();
			}
			newLink.setNext(onUse.getNext());
			onUse.setNext(newLink);
			index++;
		}
	}

	/*
	 * Recupera el elemento en la posición "i", equivalente al array[i]
	 */
	public T getData(int i) throws OutOfBoundsException {
		if (i > index)
			throw new OutOfBoundsException();
		else {
			Link<T> onUse = this.head;
			for (int j = 0; j < i; j++) {
				onUse = onUse.getNext();
			}
			return onUse.getData();
		}
	}

	/*
	 * Elimina el elemento "i". NO RELLENA CON NULL. La cadena pasa a tener un
	 * elemento menos.
	 */
	public void remove(int i) throws OutOfBoundsException {
		if (i > index)
			throw new OutOfBoundsException();
		else if (i == 1) {
			this.head = this.head.getNext();
			index--;
		} else {
			Link<T> onUse = this.head;
			for (int j = 0; j < i - 1; j++) {
				onUse = onUse.getNext();
			}
			onUse.setNext(onUse.getNext().getNext());
			index--;
		}
	}

	public String toString() {
		String out = "";
		for (int i = 1; i <= this.index; i++) {
			try {
				out = out + this.getData(i).toString();
			} catch (OutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return out;
	}

	public int getIndex() {
		return this.index;
	}

	public Link<T> getHead() {
		return this.head;
	}
	/*
	 * Vacía la cadena, borrando todos los elementos. 
	 * */
	public void clear() {
		this.head = new Link<T>(null);
		this.index = 0;
	}
	/*
	 * Devuelve verdadero si la cadena no tiene ningún elemento. 
	 * */
	public boolean isEmpty() {
		return index == 0;
	}
}
