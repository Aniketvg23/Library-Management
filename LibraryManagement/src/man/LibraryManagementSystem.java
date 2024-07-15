package man;

import java.sql.SQLException;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        BookManagement bookManagement = new BookManagement();
        PatronRecords patronRecords = new PatronRecords();
        BorrowingSystem borrowingSystem = new BorrowingSystem();
        SearchFunctionality searchFunctionality = new SearchFunctionality();
        Reports reports = new Reports();

        try {
            // Book Management
            bookManagement.addBook("The Great Gatsby", "F. Scott Fitzgerald", "Novel");
            bookManagement.addBook("To Kill a Mockingbird", "Harper Lee", "Novel");
            bookManagement.listBooks();

            // Patron Records
            patronRecords.addPatron("John Doe", "john.doe@example.com");
            patronRecords.addPatron("Jane Smith", "jane.smith@example.com");
            patronRecords.listPatrons();

            // Borrowing System
            borrowingSystem.borrowBook(1, 1);
            borrowingSystem.returnBook(1, 1);

            // Search Functionality
            searchFunctionality.searchBooks("Gatsby");
            searchFunctionality.searchPatrons("Doe");

            // Reports
            reports.generateBookAvailabilityReport();
            reports.generateBorrowingHistoryReport();
            reports.generateFinesReport();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

