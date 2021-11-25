package ua.com.alevel;

import ua.com.alevel.exceptions.IncorrectDataValueException;
import ua.com.alevel.exceptions.IncorrectInputException;
import ua.com.alevel.exceptions.RusMonthException;

import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner sc = new Scanner(System.in);
    private MyCalendar[] calendars = new MyCalendar[2];
    private String formatOfDate;

    public static void main(String[] args) {
        Main test = new Main();
        while (true)
            test.startApp();
    }

    private void startApp() {
        System.out.println();
        System.out.println("NOTE: The program has an array of two calendars.\n" +
                "All operations will be performed with first calendar in array.\n" +
                "Second calendar exists to show sorting and difference between dates.\n" +
                "\n1. Create a calendar" +
                "\n2. Choose another format" +
                "\n3. Operations with calendar" +
                "\n0. Exit");
        System.out.print("\nYour choice: ");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                try {
                    createCalendar();
                } catch (IncorrectInputException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "2":
                try {
                    editFormat();
                } catch (IncorrectInputException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "3":
                try {
                    operations();
                } catch (IncorrectInputException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "0":
                System.exit(0);
                break;
        }
    }

    private void operations() throws IncorrectInputException {
        printOperations();
        System.out.print("Your choice: ");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                difference();
                break;
            case "2":
                sortAsc();
                break;
            case "3":
                sortDesc();
                break;
            case "4":
                add("");
                break;
            case "5":
                add("s");
                break;
            case "6":
                add("min");
                break;
            case "7":
                add("hour");
                break;
            case "8":
                add("day");
                break;
            case "9":
                add("month");
                break;
            case "10":
                add("year");
                break;
            case "11":
                subtract("");
                break;
            case "12":
                subtract("s");
                break;
            case "13":
                subtract("min");
                break;
            case "14":
                subtract("hour");
                break;
            case "15":
                subtract("day");
                break;
            case "16":
                subtract("month");
                break;
            case "17":
                subtract("year");
                break;
            case "18":
                showDate(calendars[0]);
                break;
            default:
                throw new IncorrectInputException(choice + " is incorrect number of operation. It should be from 1 to 18");
        }
    }

    private void showDate(MyCalendar myCalendar) {
        try {
            myCalendar.showDate();
        } catch (RusMonthException e) {
            System.out.println(e.getMessage());
        }
    }

    private void difference() throws IncorrectInputException {
        if (!checkForFullness()) {
            System.out.println("You need to create a new calendar");
            createCalendar();
        }
        calendars[0].difference(calendars[1]);
    }

    private void sortAsc() throws IncorrectInputException {
        System.out.println("\nNote: For this operation you need to have more than one calendar!");
        List<MyCalendar> list;
        if (!checkForFullness()) {
            createCalendar();
        }
        list = MyCalendar.sortAsc(calendars);
        for (MyCalendar c : list) {
            showDate(c);
        }
    }

    private void sortDesc() throws IncorrectInputException {
        System.out.println("\nNote: For this operation you need to have more than one calendar!");
        List<MyCalendar> list;
        if (!checkForFullness()) {
            createCalendar();
        }
        list = MyCalendar.sortDesc(calendars);
        System.out.println();
        for (MyCalendar c : list) {
            showDate(c);
        }
    }

    private boolean checkForFullness() {
        for (MyCalendar c : calendars) {
            if (c == null)
                return false;
        }
        return true;
    }

    private void add(String type) {
        System.out.print("Enter a value, which you want to add: ");
        String value = sc.nextLine();
        try {
            calendars[0].addToDate(Long.parseLong(value), type);
        } catch (IncorrectDataValueException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.print("Value has been added");
    }

    private void subtract(String type) {
        System.out.print("Enter a value, which you want to subtract: ");
        String value = sc.nextLine();
        try {
            calendars[0].subtractFromDate(Long.parseLong(value), type);
        } catch (IncorrectDataValueException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.print("Value has been subtracted");
    }

    private void printOperations() {
        System.out.println("\n1. Difference between two dates\n" +
                "2. Sort dates ascending\n" +
                "3. Sort dates descending\n" +
                "4. Add milliseconds\n" +
                "5. Add seconds\n" +
                "6. Add minutes\n" +
                "7. Add hours\n" +
                "8. Add days\n" +
                "9. Add months\n" +
                "10. Add years\n" +
                "11. Subtract milliseconds\n" +
                "12. Subtract seconds\n" +
                "13. Subtract minutes\n" +
                "14. Subtract hours\n" +
                "15. Subtract days\n" +
                "16. Subtract months\n" +
                "17. Subtract years\n" +
                "18. Show date\n");
    }

    private void editFormat() throws IncorrectInputException {
        printFormats();
        System.out.print("Enter format: ");
        String choice = sc.nextLine();
        formatOfDate = chooseFormat(choice);
        calendars[0].setFormat(formatOfDate);
    }

    private void createCalendar() throws IncorrectInputException {
        printFormats();
        System.out.print("\nEnter format: ");
        String choice = sc.nextLine();
        formatOfDate = chooseFormat(choice);
        System.out.print("Enter date in selected format: ");
        String date = sc.nextLine();
        MyCalendar calendar = null;
        try {
            calendar = new MyCalendar(date, formatOfDate);
        } catch (RusMonthException | IncorrectDataValueException e) {
            System.out.println(e.getMessage());
            return;
        }
        for (int i = 0; i < calendars.length; i++) {
            if (calendars[i] == null) {
                calendars[i] = calendar;
                break;
            }
        }
        System.out.println("Calendar has been created");
    }

    private void printFormats() {
        System.out.println("\nFormats:" +
                "\n1. dd/mm/yyyy" +
                "\n2. mm/dd/yyyy" +
                "\n3. mmm-dd-yyyy" +
                "\n4. dd-mmm-yyyy" +
                "\n5. dd/mm/yyyy 00:00" +
                "\n6. mm/dd/yyyy 00:00" +
                "\n7. mmm-dd-yyyy 00:00" +
                "\n8. dd-mmm-yyyy 00:00" +
                "\n9. dd/mm/yyyy 00:00:00" +
                "\n10. mm/dd/yyyy 00:00:00" +
                "\n11. mmm-dd-yyyy 00:00:00" +
                "\n12. dd-mmm-yyyy 00:00:00:000" +
                "\n13. dd/mm/yyyy 00:00:00:000" +
                "\n14. mm/dd/yyyy 00:00:00:000" +
                "\n15. mmm-dd-yyyy 00:00:00:000" +
                "\n16. dd-mmm-yyyy 00:00:00:000");
    }

    private String chooseFormat(String choice) throws IncorrectInputException {
        switch (choice) {
            case "1":
                return "dd/mm/yyyy";
            case "2":
                return "mm/dd/yyyy";
            case "3":
                return "mmm-dd-yyyy";
            case "4":
                return "dd-mmm-yyyy";
            case "5":
                return "dd/mm/yyyy 00:00";
            case "6":
                return "mm/dd/yyyy 00:00";
            case "7":
                return "mmm-dd-yyyy 00:00";
            case "8":
                return "dd-mmm-yyyy 00:00";
            case "9":
                return "dd/mm/yyyy 00:00:00";
            case "10":
                return "mm/dd/yyyy 00:00:00";
            case "11":
                return "mmm-dd-yyyy 00:00:00";
            case "12":
                return "dd-mmm-yyyy 00:00:00:000";
            case "13":
                return "dd/mm/yyyy 00:00:00:000";
            case "14":
                return "mm/dd/yyyy 00:00:00:000";
            case "15":
                return "mmm-dd-yyyy 00:00:00:000";
            case "16":
                return "dd-mmm-yyyy 00:00:00:000";
            default:
                throw new IncorrectInputException(choice + " is incorrect number of format. It should be from 1 to 16");
        }
    }
}