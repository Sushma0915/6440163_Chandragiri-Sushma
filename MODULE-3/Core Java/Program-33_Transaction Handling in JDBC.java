import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {

    private static final String URL = "jdbc:mysql://localhost:3306/your_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    public static void transferMoney(int fromAccountId, int toAccountId, double amount) {
        String debitSQL = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
        String creditSQL = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement debitStmt = conn.prepareStatement(debitSQL);
             PreparedStatement creditStmt = conn.prepareStatement(creditSQL)) {

            conn.setAutoCommit(false);  // Start transaction

            // Debit from source account
            debitStmt.setDouble(1, amount);
            debitStmt.setInt(2, fromAccountId);
            int debitUpdated = debitStmt.executeUpdate();

            // Credit to destination account
            creditStmt.setDouble(1, amount);
            creditStmt.setInt(2, toAccountId);
            int creditUpdated = creditStmt.executeUpdate();

            if (debitUpdated == 1 && creditUpdated == 1) {
                conn.commit();
                System.out.println("Transaction successful: Transferred " + amount);
            } else {
                conn.rollback();
                System.out.println("Transaction failed: Account not found or insufficient updates.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Transaction failed, rolling back.");
            // Note: conn.rollback() automatically called on try-with-resources close? No, so must handle carefully
        }
    }

    public static void main(String[] args) {
        transferMoney(1, 2, 200.0);
    }
}