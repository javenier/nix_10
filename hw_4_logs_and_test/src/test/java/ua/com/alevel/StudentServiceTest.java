package ua.com.alevel;

import org.junit.jupiter.api.*;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.StudentService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceTest {

    private static final StudentService studentService = new StudentService();

    private static final String NAME = "name";
    private static final String NAME_UPDATE = "name_update";
    private static final int AGE_UPDATE = 25;
    private static final int DEFAULT_SIZE = 5;

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Student student = generateStudent(NAME + i, i);
            studentService.create(student);
        }
        Assertions.assertEquals(studentService.findAll().length, DEFAULT_SIZE);
    }

    @Test
    @Order(1)
    public void shouldBeUpdateStudentById() {
        Student student = getRandomStudent();
        student.setName(NAME_UPDATE);
        student.setAge(AGE_UPDATE);
        student = studentService.findById(student.getId());
        Assertions.assertEquals(AGE_UPDATE, student.getAge());
        Assertions.assertEquals(NAME_UPDATE, student.getName());
    }

    @Test
    @Order(2)
    public void shouldBeFindStudentById() {
        Student student = getRandomStudent();
        String id = student.getId();
        Student foundStudent = studentService.findById(id);
        Assertions.assertEquals(student, foundStudent);
    }

    @Test
    @Order(3)
    public void shouldBeDeleteStudentById() {
        Student student = getRandomStudent();
        String id = student.getId();
        studentService.delete(id);
        boolean exists = existsById(id);
        Assertions.assertFalse(exists);
    }

    private boolean existsById(String id) {
        for (Student c : studentService.findAll()) {
            if (c.getId().equals(id))
                return true;
        }
        return false;
    }

    private static Student generateStudent(String name, int age) {
        Student student = new Student();
        student.setAge(age);
        student.setName(name);
        return student;
    }

    private Student getRandomStudent() {
        Student rndStudent = studentService.findAll()[(int) (Math.random() * DEFAULT_SIZE)];
        if (rndStudent == null)
            return studentService.findAll()[0];
        return rndStudent;
    }
}
