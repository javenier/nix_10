package ua.com.alevel;

import java.util.Scanner;

public class Main {

    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Main test = new Main();
        while(true)
            test.startApp();
    }

    private void startApp() {
        System.out.println("0. Exit\n" +
                "1. Simple reverse\n" +
                "2. Reverse on the specified substring in the string\n" +
                "3. Reverse by first and last index");
        System.out.println();
        System.out.print("Enter tour choice: ");
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 0: {
                System.exit(0);
                break;
            }
            case 1: {
                simpleReverse();
                break;
            }
            case 2: {
                reverseBySubstring();
                break;
            }
            case 3: {
                reverseByIndexes();
                break;
            }
        }
    }

    private void reverseByIndexes() {
        System.out.print("Enter a string: ");
        String text = sc.nextLine();
        System.out.print("Enter a first index: ");
        int firstIndex = Integer.parseInt(sc.nextLine());
        System.out.print("Enter a last index: ");
        int lastIndex = Integer.parseInt(sc.nextLine());
        System.out.println("Result: " + StringHelperUtil.reverse(text, firstIndex, lastIndex + 1));
        System.out.println();
    }

    private void reverseBySubstring() {
        System.out.print("Enter a string: ");
        String text = sc.nextLine();
        System.out.print("Enter a substring: ");
        String sub = sc.nextLine();
        System.out.println("Result: " + StringHelperUtil.reverse(text, sub));
    }

    private void simpleReverse() {
        System.out.print("Enter a string: ");
        String text = sc.nextLine();
        System.out.println("If you want to reverse the full string, enter 1, else enter 2.");
        System.out.print("Your choice: ");
        int choice = Integer.parseInt(sc.nextLine());
        boolean fullString;
        if(choice == 1)
            fullString = true;
        else
            fullString = false;
        System.out.println("Result: " + StringHelperUtil.reverse(text, fullString));
        System.out.println();
    }
}
