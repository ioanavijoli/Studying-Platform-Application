package com.StudyingPlatform.application;

import com.StudyingPlatform.controllers.SuperController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;

public class StudyingApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(new Timestamp(System.currentTimeMillis()));
        primaryStage = stage;
        primaryStage.setTitle("Studying Platform");
       // try{
       //     SuperController.activeUser = DataBaseService.getUserById(1);
       // }catch (Exception e){
       // }
        StudyingApplication.jumpToView("log-in-view.fxml",400,500);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void jumpToView(String view){
        jumpToView(view,400,500);
    }
    public static void jumpToView(String view, int xSize, int ySize){
        try {
            URL url = StudyingApplication.class.getResource(view);
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Scene scene = new Scene(fxmlLoader.load(), xSize, ySize);
            primaryStage.setScene(scene);
        }catch (IOException e){
            SuperController.popError("Failed to jump to " + view);
            e.printStackTrace();
        }
    }
}

