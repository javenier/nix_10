package ua.com.alevel.dao;

import ua.com.alevel.db.DBStudent;
import ua.com.alevel.entity.Student;

public class StudentDao {

    public void create(Student student) {
        DBStudent.getInstance().create(student);
    }

    public void update(Student student) {
        DBStudent.getInstance().update(student);
    }

    public void delete(String id) {
        DBStudent.getInstance().delete(id);
    }

    public Student findById(String id) {
        return DBStudent.getInstance().findById(id);
    }

    public Student[] findAll() {
        return DBStudent.getInstance().findAll();
    }
}
