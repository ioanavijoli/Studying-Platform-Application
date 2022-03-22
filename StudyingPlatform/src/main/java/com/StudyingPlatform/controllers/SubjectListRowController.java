package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Professor;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SubjectListRowController {
    @FXML
    private Label courseNameLabel;

    Subject listedSubject;

    @FXML
    public void onViewButtonClick() throws IOException {
            URL url = StudyingApplication.class.getResource("admin-modifies-subject.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = (Parent)fxmlLoader.load();
            AdminModifiesSubjectController controller = fxmlLoader.<AdminModifiesSubjectController>getController();
            controller.setSubject(listedSubject,true);
            Stage stage = StudyingApplication.getPrimaryStage();
            stage.setScene(new Scene(root, 400, 500));
    }

    public void setSubject(Subject subject) {
        this.listedSubject = subject;
        courseNameLabel.setText(subject.getName());
    }

}
