package ua.com.alevel.entity;

import java.util.Arrays;

public class Course {

    private String id;
    private String name;
    private String teacher;
    private Student[] students;
    private int studentsCount;

    public Course() {
        students = new Student[5];
        studentsCount = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        if (students.length == studentsCount)
            students = Arrays.copyOf(students, studentsCount + 5);
        students[studentsCount++] = student;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", students=" + Arrays.toString(students) +
                '}';
    }

    public void deleteStudent(String id) {
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
}
