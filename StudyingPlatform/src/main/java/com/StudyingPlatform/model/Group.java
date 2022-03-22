package com.StudyingPlatform.model;

public class Group {
    private int id;
    private String name;
    private int subjectId;

    public Group(){

    }
    public Group(int id, String name, int subjectId ) {
        this.id = id;
        this.name = name;
        this.subjectId = subjectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
