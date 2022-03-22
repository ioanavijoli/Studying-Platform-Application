package com.StudyingPlatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MessagePopupController {
    @FXML
    private Label messageLabel;

    private Stage myStage;

    @FXML
    public void onCloseButtonClick(){
        myStage.close();
    }

    public void create(String message, Stage stage,Color color){
        myStage = stage;
        messageLabel.setText(message);
        messageLabel.setTextFill(color);
    }
}
