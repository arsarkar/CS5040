package course.advanced;

public class SubSetSumSeries {

	// The value of subset[i][j] will be true if there 
    // is a subset of set[0..j-1] with sum equal to i
	int subset[][];
	
	public SubSetSumSeries() {
		// TODO Auto-generated constructor stub
	}
	
	// Returns true if there is a subset of set[] with sun equal to given sum
    public int isSubsetSum(int set[], int n, int sum)
    {
    	subset = new int[n+1][sum+1];
    	
        // If sum is 0, then answer is true
        for (int i = 0; i <= n; i++)
          subset[i][0] = 1;
      
        // If sum is not 0 and set is empty, then answer is false
        for (int j = 1; j <= sum; j++)
          subset[0][j] = 0;
      
         // Fill the subset table in botton up manner
         for (int i = 1; i <= n; i++)
         {
	           for (int j = 1; j <= sum; j++)
	           {
	        	     subset[i][j] = subset[i-1][j];
		             if (j >= set[i-1])
			               subset[i][j] = subset[i-1][j] +
			                                          subset[i -1][j-set[i-1]];
	           }
         }
      
        // uncomment this code to print table
         System.out.print("\t");
         for (int i = 0; i <= sum; i++)
        	 System.out.print(i + "\t");
         System.out.print("\n");
         for (int i = 0; i <= n; i++)
         {
        	 	if(i==0){
        	 		System.out.print("0" + "\t");
        	 	}else{
            	 	System.out.print(set[i-1] + "\t");        	 		
        	 	}
	           for (int j = 0; j <= sum; j++)
	              System.out.print(subset[i][j] + "\t");
	           		System.out.print("\n");
         }
      
         return subset[n][sum];
    }
    
    

}
