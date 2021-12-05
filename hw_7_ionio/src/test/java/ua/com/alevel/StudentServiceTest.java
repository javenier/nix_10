package ua.com.alevel;

import org.junit.jupiter.api.*;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.StudentService;
import ua.com.alevel.service.impl.StudentServiceImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceTest {

    private final static StudentService studentService = new StudentServiceImpl();

    private static final String NAME = "name";
    private static final String NAME_UPDATE = "name_update";
    private static final int AGE_UPDATE = 30;

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < 5; i++) {
            Student student = generateStudent(NAME + i, i);
            studentService.create(student);
        }
        Assertions.assertEquals(studentService.findAll().size(), 5);
    }

    @Test
    @Order(1)
    public void shouldBeUpdateStudentById() {
        Student student = getRandomStudent();
        student.setName(NAME_UPDATE);
        student.setAge(AGE_UPDATE);
        studentService.update(student);
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
        Assertions.assertEquals(student.getId(), foundStudent.getId());
        Assertions.assertEquals(student.getAge(), foundStudent.getAge());
        Assertions.assertEquals(student.getName(), foundStudent.getName());
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
        Student rndStudent = studentService.findAll().get((int) (Math.random() * 5));
        if (rndStudent == null)
            return studentService.findAll().get(0);
        return rndStudent;
    }
}