package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Professor;
import com.StudyingPlatform.service.DataBaseService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminModifiesProfessorController extends AdminModifiesUserController{
    @FXML
    private TextField departmentField;

    @FXML
    public void onEditDepartmentClick(){
        disableAll();
        departmentField.setDisable(false);
    }
    @FXML
    public void onSaveChangesButtonClick() throws IOException {
        updateDisplayedUser();
        ((Professor)getDisplayedUser()).setDepartment(departmentField.getText());
        try{
            DataBaseService.updateUser(getDisplayedUser());
        }catch(SQLException e){
            SuperController.popError("Something went wrong, we are sorry.");
        }
    }
    void disableAll(){
        disableAllSuper();
        departmentField.setDisable(true);
    }
    public void updateView(){
        updateSuper();
        departmentField.setText(((Professor)getDisplayedUser()).getDepartment());
    }
}
