package com.example.bankingapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtility {
    private String url = "jdbc:mysql://127.0.0.1:3306/login_schema"; // Update with your DB URL
    private String user = "root"; // Your DB username
    private String password = "123"; // Your DB password

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT account_holder, balance FROM accounts"; // Update with your actual table name

        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String accountHolder = rs.getString("account");
                double balance = rs.getDouble("balance");
                accounts.add(new Account(accountHolder, balance));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly
        }

        return accounts;
    }

    public void updateBalance(String accountHolder, double newBalance) {
        String query = "UPDATE accounts SET balance = ? WHERE account_holder = ?";

        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountHolder);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly
        }
    }
}
