CREATE TABLE students (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    age INTEGER
);

INSERT INTO students (name, age) VALUES ('Alice', 20), ('Bob', 22), ('Charlie', 19);


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCExample {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:students.db"; // SQLite connection string

        try {
            // Load SQLite JDBC driver (optional with modern JDBC, but safe)
            Class.forName("org.sqlite.JDBC");

            // Connect to database
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connected to the database.");

            // Create and execute SELECT query
            String sql = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Print results
            System.out.println("Student Records:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(id + " | " + name + " | " + age);
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}