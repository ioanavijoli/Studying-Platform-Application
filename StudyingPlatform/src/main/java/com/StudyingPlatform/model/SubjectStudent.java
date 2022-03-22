package com.StudyingPlatform.model;

import java.sql.Date;

public class SubjectStudent extends SubjectProfessor{
    private Student student;
    private int gradeLecture;
    private int gradeSeminar;
    private int gradeLab;
    private boolean isEnrolledLecture;
    private boolean isEnrolledSeminar;
    private boolean isEnrolledLab;

    public SubjectStudent() {

    }
    public SubjectStudent(int id, String name, String description, boolean hasLecture, boolean hasSeminar, boolean hasLab, Date dateStart, Date dateEnd, Professor professor, int studentsCapacity, float lectureWeight, float seminarWeight, float labWeight, ScheduleTime scheduleLecture, ScheduleTime scheduleSeminar, ScheduleTime scheduleLab, boolean finishedSchedule, Student student, int gradeLecture, int gradeSeminar, int gradeLab, boolean isEnrolledLecture, boolean isEnrolledSeminar, boolean isEnrolledLab) {
        super(id, name, description, hasLecture, hasSeminar, hasLab, dateStart, dateEnd, professor, studentsCapacity, lectureWeight, seminarWeight, labWeight, scheduleLecture, scheduleSeminar, scheduleLab, finishedSchedule);
        this.student = student;
        this.gradeLecture = gradeLecture;
        this.gradeSeminar = gradeSeminar;
        this.gradeLab = gradeLab;
        this.isEnrolledLecture = isEnrolledLecture;
        this.isEnrolledSeminar = isEnrolledSeminar;
        this.isEnrolledLab = isEnrolledLab;
    }

    public int getGradeLecture() {
        return gradeLecture;
    }

    public void setGradeLecture(int gradeLecture) {
        this.gradeLecture = gradeLecture;
    }

    public int getGradeSeminar() {
        return gradeSeminar;
    }

    public void setGradeSeminar(int gradeSeminar) {
        this.gradeSeminar = gradeSeminar;
    }

    public int getGradeLab() {
        return gradeLab;
    }

    public void setGradeLab(int gradeLab) {
        this.gradeLab = gradeLab;
    }

    public boolean isEnrolledLecture() {
        return isEnrolledLecture;
    }

    public void setEnrolledLecture(boolean enrolledLecture) {
        isEnrolledLecture = enrolledLecture;
    }

    public boolean isEnrolledSeminar() {
        return isEnrolledSeminar;
    }

    public void setEnrolledSeminar(boolean enrolledSeminar) {
        isEnrolledSeminar = enrolledSeminar;
    }

    public boolean isEnrolledLab() {
        return isEnrolledLab;
    }

    public void setEnrolledLab(boolean enrolledLab) {
        isEnrolledLab = enrolledLab;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
