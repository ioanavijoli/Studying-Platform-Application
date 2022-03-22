package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.*;
import com.StudyingPlatform.service.Exceptions.SubjectNotFoundException;
import com.StudyingPlatform.service.IOService;
import com.StudyingPlatform.service.ProfessorService;
import com.StudyingPlatform.service.StudentService;
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

public class ProfessorGradesController implements Initializable {
    @FXML
    private VBox subjectsVBox;
    @FXML
    private VBox studentsVBox;
    @FXML
    private TextField selectedSubjectField;

    private List<Student> students;
    private List<SubjectProfessor> listedSubjects;
    private SubjectProfessor selectedSubject;
    private ProfessorGradesSubjectRowController selectedController;

    private List<ProfessorGradesStudentRowController> studentRowControllers = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listedSubjects = ProfessorService.professorGetSubjects((Professor) SuperController.activeUser);
        } catch (SQLException e) {
            e.printStackTrace();
            listedSubjects = new ArrayList<>();
        } catch (SubjectNotFoundException e) {
            listedSubjects = new ArrayList<>();
        }
        try {
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private void updateList() throws IOException {
        subjectsVBox.getChildren().clear();
        for (SubjectProfessor subject : listedSubjects) {
            URL url = StudyingApplication.class.getResource("professor-grades-subject-row.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent row = (Parent) fxmlLoader.load();
            ProfessorGradesSubjectRowController controller = fxmlLoader.<ProfessorGradesSubjectRowController>getController();
            controller.setSubject(subject,this);
            subjectsVBox.getChildren().add(row);
        }
    }

    @FXML
    public void onBackButtonClick() throws IOException {
        StudyingApplication.jumpToView("home.fxml",550,500);
    }
    @FXML
    public void onDownloadButtonClick() throws IOException {
        List<SubjectStudent> grades= new ArrayList<>();
        int i=0;
        for(ProfessorGradesStudentRowController studentGrades: studentRowControllers){
            grades.add(studentGrades.getSubjectStudent());
        }
        IOService.writeGrades(grades);
    }
    @FXML
    public void onSaveButtonClick(){
        int changedEntities = 0;
        for(ProfessorGradesStudentRowController controller: studentRowControllers){
            boolean success = controller.saveGrades();
            if(success){
                changedEntities++;
            }
        }
        SuperController.popMessage("Changes saved!\nUpdated "+changedEntities +
                " students out of "+studentRowControllers.size());
    }

    public void setSelectedSubject(SubjectProfessor selectedSubject) {
        this.selectedSubject = selectedSubject;
        selectedSubjectField.setText(selectedSubject.getName());
        //load new students
        try{
            students =
                    ProfessorService.professorGetStudentsBySubject((Professor) SuperController.activeUser,selectedSubject);
        }catch (SQLException e){
            e.printStackTrace();
            SuperController.popError("Something went wrong when loading students.");
            students = new ArrayList<>();
        }
        try {
            updateStudentList();
        }catch (IOException e){
            return;
        }
    }

    private void updateStudentList() throws IOException{
        studentsVBox.getChildren().clear();
        studentRowControllers.clear();
        for(Student student:students){
            List<SubjectStudent> mySubjects;
            try {
                mySubjects = StudentService.studentGetSubjects(student);
            } catch (SQLException e){
                e.printStackTrace();
                continue;
            } catch (SubjectNotFoundException e){
                mySubjects = new ArrayList<>();
            }
            SubjectStudent studentSpecificSubject = null;
            for(SubjectStudent mySubject: mySubjects){
                if(mySubject.getId() == selectedSubject.getId()){
                    studentSpecificSubject = mySubject;
                    break;
                }
            }
            if(studentSpecificSubject == null)
                return;
            URL url = StudyingApplication.class.getResource("professor-grades-student-row.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent row = (Parent) fxmlLoader.load();
            ProfessorGradesStudentRowController controller = fxmlLoader.<ProfessorGradesStudentRowController>getController();
            controller.setStudent(student,studentSpecificSubject);
            studentRowControllers.add(controller);
            studentsVBox.getChildren().add(row);

        }
    }
    public void setSelectedController(ProfessorGradesSubjectRowController selectedController) {
        this.selectedController = selectedController;
    }

    public ProfessorGradesSubjectRowController getSelectedController() {
        return selectedController;
    }
}