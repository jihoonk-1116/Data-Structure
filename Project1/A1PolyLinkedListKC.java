package DSproject;
class A1PolyLinkedListKC{
 private static class PNode{
     private int coe;
     private int exp;
     private PNode next;
     public PNode(int c, int e){
         this(c, e, null);
     }
     public PNode(int c, int e, PNode n){
         coe = c;
         exp = e;
         next = n;
     }
     public void setCoe(int c){ coe = c;}
     public void setExp(int e){ exp = e;}
     public void setNext(PNode n){ next = n;}
     public int getCoe(){ return coe;}
     public int getExp(){ return exp;}
     public PNode getNext(){ return next;}
     public String toString(){
         return coe + "x" + exp;
         
     }
 }
 private PNode first;
 private PNode last;
 private int size = 0;
         public A1PolyLinkedListKC(){
         first = last = null;
     }
     public A1PolyLinkedListKC(int c, int e){
         PNode tempn = new PNode(c, e);
         first = last = tempn;
     }
     public void print(){
         if (first == null){
             System.out.println();
             return;
         }
         PNode temp = first;
         String ans = "";
         while (temp != null){
             if (temp.getCoe() > 0) {
                 if (temp != first) ans = ans + " + ";
                 ans = ans + temp.getCoe();
             }
             else if (temp.getCoe() < 0) ans = ans + " - " + temp.getCoe() * -1;
             if (temp.getExp() != 0){
                 ans = ans + "X^" + temp.getExp();
             }
             temp = temp.getNext();
         }
         System.out.println(ans);
     }
     public A1PolyLinkedListKC add(A1PolyLinkedListKC list){
         //add function combines two linkedlists into one. 
         A1PolyLinkedListKC temp = new A1PolyLinkedListKC(0,0);
         PNode p = this.first;
         PNode q = list.first;
         PNode t = temp.first;
         while(true){
            PNode tempNode = new PNode(0,0);    
            if(p==null || q == null){
                if(p==null && q==null) break;
                else if(p==null){
                    tempNode = q;
                    q = q.next;
                    }
                else {
                    tempNode = p;
                    p = p.next;
                    }
            }
            else if(p.exp == q.exp){
                tempNode.exp = p.exp;
                tempNode.coe = p.coe + p.coe;
                p = p.next;
                q = q.next;
            }
            else{
                //p.exp > q.exp
                if(p.exp > q.exp){
                    tempNode.exp = p.exp;
                    tempNode.coe = p.coe;
                    p = p.next;
                }
                else{
                    tempNode.exp = q.exp;
                    tempNode.coe = q.coe;
                    q = q.next;
                }
            }
            t.setNext(tempNode);
            t = t.getNext();
         }
         temp.first = temp.first.next;
         return temp;
     }
     public A1PolyLinkedListKC multiply(A1PolyLinkedListKC list) {
    	 A1PolyLinkedListKC temp = new A1PolyLinkedListKC();
         PNode p = this.first;
         PNode q = list.first;
         PNode t = null;
         PNode tempNode = new PNode(0,0);
         while(p!=null) {    	 
        	 while(q!=null) {
        		 tempNode.exp = p.exp + q.exp;
                 tempNode.coe = p.coe * q.coe;
                 if(size==0) {
                 	 temp.first = tempNode;
                 	 t=temp.first;
                 	 size++;
                 }
                 else {
                	 t.setNext(tempNode);
                	 t = t.next;
                	 size++;

                 }
                 tempNode = new PNode(0,0);
                 q=q.getNext();
        	 }
        	 q=list.first;
        	 p=p.getNext();
         }
         temp = sort(temp);
         return temp;
     }
    
	public A1PolyLinkedListKC sort(A1PolyLinkedListKC beforeSorting) {
		A1PolyLinkedListKC tempList = new A1PolyLinkedListKC();
		PNode p = beforeSorting.first;
        PNode q = p.next;
        PNode t = null;
		while(p!=null) {
            PNode tempNode = null;
            while(q!=null){
                PNode prevQ = p;
                if(p.getExp() < q.getExp()) {
                    tempNode = q;
                    if(p.next == null) prevQ.next=null;
                    else prevQ.next = q.next;
                    break;
                }
                else{
                    tempNode = p;
                    q = q.next;
                    prevQ = prevQ.next;
                }
            }
            if(tempList.first==null) {
                tempList.first = tempNode;
                t=tempList.first;
            }
            else{
                t.setNext(tempNode);
                t = t.next;
            }
            if(p.exp == tempNode.exp) {p=p.next;}
            q = p;
        }
        beforeSorting = tempList;
		return beforeSorting;
    }
    
}
