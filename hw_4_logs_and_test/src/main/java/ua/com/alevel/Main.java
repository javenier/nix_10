package ua.com.alevel;

import ua.com.alevel.controller.CourseController;
import ua.com.alevel.controller.StudentController;

import java.util.Scanner;

public class Main {

    private Scanner sc = new Scanner(System.in);
    private CourseController courseController = new CourseController();
    private StudentController studentController = new StudentController();

    public static void main(String[] args) {
        Main test = new Main();
        while (true) {
            test.runProgram();
        }
    }

    public void runProgram() {
        System.out.println("Note: if there aren't any courses, you won't able to create a student. First, create a course.");
        System.out.println("1. Work with courses");
        System.out.println("2. Work with students");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();
        switch (choice) {
            case "1": {
                courseController.run();
                break;
            }
            case "2": {
                studentController.run();
                break;
            }
            case "0": {
                System.exit(0);
            }
        }
    }
}
