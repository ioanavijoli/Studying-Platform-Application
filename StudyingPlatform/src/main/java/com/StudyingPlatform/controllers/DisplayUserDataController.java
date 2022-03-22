package com.StudyingPlatform.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
abstract class DisplayUserDataController implements Initializable{
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField cnpField;
    @FXML
    private TextField ibanField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField regionField;
    @FXML
    private TextField townField;
    @FXML
    private TextField streetAddressField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField contractNumberField;
    @FXML
    public void onBackButtonClick() throws IOException {
        System.out.println("back");
    }
  /*  public void displayStudent(String firstNameField, String lastNameField,String emailField, String phoneField, String CNP,
                               String iban, Address address, String contractNumber, int year){
        this.firstNameField.setText(firstNameField);
        this.lastNameField.setText(lastNameField);
        this.emailField.setText(emailField);
        this.phoneField.setText(phoneField);
        this.cnpField.setText(CNP);
        this.ibanField.setText(iban);
        this.countryField.setText(address.getCountry());
        this.regionField.setText(address.getRegion());
        this.townField.setText(address.getTown());
        this.streetAddressField.setText(address.getStreetAddress());
        this.postalCodeField.setText(address.getPostalCode());
        this.contractNumberField.setText(contractNumber);
    }
      firstNameField.setText(SuperController.activeUser.getFirstName());
    */
    @FXML
    public void displayUser() {
        firstNameField.setText(SuperController.activeUser.getFirstName());
        lastNameField.setText(SuperController.activeUser.getLastName());
        emailField.setText(SuperController.activeUser.getEmail());
        phoneField.setText(SuperController.activeUser.getPhone());
        cnpField.setText(SuperController.activeUser.getCnp());
        ibanField.setText(SuperController.activeUser.getIban());
        countryField.setText(SuperController.activeUser.getAddress().getCountry());
        regionField.setText(SuperController.activeUser.getAddress().getRegion());
        townField.setText(SuperController.activeUser.getAddress().getTown());
        streetAddressField.setText(SuperController.activeUser.getAddress().getStreetAddress());
        postalCodeField.setText(SuperController.activeUser.getAddress().getPostalCode());
        contractNumberField.setText(SuperController.activeUser.getContractNumber());
    }
}
