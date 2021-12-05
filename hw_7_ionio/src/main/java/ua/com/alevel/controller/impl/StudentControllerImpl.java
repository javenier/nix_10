package ua.com.alevel.controller.impl;

import ua.com.alevel.controller.StudentController;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.StudentService;
import ua.com.alevel.service.impl.StudentServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class StudentControllerImpl implements StudentController {

    private final StudentService studentService = new StudentServiceImpl();

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Select your option");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                if (position.equals("0")) {
                    return;
                }
                crud(position, reader);
                position = reader.readLine();
                if (position.equals("0")) {
                    return;
                }
                crud(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void runNavigation() {
        System.out.println();
        System.out.println("1. Create student");
        System.out.println("2. Update student");
        System.out.println("3. Delete student");
        System.out.println("4. Find student by id");
        System.out.println("5. Find all students");
        System.out.println("6. Find all students by group id");
        System.out.println("0. Go back");
        System.out.println();
    }

    @Override
    public void crud(String position, BufferedReader reader) {
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
            case "6":
                findAllByGroupId(reader);
                break;
        }
        if (!position.equals("0"))
            runNavigation();
    }

    @Override
    public void create(BufferedReader reader) {
        try {
            System.out.print("Please, enter name: ");
            String name = reader.readLine();
            System.out.print("Please, enter age: ");
            String age = reader.readLine();
            System.out.print("Please, enter group id: ");
            String groupId = reader.readLine();
            Student student = new Student();
            student.setName(name);
            student.setAge(Integer.parseInt(age));
            student.setGroupId(groupId);
            studentService.create(student);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void update(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            System.out.print("Please, enter name: ");
            String name = reader.readLine();
            System.out.print("Please, enter age: ");
            String age = reader.readLine();
            Student student = new Student();
            student.setId(id);
            student.setName(name);
            student.setAge(Integer.parseInt(age));
            studentService.update(student);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void delete(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            studentService.delete(id);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void findById(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            Student student = studentService.findById(id);
            System.out.println("student = " + student);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void findAll(BufferedReader reader) {
        List<Student> students = studentService.findAll();
        for (Student student : students) {
            System.out.println(student);
        }
        if (students.size() == 0)
            System.out.println("Students are empty");
    }

    @Override
    public void findAllByGroupId(BufferedReader reader) {
        try {
            System.out.print("Please, enter group id: ");
            String groupId = reader.readLine();
            List<Student> students = studentService.findAllByGroupId(groupId);
            for (Student student : students) {
                System.out.println(student);
            }
            if (students.size() == 0)
                System.out.println("Students are empty");
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }
}