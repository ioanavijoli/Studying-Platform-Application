package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Professor;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.model.User;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.SubjectNotFoundException;
import com.StudyingPlatform.service.Exceptions.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubjectListController implements Initializable {
    @FXML
    private TextField subjectNameField;
    @FXML
    private VBox subjectsVBox;

    private List<Subject> listedSubjects;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listedSubjects = DataBaseService.getAllSubjects();
        } catch (SubjectNotFoundException e) {
            listedSubjects = new ArrayList<>();
            System.out.println("no subject found");
        } catch (SQLException e) {
            System.out.println("something went wrong in assign professor to subject initialize");
            e.printStackTrace();
            return;
        }
        try {
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateView() throws IOException {
        subjectsVBox.getChildren().clear();
        for (Subject subject : listedSubjects) {
            URL url = StudyingApplication.class.getResource("subject-row.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent row = (Parent) fxmlLoader.load();
            SubjectListRowController controller = fxmlLoader.<SubjectListRowController>getController();
            controller.setSubject(subject);
            subjectsVBox.getChildren().add(row);
        }
    }

    public void onSearchButtonClick() throws IOException {
        try {
            listedSubjects = DataBaseService.getSubjectsByName(subjectNameField.getText());
        } catch (SubjectNotFoundException e) {
           // listedProfessors = new ArrayList<>();
            System.out.println("no subject found");
        } catch (SQLException e) {
            System.out.println("something went wrong in searching for professor");
            e.printStackTrace();
            return;
        }
        updateView();
    }
    @FXML
    public void onBackButtonClick() throws IOException{
        Stage stage = StudyingApplication.getPrimaryStage();
        URL url = StudyingApplication.class.getResource("admin-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setScene(scene);
    }
    @FXML
    public void onAddSubjectButtonClick() throws IOException{
        Stage stage = StudyingApplication.getPrimaryStage();
        URL url = StudyingApplication.class.getResource("add-subject-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setScene(scene);
    }

}
