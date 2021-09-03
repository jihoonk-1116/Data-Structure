package DSproject;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

class A1DLinkedListKC<E>{
	private static class DNode<E>{
		private E element;
		private DNode<E> prev;
		private DNode<E> next;
		public DNode(E e){
			this(e, null, null);
		}
		public DNode(E e, DNode<E> p, DNode<E> n){
			element = e;
			prev = p;
			next = n;
		}
		public E getE(){
			return element;
		}
		public DNode<E> getPrev(){
			return prev;
		}
		public DNode<E> getNext(){
			return next;
		}
		public void setE(E e){
			element = e;
		}
		public void setPrev(DNode<E> p){
			prev = p;
		}
		public void setNext(DNode<E> n){
			next = n;
		}
	}
	private DNode<E> header;
	private DNode<E> trailer;
	private int size;
	public A1DLinkedListKC(){  
		header = new DNode<E>(null);
		trailer = new DNode<E>(null, header, null);
		header.setNext(trailer);
		size = 0;
	}
	public Iterator<E> iterator(){
        return new DListIterator(0);
    }
    public ListIterator<E> listIterator(){
        return new DListIterator(0);
    }
    public ListIterator<E> listIterator(int index){
        return new DListIterator(index);
	}
  
	private class DListIterator implements ListIterator<E>{
		private DNode<E> nextItem;
        private DNode<E> lastItemReturned;
		private int index;
		
		public DListIterator(int i){
            if(i<0 || i>size){
                throw new IndexOutOfBoundsException("Invaild index" + i);
            }
            lastItemReturned = null;
            if(i==size){
                index = size;
                nextItem = null;
            }
            else{
                nextItem = header;
                for(index = 0;index<i;index++){
                    nextItem = nextItem.next;
                }
            }
        }

		@Override
		public boolean hasNext() {
            return nextItem != null;
		}

		@Override
		public void add(E e) {
			if(size == 0){ 		//The case of empty list
                header = new DNode<E>(e);
                System.out.println("Create list with: " + header.getE());
				trailer = header;
				
            }
            else if(nextItem == header){ //at the head
                DNode<E> newNode = new DNode<E>(e);
                newNode.next = nextItem;
                nextItem.prev = newNode;
                //nextItem.next = newNode;
				header = newNode;
				System.out.println("Head: " + newNode.getE());
            }
            else if(nextItem == null){ //Last
                DNode<E> newNode = new DNode<E>(e);
                trailer.next = newNode;
                newNode.prev = trailer;
				trailer = newNode;
				System.out.println("Last: "+ newNode.getE());
            }
            else{
                DNode<E> newNode = new DNode<E>(e); //Middle
                newNode.prev = nextItem.prev;
                nextItem.prev.next = newNode;
                newNode.next = nextItem;
				nextItem.prev = newNode;
				System.out.println("Middle: " + newNode.getE());
            }
            size++; index++;
        }

		@Override
		public E next() {
			if(!hasNext())
                throw new NoSuchElementException();
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index ++;
            return lastItemReturned.element;
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E previous() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

		@Override
		public void set(E e) {
			// TODO Auto-generated method stub

		}
	}
	public void print(){
	        DNode<E> temp = header;
		while (temp != trailer){
			System.out.print(temp.getE().toString() + ", ");
			temp = temp.getNext();
		}
		System.out.print(temp.getE().toString());
		System.out.println();
	}
	public void add(E e, int pos){
		listIterator(pos).add(e);
		
	}
}