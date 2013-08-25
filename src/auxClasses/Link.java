package auxClasses;

public class Link<T> {
	private T data;
	private Link<T> next;
	//private Link<T> previous;
	
	public Link(T data){
		this.data = data;
		this.next = null;
		//this.previous = null; 
	}
	public Link(T data, Link<T> next){
		this.data = data;
		this.next = next;
		//this.previous = previous;
	}
	//public Link<T> getPrevious() {
		//return previous;
	//}
	//public void setPrevious(Link<T> previous) {
		//this.previous = previous;
	//}
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

