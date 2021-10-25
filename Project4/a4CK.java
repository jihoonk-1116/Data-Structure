import java.lang.reflect.Array;
import java.util.Random;

public class a4CK{
    /*-------------------------------quiz 1----------------------*/
   public static void heapSort(int[] arr){ //max-heap using bottom up alg
        int size = arr.length;
        for(int i = size/2 -1;i>=0;i--){
            maxHeapify(arr, size, i);
        }
        for (int i=size-1; i>0; i--){ 
            int temp = arr[0]; 
            arr[0] = arr[i]; 
            arr[i] = temp; 
            maxHeapify(arr, i, 0);  // call max heapify on the reduced heap 
        } 
   }
   private static void swap(int[] arr, int i, int j){
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
   }
   private static void maxHeapify(int [] arr, int n, int i) { 
        int largest = i; 
        int l = 2*i + 1; 
        int r = 2*i + 2; 
        if (l < n && arr[l] < arr[largest]) largest = l;  // If left child is larger than root 
        if (r < n && arr[r] < arr[largest]) largest = r;  // If right child is larger than largest 
        if (largest != i) {     // If largest is not root 
            swap(arr,i,largest);
            maxHeapify(arr, n, largest); 
        } 
    }
   /*Extra Credit:
   private static void quickSort(int[] a){
      quickSort(a, 0, a.length - 1);
   }
   private static void insertionSort(int[] a, int l, int h){
      for (int i = l + 1; i <= h; i++){
         int j = i;
         int v = a[i];
         while (j > l && v < a[j - 1]){
            a[j] = a[j - 1];
            j--;
         }
         a[j] = v;
      }
   }
   private static void quickSort(int[] a, int l, int h){ //Update this method to be more efficient
      if (l >= h) return;
      int pivotIdx = partition(a, l, h);
      quickSort(a, l, pivotIdx - 1); 
      quickSort(a, pivotIdx + 1, h);
   }
   public static int partition(int[] a, int l, int h){
      int len = (h - l) + 1;
      Random r = new Random();
      int idx = r.nextInt(len) + l;
      swap(a, idx, h);
      int pivot = a[h];
      int last = h;
      int first = l;
      h--;
      while (l < h){
         while(a[l] < pivot) l++;
         while(h > first && a[h] >= pivot) h--;
         if (l < h) swap(a, l, h);
	     else swap(a, l, last);
      }
      return l;
   }
   */
   public static void main(String[] args){
      long time, nextTime;
      int[] a = new int[60];//heapsort
      System.out.println("heapSort: ");
      Random r = new Random();
      for (int i = 0; i < a.length; i++) a[i] = r.nextInt(100);
      for (int s = 0; s < 3; s++) {
        /*
        if (s == 2){
            Extra Credit:
      		System.out.println("quickSort: ");
      		time = System.nanoTime();
      		quickSort(a);
      		nextTime = System.nanoTime();
      			
      	}
      	else */ {
      		time = System.nanoTime();
      		heapSort(a);
      		nextTime = System.nanoTime();
      	}
      	System.out.println("\tTime used: " + (nextTime - time) + " nseconds");
      	for (int i = 0; i < a.length; i++) System.out.print(a[i] + ",");
      	System.out.println();
      	for (int i = 0; i < a.length; i++) a[i] = r.nextInt(100);
      }

      MinHeapPriorityQueue<Integer> minHeap = new MinHeapPriorityQueue<>(a.length + 1);
      for (int i = 0; i < a.length; ++i){
         minHeap.add(a[i]);
      }
      minHeap.print();
      System.out.print("Remove:");
      System.out.println(minHeap.removeThirdMin() + ".");
      minHeap.print();
   }

}
class MinHeapPriorityQueue<E extends Comparable<? super E>>{
   private E data[];
   private int size;
   public MinHeapPriorityQueue(){
      this(20);
   }
   @SuppressWarnings("unchecked")
   public MinHeapPriorityQueue(int cap){
      size = 0;
      data = (E[]) new Comparable[cap];
   }
   
   public boolean add(E d){
      if (size >= data.length - 1) return false;
      data[++size] = d;
      bubbleUp(size);
      return true;
   }
   public E remove(){
      if (size <= 0) return null;
      E ans = data[1];
      data[1] = data[size--];
      bubbleDown(1);
      return ans;
   }
   
   private void swap(int i, int j){
      E temp = data[i];
      data[i] = data[j];
      data[j] = temp;
   }
   
   private void swap2(E[] arr, int i, int j){
      E temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
   }
   
   private void bubbleUp(int i){ //Insert
      if (i <= 1) return;
      if (data[i].compareTo(data[i / 2]) >= 0) return;
      swap(i, i / 2);
      bubbleUp(i / 2);
   }
   
   private void bubbleUp(E[] arr, int i){ //Insert
      if (i <= 1) return;
      if (arr[i].compareTo(arr[i / 2]) >= 0) return;
      swap2(arr,i, i / 2);
      bubbleUp(arr,i / 2);
   }
   
   private void bubbleDown(int i){ //Extract
      if ((i * 2) > size) return;
      int min_i = i * 2;
      if (min_i + 1 <= size) {
         if (data[min_i].compareTo(data[min_i + 1]) > 0)
	    min_i += 1;
      }
      if (data[i].compareTo(data[min_i]) <= 0) return;
      swap(i, min_i);
      bubbleDown(min_i);
  }
  
  private void bubbleDown(E[] arr, int i){ //Extract
   if ((i * 2) > arr.length) return;
   int min_i = i * 2;
   if (min_i + 1 <= arr.length) {
      if (arr[min_i].compareTo(arr[min_i + 1]) > 0)
    min_i += 1;
   }
   if (arr[i].compareTo(arr[min_i]) <= 0) return;
   swap2(arr,i, min_i);
   bubbleDown(arr, min_i);
}
/*-------------------------------quiz 2----------------------*/
public E removeThirdMin(){ 
   int count = 1;
   int pos=0;
   E min = data[1];
   for (int i=1; i<size; i++){ 
      if(min.compareTo(data[i])<0) {
         min = data[i];
         count++;
      }
      if(min.compareTo(data[i])>0){
         count++;
      }
      if(count == 3) {
         if(data[i+1]!=null && data[i+1].compareTo(min)<0) {
            min = data[i+1];
            i=i+1;
         }
         if(data[i+2]!=null && data[i+2].compareTo(min)<0) {
            count++;
            min = data[i+2];
            i=i+2;
         }
         pos = i;
         break;
      }
   }
   for(int i=1;i<size;i++){
      if(min.compareTo(data[i]) ==0) { //remove the values
         swap(i,size);
         size--;
         bubbleDown(i); 
      }
      bubbleDown(1);
   }
  return min;
} 



  public void print(){
     for (int i = 1; i <= size; i++)
        System.out.print(data[i] + ",");
     System.out.println();
  }   

}
