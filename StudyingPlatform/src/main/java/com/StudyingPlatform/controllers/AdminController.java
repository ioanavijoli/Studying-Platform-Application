package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AdminController {
    @FXML
    public void onUserListClick() {
        StudyingApplication.jumpToView("user-list-view.fxml");
    }

    @FXML
    public void onCourseListClick(){
        StudyingApplication.jumpToView("subject-list-view.fxml");
    }

    @FXML
    public void onAssignProfessorClick() {
        StudyingApplication.jumpToView("assign-professor-to-subject.fxml");
    }

    @FXML
    public void onBackButtonClick() {
        StudyingApplication.jumpToView("home.fxml",550,500);
    }
}