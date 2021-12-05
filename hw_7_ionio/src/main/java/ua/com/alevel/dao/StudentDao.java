package ua.com.alevel.dao;

import ua.com.alevel.entity.Student;

import java.util.List;

public interface StudentDao extends BaseDao<Student> {

    List<Student> findAllByGroupId(String groupId);
}