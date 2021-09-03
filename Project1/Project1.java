/*
CS313 - section37
Chihoon Kim
Student# 23694627
*/
package DSproject;

public class Project1{
    public static void main(String argc[]){
        
     A1LinkedListKC<Integer> sl = new A1LinkedListKC<>();
     A1DLinkedListKC<String> dl = new A1DLinkedListKC<>();
     A1PolyLinkedListKC sum, prod;
     for (int i = 1000; i > 0; i-=3) sl.add(i);
     
     try {
         sl.insert(111, sl.getNode(50), sl.getNode(51));
         if (sl.detectLoop()) System.out.println("Loop!");
         else System.out.println("No loop.");
     
     
         sl.insert(123, sl.getNode(51), sl.getNode(50));
         if (sl.detectLoop()) System.out.println("Loop!");
         else System.out.println("No loop.");
     }
     catch(Exception e){
         e.printStackTrace();
     }
     
     dl.add("Three",0);
     dl.add("Five",1);
     dl.add("One",0);
     dl.add("Two",1);
     dl.add("Four",3);
     dl.print(); 
    
     A1PolyLinkedListKC p1 = new A1PolyLinkedListKC(2,3);
     A1PolyLinkedListKC p2 = new A1PolyLinkedListKC(3,2);
     A1PolyLinkedListKC p3 = p1.add(p2); 
     p1 = new A1PolyLinkedListKC(3,2);
     p2 = new A1PolyLinkedListKC(1,0);
     A1PolyLinkedListKC p4 = p1.add(p2);
     sum = p3.add(p4);
     p3.print();
     p4.print();
     prod = p3.multiply(p4);
     sum.print();
     System.out.println("Prod: ");
     prod.print();

 }
}