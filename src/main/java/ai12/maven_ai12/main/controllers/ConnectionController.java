package ai12.maven_ai12.main.controllers;

import ai12.maven_ai12.beans.Picture;
import ai12.maven_ai12.beans.PlayerProfile;
import ai12.maven_ai12.data.exceptions.BadCredentialsException;
import ai12.maven_ai12.data.exceptions.BadIPAddressException;
import ai12.maven_ai12.data.exceptions.LoginNotFoundException;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForMain;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForMain.EmptyFieldException;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForMain.InvalidFieldException;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForMain.InvalidLoginException;
import ai12.maven_ai12.main.MainClientEngine;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Date;
import java.util.List;

public class ConnectionController {

    private MainClientEngine mMainClientEngine;

    @FXML
    private Pane mLogInPane, mSignUpPane;

    @FXML
    private Text mConnectionErrorText;

    @FXML
    private Button mChangePaneLogInButton, mChangePaneSignUpButton;

    @FXML
    private Button mLogInButton;
    @FXML
    private ListView<String> mUsernameListView;
    @FXML
    private PasswordField mLogInPasswordField;
    @FXML
    private TextField mLogInIpTextField, mLogInPortTextField;

    @FXML
    private TextField mSignUpUsernameTextField, mSignUpLastNameTextField, mSignUpFirstNameTextField;
    @FXML
    private PasswordField mSignUpPasswordField, mSignUpRepeatPasswordField;
    @FXML
    private DatePicker mSignUpDateOfBirthDatePicker;

    @FXML
    private Button mSignUpSubmitButton;

    public void initialize() {
        this.mMainClientEngine = MainClientEngine.getMainClientEngine(null);
        initializeLogIn();
        initializeSignUp();
    }

    private void initializeLogIn() {
        mLogInButton.setOnAction((eventConnectionButton) -> {
            try {
                mConnectionErrorText.setText("");
                this.login();
                //this.mMainClientEngine.changeToMain();
            } catch (LoginNotFoundException e) {
                mConnectionErrorText.setText("No profile has been found with this login.");
            } catch (BadCredentialsException e) {
                mConnectionErrorText.setText("The provided credentials don't match with any local profile.");
            } catch (EmptyFieldException e) {
                mConnectionErrorText.setText("The login & the password cannot be empty.");
            } catch (BadIPAddressException e) {
                mConnectionErrorText.setText("The IP address is incorrect.");
            } catch (NumberFormatException e) {
                mConnectionErrorText.setText("The listening port must be an integer value !");
            } catch (Exception e) {
                mConnectionErrorText.setText("Exception :" + e.getMessage());
            } catch (InternalError e) {
                mConnectionErrorText.setText("An internal error occured. Please check the logs.");
            }
        });

        mChangePaneLogInButton.setOnAction((eventLoginButton) -> {
            mConnectionErrorText.setText("");
            mSignUpPane.setVisible(false);
            mLogInPane.setVisible(true);
            populateUsernameListView();
        });

        populateUsernameListView();
    }

    /*
     * Faire en sorte qu'il se maj automatiquement
     */
    private void populateUsernameListView() {
        List<String> vLocalLogins = mMainClientEngine.getInsideDataCliForMain().getLocalLogins();
        if (vLocalLogins != null && !vLocalLogins.isEmpty()) {
            mUsernameListView.setItems(FXCollections.observableArrayList(vLocalLogins));
        }
    }

    private void login() throws LoginNotFoundException, BadCredentialsException, NumberFormatException, Exception {
        Integer vPort = null;
        if (mLogInPortTextField.getText() != null && !mLogInPortTextField.getText().isEmpty()) {
            vPort = Integer.parseInt(mLogInPortTextField.getText());
        }
        this.mMainClientEngine.getInsideDataCliForMain().login(mUsernameListView.getSelectionModel().getSelectedItem(),
                mLogInPasswordField.getText(), mLogInIpTextField.getText(), vPort);
    }

    private void initializeSignUp() {
        mSignUpSubmitButton.setOnAction((eventSubmitButton) -> {
            try {
                this.signUp();
                mConnectionErrorText.setText("");
                mSignUpPane.setVisible(false);
                mLogInPane.setVisible(true);
                populateUsernameListView();
            } catch (InvalidFieldException e) {
                mConnectionErrorText.setText("There is at least 1 invalid field : " + e.getMessage());
            } catch (EmptyFieldException e) {
                mConnectionErrorText.setText("There is at least 1 empty field :" + e.getMessage());
            } catch (InvalidLoginException e) {
                mConnectionErrorText
                        .setText("The login is incorrect. It must be an alphanumeric string of 8 to 20 characters.");
            }
        });

        mChangePaneSignUpButton.setOnAction((eventSignUpButton) -> {
            mConnectionErrorText.setText("");
            mLogInPane.setVisible(false);
            mSignUpPane.setVisible(true);

        });
    }

    private void signUp() throws IInsideDataCliForMain.InvalidFieldException, IInsideDataCliForMain.EmptyFieldException,
            InvalidLoginException {
        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.setLogin(mSignUpUsernameTextField.getText());
        playerProfile.setFirstname(mSignUpFirstNameTextField.getText());
        playerProfile.setLastname(mSignUpLastNameTextField.getText());
        if (mSignUpDateOfBirthDatePicker.getValue() != null) {
            playerProfile.setDateOfBirth(Date.valueOf(mSignUpDateOfBirthDatePicker.getValue()));
        }

        this.mMainClientEngine.getInsideDataCliForMain().register(playerProfile, mSignUpPasswordField.getText());
    }


}