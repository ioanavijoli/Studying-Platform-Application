package com.StudyingPlatform.controllers;

import com.StudyingPlatform.application.StudyingApplication;
import com.StudyingPlatform.model.Address;
import com.StudyingPlatform.model.Professor;
import com.StudyingPlatform.model.Student;
import com.StudyingPlatform.model.User;
import com.StudyingPlatform.service.AccountService;
import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.SignupException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

public class SingUpController{
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField CNP;
    @FXML
    private TextField country;
    @FXML
    private TextField region;
    @FXML
    private TextField town;
    @FXML
    private TextField street;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField iban;
    @FXML
    private TextField contractNumber;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button studentButton;
    @FXML
    private Button professorButton;
    @FXML
    private Button continueButton;
    @FXML
    private Label incompleteFields;
    @FXML
    private Label dynamicLabel;
    @FXML
    private TextField dynamicTextField;
    @FXML
    private void initialize() {

    }

    @FXML
    public void onStudentButtonClick() {
        studentButton.setDisable(true);
        professorButton.setDisable(false);

        dynamicLabel.setVisible(true);
        dynamicTextField.setVisible(true);

        dynamicLabel.setText("Year of Study: ");
        dynamicTextField.setText("");
    }

    @FXML
    public void onProfessorButtonClick() {
        studentButton.setDisable(false);
        professorButton.setDisable(true);

        dynamicLabel.setVisible(true);
        dynamicTextField.setVisible(true);

        dynamicLabel.setText("Department: ");
        dynamicTextField.setText("");
    }

    @FXML
    public void onAlreadyHaveAnAccountLogInClick() throws IOException {
        Stage stage = StudyingApplication.getPrimaryStage();
        URL url = StudyingApplication.class.getResource("log-in-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setScene(scene);
    }

    public boolean isValid() {
        incompleteFields.setText("");
        if (firstName.getText().trim().isEmpty()) {
            incompleteFields.setText("First name field can not be empty");
            return false;
        }
        if (lastName.getText().trim().isEmpty()) {
            incompleteFields.setText("Last name field can not be empty");
            return false;
        }
        if (CNP.getText().trim().isEmpty()) {
            incompleteFields.setText("CNP field can not be empty");
            return false;
        }
        if (country.getText().trim().isEmpty()) {
            incompleteFields.setText("Country can not be empty");
            return false;
        }
        if (region.getText().trim().isEmpty()) {
            incompleteFields.setText("Region field can not be empty");
            return false;
        }
        if (town.getText().trim().isEmpty()) {
            incompleteFields.setText("Town field can not be empty");
            return false;
        }
        if (street.getText().trim().isEmpty()) {
            incompleteFields.setText("Street field can not be empty");
            return false;
        }
        if (postalCode.getText().trim().isEmpty()) {
            incompleteFields.setText("Postal code field can not be empty");
            return false;
        }
        if (phone.getText().trim().isEmpty()) {
            incompleteFields.setText("phone field can not be empty");
            return false;
        }
        if (email.getText().trim().isEmpty()) {
            incompleteFields.setText("email field can not be empty");
            return false;
        }
        if (iban.getText().trim().isEmpty()) {
            incompleteFields.setText("iban field can not be empty");
            return false;
        }
        if (contractNumber.getText().trim().isEmpty()) {
            incompleteFields.setText("Contract number field can not be empty");
            return false;
        }
        if (username.getText().trim().isEmpty()) {
            incompleteFields.setText("Username field can not be empty");
            return false;
        }
        if (password.getText().trim().isEmpty()) {
            incompleteFields.setText("password field can not be empty");
            return false;
        }
        if (!password.getText().equals(confirmPassword.getText())) {
            incompleteFields.setText("password doesn't match");
            return false;
        }
        if (!studentButton.isDisable() && !professorButton.isDisable()) {
            incompleteFields.setText("Select account type");
            return false;
        }
        if (studentButton.isDisable()) {
            if(!dynamicTextField.getText().equals("1") &&
               !dynamicTextField.getText().equals("2") &&
               !dynamicTextField.getText().equals("3") &&
               !dynamicTextField.getText().equals("4")){
                incompleteFields.setText("Year of study must be: 1, 2, 3 or 4");
                return false;
            }
        }
        if (professorButton.isDisable()) {
            if (dynamicTextField.getText().trim().isEmpty()) {
                incompleteFields.setText("Choose department");
                return false;
            }
        }
        return true;
    }

    @FXML
    public void onContinueButtonClick() throws IOException {
        if (isValid()) {
            User user;
            if(studentButton.isDisable()){
                user = new Student();
                user.setRole("STUDENT");
                ((Student)user).setMinStudyingHours(44);
                ((Student)user).setYear(Integer.parseInt(dynamicTextField.getText()));
            }else if (professorButton.isDisable()){
                user = new Professor();
                user.setRole("PROFESSOR");
                ((Professor)user).setMinTeachingHours(44);
                ((Professor)user).setMinTeachingHours(88);
                ((Professor)user).setDepartment(dynamicTextField.getText());
            }else return;
            user.setFirstName(firstName.getText());
            user.setLastName(lastName.getText());
            user.setUsername(username.getText());
            user.setPassword(password.getText());
            user.setEmail(email.getText());
            user.setPhone(phone.getText());
            user.setCnp(CNP.getText());
            user.setIban(iban.getText());
            user.setAddress(new Address(
                    country.getText(),
                    region.getText(),
                    town.getText(),
                    street.getText(),
                    postalCode.getText()
            ));
            user.setContractNumber(contractNumber.getText());
            try{
                AccountService.signUp(user);
            }catch(SignupException e){
                switch(e.getMessage()){
                    case "unique constraint violated":
                        incompleteFields.setText("This username is already taken");
                        return;
                    default:
                        incompleteFields.setText("Something went wrong, we are sorry.");
                        return;
                }
            }
            Stage stage = StudyingApplication.getPrimaryStage();
            URL url = StudyingApplication.class.getResource("log-in-view.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Scene scene = new Scene(fxmlLoader.load(), 400, 500);
            stage.setScene(scene);
        }
    }


}


