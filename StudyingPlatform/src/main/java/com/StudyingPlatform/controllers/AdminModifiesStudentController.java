package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Student;
import com.StudyingPlatform.service.DataBaseService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AdminModifiesStudentController extends AdminModifiesUserController{
    @FXML
    private TextField yearField;

    @FXML
    public void onEditYearClick(){
        disableAll();
        yearField.setDisable(false);
    }

    @FXML
    public void onSaveChangesButtonClick() throws IOException {
        updateDisplayedUser();
        ((Student)getDisplayedUser()).setYear(Integer.parseInt(yearField.getText()));
        try{
            DataBaseService.updateUser(getDisplayedUser());
        }catch(SQLException e){
            SuperController.popError("Something went wrong, we are sorry.");
        }
    }

    void disableAll(){
         disableAllSuper();
         yearField.setDisable(true);
    }

    public void updateView(){
        updateSuper();
        Student student = (Student) getDisplayedUser();
        yearField.setText(String.valueOf(student.getYear()));
    }

}
