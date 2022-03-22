package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Activity;
import com.StudyingPlatform.service.DataBaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ActivityMessageController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label expiresAtLabel;

    private ChatController parentController;
    private Activity activity;

    @FXML
    public void onJoinButtonClick(){
        try {
            Connection connection = DataBaseService.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into student_has_activity(student_id,activity_id) values (?,?)");
            preparedStatement.setInt(1, SuperController.activeUser.getId());
            preparedStatement.setInt(2, activity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            SuperController.popError("you already joined.");
            return;
        }catch (SQLException e){
            SuperController.popError("something went wrong");
            return;
        }
        SuperController.popMessage("Joined activity.");
    }

    public void set(Activity activity, ChatController parentController){
        this.activity = activity;
        nameLabel.setText(activity.getName());
        timeLabel.setText(activity.getDate().toString() + " " +
                activity.getStartHour() + ":00 - " +
                (activity.getStartHour() + activity.getDuration()) + ":00"
        );
        statusLabel.setText("requires " + activity.countParticipants() +
                    "/" + activity.getMinParticipants()
                );
        String expireString = new String(activity.getExpireTime().toString().toCharArray(),11,8);
        expiresAtLabel.setText("expires at " + expireString);
    }
}
