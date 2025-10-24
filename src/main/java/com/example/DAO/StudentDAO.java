package com.example.DAO;


import com.example.entity.Student;
import com.example.exception.DatabaseOperationsException;
import com.example.exception.StudentNotFoundException;

import java.util.List;




public interface StudentDAO {
    void addStudent(Student s) throws DatabaseOperationsException;                     // Add a new student
    void updateStudent(Student s) throws StudentNotFoundException, DatabaseOperationsException;  // Update existing student
    void deleteStudent(int id) throws StudentNotFoundException, DatabaseOperationsException;     // Delete student by ID
    Student getStudentById(int id) throws StudentNotFoundException, DatabaseOperationsException; // Fetch student by ID
    List<Student> getAllStudents() throws DatabaseOperationsException;                 // Fetch all students
}
