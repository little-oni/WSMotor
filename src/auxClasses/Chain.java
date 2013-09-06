package auxClasses;

import basicItems.Card;

/* Array indefinido de elementos de tipo T. Puede almacenar cualquier cantidad de datos
 * y el espacio en memoria se va asignando seg�n el n�mero var�a. 
 * La funci�n "getIndex()" funciona como el "length" de los array.
 * ATENCION: a la hora de buscar elementos, funciona con �ndices encadenados, 
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
	 * Asigna un Link como cabeza. En principio, no es �til en la mayor�a de
	 * casos pero puede encontrar usu utilidad.
	 */
	public void setHead(Link<T> head) {
		this.head = head;
	}

	/*
	 * Coloca "info" como �ltimo elemento de la cadena. Info no es un Link, sino
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
	 * Inserta "info" en la posici�n i. "i" se cuenta desde 1, no desde 0, ya
	 * que es un indice encadenado.
	 */
	public void insert(T info, int i) {
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
	 * Recupera el elemento en la posici�n "i", equivalente al array[i]
	 */
	public T getData(int i) {
		Link<T> onUse = this.head;
		for (int j = 0; j < i; j++) {
			onUse = onUse.getNext();
		}
		return onUse.getData();
	}

	/*
	 * Elimina el elemento "i". NO RELLENA CON NULL. La cadena pasa a tener un
	 * elemento menos.
	 */
	public void remove(int i) {
		if (i == 1) {
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
			out = out + this.getData(i).toString();
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
	 * Vac�a la cadena, borrando todos los elementos.
	 */
	public void clear() {
		this.head = new Link<T>(null);
		this.index = 0;
	}

	/*
	 * Devuelve verdadero si la cadena no tiene ning�n elemento.
	 */
	public boolean isEmpty() {
		return index == 0;
	}
}
