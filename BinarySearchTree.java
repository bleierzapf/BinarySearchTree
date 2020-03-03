/**
 * Brian Leierzapf
 * 6/2/2019
 * CSD335
 * Binary Search Tree Assignment
 *
 * Method for assignment begin at new header below ~Line 315.
 */

package BinarySearchTree;

import java.util.ArrayList;

/*
 * Implementation of a binary search tree.
 */
public class BinarySearchTree {

    /*
     * node in a binary tree
     */
    private class Node {

        int value;
        Node left, right;

        // Constructor for leaf nodes.
        Node(int val) {
            value = val;
            left = null;
            right = null;
        }

        // Constructor for non-leaf nodes.
        Node(int val, Node leftChild, Node rightChild) {
            value = val;
            left = leftChild;
            right = rightChild;
        }
    }

    /*
     * This class represents the result of removing a node from a binary tree.
     */
    private class RemovalResult {

        Node node;    // Removed node
        Node tree;    // Remaining tree

        RemovalResult(Node node, Node tree) {
            this.node = node;
            this.tree = tree;
        }
    }

    // fields and methods for BinarySearchTree
    protected Node root;

    /*
     * The public add method adds a value to the tree by calling a private add
     * method and passing it the root of the tree.
     *
     * @param x The value to add to the tree.
     * @return true.
     */
    public boolean add(int x) {
        root = add(x, root);
        return true;
    }

    /*
     * The add method adds a value to the search tree.
     *
     * @x the value to add.
     * @param bstree The root of the binary search tree.
     * @return The root of the resulting binary search tree.
     */
    private Node add(int x, Node bstree) {
        if (bstree == null) {
            return new Node(x);
        }
        // bstree is not null. 
        if (x < bstree.value) {
            // Add x to the left subtree and replace 
            // the current left subtree with the result.
            bstree.left = add(x, bstree.left);
        } else {
            // Add x to the right subtree.
            bstree.right = add(x, bstree.right);
        }
        return bstree;
    }

    /*
     * The contains method checks to see if a value is in the binary tree.
     *
     * @param x The value to check for.
     * @return true if x is in the tree, false otherwise.
     */
    public boolean contains(int x) {
        // Call the private recursive method.
        return contains(x, root);
    }

    /*
     * The method contains checks whether an item is in a binary search tree.
     *
     * @param x The item to check for.
     * @param bstree The binary tree to look in.
     * @return true if found, false otherwise.
     */
    private boolean contains(int x, Node bstree) {
        if (bstree == null) {
            return false;
        }
        if (x == bstree.value) {
            return true;
        }
        if (x < bstree.value) {
            // Recursively look in left subtree.
            return contains(x, bstree.left);
        } else {
            // Recursively look in right subtree.
            return contains(x, bstree.right);
        }
    }

    public Node search(int x) {
        // Call the private recursive method.
        return search(x, root);
    }

    private Node search(int x, Node bstree) {
        if (bstree == null) {
            return null;
        }
        if (x == bstree.value) {
            return bstree;
        }
        if (x < bstree.value) {
            // Recursively look in left subtree.
            return search(x, bstree.left);
        } else {
            // Recursively look in right subtree.
            return search(x, bstree.right);
        }
    }

    /*
     * The remove method removes a value from the binary search tree.
     *
     * @param x The value to remove.
     * @returns true if x was removed, false if x not found.
     */
    public boolean remove(int x) {
        RemovalResult result = remove(root, x);
        if (result == null) {
            return false;
        } else {
            root = result.tree;
            return true;
        }
    }

    /*
     * This remove method removes a value a from a binary search tree and
     * returns the removed node and the remaining tree wrapped in a
     * RemovalResult object.
     *
     * @param bTree The binary search tree.
     * @param x The value to be removed.
     * @return null if x is not found in bTree.
     */
    private RemovalResult remove(Node bTree, int x) {
        if (bTree == null) {
            return null;
        }
        if (x < bTree.value) {
            // Remove x from the left subtree.
            RemovalResult result = remove(bTree.left, x);
            if (result == null) {
                return null;
            }
            bTree.left = result.tree;
            result.tree = bTree;
            return result;
        }
        if (x > bTree.value) {
            // Remove x from the right subtree.
            RemovalResult result = remove(bTree.right, x);
            if (result == null) {
                return null;
            }
            bTree.right = result.tree;
            result.tree = bTree;
            return result;
        }
        // x is in this root node.      
        // Is it a leaf? 
        if (bTree.right == null && bTree.left == null) {
            return new RemovalResult(bTree, null);
        }

        // Does the node have two children?
        if (bTree.right != null && bTree.left != null) {
            // Remove largest node in left subtree and 
            // make it the root of the remaining tree.

            /*RemovalResult remResult = removeLargest(bTree.left);
            Node newRoot = remResult.node;
            newRoot.left = remResult.tree;
            newRoot.right = bTree.right;*/

            RemovalResult remResult = removeSmallest(bTree.right);
            Node newRoot = remResult.node;
            newRoot.right = remResult.tree;
            newRoot.left = bTree.left;


            // Prepare the result to be returned.
            bTree.left = null;
            bTree.right = null;
            return new RemovalResult(bTree, newRoot);
        }
        // The node has one child
        Node node = bTree;
        Node tree;
        if (bTree.left != null) {
            tree = bTree.left;
        } else {
            tree = bTree.right;
        }
        node.left = null;
        node.right = null;
        return new RemovalResult(node, tree);
    }

