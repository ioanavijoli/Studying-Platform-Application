package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.*;
import com.StudyingPlatform.service.Exceptions.ScheduleException;
import com.StudyingPlatform.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class StudentActivitiesController {
    @FXML
    private Label lectureDay;
    @FXML
    private Label lectureStarts;
    @FXML
    private Label lectureEnds;
    @FXML
    private Button lectureJoin;
    @FXML
    private Label seminarDay;
    @FXML
    private Label seminarStarts;
    @FXML
    private Label seminarEnds;
    @FXML
    private Button seminarJoin;
    @FXML
    private Label labDay;
    @FXML
    private Label labStarts;
    @FXML
    private Label labEnds;
    @FXML
    private Button labJoin;

    private SubjectStudent subject;
    private Stage myStage;
    private ScheduleEntry lectureScheduleEntry;
    private ScheduleEntry seminarScheduleEntry;
    private ScheduleEntry labScheduleEntry;
    private List<ScheduleEntry> schedule;

    public void set(SubjectStudent subject, Stage myStage) {
        this.myStage = myStage;
        this.subject = subject;
        if (subject.getHasLecture()) {
            lectureScheduleEntry = new ScheduleEntry(
                    new ScheduleTime(
                            subject.getScheduleLecture().getDayOfWeek(),
                            subject.getScheduleLecture().getHour(),
                            subject.getScheduleLecture().getDuration()
                    ),
                    "LECTURE",
                    subject.getName() + " LECTURE"
            );
            lectureDay.setText(subject.getScheduleLecture().getDayOfWeek().toString());
            lectureStarts.setText(String.valueOf(subject.getScheduleLecture().getHour()));
            lectureEnds.setText(String.valueOf(subject.getScheduleLecture().getHour() + subject.getScheduleLecture().getDuration()));
            if(subject.isEnrolledLecture()){
                lectureJoin.setDisable(true);
            }
        } else {
            lectureDay.setVisible(false);
            lectureStarts.setVisible(false);
            lectureEnds.setVisible(false);
            lectureJoin.setVisible(false);
        }
        if (subject.getHasSeminar()) {
            seminarScheduleEntry = new ScheduleEntry(
                    new ScheduleTime(
                            subject.getScheduleSeminar().getDayOfWeek(),
                            subject.getScheduleSeminar().getHour(),
                            subject.getScheduleSeminar().getDuration()
                    ),
                    "SEMINAR",
                    subject.getName() + " SEMINAR"
            );
            seminarDay.setText(subject.getScheduleSeminar().getDayOfWeek().toString());
            seminarStarts.setText(String.valueOf(subject.getScheduleSeminar().getHour()));
            seminarEnds.setText(String.valueOf(subject.getScheduleSeminar().getHour() + subject.getScheduleLecture().getDuration()));
            if(subject.isEnrolledSeminar()){
                seminarJoin.setDisable(true);
            }
        } else {
            seminarDay.setVisible(false);
            seminarStarts.setVisible(false);
            seminarEnds.setVisible(false);
            seminarJoin.setVisible(false);
        }
        if (subject.getHasLab()) {
            labScheduleEntry = new ScheduleEntry(
                    new ScheduleTime(
                            subject.getScheduleLab().getDayOfWeek(),
                            subject.getScheduleLab().getHour(),
                            subject.getScheduleLab().getDuration()
                    ),
                    "LAB",
                    subject.getName() + " LAB"
            );
            labDay.setText(subject.getScheduleLab().getDayOfWeek().toString());
            labStarts.setText(String.valueOf(subject.getScheduleLab().getHour()));
            labEnds.setText(String.valueOf(subject.getScheduleLab().getHour() + subject.getScheduleLecture().getDuration()));
            if(subject.isEnrolledLab()){
                labJoin.setDisable(true);
            }
        } else {
            labDay.setVisible(false);
            labStarts.setVisible(false);
            labEnds.setVisible(false);
            labJoin.setVisible(false);
        }
        try{
        schedule = StudentService.studentGetSchedule((Student)SuperController.activeUser);
        }catch (ScheduleException e){
            myStage.close();
            SuperController.popError("Something went wrong.");
            return;
        }
    }

    @FXML
    public void onLectureJoinButton() {
        try {
            for(ScheduleEntry e:schedule){
                if(lectureScheduleEntry.overlaps(e)){
                    SuperController.popMessage("This overlaps with one of you other activities.");
                    return;
                }
            }
            String status = StudentService.enrollActivity(subject,"LECTURE");
            if("capacity exceeded".equals(status)){
                SuperController.popMessage("You can't enroll in this activity due to it's capacity being exceeded.");
            }else if("successful".equals(status)){
                lectureJoin.setDisable(true);
                subject.setEnrolledLecture(true);
            }
        }catch (SQLException e){
            e.printStackTrace();
            myStage.close();
            SuperController.popError("Something went wrong.");
            return;
        }
    }

    @FXML
    public void onSeminarJoinButton() {
        try {
            for(ScheduleEntry e:schedule){
                if(seminarScheduleEntry.overlaps(e)){
                    SuperController.popMessage("This overlaps with one of you other activities.");
                    return;
                }
            }
            String status = StudentService.enrollActivity(subject,"SEMINAR");
            if("capacity exceeded".equals(status)){
                SuperController.popMessage("You can't enroll in this activity due to it's capacity being exceeded.");
            }else if("successful".equals(status)){
                seminarJoin.setDisable(true);
                subject.setEnrolledSeminar(true);
            }
        }catch (SQLException e){
            e.printStackTrace();
            myStage.close();
            SuperController.popError("Something went wrong.");
            return;
        }
    }

    @FXML
    public void onLabJoinButton() {
        try {
            for(ScheduleEntry e:schedule){
                if(labScheduleEntry.overlaps(e)){
                    SuperController.popMessage("This overlaps with one of you other activities.");
                    return;
                }
            }
            String status = StudentService.enrollActivity(subject,"LAB");
            if("capacity exceeded".equals(status)){
                SuperController.popMessage("You can't enroll in this activity due to it's capacity being exceeded.");
            }else if("successful".equals(status)){
                labJoin.setDisable(true);
                subject.setEnrolledLab(true);
            }
        }catch (SQLException e){
            e.printStackTrace();
            myStage.close();
            SuperController.popError("Something went wrong.");
            return;
        }
    }

    @FXML
    public void onBackButton() {
        myStage.close();
    }


}
