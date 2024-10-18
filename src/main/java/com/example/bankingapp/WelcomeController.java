package com.example.bankingapp;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private void openMainPage(javafx.event.ActionEvent event) {
        try {
            Parent mainPage = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene mainScene = new Scene(mainPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainScene);
            stage.setTitle("Banking Application");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
