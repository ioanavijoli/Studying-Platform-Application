package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Student;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.service.Exceptions.GradesNotFoundException;
import com.StudyingPlatform.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddGradesRowController {
    @FXML
    private Label nameLabel;
    @FXML
    private TextField lectureGrade;
    @FXML
    private TextField seminarGrade;
    @FXML
    private TextField laboratourGrade;
    Student listedStudent;
    private Stage myStage;
    @FXML
    public void onEditButtonClick(){

    }


}