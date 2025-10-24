package com.example.DAO;

import com.example.config.DBConnection;
import com.example.entity.Student;
import com.example.exception.DatabaseOperationsException;
import com.example.exception.StudentNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setAge(rs.getInt("age"));
        student.setCourse(rs.getString("course"));
        return student;
    }

    @Override
    public void addStudent(Student student) throws DatabaseOperationsException {
        String SQL = "INSERT INTO STUDENT_MANAGEMENT_APP (id, name, age, course) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.makeCon();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setInt(1, student.getId());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getCourse());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseOperationsException("Adding student failed, no rows affected.", null);
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1) { // ORA-00001 duplicate ID
                throw new DatabaseOperationsException("Student ID " + student.getId() + " already exists.", e);
            }
            throw new DatabaseOperationsException("Error adding student: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateStudent(Student student) throws StudentNotFoundException, DatabaseOperationsException {
        String SQL = "UPDATE STUDENT_MANAGEMENT_APP SET name=?, age=?, course=? WHERE id=?";
        try (Connection con = DBConnection.makeCon();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getCourse());
            ps.setInt(4, student.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new StudentNotFoundException("Student with ID " + student.getId() + " not found for update.");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationsException("Error updating student: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteStudent(int id) throws StudentNotFoundException, DatabaseOperationsException {
        String SQL = "DELETE FROM STUDENT_MANAGEMENT_APP WHERE id=?";
        try (Connection con = DBConnection.makeCon();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new StudentNotFoundException("Student with ID " + id + " not found for deletion.");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationsException("Error deleting student: " + e.getMessage(), e);
        }
    }

    @Override
    public Student getStudentById(int id) throws StudentNotFoundException, DatabaseOperationsException {
        String SQL = "SELECT * FROM STUDENT_MANAGEMENT_APP WHERE id=?";
        try (Connection con = DBConnection.makeCon();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractStudentFromResultSet(rs);
            } else {
                throw new StudentNotFoundException("Student with ID " + id + " not found.");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationsException("Error fetching student: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Student> getAllStudents() throws DatabaseOperationsException {
        List<Student> list = new ArrayList<>();
        String SQL = "SELECT * FROM STUDENT_MANAGEMENT_APP";

        try (Connection con = DBConnection.makeCon();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {

            while (rs.next()) {
                list.add(extractStudentFromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseOperationsException("Error fetching students: " + e.getMessage(), e);
        }

        return list;
    }
}
