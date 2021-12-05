package ua.com.alevel.db;

import ua.com.alevel.entity.Student;

import java.util.List;

public interface StudentCSV extends BaseCSV<Student> {

    List<Student> findAllByGroupId(String groupId);
}