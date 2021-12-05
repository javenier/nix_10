package ua.com.alevel;

import org.junit.jupiter.api.*;
import ua.com.alevel.entity.Group;
import ua.com.alevel.service.GroupService;
import ua.com.alevel.service.impl.GroupServiceImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupServiceTest {

    private static final GroupService groupService = new GroupServiceImpl();

    private static final String NAME = "name";
    private static final String NAME_UPDATE = "name_update";
    private static final String CURATOR = "curator";
    private static final String CURATOR_UPDATE = "curator_update";

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < 5; i++) {
            Group group = generateGroup(NAME + i, CURATOR + i);
            groupService.create(group);
        }
        Assertions.assertEquals(groupService.findAll().size(), 5);
    }

    @Test
    @Order(1)
    public void shouldBeUpdateGroupById() {
        Group group = getRandomGroup();
        group.setName(NAME_UPDATE);
        group.setCurator(CURATOR_UPDATE);
        groupService.update(group);
        group = groupService.findById(group.getId());
        Assertions.assertEquals(CURATOR_UPDATE, group.getCurator());
        Assertions.assertEquals(NAME_UPDATE, group.getName());
    }

    @Test
    @Order(2)
    public void shouldBeFindGroupById() {
        Group group = getRandomGroup();
        String id = group.getId();
        Group foundGroup = groupService.findById(id);
        Assertions.assertEquals(group.getId(), foundGroup.getId());
        Assertions.assertEquals(group.getName(), foundGroup.getName());
        Assertions.assertEquals(group.getCurator(), foundGroup.getCurator());
    }

    @Test
    @Order(3)
    public void shouldBeDeleteGroupById() {
        Group group = getRandomGroup();
        String id = group.getId();
        groupService.delete(id);
        boolean exists = existsById(id);
        Assertions.assertFalse(exists);
    }

    private boolean existsById(String id) {
        for (Group g : groupService.findAll()) {
            if (g.getId().equals(id))
                return true;
        }
        return false;
    }

    private static Group generateGroup(String name, String curator) {
        Group group = new Group();
        group.setName(name);
        group.setCurator(curator);
        return group;
    }

    private Group getRandomGroup() {
        Group group = groupService.findAll().get((int) (Math.random() * 5));
        if (group == null)
            return groupService.findAll().get(0);
        return group;
    }
}