package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Student;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.model.SubjectStudent;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.StudentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class StudentSubjectsRowController {
    @FXML
    private Label subjectLabel;
    @FXML
    private Button firstButton;
    @FXML
    private Button secondButton;
    @FXML
    private MenuButton moreButton;

    private Subject subject;
    private boolean isJoinable;
    private Stage popUpActivities;
    private Stage popUpGrades;

    @FXML
    public void onFirstButtonClick() throws IOException {
        if (isJoinable) {
            //join
            try {
                StudentService.studentJoinSubject((Student)SuperController.activeUser, subject);
                StudyingApplication.jumpToView("student-subjects.fxml",550,500);
            }catch (SQLException e){
                e.printStackTrace();
                return;
            }
        } else {
            //activities
            if (popUpActivities != null){
                popUpActivities.close();
            }
            Stage stage = new Stage();
            URL url = StudyingApplication.class.getResource("student-activities.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = (Parent) fxmlLoader.load();
            StudentActivitiesController controller = fxmlLoader.<StudentActivitiesController>getController();
            controller.set((SubjectStudent) subject, stage);
            stage.setScene(new Scene(root, 350, 135));
            stage.setTitle(subject.getName());
            stage.show();
            popUpActivities = stage;
        }
    }

    @FXML
    public void onSecondButtonClick() throws IOException{
        if (isJoinable) {
            //view
            jumpToViewSubject();
        } else {
            //grades
            if (popUpGrades != null){
                popUpGrades.close();
            }
            Stage stage = new Stage();
            URL url = StudyingApplication.class.getResource("student-grades.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = (Parent) fxmlLoader.load();
            StudentGradesController controller = fxmlLoader.<StudentGradesController>getController();
            controller.set(
                    (Student)SuperController.activeUser,
                    subject,
                    stage
            );
            stage.setScene(new Scene(root, 340, 100));
            stage.setTitle(subject.getName());
            stage.show();
            popUpActivities = stage;
        }
    }

    @FXML
    public void onViewButtonClick() throws IOException {
        jumpToViewSubject();
    }

    @FXML
    public void onQuitButtonClick() {
        try {
            StudentService.studentQuitSubject((Student) SuperController.activeUser, subject);
        }catch (SQLException e){
            e.printStackTrace();
            SuperController.popError("Something went wrong, couldn\nt quit subject.");
            return;
        }
        StudyingApplication.jumpToView("student-subjects.fxml",550,500);
    }

    public void set(Subject subject, boolean isJoinable) {
        this.subject = subject;
        this.isJoinable = isJoinable;
        subjectLabel.setText(subject.getName());
        if (isJoinable) {
            firstButton.setText("Join");
            secondButton.setText("View");
            moreButton.setVisible(false);
        } else {
            firstButton.setText("Activities");
            secondButton.setText("Grades");
            moreButton.setVisible(true);
            if(!((SubjectStudent)subject).isFinishedSchedule()){
                firstButton.setDisable(true);
            }
        }
    }

    private void jumpToViewSubject() throws IOException{
        URL url = StudyingApplication.class.getResource("admin-modifies-subject.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = (Parent)fxmlLoader.load();
        AdminModifiesSubjectController controller = fxmlLoader.<AdminModifiesSubjectController>getController();
        controller.setSubject(subject,false);
        Stage stage = StudyingApplication.getPrimaryStage();
        stage.setScene(new Scene(root, 400, 500));
    }
}
