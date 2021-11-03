package ua.com.alevel.service;

import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.entity.Student;

public class StudentService {

    private final StudentDao studentDao = new StudentDao();

    public void create(Student student) {
        studentDao.create(student);
    }

    public void update(Student student) {
        studentDao.update(student);
    }

    public void delete(String id) {
        studentDao.delete(id);
    }

    public Student findById(String id) {
        return studentDao.findById(id);
    }

    public Student[] findAll() {
        return studentDao.findAll();
    }
}
