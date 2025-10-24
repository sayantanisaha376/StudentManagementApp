package com.example.config;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Load Oracle JDBC driver once
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not loaded: " + e.getMessage());
        }
    }

    // Create a new connection
    public static Connection makeCon() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:FREE",
                "c##scott",
                "tiger"
        );
    }
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println(" Connection closed successfully.");
            } catch (SQLException e) {
                System.out.println(" Error closing connection: " + e.getMessage());
            }
        }
    }


    // Test the connection
    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DBConnection.makeCon();
            System.out.println(" Connected successfully to Oracle Database!");
        } catch (SQLException e) {
            System.out.println(" Connection failed: " + e.getMessage());
        } finally {
            // Close the connection safely
            if (con != null) {
                try {
                    con.close();
                    System.out.println(" Connection closed successfully.");
                } catch (SQLException e) {
                    System.out.println(" Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}

