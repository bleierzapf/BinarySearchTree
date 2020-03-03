/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BinarySearchTree;

import java.util.Arrays;

public class BinarySearchTreeDemo {

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.add(35);
        bst.add(15);
        bst.add(65);
        bst.add(85);
        bst.add(50);
        bst.add(10);
        bst.add(20);
        bst.add(8);
        bst.add(56);
        bst.add(9);
        bst.add(45);
        bst.add(37);
        bst.add(13);
        bst.add(91);

        System.out.println(bst.inOrder());
        System.out.println(bst.getHeight());

        //System.out.println(bst.isPerfect());
        //System.out.println(bst.isComplete());
        System.out.println(bst.isFull());
        System.out.println(Arrays.toString(bst.getNodesAtLevel(bst.getHeight())));
    }
}
