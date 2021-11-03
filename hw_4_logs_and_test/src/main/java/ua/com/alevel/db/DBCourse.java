package ua.com.alevel.db;

import ua.com.alevel.entity.Course;

import java.util.Arrays;
import java.util.UUID;

public class DBCourse {

    private Course[] courses;
    private static DBCourse instance;
    private static int coursesCount;

    private DBCourse() {
        courses = new Course[5];
        coursesCount = 0;
    }

    public int getCount() {
        return this.coursesCount;
    }

    public static DBCourse getInstance() {
        if (instance == null)
            instance = new DBCourse();
        return instance;
    }

    public void create(Course course) {
        course.setId(generateId());
        if (courses.length == coursesCount)
            courses = Arrays.copyOf(courses, coursesCount + 5);
        courses[coursesCount++] = course;
    }

    public void update(Course course) {
        Course current = findById(course.getId());
        if (current == null)
            return;
        current.setName(course.getName());
        current.setTeacher(course.getTeacher());
    }

    public void delete(String id) {
        int index = -1;
        for (int i = 0; i < courses.length; i++) {
            if (courses[i].getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index < 0 || courses == null || index >= courses.length) {
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
    }

    public Course findById(String id) {
        for (int i = 0; i < courses.length; i++) {
            if (courses[i].getId().equals(id)) {
                return courses[i];
            }
        }
        System.out.println("Course not found");
        return null;
    }

    public Course[] findAll() {
        return courses;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        for (int i = 0; i < courses.length; i++) {
            if (courses[i].getId().equals(id))
                return generateId();
        }
        return id;
    }
}
