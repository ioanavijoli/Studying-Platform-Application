package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.User;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserListController implements Initializable {
    @FXML
    VBox usersVBox;
    @FXML
    Button anyButton;
    @FXML
    Button studentButton;
    @FXML
    Button professorButton;
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;

    List<User> listedUsers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onAnyButtonClick();
    }

    @FXML
    public void onAnyButtonClick() {
        studentButton.setDisable(false);
        professorButton.setDisable(false);
        anyButton.setDisable(true);
        try {
            listedUsers = DataBaseService.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            listedUsers = new ArrayList<>();
        } catch (UserNotFoundException e) {
            SuperController.popError("No user was found.");
            listedUsers = new ArrayList<>();
        }
        updateList();
    }

    @FXML
    public void onStudentButtonClick() {
        studentButton.setDisable(true);
        professorButton.setDisable(false);
        anyButton.setDisable(false);
        try {
            listedUsers = DataBaseService.getAllStudents();
        } catch (UserNotFoundException e) {
            SuperController.popError("No user was found.");
            listedUsers = new ArrayList<>();
        } catch (SQLException e) {
            e.printStackTrace();
            listedUsers = new ArrayList<>();
        }
        updateList();
    }

    @FXML
    public void onProfessorButtonClick() {
        studentButton.setDisable(false);
        professorButton.setDisable(true);
        anyButton.setDisable(false);

        try {
            listedUsers = DataBaseService.getAllProfessor();
        } catch (SQLException e) {
            e.printStackTrace();
            listedUsers = new ArrayList<>();
        } catch (UserNotFoundException e) {
            SuperController.popError("No user was found.");
            listedUsers = new ArrayList<>();
        }
        updateList();
    }

    @FXML
    public void onSearchButtonClick() {
        if(firstNameField.getText().equals("") && lastNameField.getText().equals("")){
            onAnyButtonClick();
            return;
        }
        studentButton.setDisable(false);
        professorButton.setDisable(false);
        anyButton.setDisable(true);
        try {
            listedUsers = DataBaseService.getUsersByName(firstNameField.getText(), lastNameField.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            listedUsers = new ArrayList<>();
        }
        updateList();
    }

    @FXML
    public void onBackButtonClick() throws IOException {
        StudyingApplication.jumpToView("admin-view.fxml");
    }

    private void updateList() {
        usersVBox.getChildren().clear();
        for (User user : listedUsers) {
            try {
                URL url = StudyingApplication.class.getResource("user-list-row.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                Parent row = fxmlLoader.load();
                UserListRowController controller = fxmlLoader.getController();
                controller.setUser(user);
                usersVBox.getChildren().add(row);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
