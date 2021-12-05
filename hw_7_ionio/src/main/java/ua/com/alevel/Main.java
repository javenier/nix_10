package ua.com.alevel;

import ua.com.alevel.controller.GroupController;
import ua.com.alevel.controller.StudentController;
import ua.com.alevel.controller.impl.GroupControllerImpl;
import ua.com.alevel.controller.impl.StudentControllerImpl;

import java.util.Scanner;

public class Main {

    private Scanner sc = new Scanner(System.in);
    private final GroupController groupController = new GroupControllerImpl();
    private final StudentController studentController = new StudentControllerImpl();

    public static void main(String[] args) {
        Main test = new Main();
        while (true) {
            test.runProgram();
        }
    }

    public void runProgram() {
        System.out.println("Note: if there aren't any groups, you won't able to create a student. First, create a group.");
        System.out.println("1. Work with groups");
        System.out.println("2. Work with students");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();
        switch (choice) {
            case "1": {
                groupController.run();
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