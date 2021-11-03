package ua.com.alevel.dao;

import ua.com.alevel.db.DBCourse;
import ua.com.alevel.entity.Course;

public class CourseDao {

    public void create(Course course) {
        DBCourse.getInstance().create(course);
    }

    public void update(Course course) {
        DBCourse.getInstance().update(course);
    }

    public void delete(String id) {
        DBCourse.getInstance().delete(id);
    }

    public Course findById(String id) {
        return DBCourse.getInstance().findById(id);
    }

    public Course[] findAll() {
        return DBCourse.getInstance().findAll();
    }
}
