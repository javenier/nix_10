package ua.com.alevel.service.impl;

import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.dao.impl.StudentDaoImpl;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao = new StudentDaoImpl();

    @Override
    public void create(Student entity) {
        studentDao.create(entity);
    }

    @Override
    public void update(Student entity) {
        studentDao.update(entity);
    }

    @Override
    public void delete(String id) {
        studentDao.delete(id);
    }

    @Override
    public boolean existsById(String id) {
        return studentDao.existsById(id);
    }

    @Override
    public Student findById(String id) {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public List<Student> findAllByGroupId(String groupId) {
        return studentDao.findAllByGroupId(groupId);
    }
}