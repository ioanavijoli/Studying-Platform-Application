package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.model.SubjectProfessor;
import com.StudyingPlatform.model.SubjectStudent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.util.List;

public class ProfessorGradesSubjectRowController {
    @FXML
    private Label name;
    @FXML
    private VBox root;

    private SubjectProfessor subject;
    private ProfessorGradesController parentController;

    public void setSubject(SubjectProfessor subject, ProfessorGradesController parentController) {
        this.parentController = parentController;
        this.subject = subject;
        name.setText(subject.getName());
    }

    @FXML
    public void onMousePressed() throws SQLException {
        if(parentController.getSelectedController() != null)
            parentController.getSelectedController().unselect();
        parentController.setSelectedController(this);
        parentController.setSelectedSubject(subject);
        this.select();
    }

    public void unselect() {
        root.setBackground(new Background(new BackgroundFill(Color.valueOf("#f4f4f4"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void select(){
        root.setBackground(new Background(new BackgroundFill(Color.valueOf("#ccd3e6"), CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
