package ua.com.alevel.db.impl;

import ua.com.alevel.db.GroupCSV;
import ua.com.alevel.entity.Group;
import ua.com.alevel.io.FileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupCSVImpl implements GroupCSV {

    private static final String PATH_GROUP = "src/main/resources/csv/group.csv";
    private static final String PATH_GROUP_STUDENT = "src/main/resources/csv/group_student.csv";
    private static final String PATH_STUDENT = "src/main/resources/csv/student.csv";
    private static GroupCSVImpl instance;

    public static GroupCSVImpl getInstance() {
        if (instance == null)
            instance = new GroupCSVImpl();
        return instance;
    }

    @Override
    public void create(Group entity) {
        entity.setId(UUID.randomUUID().toString());
        List<String[]> res = new ArrayList<>();
        String[] arr = new String[3];
        arr[0] = entity.getId();
        arr[1] = entity.getName();
        arr[2] = entity.getCurator();
        res.add(arr);
        FileHelper.write(res, true, PATH_GROUP);
    }

    @Override
    public void update(Group entity) {
        List<String[]> groups = FileHelper.read(PATH_GROUP);
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i)[0].equals(entity.getId())) {
                groups.get(i)[1] = entity.getName();
                groups.get(i)[2] = entity.getCurator();
            }
        }
        FileHelper.write(groups, false, PATH_GROUP);
    }

    @Override
    public void delete(String id) {
        if (!existsById(id))
            return;
        List<String[]> groups = FileHelper.read(PATH_GROUP);
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i)[0].equals(id)) {
                groups.remove(i);
            }
        }
        FileHelper.write(groups, false, PATH_GROUP);
        List<String[]> groupStudent = FileHelper.read(PATH_GROUP_STUDENT);
        List<String> studentIds = new ArrayList<>();
        for (int i = 0; i < groupStudent.size(); i++) {
            if (groupStudent.get(i)[0].equals(id)) {
                studentIds.add(groupStudent.get(i)[1]);
                groupStudent.remove(i);
                i--;
            }
        }
        FileHelper.write(groupStudent, false, PATH_GROUP_STUDENT);
        List<String[]> students = FileHelper.read(PATH_STUDENT);
        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < studentIds.size(); j++) {
                if (students.get(i)[0].equals(studentIds.get(j))) {
                    students.remove(i);
                    i--;
                    break;
                }
            }
        }
        FileHelper.write(students, false, PATH_STUDENT);
    }

    @Override
    public boolean existsById(String id) {
        List<String[]> groups = FileHelper.read(PATH_GROUP);
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i)[0].equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Group findById(String id) {
        List<String[]> groups = FileHelper.read(PATH_GROUP);
        Group group = null;
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i)[0].equals(id)) {
                group = new Group();
                group.setId(groups.get(i)[0]);
                group.setName(groups.get(i)[1]);
                group.setCurator(groups.get(i)[2]);
            }
        }
        return group;
    }

    @Override
    public List<Group> findAll() {
        List<String[]> groups = FileHelper.read(PATH_GROUP);
        List<Group> res = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            Group group = new Group();
            group.setId(groups.get(i)[0]);
            group.setName(groups.get(i)[1]);
            group.setCurator(groups.get(i)[2]);
            res.add(group);
        }
        return res;
    }
}