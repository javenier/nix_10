package ua.com.alevel.controller;

import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.CourseService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CourseController {

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
        System.out.println("1. Create course");
        System.out.println("2. Update course");
        System.out.println("3. Delete course");
        System.out.println("4. Find course by id");
        System.out.println("5. Find all courses");
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
            System.out.print("Please, enter teacher's name: ");
            String teacher = reader.readLine();
            Course course = new Course();
            course.setName(name);
            course.setTeacher(teacher);
            courseService.create(course);
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
            System.out.print("Please, enter teachers's name: ");
            String teacher = reader.readLine();
            Course course = new Course();
            course.setId(id);
            course.setName(name);
            course.setTeacher(teacher);
            courseService.update(course);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void delete(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            courseService.delete(id);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            Course course = courseService.findById(id);
            System.out.println("course = " + course);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findAll(BufferedReader reader) {
        Course[] courses = courseService.findAll();
        int courseCount = 0;
        for (int i = 0; i < courses.length; i++) {
            if (courses[i] != null) {
                System.out.print("course = name: " +
                        courses[i].getName() +
                        ", id: " +
                        courses[i].getId() +
                        ", teacher: " +
                        courses[i].getTeacher() + ", students =  ");
                Student[] students = courses[i].getStudents();
                for (Student st : students) {
                    if (st != null) {
                        System.out.print(st.getName() + " | ");
                    }
                }
                System.out.println();
                courseCount++;
            }
        }
        if (courseCount == 0)
            System.out.println("Courses are empty");
    }
}
