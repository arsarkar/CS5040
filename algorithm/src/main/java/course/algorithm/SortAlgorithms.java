package course.algorithm;
import java.io.Console;
import java.util.Arrays;


public class SortAlgorithms {

	
	
	
	/*
	 * This is the textbook version of insertionSort
	 */
	public static int[] insertionSort(int[] a){
		for(int j=1; j<a.length; j++){
			int key = a[j];
			int i = j-1;
			while(i>=0 && a[i]>key){
				a[i+1] = a[i];
				i--;
			}
			a[i+1]=key;
		}
		return a;
	}
	
	/*
	 * This is the Romanian Dance version of insertionSort
	 */
	public static int[] insertionSortRV(int[] a){
		for(int i=0; i<a.length-1; i++){
			for(int j=i+1;j>0; j--){
				if(a[j] < a[j-1]){
					int key = a[j];
					a[j]=a[j-1];
					a[j-1]=key; 
				}
				else
					break;
			}
		}
		return a;
	}
	
	/**
	 * MergeSort TextBook Version
	 * @param A
	 * @param l
	 * @param r
	 */
	public void mergeSort(int[] A, int l, int r) {

        if (l < r) {

            //split the array into 2
            int q = (l + r) / 2;

            //sort the left and right array
            mergeSort(A, l, q);
            mergeSort(A, q + 1, r);

            //merge the result
            merge(A, l, q + 1, r);
        }
    }
	
	/**
	 * Textbook merge method
	 * @param A
	 * @param l
	 * @param q
	 * @param r
	 */
	private void merge(int[] A, int l,
            int q, int r) {

        int leftArrayEnd = q - 1;

        int numElements = r - l + 1;
        int[] resultArray = new int[numElements];
        int resultArrayBegin = 0;

        // Find the smallest element in both these array and add it to the result
        // array i.e you may have a array of the form [1,5] [2,4]
        // We need to sort the above as [1,2,4,5]
        while (l <= leftArrayEnd && q <= r) {
            if (A[l] <= A[q]) {
                resultArray[resultArrayBegin++] = A[l++];
            } else {
                resultArray[resultArrayBegin++] = A[q++];
            }
        }

        // After the main loop completed we may have few more elements in
        // left array copy them.
        while (l <= leftArrayEnd) {
            resultArray[resultArrayBegin++] = A[l++];
        }

        // After the main loop completed we may have few more elements in
        // right array copy.
        while (q <= r) {
            resultArray[resultArrayBegin++] = A[q++];
        }

        // Copy resultArray back to the main array
        for (int i = numElements - 1; i >= 0; i--, r--) {
            A[r] = resultArray[i];
        }
    }
	
	
	/*
	 * This is a recursive version of quicksort
	 */
	public static int[] quickSortPureRecusion(int[] A, int p, int r){
		if(p<r){
			int q = recursivePartition(A, p, r, p-1, p);
			quickSortPureRecusion(A, p, q-1);
			quickSortPureRecusion(A, q+1, r);
		}
		return A;
	}
	
	/**
	 * 
	 * @param A input array
	 * @param p starting position
	 * @param r ending position
	 * @param i A[k] <= A[r] if k<i
	 * @param j A[k] > A[r] if i<=k<j 
	 * @return
	 */
	private static int recursivePartition(int[] A, int p, int r, int i, int j){
		//termination condition
		if(j==r){
			A = swap(A, r, i+1);
			return i+1;
		}
		//recursion call
		else{
			if(A[j] <= A[r]){
				swap(A, j, i+1);
				i++;
			}
			return recursivePartition(A, p, r, i, ++j);
		}
	}
	
	private static int[] swap(int[] A, int m, int n){
		int p = A[m];
		A[m] = A[n];
		A[n] = p;
		return A;
	}
	
	
	
	/*
	 **************************************************************************************
	 * Heap Sort
	 **************************************************************************************
	 */
	 public static boolean isMaxHeap(int[] A){
		 for(int i=0; i<A.length; i++){
			int p = (int) Math.floor(i/2);
			if (p < i){
				if(A[p] < A[i]){
					return false;
				}
			}
		 }
		 return true;
	 }
	
	/**
	 * Ma-Heapify will return the array A in which max-heap will be ensured for A[i] as root
	 * @param A A[i] may or may not be max-heap
	 * @param i assuming right sub-tree and left sub-tree of A[i] is max-heap
	 */
	public static int[] maxHeapify(int[] A, int i){
		i++;
		int largest = 2*i;
		if((2*i)<A.length && (2*i+1)<A.length){
			if (A[2*i]<A[2*i+1]){
				largest = 2*i+1;
			}
			if(A[largest] > A[i]){
				swap(A, i, largest);
				maxHeapify(A, largest);
			}
		}
		return A;
	}
	
	
	
}
