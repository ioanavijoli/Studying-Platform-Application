package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.model.SubjectProfessor;
import com.StudyingPlatform.model.User;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.SubjectNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ProfessorSubjectsRowController {
    @FXML
    private Label nameLabel;
    @FXML
    Button scheduleButton;

    private SubjectProfessor displayedSubject;

    @FXML
    public void onViewButtonClick() throws IOException {
        URL url = StudyingApplication.class.getResource("admin-modifies-subject.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = (Parent)fxmlLoader.load();
        AdminModifiesSubjectController controller = fxmlLoader.<AdminModifiesSubjectController>getController();
        controller.setSubject(displayedSubject,false);
        Stage stage = StudyingApplication.getPrimaryStage();
        stage.setScene(new Scene(root, 550, 500));
    }

    @FXML
    public void onWeightsButtonClick() throws IOException {
        Stage stage = new Stage();
        URL url = StudyingApplication.class.getResource("set-weights.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = (Parent) fxmlLoader.load();
        SetWeightsController controller = fxmlLoader.<SetWeightsController>getController();
        controller.create(displayedSubject,stage);
        Scene scene = new Scene(root, 325, 110);
        stage.setTitle("Configure Weights");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onScheduleButtonClick() throws IOException{
        Stage stage = new Stage();
        URL url = StudyingApplication.class.getResource("schedule-activities.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = (Parent) fxmlLoader.load();
        ScheduleActivitiesController controller = fxmlLoader.<ScheduleActivitiesController>getController();
        controller.create(displayedSubject,stage,this);
        Scene scene = new Scene(root, 300, 165);
        stage.setTitle("Schedule Activities");
        stage.setScene(scene);
        stage.show();
    }

    public void setSubject(SubjectProfessor subject){
        this.displayedSubject = subject;
        nameLabel.setText(subject.getName());
        if(subject.isFinishedSchedule()){
            scheduleButton.setDisable(true);
        }
    }


    public void disableScheduleButton(){
        scheduleButton.setDisable(true);
    }

}
