package com.example.bankingapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HelloController {

    @FXML
    private TableView<Account> accountTable;

    @FXML
    private TableColumn<Account, String> accountHolderCol;

    @FXML
    private TableColumn<Account, Number> balanceCol;

    private DBUtility dbUtility;

    @FXML
    public void initialize() {
        dbUtility = new DBUtility();
        accountHolderCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccountHolder()));
        balanceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBalance()));

        populateTable();
    }

    private void populateTable() {
        // Here you would retrieve your data from the DB
        accountTable.getItems().addAll(
                new Account("Chequing", 2000),
                new Account("Savings", 1500)
        );
    }
    @FXML
    public void showBalanceWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BalanceChart.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Account Balances");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void depositMoney() {
        Account selectedAccount = accountTable.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            double amount = getAmountFromUser("Deposit");
            if (amount > 0) {
                selectedAccount.deposit(amount);
                dbUtility.updateBalance(selectedAccount.getAccountHolder(), selectedAccount.getBalance()); // Update in DB
                accountTable.refresh();
            }
        } else {
            showAlert("No Account Selected", "Please select an account to deposit money.");
        }
    }

    @FXML
    public void withdrawMoney() {
        Account selectedAccount = accountTable.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            double amount = getAmountFromUser("Withdraw");
            if (amount > 0 && amount <= selectedAccount.getBalance()) {
                selectedAccount.withdraw(amount);
                dbUtility.updateBalance(selectedAccount.getAccountHolder(), selectedAccount.getBalance()); // Update in DB
                accountTable.refresh();
            } else {
                showAlert("Invalid Amount", "Please enter a valid amount.");
            }
        } else {
            showAlert("No Account Selected", "Please select an account to withdraw money.");
        }
    }

    private double getAmountFromUser(String action) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(action + " Money");
        dialog.setHeaderText("Enter the amount to " + action.toLowerCase() + ":");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                return Double.parseDouble(result.get());
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number.");
            }
        }
        return -1;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
