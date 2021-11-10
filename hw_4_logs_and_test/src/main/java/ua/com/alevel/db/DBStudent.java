package ua.com.alevel.db;

import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;

import java.util.Arrays;
import java.util.UUID;

public class DBStudent {

    private Student[] students;
    private static DBStudent instance;
    private static int studentsCount;

    private DBStudent() {
        students = new Student[5];
        studentsCount = 0;
    }

    public int getCount() {
        return this.studentsCount;
    }

    public static DBStudent getInstance() {
        if (instance == null)
            instance = new DBStudent();
        return instance;
    }

    public void create(Student student) {
        student.setId(generateId());
        if (students.length == studentsCount)
            students = Arrays.copyOf(students, studentsCount + 5);
        students[studentsCount++] = student;
    }

    public void update(Student student) {
        Student current = findById(student.getId());
        if (current == null)
            return;
        current.setName(student.getName());
        current.setAge(student.getAge());
    }

    public void delete(String id) {
        int index = -1;
        for (int i = 0; i < studentsCount; i++) {
            if (students[i].getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index < 0 || students == null || index >= studentsCount) {
            System.out.println("Student not found");
            return;
        }
        Student forDelete = students[index];
        Course[] courses = forDelete.getCourses();
        for (Course c : courses) {
            if (c != null) {
                Student[] courseStudents = c.getStudents();
                for (Student s : courseStudents) {
                    if (s != null) {
                        if (s.getId().equals(id)) {
                            c.deleteStudent(id);
                        }
                    }
                }
            }
        }
        Student[] temp = new Student[students.length - 1];
        for (int i = 0, k = 0; i < students.length; i++) {
            if (i == index) {
                continue;
            }
            temp[k++] = students[i];
        }
        students = Arrays.copyOf(temp, temp.length);
        studentsCount--;
    }

    public Student findById(String id) {
        for (int i = 0; i < students.length; i++) {
            if (students[i].getId().equals(id)) {
                return students[i];
            }
        }
        System.out.println("Student not found");
        return null;
    }

    public Student[] findAll() {
        return students;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        for (int i = 0; i < studentsCount; i++) {
            if (students[i].getId().equals(id))
                return generateId();
        }
        return id;
    }
}
