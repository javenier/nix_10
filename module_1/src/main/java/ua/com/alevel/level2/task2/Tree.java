package ua.com.alevel.level2.task2;

import java.util.Random;
import java.util.Stack;

public class Tree {

    private TreeNode rootNode;

    public TreeNode getRootNode() {
        return rootNode;
    }

    public Tree() {
        rootNode = null;
    }

    public void insertNode(int value) {
        TreeNode newNode = new TreeNode();
        newNode.setValue(value);
        if (rootNode == null) {
            rootNode = newNode;
        } else {
            TreeNode currentNode = rootNode;
            TreeNode parentNode;
            while (true) {
                parentNode = currentNode;
                if (value == currentNode.getValue()) {
                    return;
                } else if (value < currentNode.getValue()) {
                    currentNode = currentNode.getLeft();
                    if (currentNode == null) {
                        parentNode.setLeft(newNode);
                        return;
                    }
                } else {
                    currentNode = currentNode.getRight();
                    if (currentNode == null) {
                        parentNode.setRight(newNode);
                        return;
                    }
                }
            }
        }
    }

    public void printTree() {
        Stack globalStack = new Stack();
        globalStack.push(rootNode);
        int gaps = 32;
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);
        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) {
                TreeNode temp = (TreeNode) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.getValue());
                    localStack.push(temp.getLeft());
                    localStack.push(temp.getRight());
                    if (temp.getLeft() != null ||
                            temp.getRight() != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("__");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
        }
        System.out.println(separator);
    }

    public void generateRandomTree() {
        Random rnd = new Random(System.currentTimeMillis());
        int nodeCount = rnd.nextInt(12) + 1;
        for (int i = 0; i < nodeCount; i++) {
            insertNode(rnd.nextInt(25) + 1);
        }
    }

    public static int getDepth(TreeNode root) {
        if (root != null)
            return 1 + Math.max(getDepth(root.getLeft()), getDepth(root.getRight()));
        else
            return 0;
    }
}
