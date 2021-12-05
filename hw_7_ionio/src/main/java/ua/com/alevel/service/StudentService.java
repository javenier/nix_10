package ua.com.alevel.service;

import ua.com.alevel.entity.Student;

import java.util.List;

public interface StudentService extends BaseService<Student> {

    List<Student> findAllByGroupId(String groupId);
}