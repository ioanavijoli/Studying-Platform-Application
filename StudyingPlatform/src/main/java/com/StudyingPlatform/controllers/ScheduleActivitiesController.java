package com.StudyingPlatform.controllers;

import com.StudyingPlatform.model.Professor;
import com.StudyingPlatform.model.ScheduleEntry;
import com.StudyingPlatform.model.ScheduleTime;
import com.StudyingPlatform.model.SubjectProfessor;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.ScheduleException;
import com.StudyingPlatform.service.ProfessorService;
import com.StudyingPlatform.service.SubjectProfessorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import javax.security.auth.Subject;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleActivitiesController implements Initializable {
    @FXML
    private ChoiceBox<String> lectureDayDropDown;
    @FXML
    private ChoiceBox<String> seminarDayDropDown;
    @FXML
    private ChoiceBox<String> labDayDropDown;
    @FXML
    private Spinner<Integer> lectureStartSpinner;
    @FXML
    private Spinner<Integer> seminarStartSpinner;
    @FXML
    private Spinner<Integer> labStartSpinner;
    @FXML
    private Spinner<Integer> lectureEndSpinner;
    @FXML
    private Spinner<Integer> seminarEndSpinner;
    @FXML
    private Spinner<Integer> labEndSpinner;
    @FXML
    private Spinner<Integer> capacitySpinner;

    SubjectProfessor subject;
    Stage myStage;
    ProfessorSubjectsRowController parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] weekDays = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        lectureDayDropDown.getItems().addAll(weekDays);
        seminarDayDropDown.getItems().addAll(weekDays);
        labDayDropDown.getItems().addAll(weekDays);
        SpinnerValueFactory<Integer> lectureStartSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6,22);
        lectureStartSpinnerFactory.setValue(8);
        lectureStartSpinner.setValueFactory(lectureStartSpinnerFactory);

        SpinnerValueFactory<Integer> seminarStartSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6,22);
        seminarStartSpinnerFactory.setValue(8);
        seminarStartSpinner.setValueFactory(seminarStartSpinnerFactory);

        SpinnerValueFactory<Integer> labStartSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6,22);
        labStartSpinnerFactory.setValue(8);
        labStartSpinner.setValueFactory(labStartSpinnerFactory);

        SpinnerValueFactory<Integer> lectureEndSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6,23);
        lectureEndSpinnerFactory.setValue(9);
        lectureEndSpinner.setValueFactory(lectureEndSpinnerFactory);

        SpinnerValueFactory<Integer> seminarEndSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6,23);
        seminarEndSpinnerFactory.setValue(9);
        seminarEndSpinner.setValueFactory(seminarEndSpinnerFactory);

        SpinnerValueFactory<Integer> labEndSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(6,23);
        labEndSpinnerFactory.setValue(9);
        labEndSpinner.setValueFactory(labEndSpinnerFactory);

        SpinnerValueFactory<Integer> capacitySpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10,200);
        capacitySpinnerFactory.setValue(60);
        capacitySpinner.setValueFactory(capacitySpinnerFactory);
        //DEL LATER
        try {
            Professor professor = (Professor) DataBaseService.getUserById(2);
            subject = ProfessorService.professorGetSubjects(professor).get(0);
            subject.setProfessor(professor);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onCancelButtonClick(){
        myStage.close();
    }

    @FXML
    public void onScheduleButtonClick(){
        List<ScheduleEntry> schedule;
        try {
            schedule = ProfessorService.professorGetSchedule((Professor)SuperController.activeUser);
        }catch(ScheduleException e){
            SuperController.popError("Something went wrong.");
            return;
        }
        if(subject.getHasLecture()){
            if(lectureDayDropDown.getValue() == null){
                SuperController.popError("Lecture requires a day.");
                return;
            }
            if(!isTimeValid(lectureStartSpinner,lectureEndSpinner)){
                SuperController.popError("Invalid hour for lecture");
                return;
            }
            ScheduleTime lectureTime = new ScheduleTime(
                    lectureDayDropDown.getValue(),
                    lectureStartSpinner.getValue(),
                    lectureEndSpinner.getValue() - lectureStartSpinner.getValue()
            );
            boolean doesOverlap = false;
            for(ScheduleEntry e:schedule){
                if(e.getTime().overlaps(lectureTime)){
                    doesOverlap = true;
                    break;
                }
            }
            if(doesOverlap){
              SuperController.popError("The lecture overlaps another activity");
              return;
            }
            subject.setScheduleLecture(lectureTime);
            schedule.add(new ScheduleEntry(lectureTime,"",""));
        }
        if(subject.getHasSeminar()){
            if(seminarDayDropDown.getValue() == null){
                SuperController.popError("Seminar requires a day.");
                return;
            }
            if(!isTimeValid(lectureStartSpinner,seminarEndSpinner)){
                SuperController.popError("Invalid hour for seminar.");
                return;
            }
            ScheduleTime seminarTime = new ScheduleTime(
                    seminarDayDropDown.getValue(),
                    seminarStartSpinner.getValue(),
                    seminarEndSpinner.getValue() - seminarStartSpinner.getValue()
            );
            boolean doesOverlap = false;
            for(ScheduleEntry e:schedule){
                if(e.getTime().overlaps(seminarTime)){
                    doesOverlap = true;
                    break;
                }
            }
            if(doesOverlap){
                SuperController.popError("The seminar overlaps another activity");
                return;
            }
            subject.setScheduleSeminar(seminarTime);
            schedule.add(new ScheduleEntry(seminarTime,"",""));
        }
        if(subject.getHasLab()){
            if(labDayDropDown.getValue() == null){
                SuperController.popError("Lab requires a day.");
                return;
            }
            if(!isTimeValid(labStartSpinner,labEndSpinner)){
                SuperController.popError("Invalid hour for lab.");
                return;
            }
            ScheduleTime labTime = new ScheduleTime(
                    labDayDropDown.getValue(),
                    labStartSpinner.getValue(),
                    labEndSpinner.getValue() - labStartSpinner.getValue()
            );
            boolean doesOverlap = false;
            for(ScheduleEntry e:schedule){
                if(e.getTime().overlaps(labTime)){
                    doesOverlap = true;
                    break;
                }
            }
            if(doesOverlap){
                SuperController.popError("The lab overlaps another activity");
                return;
            }
            subject.setScheduleLab(labTime);
            schedule.add(new ScheduleEntry(labTime,"",""));
        }
        if(capacitySpinner.getValue()!=null){
            subject.setStudentsCapacity(capacitySpinner.getValue());
        }
        try {
            SubjectProfessorService.schedule_activities(subject);
        } catch (SQLException e) {
            SuperController.popError("Something went wrong");
        }
        SuperController.popMessage("Successfully scheduled the activities");
        parentController.disableScheduleButton();
        subject.setFinishedSchedule(true);
        myStage.close();
    }

    public void create(SubjectProfessor subject, Stage stage,ProfessorSubjectsRowController parentController){
        this.subject = subject;
        this.myStage = stage;
        this.parentController = parentController;
        disableAll();
        if(subject.getHasLecture()){
            lectureDayDropDown.setDisable(false);
            lectureStartSpinner.setDisable(false);
            lectureEndSpinner.setDisable(false);
        }
        if(subject.getHasSeminar()){
            seminarDayDropDown.setDisable(false);
            seminarStartSpinner.setDisable(false);
            seminarEndSpinner.setDisable(false);
        }
        if(subject.getHasLab()){
            labDayDropDown.setDisable(false);
            labStartSpinner.setDisable(false);
            labEndSpinner.setDisable(false);
        }
    }

    public void disableAll(){
        lectureDayDropDown.setDisable(true);
        lectureStartSpinner.setDisable(true);
        lectureEndSpinner.setDisable(true);
        seminarDayDropDown.setDisable(true);
        seminarStartSpinner.setDisable(true);
        seminarEndSpinner.setDisable(true);
        labDayDropDown.setDisable(true);
        labStartSpinner.setDisable(true);
        labEndSpinner.setDisable(true);
    }

    private boolean isTimeValid(Spinner<Integer> start, Spinner<Integer> end){
        if(start.getValue() == null || end.getValue() == null)
            return false;
        if(start.getValue() >= end.getValue())
            return false;
        return true;
    }
}
