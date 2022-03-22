package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class DisplayStudentDataController extends DisplayUserDataController{

    @FXML
    private TextField yearField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUser();
        yearField.setText(String.valueOf(((Student) SuperController.activeUser).getYear()));
    }
    @FXML
    public void onBackButtonClick(){
        StudyingApplication.jumpToView("home.fxml",550,500);
    }
}
