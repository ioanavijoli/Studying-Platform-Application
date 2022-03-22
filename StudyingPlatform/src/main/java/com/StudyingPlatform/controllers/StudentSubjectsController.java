package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Student;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.model.SubjectStudent;
import com.StudyingPlatform.model.User;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.SubjectNotFoundException;
import com.StudyingPlatform.service.StudentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentSubjectsController implements Initializable {
    @FXML
    private TextField subjectNameField;
    @FXML
    private VBox subjectsVBox;
    @FXML
    private Button joinButton;

    private boolean showJoinable = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPersonalCourses();
    }

    @FXML
    public void onSearchButtonClick() {
        if(subjectNameField.getText().equals("")){
            if(showJoinable){
                showJoinableCourses();
            }else{
                showPersonalCourses();
            }
            return;
        }

        List<Subject> foundSubjects,result = new ArrayList<>();
        List<SubjectStudent> mySubjects;
        try {
            foundSubjects =
                    DataBaseService.getSubjectsByName(subjectNameField.getText());
        }catch (SQLException e) {
            e.printStackTrace();
            SuperController.popError("Something went wrong when trying to search for course.");
            return;
        }catch (SubjectNotFoundException e) {
            SuperController.popMessage("There is no Course with this name.");
            return;
        }

        try {
            mySubjects =
                    StudentService.studentGetSubjects((Student)SuperController.activeUser);
        }catch (SQLException | SubjectNotFoundException e){
            e.printStackTrace();
            SuperController.popError("Something went wrong when searching for courses!");
            return;
        }

        if(showJoinable){
           for(Subject subject:foundSubjects){
               boolean hasJoinedTheSubject = false;
               for(SubjectStudent mySubject:mySubjects){
                   if(subject.getId() == mySubject.getId()){
                       hasJoinedTheSubject = true;
                       break;
                   }
               }
               if(!hasJoinedTheSubject){
                   result.add(subject);
               }
           }
        }else{
            for(SubjectStudent mySubject : mySubjects){
                boolean wasFound = false;
                for(Subject foundSubject:foundSubjects){
                    if(foundSubject.getId() == mySubject.getId()){
                        wasFound = true;
                        break;
                    }
                }
                if(wasFound){
                    result.add(mySubject);
                }
            }
        }

        if(result.isEmpty()){
            if(!showJoinable){
                SuperController.popMessage("You are not enrolled in this course.");
            }else{
                SuperController.popMessage("You are already enrolled in this course.");
            }
            return;
        }
        subjectsVBox.getChildren().clear();
        addSubjects(result,showJoinable);
    }

    @FXML
    public void onBackButtonClick() {
        StudyingApplication.jumpToView("home.fxml",550,500);
    }

    @FXML
    public void onJoinButtonClick() {
        if(showJoinable){
            joinButton.setText("join Course");
            showPersonalCourses();
        }else{
            joinButton.setText("my Courses");
            showJoinableCourses();
        }
        showJoinable = !showJoinable;
    }

    private void showPersonalCourses() {
        subjectsVBox.getChildren().clear();
        try {
            List<SubjectStudent> subjects =
                    StudentService.studentGetSubjects((Student) SuperController.activeUser);
            addSubjects(subjects,false);
        } catch (SubjectNotFoundException e) {
            return;
        } catch(SQLException e){
            e.printStackTrace();
            return;
        }
    }

    private void showJoinableCourses() {
        subjectsVBox.getChildren().clear();
        try {
            List<Subject> subjects =
                    StudentService.studentGetJoinableSubjects((Student) SuperController.activeUser);
            addSubjects(subjects, true);
        }catch(SQLException e){
            e.printStackTrace();
            return;
        }
    }

    private void addSubjects(List<? extends  Subject> subjects, boolean isJoinable){
        for(Subject subject:subjects){
            try {
                URL url = StudyingApplication.class.getResource("student-subjects-row.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                Parent row = (Parent) fxmlLoader.load();
                StudentSubjectsRowController controller = fxmlLoader.<StudentSubjectsRowController>getController();
                controller.set(subject,isJoinable);
                subjectsVBox.getChildren().add(row);
            }catch (IOException e){
                continue;
            }
        }
    }
}