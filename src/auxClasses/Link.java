package auxClasses;
/*
 * "Eslab�n" de la clase Cadena. No tiene m�s usos. 
 * */
public class Link<T> {
	private T data;
	private Link<T> next;
	
	public Link(T data){
		this.data = data;
		this.next = null;
	}
	public Link(T data, Link<T> next){
		this.data = data;
		this.next = next;
	}
	public Link<T> getNext() {
		return next;
	}
	public void setNext(Link<T> next) {
		this.next = next;
	}
	public T getData() {
		return data;
	}
}

