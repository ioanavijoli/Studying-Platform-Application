package com.StudyingPlatform.model;

public class Student extends User{
    private int year;
    private int minStudyingHours;

    public Student(){

    }
    public Student(int id, String username, String password, String role, String cnp,
                   String firstName, String lastName, Address address, String phone,
                   String email, String iban, String contractNumber, boolean isAdmin,
                   boolean isSuperAdmin, int year, int minStudyingHours) {
        super(id, username, password, role, cnp, firstName, lastName, address, phone, email, iban, contractNumber, isAdmin, isSuperAdmin);
        this.year = year;
        this.minStudyingHours = minStudyingHours;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMinStudyingHours() {
        return minStudyingHours;
    }

    public void setMinStudyingHours(int minStudyingHours) {
        this.minStudyingHours = minStudyingHours;
    }
}
