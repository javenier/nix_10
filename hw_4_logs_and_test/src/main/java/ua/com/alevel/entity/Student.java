package ua.com.alevel.entity;

import java.util.Arrays;

public class Student {

    private String id;
    private String name;
    private int age;
    private Course[] courses;
    private int coursesCount;

    public Student() {
        courses = new Course[5];
        coursesCount = 0;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        if (courses.length == coursesCount)
            courses = Arrays.copyOf(courses, coursesCount + 5);
        courses[coursesCount++] = course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", courses=" + Arrays.toString(courses) +
                '}';
    }

    public void deleteCourse(String id) {
        int index = -1;
        for (int i = 0; i < coursesCount; i++) {
            if (courses[i].getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index < 0 || courses == null || index >= coursesCount) {
            System.out.println("Course not found");
            return;
        }
        Course[] temp = new Course[courses.length - 1];
        for (int i = 0, k = 0; i < courses.length; i++) {
            if (i == index) {
                continue;
            }
            temp[k++] = courses[i];
        }
        courses = Arrays.copyOf(temp, temp.length);
        coursesCount--;
    }
}
