package com.StudyingPlatform.model;

import java.sql.Date;

public class SubjectProfessor extends Subject {
    Professor professor;
    private int studentsCapacity;
    private float lectureWeight;
    private float seminarWeight;
    private float labWeight;
    private ScheduleTime scheduleLecture;
    private ScheduleTime scheduleSeminar;
    private ScheduleTime scheduleLab;
    private boolean finishedSchedule;

    public SubjectProfessor(){

    }

    public SubjectProfessor(int id, String name, String description, boolean hasLecture, boolean hasSeminar, boolean hasLab, Date dateStart, Date dateEnd, Professor professor, int studentsCapacity, float lectureWeight, float seminarWeight, float labWeight, ScheduleTime scheduleLecture, ScheduleTime scheduleSeminar, ScheduleTime scheduleLab, boolean finishedSchedule) {
        super(id, name, description, hasLecture, hasSeminar, hasLab, dateStart, dateEnd);
        this.professor = professor;
        this.studentsCapacity = studentsCapacity;
        this.lectureWeight = lectureWeight;
        this.seminarWeight = seminarWeight;
        this.labWeight = labWeight;
        this.scheduleLecture = scheduleLecture;
        this.scheduleSeminar = scheduleSeminar;
        this.scheduleLab = scheduleLab;
        this.finishedSchedule = finishedSchedule;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public int getStudentsCapacity() {
        return studentsCapacity;
    }

    public void setStudentsCapacity(int studentsCapacity) {
        this.studentsCapacity = studentsCapacity;
    }

    public float getLectureWeight() {
        return lectureWeight;
    }

    public void setLectureWeight(float lectureWeight) {
        this.lectureWeight = lectureWeight;
    }

    public float getSeminarWeight() {
        return seminarWeight;
    }

    public void setSeminarWeight(float seminarWeight) {
        this.seminarWeight = seminarWeight;
    }

    public float getLabWeight() {
        return labWeight;
    }

    public void setLabWeight(float labWeight) {
        this.labWeight = labWeight;
    }

    public ScheduleTime getScheduleLecture() {
        return scheduleLecture;
    }

    public void setScheduleLecture(ScheduleTime scheduleLecture) {
        this.scheduleLecture = scheduleLecture;
    }

    public ScheduleTime getScheduleSeminar() {
        return scheduleSeminar;
    }

    public void setScheduleSeminar(ScheduleTime scheduleSeminar) {
        this.scheduleSeminar = scheduleSeminar;
    }

    public ScheduleTime getScheduleLab() {
        return scheduleLab;
    }

    public void setScheduleLab(ScheduleTime scheduleLab) {
        this.scheduleLab = scheduleLab;
    }

    public boolean isFinishedSchedule() {
        return finishedSchedule;
    }

    public void setFinishedSchedule(boolean finishedSchedule) {
        this.finishedSchedule = finishedSchedule;
    }
}
