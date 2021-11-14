package ua.com.alevel;

import java.util.Scanner;

public class Main {

    private Scanner sc = new Scanner(System.in);
    private MathSet mathSet;

    public static void main(String[] args) {
        Main test = new Main();
        while (true)
            test.startApp();
    }

    private void startApp() {
        System.out.println("\n\n0. Exit\n" +
                "-------------Creating a MathSet-------------\n" +
                "1. Create empty MathSet with dynamic capacity\n" +
                "2. Create empty MathSet with fixed capacity\n" +
                "3. Create MathSet from array of numbers\n" +
                "-------------Operations with MathSet-------------\n" +
                "4. Add number\n" +
                "5. Add vararg of numbers\n" +
                "6. Join with another MathSet\n" +
                "7. Intersect with another MathSet\n" +
                "8. Sort MathSet by descending\n" +
                "9. Sort a range of numbers in a MathSet by descending\n" +
                "10. Sort starting from value in a MathSet by descending\n" +
                "11. Sort MathSet by ascending\n" +
                "12. Sort a range of numbers in a MathSet by ascending\n" +
                "13. Sort starting from value in a MathSet by ascending\n" +
                "14. Get number by index\n" +
                "15. Get MAX value in MathSet\n" +
                "16. Get MIN value in MathSet\n" +
                "17. Get AVERAGE value in MathSet\n" +
                "18. Get MEDIAN value in MathSet\n" +
                "19. Display MathSet\n" +
                "20. Display a range in MathSet by indexes\n" +
                "21. Cut a range from MathSet by indexes\n" +
                "22. Clear MathSet\n" +
                "23. Clear from MathSet a range of numbers");
        System.out.println();
        System.out.print("Enter tour choice: ");
        String choice = sc.nextLine();
        switch (choice) {
            case "0": {
                System.exit(0);
                break;
            }
            case "1": {
                mathSet = new MathSet();
                System.out.println("Created empty MathSet with dynamic capacity");
                break;
            }
            case "2": {
                System.out.print("Enter capacity: ");
                int capacity = Integer.parseInt(sc.nextLine());
                mathSet = new MathSet(capacity);
                System.out.println("Created empty MathSet with fixed capacity");
                break;
            }
            case "3": {
                System.out.print("Enter array of numbers by space: ");
                String[] numbers = sc.nextLine().split(" ");
                Number[] arr = new Number[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    arr[i] = Double.parseDouble(numbers[i]);
                }
                mathSet = new MathSet(arr);
                System.out.println("Created MathSet from array of numbers");
                break;
            }
            case "4": {
                System.out.print("Enter the number: ");
                Number number = Double.parseDouble(sc.nextLine());
                mathSet.add(number);
                break;
            }
            case "5": {
                System.out.print("Enter numbers by space: ");
                String[] numbers = sc.nextLine().split(" ");
                Number[] arr = new Number[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    arr[i] = Double.parseDouble(numbers[i]);
                }
                mathSet.add(arr);
                break;
            }
            case "6": {
                System.out.print("Enter mathSet by space: ");
                String[] numbers = sc.nextLine().split(" ");
                Number[] arr = new Number[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    arr[i] = Double.parseDouble(numbers[i]);
                }
                MathSet temp = new MathSet(arr);
                mathSet.join(temp);
                break;
            }
            case "7": {
                System.out.print("Enter mathSet by space: ");
                String[] numbers = sc.nextLine().split(" ");
                Number[] arr = new Number[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    arr[i] = Double.parseDouble(numbers[i]);
                }
                MathSet temp = new MathSet(arr);
                mathSet.intersection(temp);
                break;
            }
            case "8": {
                mathSet.sortDesc();
                System.out.println("Sorted mathSet by descending");
                break;
            }
            case "9": {
                System.out.print("Enter first index: ");
                int firstIndex = Integer.parseInt(sc.nextLine());
                System.out.print("Enter last index: ");
                int lastIndex = Integer.parseInt(sc.nextLine());
                mathSet.sortDesc(firstIndex, lastIndex);
                break;
            }
            case "10": {
                System.out.print("Enter number: ");
                int num = Integer.parseInt(sc.nextLine());
                mathSet.sortDesc(num);
                break;
            }
            case "11": {
                mathSet.sortAsc();
                break;
            }
            case "12": {
                System.out.print("Enter first index: ");
                int firstIndex = Integer.parseInt(sc.nextLine());
                System.out.print("Enter last index: ");
                int lastIndex = Integer.parseInt(sc.nextLine());
                mathSet.sortAsc(firstIndex, lastIndex);
                break;
            }
            case "13": {
                System.out.print("Enter number: ");
                int num = Integer.parseInt(sc.nextLine());
                mathSet.sortAsc(num);
                break;
            }
            case "14": {
                System.out.print("Enter index: ");
                int index = Integer.parseInt(sc.nextLine());
                System.out.println(mathSet.get(index));
                break;
            }
            case "15": {
                System.out.println("MAX value: " + mathSet.getMax());
                break;
            }
            case "16": {
                System.out.println("MIN value: " + mathSet.getMin());
                break;
            }
            case "17": {
                System.out.println("AVERAGE value: " + mathSet.getAverage());
                break;
            }
            case "18": {
                System.out.println("MEDIAN value: " + mathSet.getMedian());
                break;
            }
            case "19": {
                System.out.println("MathSet:");
                for (Number n : mathSet.toArray()) {
                    System.out.print(n + " ");
                }
                break;
            }
            case "20": {
                System.out.print("Enter first index: ");
                int firstIndex = Integer.parseInt(sc.nextLine());
                System.out.print("Enter last index: ");
                int lastIndex = Integer.parseInt(sc.nextLine());
                System.out.println("A range in MathSet by indexes:");
                for (Number n : mathSet.toArray(firstIndex, lastIndex)) {
                    System.out.print(n + " ");
                }
                break;
            }
            case "21": {
                System.out.print("Enter first index: ");
                int firstIndex = Integer.parseInt(sc.nextLine());
                System.out.print("Enter last index: ");
                int lastIndex = Integer.parseInt(sc.nextLine());
                MathSet temp = mathSet.cut(firstIndex, lastIndex);
                System.out.println("Range that has been cut:");
                for (Number n : temp.toArray()) {
                    System.out.print(n + " ");
                }
                break;
            }
            case "22": {
                mathSet.clear();
                break;
            }
            case "23": {
                System.out.print("Enter numbers by space: ");
                String[] numbers = sc.nextLine().split(" ");
                Number[] arr = new Number[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    arr[i] = Double.parseDouble(numbers[i]);
                }
                mathSet.clear(arr);
                break;
            }
        }
    }
}