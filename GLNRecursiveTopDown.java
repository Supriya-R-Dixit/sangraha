package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GLNRecursiveTopDown {

    int goodPairs;

    /**
     * Counts the good leaf pairs using DFS
     * 
     * @param root     - Input tree
     * @param distance - good distance given as input
     * @return the number of nodes within good distance
     */
    public int countPairs(TreeNode root, int distance) {
        countGoodPairsUsingTopDownRecursion(root, distance, 0);
        return goodPairs;
    }

    /**
     * Count the good leaf pairs using top down recursion
     * 
     * @param root  input tree
     * @param dist  good leaf distance given as input
     * @param depth - length of each node from the root. depth of root is 0
     * @return list of leaves with their depths
     */
    List<Integer> countGoodPairsUsingTopDownRecursion(final TreeNode root, final int dist, final int depth) { // base
                                                                                                              // condition

        if (isBaseCondition(root))
            return constructBaseCondition(root);
        // add the depth into the list once a leaf is reached
        if (isLeafCondition(root))
            return constructLeafCondition(root, depth);

        List<Integer> leftLeaves, rightLeaves;
        leftLeaves = countGoodPairsUsingTopDownRecursion(root.left, dist, depth + 1);
        rightLeaves = countGoodPairsUsingTopDownRecursion(root.right, dist, depth + 1);
        goodPairs += computeGoodPairs(leftLeaves, rightLeaves, depth, dist);
        leftLeaves.addAll(rightLeaves);

        return leftLeaves;
    }

    /**
     * gets the distance of the left and right leaves from the current node and
     * checks if it is within good distance if yes, it is added as a good pair
     * 
     * @param leftLeaves
     * @param rightLeaves
     * @param depth
     * @param dist
     * @return number of good pairs
     */
    private int computeGoodPairs(final List<Integer> leftLeaves, final List<Integer> rightLeaves, int depth, int dist) {
        int pairs = 0;
        if (leftLeaves.size() != 0 && rightLeaves.size() != 0) {
            for (int i = 0; i < leftLeaves.size(); i++) {
                for (int j = 0; j < rightLeaves.size(); j++) {// Calculate the distance of left and right leaf from the
                                                              // current node and sum it up to check for good distance
                    if (leftLeaves.get(i) + rightLeaves.get(j) - 2 * depth <= dist) {
                        pairs++;
                    }
                }
            }
        }
        return pairs;
    }

    /**
     * checks if the node is null
     * 
     * @param root
     * @return true if node is null else false
     */
    private boolean isBaseCondition(final TreeNode root) {
        return root == null;
    }

    /**
     * constructs an empty array list
     * 
     * @param root of the input tree
     * @return empty array list
     */
    private List<Integer> constructBaseCondition(final TreeNode root) {
        return new ArrayList<Integer>();
    }

    /**
     * checks if the node is a leaf
     * 
     * @param root of the input tree
     * @return true if leaf else false
     */
    private boolean isLeafCondition(final TreeNode root) {
        return root.left == null && root.right == null;
    }

    /**
     * constructs a single element arraylist which contains the depth of the leaf.
     * 
     * @param root
     * @param depth
     * @return
     */
    private List<Integer> constructLeafCondition(final TreeNode root, int depth) {
        List<Integer> leaf = new ArrayList<Integer>();
        leaf.add(depth);
        return leaf;
    }
}
