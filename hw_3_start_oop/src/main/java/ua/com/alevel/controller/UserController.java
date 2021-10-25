package ua.com.alevel.controller;

import ua.com.alevel.entity.User;
import ua.com.alevel.service.UserService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final UserService userService = new UserService();

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Select your option");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                crud(position, reader);
                position = reader.readLine();
                if (position.equals("0")) {
                    System.exit(0);
                }
                crud(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("1. Create user");
        System.out.println("2. Update user");
        System.out.println("3. Delete user");
        System.out.println("4. Find user by id");
        System.out.println("5. Find all users");
        System.out.println("0. Exit");
        System.out.println();
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1":
                create(reader);
                break;
            case "2":
                update(reader);
                break;
            case "3":
                delete(reader);
                break;
            case "4":
                findById(reader);
                break;
            case "5":
                findAll(reader);
                break;
        }
        runNavigation();
    }

    private void create(BufferedReader reader) {
        try {
            System.out.print("Please, enter name: ");
            String name = reader.readLine();
            System.out.print("Please, enter email: ");
            String email = reader.readLine();
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            boolean emailRegex = matcher.find();
            if (!emailRegex) {
                System.out.println("Incorrect email! Please, try again.");
                return;
            }
            System.out.print("Please, enter age: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            User user = new User();
            user.setAge(age);
            user.setName(name);
            user.setEmail(email);
            userService.create(user);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void update(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            System.out.print("Please, enter name: ");
            String name = reader.readLine();
            System.out.print("Please, enter age: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            User user = new User();
            user.setId(id);
            user.setAge(age);
            user.setName(name);
            userService.update(user);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void delete(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            userService.delete(id);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            User user = userService.findById(id);
            System.out.println("user = " + user);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findAll(BufferedReader reader) {
        User[] users = userService.findAll();
        if (users != null && users.length != 0) {
            for (int i = 0; i < users.length; i++) {
                System.out.println("user = " + users[i]);
            }
        } else {
            System.out.println("users are empty");
        }
    }
}
