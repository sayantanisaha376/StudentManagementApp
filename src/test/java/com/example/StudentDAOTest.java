package com.example;



import com.example.DAO.StudentDAO;
import com.example.DAO.StudentDAOImpl;
import com.example.entity.Student;
import com.example.exception.DatabaseOperationsException;
import com.example.exception.StudentNotFoundException;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentDAOTest {

    private static StudentDAO dao;

    @BeforeAll
    static void init() {
        dao = new StudentDAOImpl();
    }

    @Test
    @Order(1)
    void testAddStudent() throws DatabaseOperationsException {
        Student s = new Student(101, "Souparno", 22, "Java");
        assertDoesNotThrow(() -> dao.addStudent(s));
    }

    @Test
    @Order(2)
    void testUpdateStudent() {
        Student s = new Student(101, "Souparno Updated", 23, "Python");
        assertDoesNotThrow(() -> dao.updateStudent(s));
    }

    @Test
    @Order(3)
    void testGetStudentById() {
        assertDoesNotThrow(() -> {
            Student s = dao.getStudentById(101);
            assertNotNull(s);
            assertEquals("Souparno Updated", s.getName());
        });
    }

    @Test
    @Order(4)
    void testGetAllStudents() throws DatabaseOperationsException {
        List<Student> students = dao.getAllStudents();
        assertFalse(students.isEmpty());
    }

    @Test
    @Order(5)
    void testDeleteStudent() {
        assertDoesNotThrow(() -> dao.deleteStudent(101));
    }
}
