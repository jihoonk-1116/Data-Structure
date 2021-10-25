import java.lang.Math;
import java.lang.reflect.Array;
import java.io.*;
import java.util.*;


public class A3{
   public static List<String> readInFile(String filename){
      List<String> input = new ArrayList<>();
      try (Scanner sin = new Scanner(new FileReader(filename))){
         while (sin.hasNextLine()){
	         input.add(sin.nextLine());
         }
      } catch (FileNotFoundException e){
	      e.printStackTrace();
      }
      return input;
  }
  //-----------------------------------------Problem 4 ------------------------------/
  //Detect zero sum searching absolute values which are equal with HashSet. 
    private static int sumZero(int[] num) { 
      HashSet<Integer> numset = new HashSet<Integer>();
      int ans = 0;
      for(int i=0;i<num.length;i++){ //for O(n)
         if(num[i]<0)                //If a number is negative
            num[i] *= -1;            //Convert positive number.
         if(numset.contains(num[i])){ //Search O(1), If the number is already in the set
            ans = num[i];             //Store it at ans.
         }
         else
            numset.add(num[i]);      //add O(1)
      }
      for(int i =0;i<num.length;i++){ //Search the first index of the number. 
         //if(ans==0) return -1;
         if(num[i]<0) num[i]*=-1;
         if(ans==num[i])   
            return i;
      }
      return -1;                    
    }
    //----------------------------------------------Problem 3 ----------------------/
    private static void printDuplicates(List<String> names) { 
      HashSet<String> hs = new HashSet<String>(); 
      for(int i =0;i<names.size();i++){  //O(N)
         hs.add(names.get(i)); //addition of HashSet and getting of ArrayList O(1)
      }
      System.out.println("Problem3: " + hs);
    }
    //----------------------------------------------Problem2 ----------------------/
    //-----------------------Treemap using Chaining method to handle duplicate keys
    public static void printByNumChars(List<String> n){ 
      TreeMap<Integer,List<String>> tMap = new TreeMap<Integer,List<String>>(); //K, V
      for(int i =0;i<n.size();i++){ // O(n)
         if(tMap.isEmpty()||tMap.containsKey(n.get(i).length())==false){ //If this tree is empty or adds new key //Search O(logN)
            List<String> temp = new ArrayList<String>();
            temp.add(n.get(i));             //get method O(1)
            tMap.put(n.get(i).length(),temp); //put O(logN)
            //System.out.println("Test: "+tMap);
         }
         else{  //If the key already exists, store it using Chaining method. 
            tMap.get(n.get(i).length()).add(n.get(i)); //get O(logN), 
         }
      }
      System.out.println("Problem2: "+ tMap.values()); 
   }
    
