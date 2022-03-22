package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SuperController {
    public static User activeUser;
    public static Stage popMessage(String message,Color color){
        try {
            Stage stage = new Stage();
            URL url = StudyingApplication.class.getResource("message-pop-up.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = (Parent) fxmlLoader.load();
            MessagePopupController controller = fxmlLoader.<MessagePopupController>getController();
            controller.create(message, stage, color);
            Scene scene = new Scene(root, 270, 70);
            stage.setTitle("Message");
            stage.setScene(scene);
            stage.show();
            return stage;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static Stage popMessage(String message){
        return popMessage(message,Color.color(0,0,0));
    }

    public static Stage popError(String message){
        return popMessage(message,Color.color(1,0,0));
    }


}
