package com.StudyingPlatform.model;

public class Professor extends User{
    int minTeachingHours;
    int maxTeachingHours;
    String department;

    public Professor(){

    }

    public Professor(int id, String username, String password, String role, String cnp,
                     String firstName, String lastName, Address address, String phone,
                     String email, String iban, String contractNumber, boolean isAdmin,
                     boolean isSuperAdmin, int minTeachingHours, int maxTeachingHours, String department) {
        super(id, username, password, role, cnp, firstName, lastName, address, phone, email, iban, contractNumber, isAdmin, isSuperAdmin);
        this.minTeachingHours = minTeachingHours;
        this.maxTeachingHours = maxTeachingHours;
        this.department = department;
    }

    public int getMinTeachingHours() {
        return minTeachingHours;
    }

    public void setMinTeachingHours(int minTeachingHours) {
        this.minTeachingHours = minTeachingHours;
    }

    public int getMaxTeachingHours() {
        return maxTeachingHours;
    }

    public void setMaxTeachingHours(int maxTeachingHours) {
        this.maxTeachingHours = maxTeachingHours;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
