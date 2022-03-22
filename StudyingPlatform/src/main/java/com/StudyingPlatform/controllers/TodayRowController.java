package com.StudyingPlatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TodayRowController {
    @FXML
    private Label titleLabel;
    @FXML
    private Label hourLabel;
    @FXML
    private VBox block;
    @FXML
    private VBox supportBlock;

    public void setBlock(String title) {
        if (title != null) {
            titleLabel.setText(title);
            titleLabel.setVisible(true);
        }
        block.setVisible(true);
    }

    public void addSpacing() {
        HBox h = new HBox(10);
        h.setPrefHeight(10);
        supportBlock.getChildren().add(h);
    }

    public void init(int hour) {
        hourLabel.setText(hour + ":00-" + (hour + 1) + ":00  :");
    }

}
