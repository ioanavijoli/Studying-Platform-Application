package com.StudyingPlatform.model;

public abstract class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String cnp;
    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
    private String email;
    private String iban;
    private String contractNumber;
    private boolean isAdmin;
    private boolean isSuperAdmin;

    public User(){

    }

    public User(int id, String username, String password, String role, String cnp,
                String firstName, String lastName, Address address, String phone,
                String email, String iban, String contractNumber, boolean isAdmin, boolean isSuperAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.iban = iban;
        this.contractNumber = contractNumber;
        this.isAdmin = isAdmin;
        this.isSuperAdmin = isSuperAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }
}
