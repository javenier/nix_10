package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.entity.Student;

public class StudentService {

    private final StudentDao studentDao = new StudentDao();

    private static Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    public void create(Student student) {
        LOGGER_INFO.info("start creating a student");
        studentDao.create(student);
        LOGGER_INFO.info("finish creating a student");
    }

    public void update(Student student) {
        LOGGER_INFO.info("start updating a student");
        studentDao.update(student);
        LOGGER_INFO.info("finish updating a student");
    }

    public void delete(String id) {
        LOGGER_INFO.info("start deleting a student");
        studentDao.delete(id);
        LOGGER_INFO.info("finish deleting a student");
    }

    public Student findById(String id) {
        LOGGER_INFO.info("start searching for a student");
        Student student = studentDao.findById(id);
        if (student == null) {
            LOGGER_WARN.warn("student has not been found");
        } else {
            LOGGER_INFO.info("student has been found");
        }
        return student;
    }

    public Student[] findAll() {
        LOGGER_INFO.info("start searching for all students");
        Student[] students = studentDao.findAll();
        if (students == null || students.length == 0) {
            LOGGER_WARN.warn("students are empty");
        } else {
            LOGGER_INFO.info("finish searching for all students");
        }
        return students;
    }
}
