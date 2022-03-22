package com.StudyingPlatform.model;

public class Studying {
    private int studentId;
    private int subjectId;
    private int gradeLecture;
    private int gradeSeminar;
    private int gradeLab;
    private int professorId;

    public Studying(){

    }

    public Studying(int studentId, int subjectId, int gradeLecture, int gradeSeminar,
                    int gradeLab, int professorId) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.gradeLecture = gradeLecture;
        this.gradeSeminar = gradeSeminar;
        this.gradeLab = gradeLab;
        this.professorId = professorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
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

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {this.professorId = professorId;}
}