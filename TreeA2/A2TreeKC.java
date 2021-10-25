package TreeA2;

import java.util.*;


public class A2TreeKC{
   
   public static void main(String argc[]){
      for(int i=0;i<10;i++){
         for(int j=0;j<i;j++){
             System.out.print("*");
         }
         System.out.println();
      }
      ExprTree et = new ExprTree();
      et.inToTree("(3+(4*5))");
      System.out.print("Print1: ");
      et.print();
      et.inToTree("(((1+2)*2)-((1-2)*(2+4)))");
      System.out.print("\nPrint2: ");
      et.print();
      et.inToTree("(3(4+5)*)");
      System.out.print("\nPrint3: ");
      et.print();
      et.longestZipZag(et.root);
      
   }

}  
class ExprTree{
   private static class BTNode{
      char value;
      BTNode parent, left, right;
      public BTNode(char e){
        this(e, null, null, null);
      }
      public BTNode(char e, BTNode p, BTNode l, BTNode r){
	      value = e;
	      parent = p;
	      left = l;
	      right = r;
      }
   }
   BTNode root;
   public ExprTree(){
      root = null;
   }
   public void inToTree(String ex){
      root = parseIntoTree(ex);
   }
   public BTNode parseIntoTree(String exp){
      if(exp.length() == 1 && Character.isDigit(exp.charAt(0))) return new BTNode(exp.charAt(0));

      if(exp.length()<5 || exp.charAt(0) != '(' && exp.charAt(exp.length()-1) != ')') return null;

      exp = exp.substring(1, exp.length()-1);
      String op = "+-*/%";

      Deque<Character> stack = new ArrayDeque<Character>();

      int ansidx = -1;
      for(int i =0;i<exp.length();i++){
         char c = exp.charAt(i);
         if(c=='(') stack.addLast(c);
         else if(c==')')
            if(stack.isEmpty()) return null;
            else stack.removeLast();
         else if(stack.isEmpty() && op.contains(Character.toString(c)))
            if(ansidx == -1) ansidx = i;
            else return null;
      }
      if(ansidx<=0||ansidx == exp.length()-1 || !stack.isEmpty()) return null;

      BTNode l = parseIntoTree(exp.substring(0,ansidx));
      BTNode r = parseIntoTree(exp.substring(ansidx +1));

      if(l==null || r==null) return null;

      BTNode ans = new BTNode(exp.charAt(ansidx), null,l,r);
      l.parent = r.parent = ans;

      return ans;
   }
   
   public ExprTree(String expression){ 
      char [] postFix = convertPost(expression);
      constTree(postFix);
   }
   private char[] convertPost(String expression) { //convert to post order without parenthsis
      Stack<Character> st = new Stack<Character>();
      char [] charArray = expression.toCharArray();
      char [] postOrder= new char[charArray.length];
      int count =0;
      int j=0;
      for(int i =0;i<charArray.length;i++){
         if(isNum(charArray[i])==true){ //number
            postOrder[j] = charArray[i];
            count++;
            j++;
         }
         if(isOp(charArray[i])==true){ //operator
            st.push(charArray[i]);
         }
         if(count == 2){              //pop
            postOrder[j] = st.pop();
            j++;
            count = 0;
         }
      }
      j=0;
      while(st.empty() != true){
         if(postOrder[j]==0) postOrder[j] = st.pop();
         j++;
      }
      st.clear();
      return postOrder;
   }
   public void constTree(char [] ex){  
      Stack<BTNode> st = new Stack<BTNode>(); //Stack for tracking operation
      char [] postFix = new char[ex.length];//convert to Post-fix 
      postFix = ex;
      BTNode node;
      for(int i =0; i<postFix.length;i++){
         if(isNum(postFix[i]) == true){ //operend
            node = new BTNode(postFix[i]);
            st.push(node); //push
         }
         else if(isNum(postFix[i])==false){ //operator
            BTNode temp = st.pop();  //pop
            BTNode temp2 = st.pop();
            node = new BTNode(postFix[i]);
            node.left = temp2;
            node.right = temp;
            st.push(node);
         }
      }
      this.root = st.pop();
      st.clear();
   }
   /*
   public void inToTree(String expression){ 
      Stack<BTNode> st = new Stack<BTNode>(); //Stack for tracking operation and parenthsis.
      char [] postFix = convertToPost(expression); //convert to Post-fix 
      BTNode node;
      for(int i =0; i<postFix.length;i++){
         if(isNum(postFix[i]) == true){ //operend
            node = new BTNode(postFix[i]);
            st.push(node); //push
         }
         else if(isNum(postFix[i])==false){ //operator
            BTNode temp = st.pop();  //pop
            BTNode temp2 = st.pop();
            node = new BTNode(postFix[i]);
            node.left = temp2;
            node.right = temp;
            st.push(node);
         }
      }
      this.root = st.pop();
      st.clear();
   }
   */
   private char[] convertToPost(String expression) { //re-arrangement for post-ordering using parenthsis
      Deque<Character> dq = new ArrayDeque<Character>(); //Deque for tracking operation and parenthsis.
      char[] charArray = expression.toCharArray();
      char[] postFix = new char[expression.length()];
      int j =0;
      for(int i =0; i<charArray.length; i++){
         if(isNum(charArray[i]) == true){ //operend
            postFix[j] = charArray[i];
            j++;
         }
         else if(charArray[i] == '('){   //left parenthsis
            dq.addFirst(charArray[i]);
         }
         else if(isOp(charArray[i]) == true){  //operator
            dq.addFirst(charArray[i]);
         }
         else if(charArray[i] == ')'){ //right parenthsis
            while(dq.peekFirst() != '('){
               if(isOp(dq.peekFirst())==true){ 
               postFix[j] = dq.removeFirst();
               j++;
               }
               else dq.removeFirst();  //remove corresponding left parenthsis with the right one.
            }
            dq.removeFirst();
         }
      }
      dq.clear();
      postFix = reduceSize(postFix); //adjust size as the number of parenthsis
      return postFix;
   }
   public int longestZipZag(BTNode n){
      if(this.isLeaf(n)) return 0;
      int count=0;
      System.out.println(n.left);
      int leftcount = moveNode(n.left,count);
      int rightcount = moveNode(n.right,count);
      System.out.println("Count: " +leftcount+ " ," +rightcount);
      return Math.max(leftcount,rightcount);
   }
   public int moveNode(BTNode n,int c){
      if(n.left==null && n.right==null) return 0;
      if(n.left!=null && n.parent.right == n){
         System.out.println("In left move: "+ n.value);
         c+= 1 + moveNode(n.left, c);
      }
      if(n.right!=null && n.parent.left == n)
      {
         System.out.println("In right Move: "+n.value);
         c+= 1 + moveNode(n.right,c);
      }
      return c;
   }
   
