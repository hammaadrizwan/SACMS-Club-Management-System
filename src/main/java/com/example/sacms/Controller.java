package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private Stage stage;//main stage where all our windows appear
    private Scene scene;//changes depending on the users requirement each scene is a window
    @FXML
    private AnchorPane userIconButtonOptionPane; //This displays the options available to a user when icon is clicked
    @FXML
    private TextField clubNameInputClubCreationScreen, clubAdvisorIDInputClubCreationScreen, clubStaffIDInputClubCreationScreen;
    @FXML
    private TextArea clubDescriptionInputClubCreationScreen;
    @FXML
    private Label errorClubNameInputClubCreationScreen, errorClubAdvisorIDInputClubCreationScreen, errorClubDescriptionInputClubCreationScreen, errorStaffIDInputClubCreationScreen;

    //SCREEN NAVIGATION METHODS
    @FXML
    public void onSplashScreenButtonClicked(ActionEvent event) throws IOException {//User is required to login inorder to access the app
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("loginScreen.fxml"));//loads the fxml to the main screen
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Login");//Window title
        this.stage.setScene(this.scene);//sets the scene to the stage
        this.stage.show();//displays the window
        this.stage.setResizable(false);//make the window not resizable
    }

    @FXML
    public void onDashboardScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("dashboard.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Dashboard");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    @FXML
    public void onReportsScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("reportsView.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Reports");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onClubsViewScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("clubsView.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Clubs");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onEventsViewScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("eventsView.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Events");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onSignInButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("signInZero.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Sign in");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    @FXML
    public void onScheduleEventButtonOneClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("eventCreation.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Schedule Event");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onCreateClubButtonOneClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("clubCreation.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Create Club");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    @FXML
    public void onStudentSignInButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("signInStudent.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Student Sign-In");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    @FXML
    public void onClubAdvisorSignInButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("signInClubAdvisor.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Club Advisor Sign-In");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    public void onTeacherSignInButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("signInTeacher.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Club Advisor Sign-In");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    //User options methods when the icon is clicked
    @FXML
    public void onExitButtonClicked(ActionEvent event) throws IOException {
        System.exit(0);// Close the app and terminate the session
    }
    public void onUserIconButtonClicked (ActionEvent event) throws IOException {
        userIconButtonOptionPane.setOpacity(1.00);//Displays the options available
    }
    public void onUserIconHideButtonClicked (ActionEvent event) throws IOException {
        userIconButtonOptionPane.setOpacity(0.00);//hides the buttons
    }
    @FXML
    public void onLogOutButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("loginScreen.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Login");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    //Student SIGN IN SCREEN
    public void onRegisterStudentButtonClicked (ActionEvent event) throws IOException {
        //checks if the fields are valid and then registeres the student to the system
    }
    //ClubAdvisor SIGN IN SCREEN
    public void onRegisterClubAdvisorButtonClicked (ActionEvent event) throws IOException {
        //checks if the fields are valid and then registeres the Club advisor to the system
    }
    //ClubAdvisor REQUEST
    public void onrequestClubAdvisorRoleButtonClicked (ActionEvent event) throws IOException {
        //Requests for approval from the club incharge which is the teacher then if they accept the ClubAdvisor is status is being given to the student
    }

    //Teacher SIGN IN SCREEN
    public void onRegisterTeacherButtonClicked (ActionEvent event) throws IOException {
        //checks if the fields are valid and then registeres the teacher to the system
    }

    //Club Creation SCREEN
    public void onLoadStaffIDClubScreationScreenClicked (ActionEvent event) throws IOException {
        //displaylist of staffa avilable for a club incharge role
    }

    public void onCreateClubClubCreationScreenClicked (ActionEvent event) throws IOException {
        boolean clubNameValid = true;//initially all input fields are said to be valid
        boolean clubAdvisorID = true;
        boolean clubDescription = true;
        boolean staffID = true;
        if (clubNameInputClubCreationScreen.getText().equals("")) {//checks if the club name input field is blank
            errorClubNameInputClubCreationScreen.setText("Cannot be empty");//display a message to the user to re-enter
            clubNameValid = false;//sets clubName validity to be false
        } else {//checks whether there is at least one integer in the clubName, since the name cannot contain any numbers/ digits
            char[] clubNameCharacters = clubNameInputClubCreationScreen.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : clubNameCharacters) {//and by using an enhanced for loop the program checks whether a number is present in the list of characters
                if (Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is a digit
                    clubNameValid = false;//if it is then clubName input will be invalid and the loop will break
                    break;
                }
            }
            if (!clubNameValid) {//if the clubName is invalid, a message will be displayed to the user saying its incorrect
                errorClubNameInputClubCreationScreen.setText("Incorrect value");
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorClubNameInputClubCreationScreen.setText("");
            }
        }
        if (clubAdvisorIDInputClubCreationScreen.getText().equals("")) {//checks if the club advisor ID input field is blank
            errorClubAdvisorIDInputClubCreationScreen.setText("Cannot be empty");//display a message to the user to re-enter
            clubAdvisorID = false;//sets clubAdvisorID validity to be false
        }
        if (clubDescriptionInputClubCreationScreen.getText().equals("")) { // checks if the club description input field is blank
            errorClubDescriptionInputClubCreationScreen.setText("Cannot be empty");//display a message to the user to re-enter
            clubDescription = false;//sets clubDescription validity to be false
        }
        if (clubStaffIDInputClubCreationScreen.getText().equals("")) { // checks if the staff ID input field is blank
            errorStaffIDInputClubCreationScreen.setText("Cannot be empty");//display a message to the user to re-enter
            staffID = false;//sets staffID validity to be false
        }
    }
}