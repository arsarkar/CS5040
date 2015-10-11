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
			return recursivePartition(A, p, r, i, j++);
		}
	}
	
	private static int[] swap(int[] A, int m, int n){
		int p = A[m];
		A[m] = A[n];
		A[n] = p;
		return A;
	}
	
}