   public void print(){ 
      if(root == null) {
         return;
      }
      inOrderPrint(root);
   }
   public void inOrderPrint(BTNode bn){  
      if(bn == null) {
         return;
      }
      if(isNum(bn.value) == false) System.out.print("("); //print a parenthsis using isNum method
      inOrderPrint(bn.left);
      System.out.print(bn.value);
      inOrderPrint(bn.right);
      if(isNum(bn.value) == false) System.out.print(")");
   }
   
   /*
   public char[] reduceSize(char[] cA){
      int size =0;
      for(int i= 0; i<cA.length;i++){
         if(cA[i]==0){
            break;
         }
         size++;
      }
      char [] temp = new char[size];
      for(int i =0;i<size;i++){
         temp[i] = cA[i];
      }
      cA = temp;
      return cA;
   }
   */
   public boolean isEmpty(){
      return root == null;
   }
   public boolean isNum(char c){
      if(c =='+' || c =='-' || c == '*' || c=='/' || c=='(' || c==')') 
         return false;
      return true; 
   }
   public boolean isOp(char c){
      if(c =='+' || c =='-' || c == '*' || c=='/') 
         return true;
      return false; 
   }
   public boolean isLeaf(BTNode n){ 
      if(n==null) return false;
      return n.left==null && n.right == null;
   }
}

class Tree{
   private static class TNode{
      private int value;
      private TNode parent;
      private List<TNode> children;
      public TNode(){
         this(0, null);
      }
      public TNode(int e){
         this(e, null);
      }
      public TNode(int e, TNode p){
         value = e;
         parent = p;
         children = new ArrayList<TNode>();
      }
   }
   private TNode root;
   private int size;
   Tree(){
      root = null;
      size = 0;
   }
    public TNode createNode(int e, TNode p){
       return new TNode(e, p);
    }
    public TNode addChild(TNode n, int e){
       TNode temp = createNode(e, n);
       n.children.add(temp);
       size++;
       return temp;
    }
    public void makeTree(){
       root = createNode(0, null);
       size++;
       buildTree(root, 3);
    }
    public void makeTree2(){
       root = createNode(0, null); 
       size++; 
       buildTree(root, 1);
    }
    private void buildTree(TNode n, int i){
       if (i <= 0) return;
       TNode fc = addChild(n, size);
       TNode sc = addChild(n, size);
       TNode tc = addChild(n, size);
       buildTree(fc, i - 1);
       buildTree(sc, i - 2);
       if (i % 2 == 0)
          buildTree(tc, i - 1);
   }
   public void removeEven(List<TNode> list){
      for(int i :list){
         if(i%2==0) list.remove(i);
      }
   }
   //Quiz#3 code ********************
   public void levelOrder(){  
      Queue<TNode> q = new LinkedList<TNode>();
      q.add(this.root);
      while(!q.isEmpty()){
         TNode temp = q.poll();
         if(temp.children != null){
            for(int i =0;i<temp.children.size();i++){
               q.add(temp.children.get(i));
            }
         }
         System.out.print(temp.value+" ");
      }
   }
}
