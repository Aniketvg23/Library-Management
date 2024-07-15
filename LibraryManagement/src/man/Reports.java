package man;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reports {

    public void generateBookAvailabilityReport() throws SQLException {
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Book Availability Report");
            System.out.println("========================");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("book_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Genre: " + rs.getString("genre"));
                System.out.println("Available: " + rs.getBoolean("available"));
                System.out.println("-----------------------------");
            }
        }
    }

    public void generateBorrowingHistoryReport() throws SQLException {
        String sql = "SELECT * FROM borrowing_records";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Borrowing History Report");
            System.out.println("========================");
            while (rs.next()) {
                System.out.println("Record ID: " + rs.getInt("record_id"));
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Patron ID: " + rs.getInt("patron_id"));
                System.out.println("Borrow Date: " + rs.getDate("borrow_date"));
                System.out.println("Return Date: " + rs.getDate("return_date"));
                System.out.println("Due Date: " + rs.getDate("due_date"));
                System.out.println("Fine: " + rs.getDouble("fine"));
                System.out.println("-----------------------------");
            }
        }
    }

    public void generateFinesReport() throws SQLException {
        String sql = "SELECT * FROM borrowing_records WHERE fine > 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Fines Report");
            System.out.println("========================");
            while (rs.next()) {
                System.out.println("Record ID: " + rs.getInt("record_id"));
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Patron ID: " + rs.getInt("patron_id"));
                System.out.println("Borrow Date: " + rs.getDate("borrow_date"));
                System.out.println("Return Date: " + rs.getDate("return_date"));
                System.out.println("Due Date: " + rs.getDate("due_date"));
                System.out.println("Fine: " + rs.getDouble("fine"));
                System.out.println("-----------------------------");
            }
        }
    }
}

