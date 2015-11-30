package course.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class MaxSkipChain {
	
int[] skipChain;

public static void main(String[] args) {
		
		//store the arguments in an array of strings
		String[] testFiles = args;
		MaxSkipChain finder = new MaxSkipChain();
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
		            int[] y = new int[x.length];
		            for(int j=0; j<x.length; j++){
		            	y[j] = Integer.parseInt(x[j]);
		            }
		            finder.printArray(y);
		            finder.findMaxSkipChain(y);
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
	 * method to find maximum skip chain
	 * @param x array of integers
	 * @return the sum of the maximum skip chain
	 */
	public int findMaxSkipChain(int[] x){
		//store the length of the sequence 
		int xLength = x.length;
		skipChain = new int[xLength];
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
				int j = s+r;
				
				//C[i,j] = x[i] when i==j
				if(i == j){
					c[i][j] = x[i];
				}
				//C[i,j] = max(x[i],x[j]} when j=i+1
				else if(j==i+1){
					if(x[i]>x[j]){
						c[i][j] = x[i];
						solution[i][j]="w";
					}
					else{
						c[i][j] = x[j];
						solution[i][j]="s";
					}
				}
				//C[i,j] = max(x[i]+x[j],x[i+1]} when j=i+2
				else if(j==i+2){
					if(x[i]+x[j]>x[i+1]){
						c[i][j] = x[i]+x[j];
						solution[i][j]=")(";
					}
					else{
						c[i][j] = x[i+1];
						solution[i][j]="()";
					}
				}
				//recurrence equation
				else{
					int c1 = x[i]+x[j]+c[i+2][j-2];
					int c2 = x[i]+c[i+2][j];
					int c3 = x[j]+c[i][j-2];
					int c4 = c[i+1][j-1];
					
					if (c1>=c2 && c1>=c3 && c1>=c4){
						c[i][j]=c1;
						solution[i][j]="+";
					}
					else if (c2>=c1 && c2>=c3 && c2>=c4){
						c[i][j]=c2;
						solution[i][j]="w";
					}
					else if (c3>=c1 && c3>=c2 && c3>=c4){
						c[i][j]=c3;
						solution[i][j]="s";
					}
					else{
						c[i][j]=c4;
						solution[i][j]="x";
					}
				}
			}
		}

		System.out.println("Cost Matrix:");
		printMatrixInt(c);
		
		System.out.println("Reconstruction table:");
		printMatrixString(solution);
		
		//parse solution and print out result
		int setSize = c[0][xLength-1];
		System.out.println("Maximal skip chain sum:: " + c[0][xLength-1]);
		int i= 0, j = xLength-1, m= 0; 
		m = parseSolution(x, solution, i, j, m);
		int sum = 0;
		for(int n=0; n<m; n++){			
			sum += x[skipChain[n]];
			System.out.print(x[skipChain[n]]+"("+skipChain[n]+")");
			if (n<m-1){
				System.out.print("+");
			}
		}
		System.out.println(" = " + sum);
		return c[0][xLength-1];
		
	}
	
	/**
	 * parses the solution matrix to identify which pairs are added to the optimal set
	 * @param solution
	 * @param i
	 * @param j
	 */
	private int parseSolution(int[] x, String[][] solution, int i, int l, int m){
		if (solution[i][l].equals("+")){
			skipChain[m] = i;
			m++;
			skipChain[m] = l;
			m++;
			m = parseSolution(x, solution, i+2, l-2, m);
		}
		else if(solution[i][l].equals("w")){
			skipChain[m] = i;
			m++;
			m = parseSolution(x, solution, i+2, l, m);
		}
		else if(solution[i][l].equals("s")){
			skipChain[m] = l;
			m++;
			m = parseSolution(x, solution, i, l-2, m);
		}
		else if(solution[i][l].equals("x")){
			m = parseSolution(x, solution, i+1, l-1, m);
		}
		else if(solution[i][l].equals("()")){
			skipChain[m] = i+1;
			m++;
		}
		else if(solution[i][l].equals(")(")){
			skipChain[m] = i;
			m++;
			skipChain[m] = l;
			m++;
		}
		for(int n=m; n<x.length; n++){
			skipChain[n] = -1;
		}
		return m;
	}
	
	private void printArray(int[] y){
		for(int s:y){
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
