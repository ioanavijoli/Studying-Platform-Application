package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.service.DataBaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;

public class AddSubjectController {
    @FXML
    TextField nameField;
    @FXML
    TextArea descriptionText;
    @FXML
    CheckBox lectureCheckBox;
    @FXML
    CheckBox seminarCheckBox;
    @FXML
    CheckBox labCheckBox;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker finishDatePicker;
    @FXML
    Label errorLabel;

    @FXML
    public void onCancelButtonClick() throws IOException{
        Stage stage = StudyingApplication.getPrimaryStage();
        URL url = StudyingApplication.class.getResource("subject-list-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setScene(scene);
    }
    @FXML
    public void onAddCourseButtonClick() throws IOException {
        try{
            DataBaseService.insertSubject(new Subject(
                    -1,
                    nameField.getText(),
                    descriptionText.getText(),
                    lectureCheckBox.isSelected(),
                    seminarCheckBox.isSelected(),
                    labCheckBox.isSelected(),
                    Date.valueOf(startDatePicker.getValue()),
                    Date.valueOf(finishDatePicker.getValue())
            ));
        }catch(SQLException e){
            errorLabel.setVisible(true);
            return;
        }
        Stage stage = StudyingApplication.getPrimaryStage();
        URL url = StudyingApplication.class.getResource("admin-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setScene(scene);
    }
}
