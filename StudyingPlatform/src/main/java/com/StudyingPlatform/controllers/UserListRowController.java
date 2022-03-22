package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class UserListRowController {
    @FXML
    private Label userNameLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;

    private User listedUser;

    @FXML
    public void onViewProfileButtonClick() throws IOException {
        if("STUDENT".equals(listedUser.getRole())){
            URL url = StudyingApplication.class.getResource("admin-modifies-student.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = (Parent)fxmlLoader.load();
            AdminModifiesStudentController controller
                    = fxmlLoader.<AdminModifiesStudentController>getController();
            controller.setUser(listedUser);
            Stage stage = StudyingApplication.getPrimaryStage();
            stage.setScene(new Scene(root, 400, 500));
        }else if("PROFESSOR".equals(listedUser.getRole())){
            URL url = StudyingApplication.class.getResource("admin-modifies-professor.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = (Parent)fxmlLoader.load();
            AdminModifiesProfessorController controller
                    = fxmlLoader.<AdminModifiesProfessorController>getController();
            controller.setUser(listedUser);
            Stage stage = StudyingApplication.getPrimaryStage();
            stage.setScene(new Scene(root, 400, 500));
        }
    }

    public void setUser(User user){
        listedUser = user;
        userNameLabel.setText(user.getUsername());
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
    }
}
