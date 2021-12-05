package ua.com.alevel.controller.impl;

import ua.com.alevel.controller.GroupController;
import ua.com.alevel.entity.Group;
import ua.com.alevel.service.GroupService;
import ua.com.alevel.service.impl.GroupServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class GroupControllerImpl implements GroupController {

    private final GroupService groupService = new GroupServiceImpl();

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Select your option");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                if (position.equals("0")) {
                    return;
                }
                crud(position, reader);
                position = reader.readLine();
                if (position.equals("0")) {
                    return;
                }
                crud(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void runNavigation() {
        System.out.println();
        System.out.println("1. Create group");
        System.out.println("2. Update group");
        System.out.println("3. Delete group");
        System.out.println("4. Find group by id");
        System.out.println("5. Find all groups");
        System.out.println("0. Go back");
        System.out.println();
    }

    @Override
    public void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1":
                create(reader);
                break;
            case "2":
                update(reader);
                break;
            case "3":
                delete(reader);
                break;
            case "4":
                findById(reader);
                break;
            case "5":
                findAll(reader);
                break;
        }
        if (!position.equals("0"))
            runNavigation();
    }

    @Override
    public void create(BufferedReader reader) {
        try {
            System.out.print("Please, enter name: ");
            String name = reader.readLine();
            System.out.print("Please, enter curator's name: ");
            String curator = reader.readLine();
            Group group = new Group();
            group.setName(name);
            group.setCurator(curator);
            groupService.create(group);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void update(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            System.out.print("Please, enter name: ");
            String name = reader.readLine();
            System.out.print("Please, enter curator's name: ");
            String curator = reader.readLine();
            Group group = new Group();
            group.setId(id);
            group.setName(name);
            group.setCurator(curator);
            groupService.update(group);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void delete(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            groupService.delete(id);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void findById(BufferedReader reader) {
        try {
            System.out.print("Please, enter id: ");
            String id = reader.readLine();
            Group group = groupService.findById(id);
            System.out.println("group = " + group);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    @Override
    public void findAll(BufferedReader reader) {
        List<Group> groups = groupService.findAll();
        for (Group group : groups) {
            System.out.println(group);
        }
        if (groups.size() == 0)
            System.out.println("Groups are empty");
    }
}