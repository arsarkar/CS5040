package course.algorithm;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Unit test for simple App.
 */
public class AppTest 
    
{

	static int inputArray[] = {23, 34,3, 47, 100, 23, 45, 123, 87, 36, 55, 46, 67, 101, 123};
	static int outputArray[] = {3, 23, 23, 34, 36, 45, 46, 47, 55, 67, 87, 100, 101, 123, 123};

	@Test
	public void testIntertionSort(){
		assertArrayEquals(outputArray, SortAlgorithms.insertionSort(inputArray));
	}
	
	@Test
	public void testIntertionSortRV(){
		assertArrayEquals(outputArray, SortAlgorithms.insertionSortRV(inputArray));
	}
	
	@Test
	public void testQuickSort(){
		assertArrayEquals(outputArray, SortAlgorithms.quickSortPureRecusion(inputArray, 0, inputArray.length-1));
	}
	
	@Test
	public void testIsMaxHeap(){
		int[] A = new int[]{16,14,10,8,7,9,3,2,4,1};
		assertTrue(SortAlgorithms.isMaxHeap(A));
	}
	
	@Test
	public void testMaxHeapify(){
		int[] A = new int[]{16,4,10,14,7,9,3,2,8,1};
		int[] B = new int[]{16,14,10,8,7,9,3,2,4,1};
		assertArrayEquals(B, SortAlgorithms.maxHeapify(A, 1));
	}
}
