package DSproject;

class A1LinkedListKC<E>{
	private static class Node<E>{
		private E element;
		private Node<E> next;
		public Node(E e, Node<E> n){
			element = e;
			next = n;
		}
		public E getE(){
			return element;
		}
		public Node<E> getNext(){
			return next;
		}
		public void setE(E e){
			element = e;
		}
		public void setNext(Node<E> n){
			next = n;
		}
	}
	private Node<E> head;
	public A1LinkedListKC(){
		head = null;
	}
	public void add(E e){
		Node<E> temp = new Node<>(e, head);			
		head = temp;
		
	}
	public void insert(E e, Node<E> p, Node<E> n){
		p.setNext(new Node<>(e, n));  
		    
	}
	public Node<E> getNode(int i) throws Exception{
		Node<E> temp = head;
		while (i > 0){
			if (temp == null) throw new Exception("Out of bound");
			temp = temp.getNext();
			i--;
		}
		return temp;
	}
	public boolean detectLoop(){
		if(this.head ==null || this.head.next == null) 
			return false;
        Node<E> p = this.head;
		Node<E> q = p.getNext();
        while(p != q) {
			if(p == null || q == null) 
				return false;
            p = p.getNext();
			q = q.getNext().next;
		}
		return true;
    }
}
