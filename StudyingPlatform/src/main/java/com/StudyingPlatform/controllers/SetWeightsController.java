package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.SubjectProfessor;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.SubjectProfessorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.security.auth.Subject;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SetWeightsController implements Initializable {
    @FXML
    private Spinner<Integer> lectureSpinner;
    @FXML
    private Spinner<Integer> seminarSpinner;
    @FXML
    private Spinner<Integer> labSpinner;
    @FXML
    private Label errorLabel;

    private Stage myStage;
    private SubjectProfessor subject;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> lectureSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        lectureSpinnerFactory.setValue(0);
        lectureSpinner.setValueFactory(lectureSpinnerFactory);

        SpinnerValueFactory<Integer> seminarSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        seminarSpinnerFactory.setValue(0);
        seminarSpinner.setValueFactory(seminarSpinnerFactory);

        SpinnerValueFactory<Integer> labSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        labSpinnerFactory.setValue(0);
        labSpinner.setValueFactory(labSpinnerFactory);
    }

    @FXML
    public void onSetButtonClick() {
        int sum = 0;
        if (subject.getHasLecture()) {
            sum += lectureSpinner.getValue();
        }
        if (subject.getHasSeminar()) {
            sum += seminarSpinner.getValue();
        }
        if (subject.getHasLab()) {
            sum += labSpinner.getValue();
        }
        if(sum!=100){
            errorLabel.setText("The percentages do not add up to 100%.");
            errorLabel.setVisible(true);
            return;
        }
        try{
            SubjectProfessorService.setWeights(subject,
                    subject.getProfessor(),
                    lectureSpinner.getValue(),
                    seminarSpinner.getValue(),
                    labSpinner.getValue()
                    );
            myStage.close();
        }catch (SQLException e){
            e.printStackTrace();
            errorLabel.setText("Something went wrong.");
            errorLabel.setVisible(true);
            return;
        }
    }

    @FXML
    public void onCancelButtonClick() {
        myStage.close();
    }

    public void create(SubjectProfessor subject, Stage stage) {
        myStage = stage;
        this.subject = subject;
        if (!subject.getHasLecture()) {
            lectureSpinner.setDisable(true);
        }
        if (!subject.getHasSeminar()) {
            seminarSpinner.setDisable(true);
        }
        if (!subject.getHasLab()) {
            labSpinner.setDisable(true);
        }
    }


}
