package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Professor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class DisplayProfessorDataController extends DisplayUserDataController{

    @FXML
    private TextField departmentField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUser();
        departmentField.setText(((Professor) SuperController.activeUser).getDepartment());
    }
    @FXML
    public void onBackButtonClick() throws IOException {
        StudyingApplication.jumpToView("home.fxml",550,500);
    }
}