    public static void main(String[] args){
      List<String> names = readInFile("input.txt");
      printByNumChars(names);
      
      names = readInFile("input.txt");
      printDuplicates(names);
      
      int[] num = {24,4,-243,-345,246,-45,-34,23,346,65,75,-73,-3,54,63,-35,-23,86,-4,29};
      int i = sumZero(num);
      if (i != -1)System.out.println("Problem 4: Pair value is " + num[i]);
      
      AVLtree<Integer> tr = new AVLtree<Integer>();
      tr.add(4);
      tr.add(5);
      tr.print();
      System.out.println("Tree Height:" + tr.height());//1
      tr.print();
      tr.add(6);
      System.out.println("Tree Height:" + tr.height());//1
      tr.print();
      tr.add(1);
      System.out.println("Tree Height:" + tr.height());//2
      tr.print();
      tr.add(2);
      System.out.println("Tree Height:" + tr.height());//2
      tr.print();
      tr.add(0);
      System.out.println("Tree Height:" + tr.height());//2
      System.out.println("Tree Height:" + tr.height());//3
      tr.print();
      //tr.print2();//2, 1, 0, 5, 4, 3, 6,
      System.out.println("Bonus Problem - Longest Zipzag:"+ tr.longestZipZag(tr.getRoot()));

   }
}
class AVLtree<E extends Comparable<? super E>>{
   private static class AVLNode<E extends Comparable<? super E>>{
      private E element;
      private AVLNode<E> parent;
      private AVLNode<E> left;
      private AVLNode<E> right;
      int height;
      public AVLNode(E e){
	  this(e, null, null);
     }
      public AVLNode(E e, AVLNode<E> l, AVLNode<E> r){
	 	element = e;
	 	left = l;
	 	right = r;
         height = 0;
         if (l != null && r != null) height = Math.max(l.height, r.height) + 1;
         else if (r != null) height = r.height + 1;
         else if (l != null) height = l.height + 1;
      }
      public E getE(){
	 return element;
      }
      public AVLNode<E> getParent(){
	 return parent;
      }
      public AVLNode<E> getLeft(){
	 return left;
      }
      public AVLNode<E> getRight(){
	 return right;
      }
      public int getHeight(){
	 return height;
      }
      public void setE(E e){
	 element = e;
      }
      public void setParent(AVLNode<E> p){
	 parent = p;
      }
      public void setLeft(AVLNode<E> l){
	 left = l;
      }
      public void setRight(AVLNode<E> r){
	 right = r;
      }
      public void setHeight(int h){
	 height = h;
      }
   }
   private AVLNode<E> root;
   private int size;
   public AVLtree(){
      root = null;
      size = -1;
   }
   private AVLNode<E> balance(AVLNode<E> n){
      if (n == null || isLeaf(n))
         return n;
      int bFactor = height(n.left) - height(n.right);
      if (bFactor > 1){
         if (height(n.left.left) >= height(n.left.right))
            n = rotateWithLeftChild(n);
         else
            n = doubleRotateLeftChild(n);
      }
      else if (bFactor < -1){
         if (height(n.right.right) >= height(n.right.left))
	    n = rotateWithRightChild(n);
         else
	    n = doubleRotateRightChild(n);
      }
      return n;
   }
   public int height(){
      return height(root);
   }
   private int height(AVLNode<E> n){
      if (n == null) return -1;
      return n.height;
   }
   private boolean isLeaf(AVLNode<E> n){
      if (n != null || n.left != null || n.right != null) return false;
      return true;
   }
   /* ----------------------------------------QUESTION #1-----------------------*/
   private AVLNode<E> rotateWithLeftChild(AVLNode<E> n1){
      AVLNode<E> n2 = n1.left; 
      n1.left = n2.right;            //Assign the n2's right child to the n1's left.
      n2.right =  n1;                //n2 become a new parent node.
      n1.height =  Math.max(height(n1.right), height(n1.left)) + 1; //Adjust their heights.
      n2.height = Math.max(height(n2.right), height(n2.left)) + 1;
      if(n1.getParent() == null)     //Unless the n1's parent exist, 
        n2 = root;                   //n2 become the new root of the tree
      else if(n1 == n1.parent.left) {//Judge whether n1 was left child or right. 
        n1.parent.left = n2;
      }else{
        n1.parent.right = n2;
      }
      n2.setParent(n1.getParent()); //Set n2's parent to n1's parent(previous parent node).
      n1.setParent(n2);             //Set n1's parent to n2(new parent node)
      if (n1.left != null)          //If there is a left child,
        n1.left.setParent(n1);      //Assign n1 as the new left child's parent. 
      return n2;                    //return a new node.
  }
  /*-----------------------------------------Bonus Problem---------------*/ 
  public int longestZipZag(AVLNode<E> n){
   if(isLeaf(n)) return 0;                   //If the node has no children, return 0.
   int count=0;
   int leftcount = moveNode(n.left,count);   //Count left side at first.
   int rightcount = moveNode(n.right,count); //Count right side at second.
   return Math.max(leftcount,rightcount);    //Compare the left side and the right.
}
public int moveNode(AVLNode<E> n,int c){  //Count path using recursion
   if(n==null) return 0;                  //Base case
   if(n.parent.right == n){               //If the node moved the right before
      c+= 1 + moveNode(n.left, c);        //Go to left , recursion
      System.out.println("Left Move: " + n.getE());  //Tracking
   }
   else if(n.parent.left == n){           //If the node moved the left before
      c+= 1 + moveNode(n.right,c);        //Go to right , recursion
      System.out.println("Right Move: " + n.getE()); 
   }
   return c;                              //Return count.
}
   private AVLNode<E> rotateWithRightChild(AVLNode<E> n1){
      AVLNode<E> n2 = n1.right;
      n1.right = n2.left;
      n2.left =  n1;
      n1.height =  Math.max(height(n1.left), height(n1.right)) + 1;
      n2.height = Math.max(height(n2.left), height(n2.right)) + 1;
      n2.setParent(n1.getParent());
      n1.setParent(n2);
      if (n1.right != null)
         n1.right.setParent(n1);
      return n2;
  }
   private AVLNode<E> doubleRotateLeftChild(AVLNode<E> n1){
      n1.left = rotateWithRightChild(n1.left);
      return rotateWithLeftChild(n1);
   }
   private AVLNode<E> doubleRotateRightChild(AVLNode<E> n1){
      n1.right = rotateWithLeftChild(n1.right);
      return rotateWithRightChild(n1);
   }

   public void add(E e){
      root = add(e, root);
      root.setParent(null);
   }
   private AVLNode<E> add(E e, AVLNode<E> n){
      if (n == null){
         size++;
         return  new AVLNode <E>(e);
      }
      int compare = e.compareTo(n.getE());
      if (compare < 0){
	 	n.left = add(e, n.left);
	 	n.left.setParent(n);
      }
      else if (compare > 0){
	 	n.right = add(e, n.right);
	 	n.right.setParent(n);
      }
      n = balance(n); 
      return n;
   }
   public AVLNode<E> getRoot(){
      return root;
   }
   public AVLNode<E> findMin(AVLNode<E> n){
      if (n == null) return n;
      while (n.left != null) n = n.left;
      return n;
   }
   public int size(){ return size;}
   public void print(){
         System.out.println("Total: " + size);
	 	 inOrder(root);
         System.out.println();
   }
   public void print2(){
         System.out.println("Total: " + size);
	 	 preOrder(root);
         System.out.println();
   }
   public void inOrder(AVLNode<E> n){
         if (n == null) return;
         inOrder(n.left);
         System.out.print(n.getE() + ", ");
         inOrder(n.right);
   }
   public void preOrder(AVLNode<E> n){
        if (n == null) return;
        System.out.print(n.getE() + ", " );
 		  preOrder(n.left);
        preOrder(n.right);
 }
}