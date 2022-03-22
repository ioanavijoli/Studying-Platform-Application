package com.StudyingPlatform.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MessageController {
    @FXML
    private VBox bigBox;
    @FXML
    private VBox smallBox;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label message;

    public void set(String message, String username, boolean isRight) {
        this.message.setText(message);
        this.usernameLabel.setText(username);

        if(isRight){
            bigBox.setAlignment(Pos.TOP_RIGHT);
            smallBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#e0fcd4"),
                    CornerRadii.EMPTY,
                    Insets.EMPTY)));
        }else{
            bigBox.setAlignment(Pos.TOP_LEFT);
            smallBox.setBackground(new Background(new BackgroundFill(Color.WHITE,
                    CornerRadii.EMPTY,
                    Insets.EMPTY)));
        }

    }
}
