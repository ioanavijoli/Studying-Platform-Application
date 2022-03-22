package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Group;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;

public class InboxRowController {
    @FXML
    private Label name;

    private Group group;
    private boolean isJoined;
    private ChatController parentController;

    public void setGroup(Group g, boolean isJoined, ChatController parentController) {
        this.group = g;
        this.isJoined = isJoined;
        this.parentController = parentController;
        name.setText(g.getName());
        if (!isJoined) {
            Font font = Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14);
            name.setFont(font);
            name.setTextFill(Color.valueOf("#25335c"));
        } else {
            Font font = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 14);
            name.setFont(font);
        }
    }

    @FXML
    public void onMousePressed() throws SQLException {
        if (parentController.getSelectedInboxRow() != null) {
            parentController.getSelectedInboxRow().unselect();
        }
        parentController.setSelectedGroup(group);
        parentController.setSelectedInboxRow(this);
        Font font = Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 14);
        name.setFont(font);
        if (isJoined) {
            parentController.messagesListInit();
        } else {
            parentController.showJoinButton();
        }
    }

    public void unselect() {
        Font font = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 14);
        name.setFont(font);
    }
}