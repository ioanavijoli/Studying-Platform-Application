package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private MenuButton menuButton;
    @FXML
    private Button gradesButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuButton.setText(SuperController.activeUser.getUsername());
        if (SuperController.activeUser.isAdmin() || SuperController.activeUser.isSuperAdmin()) {
            MenuItem menuItem = new MenuItem("Administrate");
            menuItem.setOnAction(actionEvent -> StudyingApplication.jumpToView("admin-view.fxml"));
            menuButton.getItems().add(menuItem);
        }
        switch (SuperController.activeUser.getRole()) {
            case "STUDENT":
                gradesButton.setVisible(false);
                break;
            case "PROFESSOR":
                break;
            default:
                throw new IllegalStateException("Unexpected role for user");
        }
    }

    @FXML
    public void onTodayButtonClick() throws IOException {
        StudyingApplication.jumpToView("today.fxml");
    }

    @FXML
    public void onCoursesButtonClick() {
        if ("STUDENT".equals(SuperController.activeUser.getRole())) {
            StudyingApplication.jumpToView("student-subjects.fxml", 550, 500);
        } else if ("PROFESSOR".equals(SuperController.activeUser.getRole())) {
            StudyingApplication.jumpToView("professor-subjects.fxml", 550, 500);
        } else throw new IllegalStateException("Unexpected role for user");
    }

    @FXML
    public void onGroupsButtonClick() {
        StudyingApplication.jumpToView("chat.fxml", 550, 500);
    }

    @FXML
    public void onProfileActionClick() {
        if ("STUDENT".equals(SuperController.activeUser.getRole())) {
            StudyingApplication.jumpToView("display-student-data.fxml");
        } else if ("PROFESSOR".equals(SuperController.activeUser.getRole())) {
            StudyingApplication.jumpToView("display-professor-data.fxml");
        } else throw new IllegalStateException("Unexpected role for user");
    }

    @FXML
    public void onLogOutActionClick() {
        StudyingApplication.jumpToView("log-in-view.fxml");
        SuperController.activeUser = null;
    }

    @FXML
    public void onGradesButtonClick() {
        StudyingApplication.jumpToView("professor-grades.fxml", 550, 500);
    }

}
