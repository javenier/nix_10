package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.CourseDao;
import ua.com.alevel.entity.Course;

public class CourseService {

    private final CourseDao courseDao = new CourseDao();

    private static Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    public void create(Course course) {
        LOGGER_INFO.info("start creating course");
        courseDao.create(course);
        LOGGER_INFO.info("finish creating course");
    }

    public void update(Course course) {
        LOGGER_INFO.info("start updating course");
        courseDao.update(course);
        LOGGER_INFO.info("finish updating course");
    }

    public void delete(String id) {
        LOGGER_INFO.info("start deleting course");
        courseDao.delete(id);
        LOGGER_INFO.info("finish deleting course");
    }

    public Course findById(String id) {
        LOGGER_INFO.info("start searching for a course");
        Course foundCourse = courseDao.findById(id);
        if (foundCourse == null) {
            LOGGER_WARN.warn("course has not been found");
        } else {
            LOGGER_INFO.info("course has been found");
        }
        return foundCourse;
    }

    public Course[] findAll() {
        LOGGER_INFO.info("start searching for all courses");
        Course[] courses = courseDao.findAll();
        if (courses.length == 0 || courses == null) {
            LOGGER_WARN.warn("courses are empty");
        } else {
            LOGGER_INFO.info("finish searching for all courses");
        }
        return courses;
    }
}
