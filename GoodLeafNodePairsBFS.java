package leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreeNodeWithParent {
    final int val;
    TreeNodeWithParent left;
    TreeNodeWithParent right;
    TreeNodeWithParent parent;

    TreeNodeWithParent(int val) {
        this.val = val;
    }

    TreeNodeWithParent(int val, TreeNodeWithParent left, TreeNodeWithParent right, TreeNodeWithParent parent) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
}

class TreeNode {
    final int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class GoodLeafNodePairsBFS {
    private final static int BFS_DIVISION_CONSTANT = 2;

    /**
     * Count the pairs by cloning the input tree and adding a parent node. Post that collect the leaves
     * of the morphed tree and do a breadth first search to count the good pairs. 
     * @param root - root node of the tree for which we have to find the good pairs
     * @param distance - distance between nodes that we need to calculate good pairs for
     * @return - the number of nodes within the input distance.
     */
    
    public int countPairs(TreeNode root, int distance) {
        //clone the tree and a parent to each node
        final TreeNodeWithParent clonedTree = new TreeNodeWithParent(root.val);
        cloneTreeAndAddParentReference(root, clonedTree, null);
        // collect the leaves of the cloned tree
        final HashSet<TreeNodeWithParent> leafSet = new HashSet<>();
        collectLeaves(clonedTree, leafSet);
        // count the good pairs from the cloned tree leaf nodes that have a parent reference 
        return countGoodPairs(distance, leafSet);
    }
    
    /**
     * Clone the existing tree and add a parent node for each.  
     * @param root - input tree.
     * @param rootP - root of tree with nodes cloned from the original tree but changed to have a parent reference.
     * @param parent - the parent reference for each node of the cloned tree.
     */
    
    private void cloneTreeAndAddParentReference(TreeNode root, TreeNodeWithParent rootP, TreeNodeWithParent parent) {
        // base condition
        if (root == null) {
            rootP = null;
            return;
        }

        if (root.left != null)
            rootP.left = new TreeNodeWithParent(root.left.val);

        if (root.right != null)
            rootP.right = new TreeNodeWithParent(root.right.val);

        // add the parent reference
        rootP.parent = parent;

        cloneTreeAndAddParentReference(root.left, rootP.left, rootP);
        cloneTreeAndAddParentReference(root.right, rootP.right, rootP);

    }

    /**
     * Collect all the leaves of the cloned tree.
     * @param rootP - root of the cloned tree.
     * @param leafSet - set to collect the leaves in.
     */
    
    private void collectLeaves(final TreeNodeWithParent rootP, final HashSet<TreeNodeWithParent> leafSet) {
        // Construct a empty hash set
        if (rootP == null) {
            return;
        }

        if (rootP.left == null && rootP.right == null) {
            leafSet.add(rootP);
            return;
        }

        collectLeaves(rootP.left, leafSet);
        collectLeaves(rootP.right, leafSet);

    }

    /**
     * The function that actually does the work. Take in a leaf set with parent references and do the bfs. 
     * @param dist  - distance considered good.
     * @param leafSet - input set with nodes with parent references.
     * @return - number of good pairs.
     */
    
    private int countGoodPairs(final int dist, final HashSet<TreeNodeWithParent> leafSet) {
        int count = 0;
        for (TreeNodeWithParent n : leafSet) {
            count = count + computeGoodPairsUsingBfs(n, dist);
        }
        return count / BFS_DIVISION_CONSTANT;
    }

    /**
     * Run a bfs to get the good pairs for each leaf.
     * @param node - node that has a parent reference.
     * @param dist - distance that we are measuring.
     * @return - count of good pairs for each leaf.
     */
    
    private int computeGoodPairsUsingBfs(TreeNodeWithParent node, int dist) {
        // construct the queue to implement the bfs
        final Queue<TreeNodeWithParent> q = new LinkedList<>();
        // keep track of visited nodes
        final HashSet<TreeNodeWithParent> visited = new HashSet<>();
        int level = 0, goodPairs = -1;

        q.add(node);
        q.add(null);

        // traverse level by level 
        while (!q.isEmpty() && level <= dist) {
            // null is a level marker. 
            if (q.peek() == null) {
                // increase the level count
                level++;
                //add a null marker to the end of the queue now that we have reached the end of the current level.
                // all the elements till this marker are children of the previous level that we just traversed.
                q.add(null);
                // remove the null that terminates previous level
                q.poll();
                // move forward to the next level
                continue;
            }

            TreeNodeWithParent temp = q.poll();
            visited.add(temp);

            if (temp.left == null && temp.right == null) {
                goodPairs++;
            }

            if (temp.left != null && !visited.contains(temp.left))
                q.add(temp.left);
            if (temp.right != null && !(visited.contains(temp.right)))
                q.add(temp.right);
            if (temp.parent != null && !visited.contains(temp.parent))
                q.add(temp.parent);
        }

        return goodPairs;
    }

}