    /*
     * The removeLargest method removes the largest node from a binary search
     * tree.
     *
     * @param bTree: The binary search tree.
     * @return The result of removing the largest node.
     */
    private RemovalResult removeLargest(Node bTree) {
        if (bTree == null) {
            return null;
        }

        if (bTree.right == null) {
            // Root is the largest node
            Node tree = bTree.left;
            bTree.left = null;
            return new RemovalResult(bTree, tree);
        } else {
            // Remove the largest node from the right subtree.
            RemovalResult remResult = removeLargest(bTree.right);
            bTree.right = remResult.tree;
            remResult.tree = bTree;
            return remResult;
        }
    }

    private RemovalResult removeSmallest(Node bTree) {
        if (bTree == null) {
            return null;
        }

        if (bTree.left == null) {
            // Root is the smallest node
            Node tree = bTree.right;
            bTree.right = null;
            return new RemovalResult(bTree, tree);
        } else {
            // Remove the largest node from the right subtree.
            RemovalResult remResult = removeLargest(bTree.left);
            bTree.left = remResult.tree;
            remResult.tree = bTree;
            return remResult;
        }
    }

    public String inOrder(){
        StringBuilder sb = inOrder(this.root, new StringBuilder());
        return sb.toString();
    }

    private StringBuilder inOrder(Node bst, StringBuilder sb) {
        if(bst != null){
            inOrder(bst.left, sb);
            sb.append(bst.value).append(" ");
            inOrder(bst.right, sb);
        }
        return sb;
    }

    public String topDown(){
        StringBuilder sb = topDown(this.root, new StringBuilder());
        return sb.toString();
    }

    private StringBuilder topDown(Node bst, StringBuilder sb){
        if(bst != null){
            sb.append(bst.value).append(" ");
            System.out.print(bst.value + " ");
            topDown(bst.left, sb);
            topDown(bst.right, sb);
            System.out.println();
        }
        return sb;
    }

    /** Binary Search Tree Assignment Below
     *  Methods For Assignment:
     *  isFull()
     *  isComplete()
     *  isPerfect()
     *  getHeight()
     *  getNodesAtLevel
     */


    /* This method returns a value of true or false depending on whether
     * or not the binary search tree is a Full binary tree. A binary tree
     * is Full if every node has 0 or 2 children
     */

    // Boolean declared to track if false condition occurs in isFull method.
    private boolean isFull;

    // Sets isFull boolean to true and calls isFullStatus. Returns result.
    public boolean isFull(){
        isFull = true;
        isFullStatus(root);
        return isFull;
    }

    private void isFullStatus(Node bstree){
        // Result is false if BST is empty.
        if(bstree == null){ isFull = false; }

        // Checks if both children are null. If so returns to test next nodes.
        if(bstree.left == null && bstree.right == null){
            return;

            // If only 1 child of node is null then condition of isFull is false.
        } else if (bstree.left == null || bstree.right == null) {
            isFull = false;
            return;
        }

        /* redundant code.
        if (!(bstree.left.value >= 0 && bstree.right.value >= 0)) {
            isFull = false;
        } */

        // Recursive loop through BST until finds end points and checks.
        if(isFull){
            isFullStatus(bstree.left);
            isFullStatus(bstree.right);
        }
    }

    /* This method returns true or false depending on whether or not the
     * binary tree is a Complete binary tree. A binary tree is Complete if
     * all levels, except possibly the last are completely full and all
     * nodes in the last level are as far left as possible.
     */
    public boolean isComplete(){
        // Gets height of BST
        int h = getHeight();
        // Checks if 2nd to last row is complete. If so, it checks if
        // last row is in correct format.
        if (prevLevelFull(h)){
            // Return true if complete.
            return isCompleteStatus(h);
        }
        return false;
    }

    // Method to check if 2nd to last row is complete.
    // If 2nd to last row isn't complete then BST can't be Complete.
    private boolean prevLevelFull(int h){
        /* Creates array of Node values are 2nd to last row.
         * Null values are written into array as -1.
         * If any -1 show up in array then row is not completed.
         */
        int[] nodes = createNodeArray(h-1);
        for (int i : nodes) {
            if(i == -1) { return false; }
        }
        return true;
    }

