package leetcode;

public final class GLNRecursiveBottomUpOptimized {
    int goodPairs;
   
    /**
     * Counts the good leaf pairs using bottom up recursion
     * @param root - Input tree
     * @param distance - good distance given as input
     * @return the number of nodes within good distance
     */
    public int countPairs(TreeNode root, int distance) 
    {       
        countGoodPairsUsingBottomUpRecursion(root, distance);
        return goodPairs;      
    }
    
    private int[] countGoodPairsUsingBottomUpRecursion(TreeNode root, int dist) {
        // base condition for dfs
        if (isBaseCondition(root))
            return constructBaseCondition();

        if (isLeafCondition(root)) {
            return constructLeafCondition(root);
        }
        final int[] leftLeaves = countGoodPairsUsingBottomUpRecursion(root.left, dist);
        final int[] rightLeaves = countGoodPairsUsingBottomUpRecursion(root.right, dist);
        goodPairs += computeGoodPairs(leftLeaves, rightLeaves, dist);        
        return updateLeavesWithDist(leftLeaves, rightLeaves, dist);
    }
 
   
   /**
    * If the sum of the distances of the left leaf and right leaf from the current node is within good distance
    * count it as a good pair
    * @param leftLeaves
    * @param rightLeaves
    * @param dist 
    * @return the number of good pairs
    */
   private int computeGoodPairs(final int[] leftLeaves, final int[] rightLeaves, final int dist) {
       int pairs = 0;
       for (int i = 0; i < leftLeaves.length; i++) {
           for (int j = 0; j < rightLeaves.length; j++) {
               if (leftLeaves[i] + rightLeaves[j] <= dist) {
                   pairs++;
               }
           }
       }
       return pairs;
   }
   
   /**
    * create an array of size equal to the number of good leaves and populate it with the updated distance
    * with respect to the parent of the current node and return the same
    * @param leftLeaves
    * @param rightLeaves
    * @param dist
    * @return leaves with updated distance from the parent of the current node
    */
   
   private int[] updateLeavesWithDist(final int[] leftLeaves, final int[] rightLeaves, final int dist) {
   
       final int leaves[] = new int[getCount(leftLeaves, dist) + getCount(rightLeaves, dist)];

       if (leaves.length == 0)
           return leaves;

       int index = 0;
       for (int i = 0; i < leftLeaves.length; i++) {
           if (leftLeaves[i] + 1 <= dist)
               leaves[index++] = leftLeaves[i] + 1;
       }
       for (int i = 0; i < rightLeaves.length; i++) {
           if (rightLeaves[i] + 1 <= dist)
               leaves[index++] = rightLeaves[i] + 1;
       }
       return leaves;
   }
   
   /**
    * 
    * @param leaves - array with distance of leaves from the current node
    * @param dist - input give which is the good distance
    * @return - count of the leaves which will remain within or equal to good distance when returned
    * to the parent node
    */
   
   private int getCount(int[] leaves, int dist)
   {
       int count = 0;
       for(int i =0; i<leaves.length; i++)
       {
           if(leaves[i]+1 <= dist)
               count++;
       }
       return count;
   }

   
   /**
    * check if a given node is null
    * @param root
    * @return true if null else false
    */
   private boolean isBaseCondition(final TreeNode root) {
       return root == null;
   }
   
   /**
    *if the current node is null then return empty array 
    * @return empty array
    */
   private int[] constructBaseCondition() {
       return new int[0];
   }
   
   /**
    * check if the given node is a leaf
    * @param root
    * @return true if leaf else false
    */
   private boolean isLeafCondition(final TreeNode root) {
       return root.left == null && root.right == null;
   }
   
   
   /**
    * if the given node is a leaf return an array with a single element 
    * @param root
    * @return array
    */
   private int[] constructLeafCondition(final TreeNode root) {
       // distance is calculated bottom up. Since the leaf is at a distance of 1 from
       // its parent, the same is returned in the array below.
       final int leaf[] = new int[1];
       leaf[0] = 1;
       return leaf;
   }

}
