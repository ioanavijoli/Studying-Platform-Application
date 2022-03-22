package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Student;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.service.Exceptions.GradesNotFoundException;
import com.StudyingPlatform.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class StudentGradesController {
    @FXML
    private Label lectureGrade;
    @FXML
    private Label seminarGrade;
    @FXML
    private Label labGrade;

    private Stage myStage;

    @FXML
    public void onCloseButtonClick() {
        if (myStage != null){
            myStage.close();
        }
    }

    public void set(Student student, Subject subject, Stage myStage) {
        this.myStage = myStage;
        int[] grades = null;
        try {
            grades = StudentService.studentGetGrades(student, subject);

        } catch (GradesNotFoundException | SQLException e) {
            myStage.close();
            SuperController.popError("Something went wrong");
            return;
        }
        if (subject.getHasLecture()) {
            if (grades[0] != 0)
                lectureGrade.setText(String.valueOf(grades[0]));
            else
                lectureGrade.setText("necules");
        }
        if (subject.getHasSeminar()) {
            if (grades[1] != 0)
                seminarGrade.setText(String.valueOf(grades[1]));
            else
                seminarGrade.setText("necules");
        }
        if (subject.getHasLecture()) {
            if (grades[2] != 0)
                labGrade.setText(String.valueOf(grades[2]));
            else
                labGrade.setText("necules");
        }
    }
}
