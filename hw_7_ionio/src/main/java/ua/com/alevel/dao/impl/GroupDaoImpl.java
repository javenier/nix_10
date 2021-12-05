package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.GroupDao;
import ua.com.alevel.db.impl.GroupCSVImpl;
import ua.com.alevel.entity.Group;

import java.util.List;

public class GroupDaoImpl implements GroupDao {

    @Override
    public void create(Group entity) {
        GroupCSVImpl.getInstance().create(entity);
    }

    @Override
    public void update(Group entity) {
        GroupCSVImpl.getInstance().update(entity);
    }

    @Override
    public void delete(String id) {
        GroupCSVImpl.getInstance().delete(id);
    }

    @Override
    public boolean existsById(String id) {
        return GroupCSVImpl.getInstance().existsById(id);
    }

    @Override
    public Group findById(String id) {
        return GroupCSVImpl.getInstance().findById(id);
    }

    @Override
    public List<Group> findAll() {
        return GroupCSVImpl.getInstance().findAll();
    }
}