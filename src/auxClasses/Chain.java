package auxClasses;

public class Chain<T> { // TAD que viene a ser un array indefinido. Ventaja:
						// puede almacenar listas largas. Desventaja: out of
						// bounds
	private Link<T> head;
	private int index;

	public Chain() {
		head = new Link<T>(null);
		index = 0;
	}

	public void setHead(Link<T> head) {// esto ... no sé muy bien para qué está,
										// puede ser útil para borrar la lista
										// rápido.
		this.head = head;
	}

	public void add(T info) {// añade Info a la cadena, al final
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

	public void insert(T info, int i) throws OutOfBoundsException {
		// inserta Info en la posición i contando desde 1 hasta index
	
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

	public T getData(int i) throws OutOfBoundsException {// recupera el dato en
															// i, contando desde
															// 1 hasta index
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

	public void remove(int i) throws OutOfBoundsException {// borra el elemento
															// en i, contando
															// desde 1 hasta
															// index
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
	public int getIndex(){
		return this.index;
	}
	public Link<T> getHead(){
		return this.head;
	}
	public void clear(){
		this.head=new Link<T>(null);
		this.index=0;
	}

}
