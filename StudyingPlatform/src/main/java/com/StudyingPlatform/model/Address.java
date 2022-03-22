package com.StudyingPlatform.model;

public class Address {
    private String country;
    private String region;
    private String town;
    private String streetAddress;
    private String postalCode;

    public Address(){

    }

    public Address(String country,String region, String town, String streetAddress, String postalCode) {
        this.country = country;
        this.region = region;
        this.town = town;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }
}
