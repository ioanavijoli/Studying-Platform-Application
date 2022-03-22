package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.*;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.ScheduleException;
import com.StudyingPlatform.service.ProfessorService;
import com.StudyingPlatform.service.SubjectProfessorService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class AddActivityController implements Initializable {

    @FXML
    private Spinner<Integer> activityStartSpinner;
    @FXML
    private Spinner<Integer> activityEndSpinner;
    @FXML
    private Spinner<Integer> capacitySpinner;
    @FXML
    private Spinner<Integer> expireSpinner;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField nameField;

    private SubjectProfessor subject;
    private Stage myStage;
    private ChatController parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> activityStartSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6,22);
        activityStartSpinnerFactory.setValue(8);
        activityStartSpinner.setValueFactory(activityStartSpinnerFactory);

        SpinnerValueFactory<Integer> activityEndSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6,23);
        activityEndSpinnerFactory.setValue(9);
        activityEndSpinner.setValueFactory(activityEndSpinnerFactory);

        SpinnerValueFactory<Integer> capacitySpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(2,60);
        capacitySpinnerFactory.setValue(60);
        capacitySpinner.setValueFactory(capacitySpinnerFactory);

        SpinnerValueFactory<Integer> expireSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,4);
        capacitySpinnerFactory.setValue(2);
        expireSpinner.setValueFactory(expireSpinnerFactory);
    }

    @FXML
    public void onCancelButtonClick(){
        myStage.close();
    }

    @FXML
    public void onScheduleButtonClick() throws IOException {
        Activity activity = new Activity(
                0,
                parentController.getSelectedGroup(),
                nameField.getText(),
                Date.valueOf(datePicker.getValue()),
                activityStartSpinner.getValue(),
                activityEndSpinner.getValue() - activityStartSpinner.getValue(),
                capacitySpinner.getValue(),
                Timestamp.valueOf(LocalDateTime.now().plusHours(expireSpinner.getValue()))
                );
        try {
            activity.insertInDataBase();
        }catch (SQLException e){
            e.printStackTrace();
            SuperController.popError("Something went wrong.");
            myStage.close();
            return;
        }

        URL url = StudyingApplication.class.getResource("activity-message.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent row = (Parent) fxmlLoader.load();
        ActivityMessageController controller = fxmlLoader.<ActivityMessageController>getController();
        controller.set(activity, parentController);
        parentController.getMessageVBox().getChildren().add(
                parentController.getMessageVBox().getChildren().size(),
                row
        );
        myStage.close();
    }

    public void create(Stage stage, ChatController parentController){
        this.myStage = stage;
        this.parentController = parentController;
    }

    private boolean isTimeValid(Spinner<Integer> start, Spinner<Integer> end){
        if(start.getValue() == null || end.getValue() == null)
            return false;
        if(start.getValue() >= end.getValue())
            return false;
        return true;
    }
}
