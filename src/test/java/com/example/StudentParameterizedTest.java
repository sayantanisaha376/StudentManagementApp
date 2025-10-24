package com.example;



import com.example.DAO.StudentDAO;
import com.example.DAO.StudentDAOImpl;
import com.example.entity.Student;
import com.example.exception.DatabaseOperationsException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class StudentParameterizedTest {

    private final StudentDAO dao = new StudentDAOImpl();

    @ParameterizedTest
    @CsvSource({
            "51, Tam, 21, CSE",
            "61, Maddy, 20, ECE",
            "71, KK2, 23, IT"
    })
    void testAddMultipleStudents(int id, String name, int age, String course) {
        assertDoesNotThrow(() -> dao.addStudent(new Student(id, name, age, course)));
    }
}

