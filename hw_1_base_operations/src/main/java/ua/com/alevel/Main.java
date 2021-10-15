package ua.com.alevel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("First task");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        System.out.println("Sum of numbers: " + SumOfNumbers.sumOfNumbers(str));
        System.out.println();

        System.out.println("Second task");
        System.out.print("Enter a string: ");
        String str2 = sc.nextLine();
        SortedEntries.countFrequencyOfChar(str2);
        System.out.println();

        System.out.println("Third task");
        System.out.println("Enter the number of lesson: ");
        SchoolSchedule.calcEndOfLesson(sc.nextInt());
        sc.close();
    }

}
