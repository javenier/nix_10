package ua.com.alevel.db.impl;

import ua.com.alevel.db.StudentCSV;
import ua.com.alevel.entity.Student;
import ua.com.alevel.io.FileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentCSVImpl implements StudentCSV {

    private static final String PATH_STUDENT = "src/main/resources/csv/student.csv";
    private static final String PATH_GROUP_STUDENT = "src/main/resources/csv/group_student.csv";
    private static StudentCSVImpl instance;

    public static StudentCSVImpl getInstance() {
        if (instance == null)
            instance = new StudentCSVImpl();
        return instance;
    }

    @Override
    public void create(Student entity) {
        entity.setId(UUID.randomUUID().toString());
        List<String[]> res = new ArrayList<>();
        String[] arr = new String[3];
        arr[0] = entity.getId();
        arr[1] = entity.getName();
        arr[2] = String.valueOf(entity.getAge());
        res.add(arr);
        FileHelper.write(res, true, PATH_STUDENT);
        res.clear();
        String[] arr2 = new String[2];
        arr2[0] = entity.getGroupId();
        arr2[1] = entity.getId();
        res.add(arr2);
        FileHelper.write(res, true, PATH_GROUP_STUDENT);
    }

    @Override
    public void update(Student entity) {
        List<String[]> students = FileHelper.read(PATH_STUDENT);
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i)[0].equals(entity.getId())) {
                students.get(i)[1] = entity.getName();
                students.get(i)[2] = String.valueOf(entity.getAge());
            }
        }
        FileHelper.write(students, false, PATH_STUDENT);
    }

    @Override
    public void delete(String id) {
        if (!existsById(id))
            return;
        List<String[]> students = FileHelper.read(PATH_STUDENT);
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i)[0].equals(id)) {
                students.remove(i);
            }
        }
        FileHelper.write(students, false, PATH_STUDENT);
        List<String[]> groupStudent = FileHelper.read(PATH_GROUP_STUDENT);
        for (int i = 0; i < groupStudent.size(); i++) {
            if (groupStudent.get(i)[1].equals(id)) {
                groupStudent.remove(i);
            }
        }
        FileHelper.write(groupStudent, false, PATH_GROUP_STUDENT);
    }

    @Override
    public boolean existsById(String id) {
        List<String[]> students = FileHelper.read(PATH_STUDENT);
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i)[0].equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Student findById(String id) {
        List<String[]> students = FileHelper.read(PATH_STUDENT);
        Student student = null;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i)[0].equals(id)) {
                student = new Student();
                student.setId(students.get(i)[0]);
                student.setName(students.get(i)[1]);
                student.setAge(Integer.parseInt(students.get(i)[2]));
            }
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        List<String[]> students = FileHelper.read(PATH_STUDENT);
        List<Student> res = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            Student student = new Student();
            student.setId(students.get(i)[0]);
            student.setName(students.get(i)[1]);
            student.setAge(Integer.parseInt(students.get(i)[2]));
            res.add(student);
        }
        return res;
    }

    @Override
    public List<Student> findAllByGroupId(String groupId) {
        List<String[]> groupStudent = FileHelper.read(PATH_GROUP_STUDENT);
        List<String> studentIds = new ArrayList<>();
        List<Student> res = new ArrayList<>();
        for (int i = 0; i < groupStudent.size(); i++) {
            if (groupStudent.get(i)[0].equals(groupId)) {
                studentIds.add(groupStudent.get(i)[1]);
            }
        }
        List<String[]> students = FileHelper.read(PATH_STUDENT);
        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < studentIds.size(); j++) {
                if (students.get(i)[0].equals(studentIds.get(j))) {
                    Student student = new Student();
                    student.setId(students.get(i)[0]);
                    student.setName(students.get(i)[1]);
                    student.setAge(Integer.parseInt(students.get(i)[2]));
                    student.setGroupId(groupId);
                    res.add(student);
                }
            }
        }
        return res;
    }
}