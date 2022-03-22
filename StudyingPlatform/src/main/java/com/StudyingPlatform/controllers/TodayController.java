package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Professor;
import com.StudyingPlatform.model.ScheduleEntry;
import com.StudyingPlatform.model.Student;
import com.StudyingPlatform.service.Exceptions.ScheduleException;
import com.StudyingPlatform.service.IOService;
import com.StudyingPlatform.service.ProfessorService;
import com.StudyingPlatform.service.StudentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TodayController implements Initializable {
    @FXML
    private VBox vBox;

    List<ScheduleEntry> schedule;

    @FXML
    public void onBackButtonClick() {
        StudyingApplication.jumpToView("home.fxml",550,500);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if ("STUDENT".equals(SuperController.activeUser.getRole())) {
                schedule = StudentService.studentGetSchedule((Student) SuperController.activeUser);
            } else if ("PROFESSOR".equals(SuperController.activeUser.getRole())) {
                schedule = ProfessorService.professorGetSchedule((Professor) SuperController.activeUser);
            } else {
                throw new IllegalStateException("Unexpected role");
            }
        } catch (ScheduleException e) {
            e.printStackTrace();
            SuperController.popError("Could not initialize the -today- page for you.");
            return;
        }

        List<TodayRowController> controllers = new ArrayList<>();
        for (int i = 6; i <= 22; i++) {
            try {
                URL myUrl = StudyingApplication.class.getResource("today-row.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(myUrl);
                Parent row = (Parent) fxmlLoader.load();
                TodayRowController controller = fxmlLoader.<TodayRowController>getController();
                controllers.add(controller);
                controller.init(i);
                vBox.getChildren().add(row);
            } catch (IOException e) {
                e.printStackTrace();
                SuperController.popError("Could not initialize the -today- page for you.");
                return;
            }
        }
        for (ScheduleEntry e : schedule) {
            if (!e.getTime().getDayOfWeek().equals(LocalDateTime.now().getDayOfWeek()))
                continue;
            int hour = e.getTime().getHour();
            int duration = e.getTime().getDuration();
            int controllersIndex = hour - 6;
            controllers.get(controllersIndex).setBlock(e.getName() + " " + e.getType());
            for (int i = controllersIndex + 1; i < controllersIndex + duration; i++) {
                controllers.get(i).setBlock(null);
            }
            controllers.get(controllersIndex + duration - 1).addSpacing();
        }
    }
    public void onDownloadButtonClick() throws IOException {
        IOService.writeActivities(schedule);
    }
}
