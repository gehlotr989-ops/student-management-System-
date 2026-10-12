import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

    // डेटाबेस क्रेडेंशियल्स (यहाँ अपना नया MySQL पासवर्ड लिखें)
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Rahul@0100#"; // उदाहरण: "root123"

    public static void main(String[] args) {
        try {
            // JDBC ड्राइवर लोड करना
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver नहीं मिला!");
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== STUDENT MANAGEMENT SYSTEM ===");
            System.out.println("1. नया छात्र जोड़ें (Create)");
            System.out.println("2. सभी छात्र देखें (Read/Report)");
            System.out.println("3. छात्र विवरण अपडेट करें (Update)");
            System.out.println("4. छात्र हटाएं (Delete)");
            System.out.println("5. छात्र खोजें (Search)");
            System.out.println("6. बाहर निकलें (Exit)");
            System.out.print("अपना विकल्प चुनें: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    deleteStudent(scanner);
                    break;
                case 5:
                    searchStudent(scanner);
                    break;
                case 6:
                    System.out.println("प्रोग्राम बंद हो रहा है...");
                    scanner.close();
                    return;
                default:
                    System.out.println("अमान्य विकल्प! कृपया पुनः प्रयास करें।");
            }
        }
    }

    // 1. Add Student (Create)
    private static void addStudent(Scanner scanner) {
        System.out.print("नाम लिखें: ");
        String name = scanner.nextLine();
        System.out.print("ईमेल लिखें: ");
        String email = scanner.nextLine();
        System.out.print("कोर्स लिखें: ");
        String course = scanner.nextLine();
        System.out.print("अंक (Marks) लिखें: ");
        double marks = scanner.nextDouble();

        String sql = "INSERT INTO students (name, email, course, marks) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, course);
            stmt.setDouble(4, marks);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("छात्र सफलतापूर्वक जोड़ा गया!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. View All Students (Read/Reports Generation)
    private static void viewStudents() {
        String sql = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- छात्र रिपोर्ट ---");
            System.out.printf("%-5s %-20s %-25s %-15s %-10s\n", "ID", "Name", "Email", "Course", "Marks");
            System.out.println("------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-25s %-15s %-10.2f\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getDouble("marks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Update Student
    private static void updateStudent(Scanner scanner) {
        System.out.print("अपडेट करने के लिए स्टूडेंट ID दर्ज करें: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("नया कोर्स दर्ज करें: ");
        String course = scanner.nextLine();
        System.out.print("नए अंक (Marks) दर्ज करें: ");
        double marks = scanner.nextDouble();

        String sql = "UPDATE students SET course = ?, marks = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course);
            stmt.setDouble(2, marks);
            stmt.setInt(3, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("छात्र की जानकारी अपडेट हो गई!");
            } else {
                System.out.println("ID नहीं मिली।");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Delete Student
    private static void deleteStudent(Scanner scanner) {
        System.out.print("हटाने के लिए स्टूडेंट ID दर्ज करें: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("छात्र का रिकॉर्ड हटा दिया गया!");
            } else {
                System.out.println("ID नहीं मिली।");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. Search Student (Search Functionality)
    private static void searchStudent(Scanner scanner) {
        System.out.print("खोजने के लिए छात्र का नाम या ईमेल लिखें: ");
        String keyword = scanner.nextLine();

        String sql = "SELECT * FROM students WHERE name LIKE ? OR email LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- खोज परिणाम ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email") +
                        ", Course: " + rs.getString("course") +
                        ", Marks: " + rs.getDouble("marks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}