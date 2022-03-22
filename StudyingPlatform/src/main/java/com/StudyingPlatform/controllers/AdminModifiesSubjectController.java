package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.service.DataBaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class AdminModifiesSubjectController {
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionText;
    @FXML
    private CheckBox lectureCheckBox;
    @FXML
    private CheckBox seminarCheckBox;
    @FXML
    private CheckBox labCheckBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker finishDatePicker;
    @FXML
    private Button nameEditButton;
    @FXML
    private Button descriptionEditButton;
    @FXML
    private Button saveChangesButton;

    private Subject displayedSubject;
    private boolean canEdit;

    @FXML
    public void onEditNameFieldClick() {
        nameField.setDisable(false);
        descriptionText.setDisable(true);
    }

    @FXML
    public void onEditDescriptionTextClick() {
        nameField.setDisable(true);
        descriptionText.setDisable(false);
    }

    @FXML
    public void onSaveChangesButtonClick() throws IOException {
        this.displayedSubject.setName(nameField.getText());
        this.displayedSubject.setDescription(descriptionText.getText());
        try {
            DataBaseService.updateSubject(this.displayedSubject);
            SuperController.popMessage("Subject updated successfully");
        } catch (SQLException e) {
            SuperController.popError("Something went wrong.");
            e.printStackTrace();
        }

    }

    @FXML
    public void onBackButtonClick() throws IOException {
        if (canEdit) {
            Stage stage = StudyingApplication.getPrimaryStage();
            URL url = StudyingApplication.class.getResource("admin-view.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Scene scene = new Scene(fxmlLoader.load(), 400, 500);
            stage.setScene(scene);
        } else {
            if ("STUDENT".equals(SuperController.activeUser.getRole())) {
                StudyingApplication.jumpToView("student-subjects.fxml", 550, 500);
            } else if ("PROFESSOR".equals(SuperController.activeUser.getRole())) {
                StudyingApplication.jumpToView("professor-subjects.fxml", 550, 500);
            } else throw new IllegalStateException("unexpected user");
        }
    }

    public void setSubject(Subject subject, boolean canEdit) {
        this.displayedSubject = subject;
        this.canEdit = canEdit;
        if (!canEdit) {
            saveChangesButton.setVisible(false);
            nameEditButton.setVisible(false);
            descriptionEditButton.setVisible(false);
        }
        updateView();
    }

    public Subject getDisplayedSubject() {
        return displayedSubject;
    }

    private void updateView() {
        nameField.setText(displayedSubject.getName());
        descriptionText.setText(displayedSubject.getDescription());
        lectureCheckBox.setSelected(displayedSubject.getHasLecture());
        seminarCheckBox.setSelected(displayedSubject.getHasSeminar());
        labCheckBox.setSelected(displayedSubject.getHasLab());
    }
}
