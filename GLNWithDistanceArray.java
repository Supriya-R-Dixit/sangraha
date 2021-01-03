package leetcode;

public class GLNWithDistanceArray{    
    int goodPairs;

    /**
     * Counts the good leaf pairs using DFS
     * @param root - Input tree
     * @param distance - good distance given as input
     * @return the number of nodes within good distance
     */
    public int countPairs(TreeNode root, int distance) 
    {
        computeGoodLeafPairsUsingDfsAndFixedArray(root, distance);
        return goodPairs;    
    }

    /**
     * Computes the good leaf pairs by creating an array of size=distance+1 at each node. The index of this array represents the distance of 
     * the leaves that can be reached from the current node. Leaves which are at a length greater than "good distance" from the current node are 
     * ignored as they do not contribute to good pairs. The value in the array is the number of leaves which are at a distance==index from the
     *  current node.   
     * @param root input tree
     * @param dist distance considered good
     * @return array of leaves within good distance from the given node
     */

    int[] computeGoodLeafPairsUsingDfsAndFixedArray(final TreeNode root, final int dist)
    {
        //base condition 
        if(isNodeNullCondition(root))
            return constructBaseCondition(root, dist);
        
       
        //this single leaf is at index==distance "1" from its parent.
        if(isLeafCondition(root))
            return constructLeafCondition(root, dist);

        final int[] leftLeaves = computeGoodLeafPairsUsingDfsAndFixedArray(root.left, dist);       
        final int[] rightLeaves = computeGoodLeafPairsUsingDfsAndFixedArray(root.right, dist);

        goodPairs += computeGoodPairs(leftLeaves, rightLeaves, dist);  
        
        //create a new array and populate it with data of all the leaves at the correct distances with respect to the parent node and 
        //return this array to the parent
        
        return updateLeafCountAtCorrectDistance(leftLeaves, rightLeaves, dist);
        
    }
    
    private int[] updateLeafCountAtCorrectDistance(final int[] leftLeaves, final int[] rightLeaves, int dist)
    {
        final int leaves[] = new int[dist+1];
        for(int i =1; i<dist; i++)
        {  //at each internal node in the tree, the number of leaves which are "index" distance from the current node is computed      
            leaves[i+1] = leftLeaves[i] + rightLeaves[i];
        }                 
        return leaves;  
    }
    
    private int computeGoodPairs(final int[] leftLeaves, final int[] rightLeaves, final int dist) {
        int pairs = 0;
        
        for(int i=1; i<=dist; i++)
        {
            for(int j=1; j<=dist;j++)
            {
                //the sum of the indices represent the distance of the left leaf + distance of the right leaf
                if(i+j <= dist)
                {//the number of left leaves multiplied by the number right leaves gives the goodPairs for distance==i+j
                    pairs += leftLeaves[i] * rightLeaves[j];
                }
            }
        }
        return pairs;
        
    }
    
   private boolean isNodeNullCondition(final TreeNode root)
    {
        return root==null;
    }
   
   private int[] constructBaseCondition(final TreeNode root, final int dist)
   {
       return new int[dist+1];
   }
   
   private boolean isLeafCondition(final TreeNode root) {
       return root.left == null && root.right == null;
   }
   
   private int[] constructLeafCondition(final TreeNode root, final int dist) {
       int leaves[]=new int[dist+1];
       leaves[1]=1;
       return leaves;
       
   }
}
