package man;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchFunctionality {

    public void searchBooks(String keyword) throws SQLException {
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
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
    }

    public void searchPatrons(String keyword) throws SQLException {
        String sql = "SELECT * FROM patrons WHERE name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("patron_id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Contact Info: " + rs.getString("contact_info"));
                    System.out.println("-----------------------------");
                }
            }
        }
    }
}

