package com.StudyingPlatform.controllers;


import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.*;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.GradesNotFoundException;
import com.StudyingPlatform.service.Exceptions.SubjectNotFoundException;
import com.StudyingPlatform.service.ProfessorService;
import com.StudyingPlatform.service.StudentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddGradesController implements Initializable {
    @FXML
    private VBox studentsVBox;

    private List<SubjectStudent> subjectStudents;
    private List<Subject> subject;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void updateList(){
    }

    @FXML
    public void onBackButtonClick() throws IOException {
        StudyingApplication.jumpToView("professor-grades.fxml");
    }
    @FXML
    public void onDownloadButtonClick() throws IOException {

    }

    @FXML
    public void onSaveChangesButtonClick() throws IOException {

    }
}