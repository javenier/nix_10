package ua.com.alevel;

import ua.com.alevel.level1.task2.HorseMove;
import ua.com.alevel.level1.task3.Triangle;
import ua.com.alevel.level1.task1.UniqueSymbols;
import ua.com.alevel.level2.task1.NestedParentheses;
import ua.com.alevel.level2.task2.Tree;
import ua.com.alevel.level3.GameOfLife;

import java.awt.*;
import java.util.Scanner;

public class Main {

    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Main test = new Main();
        while (true)
            test.runApp();
    }

    private void runApp() {
        System.out.print("Choose level (1-3) or 0 to exit from the program: ");
        String level = sc.nextLine();
        switch (level) {
            case "1": {
                System.out.print("Enter the number of the task (1-3) or 0 to change the level: ");
                String task = sc.nextLine();
                switch (task) {
                    case "1":
                        solveUniqueSymbols();
                        break;
                    case "2":
                        solveHorseMove();
                        break;
                    case "3":
                        solveTriangle();
                        break;
                    case "0":
                        break;
                }
                break;
            }
            case "2": {
                System.out.print("Enter the number of the task (1-2) or 0 to change the level: ");
                String task = sc.nextLine();
                switch (task) {
                    case "1":
                        solveNestedParentheses();
                        break;
                    case "2":
                        solveTreeDepth();
                        break;
                    case "0":
                        break;
                }
                break;
            }
            case "3": {
                solveGameOfLife();
                break;
            }
            case "0": {
                System.exit(0);
                break;
            }

        }
    }

    private void solveGameOfLife() {
        System.out.println();
        System.out.println("#Game of life#");
        GameOfLife test = new GameOfLife();
        int[][] board = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        test.setBoard(board);
        while (true) {
            System.out.println("Before");
            test.printBoard();
            test.gameOfLife();
            System.out.println("After");
            test.printBoard();
            System.out.print("Enter 1 if you want to continue the game or 0 to close the game: ");
            String choice = sc.nextLine();
            System.out.println();
            if (choice.equals("0"))
                return;
        }

    }

    private void solveTreeDepth() {
        System.out.println();
        System.out.println("#Tree depth#");
        Tree tree = new Tree();
        tree.generateRandomTree();
        tree.printTree();
        System.out.println("The depth of the tree: " + Tree.getDepth(tree.getRootNode()));
        System.out.println();
    }

    private void solveNestedParentheses() {
        System.out.println();
        System.out.println("#Nested parentheses#");
        System.out.print("Enter a string: ");
        String src = sc.nextLine();
        if (NestedParentheses.isValidNestedParentheses(src))
            System.out.println("The string is correct");
        else
            System.out.println("The string is not correct");
        System.out.println();
    }

    private void solveHorseMove() {
        int x1, y1, x2, y2;
        System.out.println();
        System.out.println("#Horse's moves#");
        System.out.println();
        HorseMove.printChessBoard();
        System.out.println();
        System.out.println();
        System.out.println("Coordinates of initial position:");
        System.out.print("Enter first coordinate: ");
        x1 = Integer.parseInt(sc.nextLine());
        System.out.print("Enter second coordinate: ");
        y1 = Integer.parseInt(sc.nextLine());
        System.out.println("Coordinates of destination position:");
        System.out.print("Enter first coordinate: ");
        x2 = Integer.parseInt(sc.nextLine());
        System.out.print("Enter second coordinate: ");
        y2 = Integer.parseInt(sc.nextLine());
        if (HorseMove.isPossibleMove(x1, y1, x2, y2))
            System.out.println("It's possible move");
        else
            System.out.println("It's impossible move");
        System.out.println();
    }

    private void solveTriangle() {
        int x1, y1, x2, y2, x3, y3;

        System.out.println();
        System.out.println("#Square of triangle#");
        System.out.println("Coordinates of point A:");
        System.out.print("Enter x: ");
        x1 = Integer.parseInt(sc.nextLine());
        System.out.print("Enter y: ");
        y1 = Integer.parseInt(sc.nextLine());
        System.out.println("Coordinates of point B:");
        System.out.print("Enter x: ");
        x2 = Integer.parseInt(sc.nextLine());
        System.out.print("Enter y: ");
        y2 = Integer.parseInt(sc.nextLine());
        System.out.println("Coordinates of point C:");
        System.out.print("Enter x: ");
        x3 = Integer.parseInt(sc.nextLine());
        System.out.print("Enter y: ");
        y3 = Integer.parseInt(sc.nextLine());

        Triangle tr = new Triangle(new Point(x1, y1), new Point(x2, y2), new Point(x3, y3));
        if (tr.isTriangle()) {
            System.out.println("Triangle is valid.");
        } else {
            System.out.println("Triangle is not exist. Please, try again.");
            return;
        }
        System.out.println("Square of the triangle: " + tr.calcSquare());
        System.out.println();
    }

    private void solveUniqueSymbols() {
        System.out.println();
        System.out.println("#Count unique symbols#");
        System.out.print("Enter symbols by space: ");
        String src = sc.nextLine();
        System.out.println("Count of unique numbers in string: " + UniqueSymbols.countUniqueSymbols(src));
        System.out.println();
    }
}
