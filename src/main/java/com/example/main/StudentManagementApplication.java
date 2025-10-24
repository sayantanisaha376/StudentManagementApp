package com.example.main;

import com.example.DAO.StudentDAO;
import com.example.DAO.StudentDAOImpl;
import com.example.entity.Student;
import com.example.exception.DatabaseOperationsException;
import com.example.exception.StudentNotFoundException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StudentManagementApplication {

    private static final StudentDAO studentDAO = new StudentDAOImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            choice = getUserChoice();

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> updateStudent();
                    case 3 -> deleteStudent();
                    case 4 -> viewStudentById();
                    case 5 -> viewAllStudents();
                    case 0 -> System.out.println("Exiting Student Management System. Goodbye!");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (DatabaseOperationsException e) {
                System.err.println("Database Error: " + e.getMessage());
            } catch (StudentNotFoundException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter the correct data type.");
                scanner.nextLine(); // clear invalid input
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine(); // wait for user
        } while (choice != 0);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Student Management System ---");
        System.out.println("1. Add New Student");
        System.out.println("2. Update Student");
        System.out.println("3. Delete Student");
        System.out.println("4. View Student by ID");
        System.out.println("5. View All Students");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // clear invalid input
            return -1;
        }
    }

    private static void addStudent() throws DatabaseOperationsException {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Course: ");
        String course = scanner.nextLine();

        Student student = new Student(id, name, age, course);
        studentDAO.addStudent(student);
        System.out.println("Student added successfully! ID: " + student.getId());
    }

    private static void updateStudent() throws DatabaseOperationsException, StudentNotFoundException {
        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Student student = studentDAO.getStudentById(id);
        System.out.println("Existing Student: " + student);

        System.out.print("Enter New Name (leave blank to keep '" + student.getName() + "'): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) student.setName(name);

        System.out.print("Enter New Age (leave blank to keep '" + student.getAge() + "'): ");
        String ageStr = scanner.nextLine();
        if (!ageStr.trim().isEmpty()) student.setAge(Integer.parseInt(ageStr));

        System.out.print("Enter New Course (leave blank to keep '" + student.getCourse() + "'): ");
        String course = scanner.nextLine();
        if (!course.trim().isEmpty()) student.setCourse(course);

        studentDAO.updateStudent(student);
        System.out.println("Student updated successfully!");
    }

    private static void deleteStudent() throws DatabaseOperationsException, StudentNotFoundException {
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        studentDAO.deleteStudent(id);
        System.out.println("Student with ID " + id + " deleted successfully!");
    }

    private static void viewStudentById() throws DatabaseOperationsException, StudentNotFoundException {
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Student student = studentDAO.getStudentById(id);
        System.out.println("Found Student: " + student);
    }

    private static void viewAllStudents() throws DatabaseOperationsException {
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }
}
