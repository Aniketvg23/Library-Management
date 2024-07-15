package man;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatronRecords {

    public void addPatron(String name, String contactInfo) throws SQLException {
        String sql = "INSERT INTO patrons (name, contact_info) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, contactInfo);
            pstmt.executeUpdate();
        }
    }

    public void listPatrons() throws SQLException {
        String sql = "SELECT * FROM patrons";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("patron_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Contact Info: " + rs.getString("contact_info"));
                System.out.println("-----------------------------");
            }
        }
    }
}

