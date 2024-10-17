package com.example.bankingapp;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class BalanceChartController {

    @FXML
    private BarChart<String, Number> accountChart;

    @FXML
    public void initialize() {
        populateChart();
    }

    private void populateChart() {
        // Sample data for demonstration
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Account Balances");

        // Here you would retrieve your data from the DB
        series.getData().add(new XYChart.Data<>("Chequing", 2000));
        series.getData().add(new XYChart.Data<>("Savings", 1500));

        accountChart.getData().add(series);
    }
}
