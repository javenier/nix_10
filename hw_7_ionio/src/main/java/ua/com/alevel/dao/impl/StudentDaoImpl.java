package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.db.impl.StudentCSVImpl;
import ua.com.alevel.entity.Student;

import java.util.List;

public class StudentDaoImpl implements StudentDao {

    @Override
    public void create(Student entity) {
        StudentCSVImpl.getInstance().create(entity);
    }

    @Override
    public void update(Student entity) {
        StudentCSVImpl.getInstance().update(entity);
    }

    @Override
    public void delete(String id) {
        StudentCSVImpl.getInstance().delete(id);
    }

    @Override
    public boolean existsById(String id) {
        return StudentCSVImpl.getInstance().existsById(id);
    }

    @Override
    public Student findById(String id) {
        return StudentCSVImpl.getInstance().findById(id);
    }

    @Override
    public List<Student> findAll() {
        return StudentCSVImpl.getInstance().findAll();
    }

    @Override
    public List<Student> findAllByGroupId(String groupId) {
        return StudentCSVImpl.getInstance().findAllByGroupId(groupId);
    }
}