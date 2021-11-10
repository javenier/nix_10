package ua.com.alevel;

import org.junit.jupiter.api.*;
import ua.com.alevel.entity.Course;
import ua.com.alevel.service.CourseService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseServiceTest {

    private static final CourseService courseService = new CourseService();

    private static final String NAME = "name";
    private static final String NAME_UPDATE = "name_update";
    private static final String TEACHER = "teacher";
    private static final String TEACHER_UPDATE = "teacher_update";
    private static final int DEFAULT_SIZE = 5;

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Course course = generateCourse(NAME + i, TEACHER + i);
            courseService.create(course);
        }
        Assertions.assertEquals(courseService.findAll().length, DEFAULT_SIZE);
    }

    @Test
    @Order(1)
    public void shouldBeUpdateCourseById() {
        Course course = getRandomCourse();
        course.setName(NAME_UPDATE);
        course.setTeacher(TEACHER_UPDATE);
        courseService.update(course);
        course = courseService.findById(course.getId());
        Assertions.assertEquals(TEACHER_UPDATE, course.getTeacher());
        Assertions.assertEquals(NAME_UPDATE, course.getName());
    }

    @Test
    @Order(2)
    public void shouldBeFindCourseById() {
        Course course = getRandomCourse();
        String id = course.getId();
        Course foundCourse = courseService.findById(id);
        Assertions.assertEquals(course, foundCourse);
    }

    @Test
    @Order(3)
    public void shouldBeDeleteCourseById() {
        Course course = getRandomCourse();
        String id = course.getId();
        courseService.delete(id);
        boolean exists = existsById(id);
        Assertions.assertFalse(exists);
    }

    private boolean existsById(String id) {
        for (Course c : courseService.findAll()) {
            if (c.getId().equals(id))
                return true;
        }
        return false;
    }

    private static Course generateCourse(String name, String teacher) {
        Course course = new Course();
        course.setName(name);
        course.setTeacher(teacher);
        return course;
    }

    private Course getRandomCourse() {
        Course rndCourse = courseService.findAll()[(int) (Math.random() * DEFAULT_SIZE)];
        if (rndCourse == null)
            return courseService.findAll()[0];
        return rndCourse;
    }
}
