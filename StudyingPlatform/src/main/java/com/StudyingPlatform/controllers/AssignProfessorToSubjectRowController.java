package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Professor;
import com.StudyingPlatform.model.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AssignProfessorToSubjectRowController {
    @FXML
    Label idLabel;
    @FXML
    Label nameLabel;

    private AssignProfessorToSubjectController parent;

    private Object listedEntity;

    @FXML
    public void onSelectButtonClick(){
        if(listedEntity instanceof Professor){
            parent.setSelectedProfessor((Professor) listedEntity);
        }else if(listedEntity instanceof Subject){
            parent.setSelectedSubject((Subject) listedEntity);
        }
    }

    public void setSubject(Subject subject, AssignProfessorToSubjectController parent){
        this.parent = parent;
        listedEntity = subject;
        idLabel.setText(String.valueOf(((Subject)listedEntity).getId())+ ":  ");
        nameLabel.setText(((Subject)listedEntity).getName());
    }

    public Subject getSubject(){
        return (Subject)listedEntity;
    }

    public void setProfessor(Professor professor,AssignProfessorToSubjectController parent){
        this.parent = parent;
        listedEntity = professor;
        idLabel.setText(String.valueOf(((Professor)listedEntity).getId())+ ":  ");
        nameLabel.setText(((Professor)listedEntity).getFirstName() + " " + ((Professor)listedEntity).getLastName());
    }

    public Professor getProfessor(){
        return (Professor)listedEntity;
    }
}