    // Checks if all values in final row are as far left as possible.
    private boolean isCompleteStatus(int h){
        // Creates array of Node values for last row.
        int[] nodes = createNodeArray(h);
        // Variable if null value is found.
        boolean fndNull = false;
        /* Checks array for -1 (null) value. Once null value is found
         * changes fndNull to true. If any value that isn't -1 is found
         * further down the array then BST last row is not complete.
         */
        for (int i : nodes) {
            if(i == -1) { fndNull = true; }
            if(fndNull && i != -1) {
                return false;
            }
        }
        return true;
    }

    /* This method returns true or false depending on whether or not the binary
     * tree is a Perfect binary tree. A binary tree is Perfect if all internal
     * nodes have 2 children, and all leaf nodes are at the same level.
     */
    public boolean isPerfect() {
        // Get height of BST
        int h = getHeight();
        // Builds array of Nodes at last level.
        int[] nodesAtLevel = createNodeArray(h);
        /* Checks to make sure last level has no -1 (null) values. No null values
         * in final row indicates a perfect BST.
        */
        for(int i = 0; i < nodesAtLevel.length; i++){
            if(nodesAtLevel[i] == -1){
                return false;
            }
        }
        return true;
    }

    /* This method returns the Height of the binary search tree. A tree's height
     * is the largest depth of any node. A tree with just one node has height 0.
     */
    public int getHeight() {
        int h = 0;
        // Create arraylist to store height values.
        ArrayList<Integer> hList = new ArrayList<>();
        hList.add(h);
        // Calculate tallest point on BST
        hList = treeHeight(hList, h, root);
        // Iterates through arraylist to find largest value.
        for(int i = 0; i < hList.size(); i++){
            if(hList.get(i)>h){
                h = hList.get(i);
            }
        }
        return h;
    }

    /* Recursion through BST to find largest value. When larger value is found returns value
     * to the arraylist. Process is completed until end of BST is reached.
     */
    private ArrayList<Integer> treeHeight(ArrayList<Integer> hList, int h,  Node bstree){
        if(bstree != null){
            if(hList.get(0) < h){
                hList.add(h);
            }
            h++;
            // Moves down left tree.
            if (bstree.left != null) {
                treeHeight(hList, h, bstree.left);
            }
            // Moves down right tree.
            if (bstree.right != null) {
                treeHeight(hList, h, bstree.right);
            }
        }
        return hList;
    }

    /* This method returns an array of node values of all of the nodes at the
     * level specified by the method parameter, level. All nodes at the same
     * depth form a tree Level.
     */

    public int[] getNodesAtLevel(int level){
        // Generates new array of Nodes at level.
        int valArr[] = createNodeArray(level);
        // Create empty array same size as filled array.
        int tmpArr[] = new int[valArr.length];
        // Int to be used to track size of output array
        int tmpCnt = 0;

        // If value of array is NOT -1 move value into new array, tmpCnt +1.
        for (int i = 0; i < valArr.length; i++){
            if(valArr[i] != -1){
                tmpArr[tmpCnt] = valArr[i];
                tmpCnt++;
            }
        }

        // Build print array sized to tmpCnt. Final array to only print values
        // that are not null at requested level.
        int pntArr[] = new int[tmpCnt];
        for (int i = 0; i < pntArr.length; i++){
            pntArr[i] = tmpArr[i];
        }

        return pntArr;
    }

    // Int to track location of full position in array.
    private int fillArrPos;

    private int[] createNodeArray(int level){
        // Create array size of max number of results at level in binary tree.
        int valArr[] = new int[((int) Math.pow(2, level))];
        // Resets fill position to 0 for each pass through.
        fillArrPos = 0;

        // Fill array with -1 values. If -1 shows in result the node was null.
        for (int i = 0; i < valArr.length; i++){
            valArr[i] = -1;
        }

        // Builds array of Nodes are level.
        // Always starts at depth of 0, root Node.
        valArr = nodesAtLevel(valArr, level, 0, root);

        return valArr;
    }

    // Fills array created from createNodeArray.
    private int[] nodesAtLevel(int[] valArr, int returnLevel, int depth, Node bstree){
        // Returns root if returnLevel is 0.
        if(returnLevel == 0){
            valArr[0] = bstree.value;
            return valArr;
        }

        /* Checks if node at depth is 1 level above the requested return level.
         * If true checks left and right children of that Node, if not null adds
         * child Node value to array.
         * Moves from Left of tree to the Right.
         * After inserting of 1 set of child nodes, fill array position moves to
         * next set (+2) be checked for values.
         */
        if(depth + 1 == returnLevel){
            if(bstree.left != null){
                valArr[fillArrPos] = bstree.left.value;
            }
            if(bstree.right != null){
                valArr[fillArrPos+1] = bstree.right.value;
            }
            fillArrPos = fillArrPos + 2;
            return valArr;
        }else {
            // Recursion untill level found to input values into array.
            if (bstree.left != null) {
                nodesAtLevel(valArr, returnLevel, depth + 1, bstree.left);
            }
            if (bstree.right != null) {
                nodesAtLevel(valArr, returnLevel, depth + 1, bstree.right);
            }
        }

        return valArr;
    }

}
