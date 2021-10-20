package ua.com.alevel;

import java.util.Scanner;

public class Main {

    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Main test = new Main();
        while(true)
            test.startApp();

    }

    private void startApp(){
        System.out.println("Enter the number of the task or 0 to exit.");
        System.out.print("Your choice: ");
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 0: {
                System.exit(0);
                break;
            }
            case 1: {
                solveSumOfNumbers();
                break;
            }
            case 2: {
                solveSortedEntries();
                break;
            }
            case 3: {
                solveSchoolSchedule();
                break;
            }
        }
    }

    private void solveSchoolSchedule() {
        System.out.print("Enter the number of the lesson: ");
        int lesson = Integer.parseInt(sc.nextLine());
        SchoolSchedule.calcEndOfLesson(lesson);
        System.out.println();
    }

    private void solveSortedEntries() {
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        SortedEntries.countFrequencyOfChar(str);
        System.out.println();
    }

    private void solveSumOfNumbers() {
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        System.out.println("Sum of numbers = " + SumOfNumbers.sumOfNumbers(str));
        System.out.println();
    }

}
