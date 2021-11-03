package ua.com.alevel.service;

import ua.com.alevel.dao.CourseDao;
import ua.com.alevel.entity.Course;

public class CourseService {

    private final CourseDao courseDao = new CourseDao();

    public void create(Course course) {
        courseDao.create(course);
    }

    public void update(Course course) {
        courseDao.update(course);
    }

    public void delete(String id) {
        courseDao.delete(id);
    }

    public Course findById(String id) {
        return courseDao.findById(id);
    }

    public Course[] findAll() {
        return courseDao.findAll();
    }
}
