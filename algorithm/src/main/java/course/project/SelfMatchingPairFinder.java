package course.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Problem 1: Optimal self matching problem
 * 
 * @author sarkara1
 *
 * This program reads a text file with symbols written separated by space and print the optimal cost matrix
 * result matrix and self matching pairs found
 *  
 */
public class SelfMatchingPairFinder {

	public static void main(String[] args) {
		
		//store the arguments in an array of strings
		String[] testFiles = args;
		SelfMatchingPairFinder finder = new SelfMatchingPairFinder();
		for(int i=0;i<args.length;i++){
			System.out.println("Unit Test "+(i+1)+" Case File : "+testFiles[i]);
			try {
				//create File Reader to read the file
				FileReader inputFile = new FileReader(testFiles[i]);
				//Instantiate the BufferedReader Class
		        BufferedReader bufferReader = new BufferedReader(inputFile);
		        // Read file line by line and print on the console
		        String line;
		        while ((line = bufferReader.readLine()) != null)   {
		            String[] x = line.split(" ");
		            finder.printArray(x);
		            finder.findLargestSelfMatchingSet(x);
		        }  
		        bufferReader.close();
		        inputFile.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * method to find the optimal self matching pair
	 * @param x array of strings
	 * @return size of optimal self matching pair set
	 */
	public int findLargestSelfMatchingSet(String[] x){
		int noValue = -1;
		//store the length of the sequence 
		int xLength = x.length;
		//declare the optimal recursive structure as nXn matrix where n = x.length
		int[][] c = new int[xLength][xLength];
		//declare an auxiliary matrix to denote which pairs are chosen
		String[][] solution = new String[xLength][xLength]; 
		
		
		//initialize the matrix
		for(int i=0; i<xLength; i++){
			for(int j=0; j<xLength; j++){
				c[i][j]= 0;
				solution[i][j]="#";
			}
		}
		
		//if i=l then c[i,l] = 0
		for(int i=0; i<xLength; i++){
			for(int j=0; j<xLength; j++){
				if(i==j){
					c[i][j]= 0;
				}
			}
		}
		
		//traverse the cost matrix diagonally
		for(int r=0; r<xLength; r++){
			int m = xLength - r;
			for(int s=0; s<m; s++){
				int i = s;
				int l = s+r;
				if(i<l){
					//when a pair can be formed with both index add 1 along with along with all other 
					//pairs could possibly made from elements from index i+1 and l-1.
					if(x[i].equals(x[l])){
						c[i][l] = c[i+1][l-1]+1;
						//mark the solution that this pair is added
						solution[i][l] = "+"; 
					}
					else{
						//else first calculate the leftmost pair of xi and the rightmost pair of xl.
						int j = findPairOfLeftIndex(x, i, l);
						int k = findPairOfRightIndex(x, i, l);
						//if (i,j) and (k,l) are crossing pair
						if((i<k) && (k<=j) && (j<l)){
							//if (i,j) is chosen
							int c1;
							if(i==j){
								c1=c[j+1][l]+1;
							}
							else{
								c1=c[i+1][j-1]+c[j+1][l]+1;
							}
							
							//if (k,l) is chosen
							int c2;
							if(k==l){
								c2=c[i][k-1]+1;
							}
							else{
								c2=c[i][k-1]+c[k+1][l-1]+1;
							} 
							//if none of the pairs are chosen
							int c3 = c[i+1][l-1];
							
							//if (i.j) is chosen
							if(c1>=c2 && c1>=c3){
								c[i][l] = c1;
								if (i != j)
									solution[i][l] = j+","+noValue;
							}
							//if (k,l) is chosen
							else if(c2>=c1 && c2>=c3){
								c[i][l] = c2;
								if (k != l)
									solution[i][l] = noValue+","+k;
							}
							//if none is chosen just skip both elements 
							else{
								c[i][l] = c3; 
								solution[i][l] = "SW";
							}
						}
						//if (i,j) and (k,l) are self matching pairs
						else{	
							//if both pairs are chosen
							int c1;
							// case 1 if both pairs don't exist just skip the elements
							if((i==j)&&(k==l)){
								c1 = c[i+1][l-1];
							}
							//case 2: if left pair is a self pair then only choose right pair
							else if((i==j)&&(k<l)){
								c1 = c[i+1][k-1]+c[k+1][l-1]+1;
							}
							//case 3: if right pair is a self pair then only choose left pair
							else if((i<j)&&(k==l)){
								c1 = c[i+1][j-1]+c[j+1][l-1]+1;
							}	
							// if both of them exist add both 
							else{
								c1 = c[i+1][j-1]+c[j+1][k-1]+c[k+1][l-1]+2;
							}
							int c2 = c[i+1][l-1];
							
							if(c1>=c2){
								c[i][l] = c1;
								if((i==j)&&(k<l)){
									solution[i][l] = noValue+","+k;
								}
								//case 3: if right pair is a self pair then only choose left pair
								else if((i<j)&&(k==l)){
									solution[i][l] = j+","+noValue;
								}
								else if ((i<j) && (k<l) ){
									solution[i][l] = j+","+k;
								}
								else{
									solution[i][l] = "SW";
								}
							}
							else{
								c[i][l] = c2;
								solution[i][l] = "SW";
							}
						}
					}
				}
				//This will initialize the lower half of the diagonal with 0
				else{
					c[i][l]= 0;
				} 
				
			}
		}

		System.out.println("Cost Matrix:");
		printMatrixInt(c);
		
		System.out.println("Reconstruction table:");
		printMatrixString(solution);
		
		//parse solution and print out result
		int setSize = c[0][xLength-1];
		System.out.println("Optimal self matching set size:: " + c[0][xLength-1]);
		int i= 0, j = xLength-1;
		parseSolution(solution, i, j);
		return c[0][xLength-1];
		
	}
	
	/**
	 * parses the solution matrix to identify which pairs are added to the optimal set
	 * @param solution
	 * @param i
	 * @param j
	 */
	private void parseSolution(String[][] solution, int i, int l){
		if (solution[i][l].equals("+")){
			System.out.println("("+i+","+l+")");
			parseSolution(solution, i+1, l-1);
		}
		else if(solution[i][l].equals("SW")){
			parseSolution(solution, i+1, l-1);
		}
		else if(solution[i][l].equals("#")){
			return;
		}
		else{
			String[] indices = solution[i][l].split(",");
			int j = Integer.parseInt(indices[0]);
			int k = Integer.parseInt(indices[1]);
			if (k == -1){
				parseSolution(solution, i, j);
				parseSolution(solution, j+1, l);
			}
			else if(j == -1){
				parseSolution(solution, i, k-1);
				parseSolution(solution, k, l);
			}
			else{
				parseSolution(solution, i, j);
				parseSolution(solution, j+1, k-1);
				parseSolution(solution, k, l);
			}
		}
	}
	
	
	/**
	 *  Procedures findPairOfRightIndex and
	 *  findPairOfLeftIndex finds the index of the element which matches with the element of the right most index and left most index consecutively. 
	 * @param x
	 * @param i
	 * @param l
	 * @return
	 */
	private int findPairOfRightIndex(String[] x, int i, int l){
		for(int p=i;p<=l;p++){
			if(x[p].equals(x[l])){
				return p;
			}
		}
		return l;
	}
	
	/**
	 * Procedures findPairOfRightIndex and findPairOfLeftIndex finds the index of the element which matches with the element
	 *  of the right most index and left most index consecutively. 
	 * @param x
	 * @param i
	 * @param l
	 * @return
	 */
	private int findPairOfLeftIndex(String[] x, int i, int l){
		for(int p=l;p>=i;p--){
			if(x[p].equals(x[i])){
				return p;
			}
		}
		return i;
	}
	
	private void printArray(String[] x){
		System.out.print("X:");
		for(String s:x){
			System.out.print(s+"\t");
		}
		System.out.print("\n");
	}

	private void printMatrixInt(int[][] x){
		for(int i= 0; i<x.length; i++){
			for(int j=0; j<x.length; j++){
				System.out.print(x[i][j]+"\t");
			}
			System.out.print("\n");
		}
	}
	
	private void printMatrixString(String[][] x){
		for(int i= 0; i<x.length; i++){
			for(int j=0; j<x.length; j++){
				System.out.print(x[i][j]+"\t");
			}
			System.out.print("\n");
		}
	}
}
