package ua.com.alevel.controller;

import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.CourseService;
import ua.com.alevel.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StudentController {

    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

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

    private void runNavigation() {
        System.out.println();
        System.out.println("1. Create student");
        System.out.println("2. Update student");
        System.out.println("3. Delete student");
        System.out.println("4. Find student by id");
        System.out.println("5. Find all students");
        System.out.println("0. Go back");
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
        if (!position.equals("0"))
            runNavigation();
    }

    private void create(BufferedReader reader) {
        try {
            System.out.print("Please, enter name: ");
            String name = reader.readLine();
            System.out.print("Please, enter age: ");
            String age = reader.readLine();
            System.out.println("Available courses: ");
            Course[] courses = courseService.findAll();
            for (Course c : courses) {
                if (c != null)
                    System.out.print(c.getName() + " ");
            }
            System.out.print("\nPlease, enter courses by space: ");
            String[] coursesString = reader.readLine().split(" ");
            Student student = new Student();
            student.setName(name);
            student.setAge(Integer.parseInt(age));
            for (String src : coursesString) {
                for (Course crs : courses) {
                    if (crs != null) {
                        if (src.equals(crs.getName())) {
                            student.addCourse(crs);
                            crs.addStudent(student);
                        }
                    }
                }
            }
            studentService.create(student);
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

    private void delete(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            studentService.delete(id);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            Student student = studentService.findById(id);
            System.out.println("student = " + student);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findAll(BufferedReader reader) {
        Student[] students = studentService.findAll();
        int studentsCount = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i] != null) {
                System.out.print("student = name: " +
                        students[i].getName() +
                        ", id: " +
                        students[i].getId() +
                        ", age: "
                        + students[i].getAge()
                        + ", courses = ");
                Course[] courses = students[i].getCourses();
                for (Course c : courses) {
                    if (c != null) {
                        System.out.print("name: " + c.getName() + " | ");
                    }
                }
                System.out.println();
                studentsCount++;
            }
        }
        if (studentsCount == 0)
            System.out.println("Students are empty");
    }
}
