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

}
