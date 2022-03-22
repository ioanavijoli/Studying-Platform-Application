package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Student;
import com.StudyingPlatform.model.SubjectStudent;
import com.StudyingPlatform.service.Exceptions.GradesNotFoundException;
import com.StudyingPlatform.service.ProfessorService;
import com.StudyingPlatform.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ProfessorGradesStudentRowController {
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private TextField lectureTextField;
    @FXML
    private TextField seminarTextField;
    @FXML
    private TextField labTextField;

    private Student student;
    private SubjectStudent subject;
    private int[] grades;

    public void setStudent(Student student, SubjectStudent subject) {
        this.student = student;
        this.subject = subject;
        firstNameLabel.setText(student.getFirstName());
        lastNameLabel.setText(student.getLastName());
        //plus more
        try {
            grades = StudentService.studentGetGrades(student, subject);
        } catch (SQLException | GradesNotFoundException e) {
            e.printStackTrace();
            return;
        }
        if (subject.getHasLecture()) {
            lectureTextField.setText(String.valueOf(grades[0]));
        } else {
            lectureTextField.setDisable(true);
        }
        if (subject.getHasSeminar()) {
            seminarTextField.setText(String.valueOf(grades[1]));
        } else {
            seminarTextField.setDisable(true);
        }
        if (subject.getHasLab()) {
            labTextField.setText(String.valueOf(grades[2]));
        } else {
            labTextField.setDisable(true);
        }
    }

    public boolean saveGrades() {
        int[] newGrades = new int[3];
        newGrades[0] = grades[0];
        newGrades[1] = grades[1];
        newGrades[2] = grades[2];
        boolean haveBeenChanged = false;
        if (subject.getHasLecture()) {
            Integer lectureGrade = this.getGrade("LECTURE");
            if (lectureGrade != null && !lectureGrade.equals(Integer.valueOf(grades[0]))) {
                haveBeenChanged = true;
                newGrades[0] = lectureGrade;
            }
        }
        if (subject.getHasSeminar()) {
            Integer seminarGrade = this.getGrade("SEMINAR");
            if (seminarGrade != null && !seminarGrade.equals(Integer.valueOf(grades[1]))) {
                haveBeenChanged = true;
                newGrades[1] = seminarGrade;
            }
        }
        if (subject.getHasLab()) {
            Integer labGrade = this.getGrade("LAB");
            if (labGrade != null && !labGrade.equals(Integer.valueOf(grades[2]))) {
                haveBeenChanged = true;
                newGrades[2] = labGrade;
            }
        }
        if (haveBeenChanged) {
            try {
                ProfessorService.gradeStudent(student, subject, newGrades);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private Integer getGrade(String type) {
        try {
            int grade = switch (type) {
                case "LECTURE" -> Integer.parseInt(lectureTextField.getText());
                case "SEMINAR" -> Integer.parseInt(seminarTextField.getText());
                case "LAB" -> Integer.parseInt(labTextField.getText());
                default -> throw new IllegalStateException("Unexpected string value in type/getGrades/" + getClass().getName());
            };
            if (grade < 1 || grade > 10) {
                return null;
            }
            return grade;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public SubjectStudent getSubjectStudent() {
        return subject;
    }
}
