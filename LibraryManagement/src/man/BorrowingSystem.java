package man;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowingSystem {

    public void borrowBook(int bookId, int patronId) throws SQLException {
        String sql = "INSERT INTO borrowing_records (book_id, patron_id, borrow_date, due_date) VALUES (?, ?, ?, ?)";
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusWeeks(2);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, patronId);
            pstmt.setDate(3, java.sql.Date.valueOf(borrowDate));
            pstmt.setDate(4, java.sql.Date.valueOf(dueDate));
            pstmt.executeUpdate();

            updateBookAvailability(bookId, false);
        }
    }

    public void returnBook(int bookId, int patronId) throws SQLException {
        String sql = "UPDATE borrowing_records SET return_date = ?, fine = ? WHERE book_id = ? AND patron_id = ? AND return_date IS NULL";
        LocalDate returnDate = LocalDate.now();
        double fine = calculateFine(bookId, patronId, returnDate);
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(returnDate));
            pstmt.setDouble(2, fine);
            pstmt.setInt(3, bookId);
            pstmt.setInt(4, patronId);
            pstmt.executeUpdate();

            updateBookAvailability(bookId, true);
        }
    }

    private double calculateFine(int bookId, int patronId, LocalDate returnDate) throws SQLException {
        String sql = "SELECT due_date FROM borrowing_records WHERE book_id = ? AND patron_id = ? AND return_date IS NULL";
        double fine = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, patronId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                    long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
                    if (daysOverdue > 0) {
                        fine = daysOverdue * 0.50; // 50 cents per day
                    }
                }
            }
        }

        return fine;
    }

    private void updateBookAvailability(int bookId, boolean available) throws SQLException {
        String sql = "UPDATE books SET available = ? WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        }
    }
}

