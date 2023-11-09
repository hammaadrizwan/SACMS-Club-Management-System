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
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

public class Controller {
    public String sessionUser;
    public String text;
    private Stage stage;//main stage where all our windows appear
    private Scene scene;//changes depending on the users requirement each scene is a window
    @FXML
    private AnchorPane userIconButtonOptionPane; //This displays the options available to a user when icon is clicked
    @FXML
    private TextField clubNameInputClubCreationScreen, clubAdvisorIDInputClubCreationScreen, clubStaffIDInputClubCreationScreen, eventNameEventCreationInput, eventDateEventCreationInput, eventTimeEventCreationInput, clubIDEventCreationInput, studentIDSigInClubAdvisorScreen, positionSigInClubAdvisorScreen, clubIDSigInClubAdvisorScreen, firstNameSignInStudentInput, lastNameSignInStudentInput, dateSignInStudentInput, classSignInStudentInput, emailSignInStudentInput, contactNoSignInStudentInput, passwordSignInStudentInput, studentIDSignInStudentInput, firstNameSignInTeacherInput, lastNameSignInTeacherInput, dateSignInTeacherInput, contactNoSignInTeacherInput, emailSignInTeacherInput, staffIDSignInTeacherInput, passwordSignInTeacherInput, IDLoginInput, passwordLoginInput;
    @FXML
    private TextArea clubDescriptionInputClubCreationScreen, eventDescriptionEventCreationInput;
    @FXML
    private Label dayLabelDashboard,timeLabelDashboard,errorClubNameInputClubCreationScreen, errorClubAdvisorIDInputClubCreationScreen, errorClubDescriptionInputClubCreationScreen, errorStaffIDInputClubCreationScreen, errorEventNameEventCreationInput, errorEventDateEventCreationInput, errorEventTimeEventCreationInput, errorClubIDEventCreationInput, errorEventDescriptionEventCreationInput, errorStudentIDSigInClubAdvisorScreen, errorPositionSigInClubAdvisorScreen, errorClubIDSigInClubAdvisorScreen, errorFirstNameSignInStudentInput, errorLastNameSignInStudentInput, errorDateSignInStudentInput, errorClassSignInStudentInput, errorEmailSignInStudentInput, errorContactNoSignInStudentInput, errorPasswordSignInStudentInput, errorStudentIDSignInStudentInput, errorFirstNameSignInTeacherInput, errorLastNameSignInTeacherInput, errorDateSignInTeacherInput, errorContactNoSignInTeacherInput, errorEmailSignInTeacherInput, errorStaffIDSignInTeacherInput, errorPasswordSignInTeacherInput, errorIDLoginInput, errorPasswordLoginInput;
    @FXML
    private Label userNameLabelDashboard;

    @FXML
    private Button refreshButtonDashboard;
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

    public void onRefreshDashboardScreenButtonClicked(ActionEvent event) throws IOException {

        LocalDateTime now = LocalDateTime.now();
        refreshButtonDashboard.setOpacity(0.0);
        refreshButtonDashboard.setDisable(true);

        // Format the date to "THU 03 OCT" using a custom DateTimeFormatter
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE  dd  MMM");
        String formattedDate = now.format(dateFormatter).toUpperCase();

        // Format the time to "15:24"
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(timeFormatter);

        // Set the formatted date and time to the label
        System.out.println(getUserLabelDashboardText());
        userNameLabelDashboard.setText(getUserLabelDashboardText());
        dayLabelDashboard.setText(formattedDate);
        timeLabelDashboard.setText(formattedTime);


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
    public void onStudentSignInOneButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("signInStudent.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Student Sign-In");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    @FXML
    public void onClubAdvisorSignInOneButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("signInClubAdvisor.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Club Advisor Sign-In");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    public void onTeacherSignInOneButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("signInTeacher.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Club Advisor Sign-In");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    //User options methods when the icon is clicked
    public void onLogOutButtonClicked(ActionEvent event) throws IOException {
        onLogInScreenButtonClicked(event);// Close the app and terminate the session
    }
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
    public void onLogInScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("loginScreen.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Login");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    //Club Creation SCREEN
    public void onLoadStaffIDClubScreationScreenClicked (ActionEvent event) throws IOException {
        //displaylist of staffa avilable for a club incharge role
    }

    public void onCreateClubButtonTwoClicked (ActionEvent event) throws IOException {
        boolean clubNameValid = true;//initially all input fields are said to be valid
        boolean clubAdvisorIDValid = true;
        boolean clubDescriptionValid = true;
        boolean staffIDValid = true;
        errorClubNameInputClubCreationScreen.setText("");//all the error labels are made invisible at the start of the validation
        errorClubAdvisorIDInputClubCreationScreen.setText("");
        errorClubDescriptionInputClubCreationScreen.setText("");
        errorStaffIDInputClubCreationScreen.setText("");
        if (clubNameInputClubCreationScreen.getText().equals("")) {//checks if the club name input field is blank
            errorClubNameInputClubCreationScreen.setText("Cannot be empty");//display a message to the user to re-enter
            clubNameValid = false;//sets clubName validity to be false
            clubNameInputClubCreationScreen.clear();//clears the text field
        } else {//checks whether there is at least one integer in the clubName, since the name cannot contain any numbers/ digits
            char[] clubNameCharacters = clubNameInputClubCreationScreen.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : clubNameCharacters) {//and by using an enhanced for loop the program checks whether a number is present in the list of characters
                if (Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is a digit
                    clubNameValid = false;//if it is then clubName input will be invalid and the loop will break
                    break;
                }
            }
            if (!clubNameValid) {//if the clubName is invalid, a message will be displayed to the user saying its incorrect
                errorClubNameInputClubCreationScreen.setText("Invalid club name");
                clubNameInputClubCreationScreen.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorClubNameInputClubCreationScreen.setText("");
            }
        }
        if (clubAdvisorIDInputClubCreationScreen.getText().equals("")) {//checks if the club advisor ID input field is blank
            errorClubAdvisorIDInputClubCreationScreen.setText("Cannot be empty");//display a message to the user to re-enter
            clubAdvisorIDValid = false;//sets clubAdvisorID validity to be false
            clubAdvisorIDInputClubCreationScreen.clear();//clears the text field
        }
        if (clubDescriptionInputClubCreationScreen.getText().equals("")) {//checks if the club description input field is blank
            errorClubDescriptionInputClubCreationScreen.setText("Cannot be empty");//display a message to the user to re-enter
            clubDescriptionValid = false;//sets clubDescription validity to be false
            clubDescriptionInputClubCreationScreen.clear();//clears the text field
        }
        if (clubStaffIDInputClubCreationScreen.getText().equals("")) {//checks if the staff ID input field is blank
            errorStaffIDInputClubCreationScreen.setText("Cannot be empty");//display a message to the user to re-enter
            staffIDValid = false;//sets staffID validity to be false
            clubStaffIDInputClubCreationScreen.clear();//clears the text field
        }
        if (clubNameValid && clubAdvisorIDValid && clubDescriptionValid && staffIDValid) {//if the above inputs done by the user is valid the data will be stored
            clubNameInputClubCreationScreen.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            clubAdvisorIDInputClubCreationScreen.clear();
            clubDescriptionInputClubCreationScreen.clear();
            clubStaffIDInputClubCreationScreen.clear();
            // Hammad complete this part this is linked with the database u have to store these data there
        }
    }

    public void onScheduleEventButtonTwoClicked (ActionEvent event) throws IOException {
        boolean eventNameValid = true;//initially all input fields are said to be valid
        boolean eventDateValid = true;
        boolean eventTimeValid = true;
        boolean clubIDValid = true;
        boolean eventDescriptionValid = true;
        errorEventNameEventCreationInput.setText("");//all the error labels are made invisible at the start of the validation
        errorEventDateEventCreationInput.setText("");
        errorEventTimeEventCreationInput.setText("");
        errorClubIDEventCreationInput.setText("");
        errorEventDescriptionEventCreationInput.setText("");
        if (eventNameEventCreationInput.getText().equals("")) {//checks if the event name input field is blank
            errorEventNameEventCreationInput.setText("Cannot be empty");//display a message to the user to re-enter
            eventNameValid = false;//sets eventName validity to be false
            eventNameEventCreationInput.clear();//clears the text field
        } else {//checks whether there is at least one integer in the eventName, since the name cannot contain any numbers/digits
            char[] eventNameCharacters = eventNameEventCreationInput.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : eventNameCharacters) {//and by using an enhanced for loop the program checks whether a number is present in the list of characters
                if (Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is a digit
                    eventNameValid = false;//if it is then eventName input will be invalid and the loop will break
                    break;
                }
            }
            if (!eventNameValid) {//if the eventName is invalid, a message will be displayed to the user saying its incorrect
                errorEventNameEventCreationInput.setText("Invalid event name");
                eventNameEventCreationInput.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorEventNameEventCreationInput.setText("");
            }
        }
        if (eventDateEventCreationInput.getText().equals("")) {//checks if the event date input field is blank
            errorEventDateEventCreationInput.setText("Cannot be empty");//display a message to the user to re-enter
            eventDateValid = false;//sets eventDate validity to be false
            eventDateEventCreationInput.clear();//clears the text field
        } else {
            try {
                LocalDate.parse(eventDateEventCreationInput.getText());//checks if the eventDate is in the correct format
            } catch (DateTimeParseException e) {//exception handling to catch for DateTimeParseException error
                errorEventDateEventCreationInput.setText("Invalid date");//if the eventDate is invalid, a message will be displayed to the user saying its incorrect
                eventDateValid = false;
                eventDateEventCreationInput.clear();//clears the text field
            }
        }
        if (eventTimeEventCreationInput.getText().equals("")) {//checks if the event time input field is blank
            errorEventTimeEventCreationInput.setText("Cannot be empty");//display a message to the user to re-enter
            eventTimeValid = false;//sets eventTime validity to be false
            eventTimeEventCreationInput.clear();//clears the text field
        } else {
            try {
                LocalTime.parse(eventTimeEventCreationInput.getText());//checks if the eventTime is in the correct format
            } catch (DateTimeParseException e) {//exception handling to catch for DateTimeParseException error
                errorEventTimeEventCreationInput.setText("Invalid time");//if the eventTime is invalid, a message will be displayed to the user saying its incorrect
                eventTimeValid = false;
                eventTimeEventCreationInput.clear();//clears the text field
            }
        }
        if (clubIDEventCreationInput.getText().equals("")) {//checks if the club ID input field is blank
            errorClubIDEventCreationInput.setText("Cannot be empty");//display a message to the user to re-enter
            clubIDValid = false;//sets clubID validity to be false
            clubIDEventCreationInput.clear();//clears the text field
        }
        if (eventDescriptionEventCreationInput.getText().equals("")) {//checks if the event description input field is blank
            errorEventDescriptionEventCreationInput.setText("Cannot be empty");//display a message to the user to re-enter
            eventDescriptionValid = false;//sets eventDescription validity to be false
            eventDescriptionEventCreationInput.clear();//clears the text field
        }
        if (eventNameValid && eventDateValid && eventTimeValid && clubIDValid && eventDescriptionValid) {//if the above inputs done by the user is valid the data will be stored
            eventNameEventCreationInput.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            eventDateEventCreationInput.clear();
            eventTimeEventCreationInput.clear();
            clubIDEventCreationInput.clear();
            eventDescriptionEventCreationInput.clear();
            // Hammad complete this part this is linked with the database u have to store these data there
        }
    }

    public void onClubAdvisorSignInTwoButtonClicked(ActionEvent event) throws IOException {
        boolean studentIDValid = true;//initially all input fields are said to be valid
        boolean positionValid = true;
        boolean clubIDValid = true;
        errorStudentIDSigInClubAdvisorScreen.setText("");//all the error labels are made invisible at the start of the validation
        errorPositionSigInClubAdvisorScreen.setText("");
        errorClubIDSigInClubAdvisorScreen.setText("");
        if (studentIDSigInClubAdvisorScreen.getText().equals("")) {//checks if the student ID input field is blank
            errorStudentIDSigInClubAdvisorScreen.setText("Cannot be empty");//display a message to the user to re-enter
            studentIDValid = false;//sets studentID validity to be false
            studentIDSigInClubAdvisorScreen.clear();//clears the text field
        }
        if (positionSigInClubAdvisorScreen.getText().equals("")) {//checks if the club advisor position input field is blank
            errorPositionSigInClubAdvisorScreen.setText("Cannot be empty");//display a message to the user to re-enter
            positionValid = false;//sets position validity to be false
            positionSigInClubAdvisorScreen.clear();//clears the text field
        } else {//checks whether there is at least one integer in the position, since the name cannot contain any numbers/digits
            char[] position = positionSigInClubAdvisorScreen.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : position) {//and by using an enhanced for loop the program checks whether a number is present in the list of characters
                if (Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is a digit
                    positionValid = false;//if it is then position input will be invalid and the loop will break
                    break;
                }
            }
            if (!positionValid) {//if the position is invalid, a message will be displayed to the user saying its incorrect
                errorPositionSigInClubAdvisorScreen.setText("Invalid position");
                positionSigInClubAdvisorScreen.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorPositionSigInClubAdvisorScreen.setText("");
            }
        }
        if (clubIDSigInClubAdvisorScreen.getText().equals("")) {//checks if the club ID input field is blank
            errorClubIDSigInClubAdvisorScreen.setText("Cannot be empty");//display a message to the user to re-enter
            clubIDValid = false;//sets clubID validity to be false
            clubIDSigInClubAdvisorScreen.clear();//clears the text field
        }
        if (studentIDValid && positionValid && clubIDValid) {//if the above inputs done by the user is valid the data will be stored
            studentIDSigInClubAdvisorScreen.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            positionSigInClubAdvisorScreen.clear();
            clubIDSigInClubAdvisorScreen.clear();
            // Hammad complete this part this is linked with the database u have to store these data there
        }
    }

    public void onStudentSignInTwoButtonClicked(ActionEvent event) throws  IOException {
        boolean firstNameValid = true;//initially all input fields are said to be valid
        boolean lastNameValid = true;
        boolean DOBValid = true;
        boolean classValid = true;
        boolean emailValid = true;
        boolean contactNoValid = true;
        boolean passwordValid = true;
        boolean studentIDValid = true;
        errorFirstNameSignInStudentInput.setText("");//all the error labels are made invisible at the start of the validation
        errorLastNameSignInStudentInput.setText("");
        errorDateSignInStudentInput.setText("");
        errorClassSignInStudentInput.setText("");
        errorEmailSignInStudentInput.setText("");
        errorContactNoSignInStudentInput.setText("");
        errorPasswordSignInStudentInput.setText("");
        errorStudentIDSignInStudentInput.setText("");
        if (firstNameSignInStudentInput.getText().equals("")) {//checks if the first name input field is blank
            errorFirstNameSignInStudentInput.setText("Cannot be empty");//display a message to the user to re-enter
            firstNameValid = false;//sets firstName validity to be false
            firstNameSignInStudentInput.clear();//clears the text field
        } else {//checks whether there is at least one integer in the firstName, since the name cannot contain any numbers/digits
            char[] firstNameCharacters = firstNameSignInStudentInput.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : firstNameCharacters) {//and by using an enhanced for loop the program checks whether a number is present in the list of characters
                if (Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is a digit
                    firstNameValid = false;//if it is then firstName input will be invalid and the loop will break
                    break;
                }
            }
            if (!firstNameValid) {//if the firstName is invalid, a message will be displayed to the user saying its incorrect
                errorFirstNameSignInStudentInput.setText("Invalid first name");
                firstNameSignInStudentInput.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorFirstNameSignInStudentInput.setText("");
            }
        }
        if (lastNameSignInStudentInput.getText().equals("")) {//checks if the last name input field is blank
            errorLastNameSignInStudentInput.setText("Cannot be empty");//display a message to the user to re-enter
            lastNameValid = false;//sets lastName validity to be false
            lastNameSignInStudentInput.clear();//clears the text field
        } else {//checks whether there is at least one integer in the lastName, since the name cannot contain any numbers/digits
            char[] lastNameCharacters = lastNameSignInStudentInput.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : lastNameCharacters) {//and by using an enhanced for loop the program checks whether a number is present in the list of characters
                if (Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is a digit
                    lastNameValid = false;//if it is then lastName input will be invalid and the loop will break
                    break;
                }
            }
            if (!lastNameValid) {//if the lastName is invalid, a message will be displayed to the user saying its incorrect
                errorLastNameSignInStudentInput.setText("Invalid last name");
                lastNameSignInStudentInput.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorLastNameSignInStudentInput.setText("");
            }
        }
        if (dateSignInStudentInput.getText().equals("")) {//checks if the DOB input field is blank
            errorDateSignInStudentInput.setText("Cannot be empty");//display a message to the user to re-enter
            DOBValid = false;//sets DOBValid validity to be false
            dateSignInStudentInput.clear();//clears the text field
        } else {
            char[] date=dateSignInStudentInput.toString().toCharArray();

        }
        if (classSignInStudentInput.getText().equals("")) {//checks if the class input field is blank
            errorClassSignInStudentInput.setText("Cannot be empty");//display a message to the user to re-enter
            classValid = false;//sets class validity to be false
            classSignInStudentInput.clear();//clears the text field
        }
        if (emailSignInStudentInput.getText().equals("")) {//checks if the email input field is blank
            errorEmailSignInStudentInput.setText("Cannot be empty");//display a message to the user to re-enter
            emailValid = false;//sets email validity to be false
            emailSignInStudentInput.clear();//clears the text field
        } else {
            if (emailSignInStudentInput.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {//checks if the email is in the correct format
                errorEmailSignInStudentInput.setText("");//if so then no error message will be displayed
            } else {
                errorEmailSignInStudentInput.setText("Invalid email");//if there is an error then an error message is displayed
                emailValid = false;//sets email validity to be false
                emailSignInStudentInput.clear();//clears the text field
            }
        }
        if (contactNoSignInStudentInput.getText().equals("")) {//checks if the contact No input field is blank
            errorContactNoSignInStudentInput.setText("Cannot be empty");//display a message to the user to re-enter
            contactNoValid = false;//sets contactNo validity to be false
            contactNoSignInStudentInput.clear();//clears the text field
        } else {//checks whether there is at least one string in the contactNo, since contactNo cannot contain any string characters
            char[] contactNoCharacters = contactNoSignInStudentInput.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : contactNoCharacters) {//and by using an enhanced for loop the program checks whether a string character is present in the list of characters
                if (!Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is not a digit
                    contactNoValid = false;//if it is then contactNo input will be invalid and the loop will break
                    break;
                }
            }
            if (!contactNoValid) {//if the contactNo is invalid, a message will be displayed to the user saying its incorrect
                errorContactNoSignInStudentInput.setText("Invalid contact no.");
                contactNoSignInStudentInput.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorContactNoSignInStudentInput.setText("");
            }
        }
        if (passwordSignInStudentInput.getText().equals("")) {//checks if the password input field is blank
            errorPasswordSignInStudentInput.setText("Cannot be empty");//display a message to the user to re-enter
            passwordValid = false;//sets password validity to be false
            passwordSignInStudentInput.clear();//clears the text field
        }
        if (studentIDSignInStudentInput.getText().equals("")) {//checks if the student ID input field is blank
            errorStudentIDSignInStudentInput.setText("Cannot be empty");//display a message to the user to re-enter
            studentIDValid = false;//sets studentID validity to be false
            studentIDSignInStudentInput.clear();//clears the text field
        }
        if (firstNameValid && lastNameValid && DOBValid && classValid && emailValid && contactNoValid && passwordValid && studentIDValid) {//if the above inputs done by the user is valid the data will be stored
            firstNameSignInStudentInput.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            lastNameSignInStudentInput.clear();
            dateSignInStudentInput.clear();
            classSignInStudentInput.clear();
            emailSignInStudentInput.clear();
            contactNoSignInStudentInput.clear();
            passwordSignInStudentInput.clear();
            studentIDSignInStudentInput.clear();
            // Hammad complete this part this is linked with the database u have to store these data there
        }
    }

    public void onTeacherSignInTwoButtonClicked(ActionEvent event) throws  IOException {
        boolean firstNameValid = true;//initially all input fields are said to be valid
        boolean lastNameValid = true;
        boolean DOBValid = true;
        boolean contactNoValid = true;
        boolean emailValid = true;
        boolean staffIDValid = true;
        boolean passwordValid = true;
        errorFirstNameSignInTeacherInput.setText("");//all the error labels are made invisible at the start of the validation
        errorLastNameSignInTeacherInput.setText("");
        errorDateSignInTeacherInput.setText("");
        errorContactNoSignInTeacherInput.setText("");
        errorEmailSignInTeacherInput.setText("");
        errorStaffIDSignInTeacherInput.setText("");
        errorPasswordSignInTeacherInput.setText("");
        if (firstNameSignInTeacherInput.getText().equals("")) {//checks if the first name input field is blank
            errorFirstNameSignInTeacherInput.setText("Cannot be empty");//display a message to the user to re-enter
            firstNameValid = false;//sets firstName validity to be false
            firstNameSignInTeacherInput.clear();//clears the text field
        } else {//checks whether there is at least one integer in the firstName, since the name cannot contain any numbers/digits
            char[] firstNameCharacters = firstNameSignInTeacherInput.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : firstNameCharacters) {//and by using an enhanced for loop the program checks whether a number is present in the list of characters
                if (Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is a digit
                    firstNameValid = false;//if it is then firstName input will be invalid and the loop will break
                    break;
                }
            }
            if (!firstNameValid) {//if the firstName is invalid, a message will be displayed to the user saying its incorrect
                errorFirstNameSignInTeacherInput.setText("Invalid first name");
                firstNameSignInTeacherInput.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorFirstNameSignInTeacherInput.setText("");
            }
        }
        if (lastNameSignInTeacherInput.getText().equals("")) {//checks if the last name input field is blank
            errorLastNameSignInTeacherInput.setText("Cannot be empty");//display a message to the user to re-enter
            lastNameValid = false;//sets lastName validity to be false
            lastNameSignInTeacherInput.clear();//clears the text field
        } else {//checks whether there is at least one integer in the lastName, since the name cannot contain any numbers/digits
            char[] lastNameCharacters = lastNameSignInTeacherInput.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : lastNameCharacters) {//and by using an enhanced for loop the program checks whether a number is present in the list of characters
                if (Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is a digit
                    lastNameValid = false;//if it is then lastName input will be invalid and the loop will break
                    break;
                }
            }
            if (!lastNameValid) {//if the lastName is invalid, a message will be displayed to the user saying its incorrect
                errorLastNameSignInTeacherInput.setText("Invalid last name");
                lastNameSignInTeacherInput.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorLastNameSignInTeacherInput.setText("");
            }
        }
        if (dateSignInTeacherInput.getText().equals("")) {//checks if the DOB input field is blank
            errorDateSignInTeacherInput.setText("Cannot be empty");//display a message to the user to re-enter
            DOBValid = false;//sets DOBValid validity to be false
            dateSignInTeacherInput.clear();//clears the text field
        } else {
            try {
                LocalDate.parse(dateSignInTeacherInput.getText());//checks if the DOB is in the correct format
            } catch (DateTimeParseException e) {//exception handling to catch for DateTimeParseException error
                errorDateSignInTeacherInput.setText("Invalid DOB");//if the DOB is invalid, a message will be displayed to the user saying its incorrect
                DOBValid = false;
                dateSignInTeacherInput.clear();//clears the text field
            }
        }
        if (contactNoSignInTeacherInput.getText().equals("")) {//checks if the contact No input field is blank
            errorContactNoSignInTeacherInput.setText("Cannot be empty");//display a message to the user to re-enter
            contactNoValid = false;//sets contactNo validity to be false
            contactNoSignInTeacherInput.clear();//clears the text field
        } else {//checks whether there is at least one string in the contactNo, since contactNo cannot contain any string characters
            char[] contactNoCharacters = contactNoSignInTeacherInput.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : contactNoCharacters) {//and by using an enhanced for loop the program checks whether a string character is present in the list of characters
                if (!Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is not a digit
                    contactNoValid = false;//if it is then contactNo input will be invalid and the loop will break
                    break;
                }
            }
            if (!contactNoValid) {//if the contactNo is invalid, a message will be displayed to the user saying its incorrect
                errorContactNoSignInTeacherInput.setText("Invalid contact no.");
                contactNoSignInTeacherInput.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                errorContactNoSignInTeacherInput.setText("");
            }
        }
        if (emailSignInTeacherInput.getText().equals("")) {//checks if the email input field is blank
            errorEmailSignInTeacherInput.setText("Cannot be empty");//display a message to the user to re-enter
            emailValid = false;//sets email validity to be false
            emailSignInTeacherInput.clear();//clears the text field
        } else {
            if (emailSignInTeacherInput.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {//checks if the email is in the correct format
                errorEmailSignInTeacherInput.setText("");//if so then no error message will be displayed
            } else {
                errorEmailSignInTeacherInput.setText("Invalid email");//if there is an error then an error message is displayed
                emailValid = false;//sets email validity to be false
                emailSignInTeacherInput.clear();//clears the text field
            }
        }
        if (staffIDSignInTeacherInput.getText().equals("")) {//checks if the staff ID input field is blank
            errorStaffIDSignInTeacherInput.setText("Cannot be empty");//display a message to the user to re-enter
            staffIDValid = false;//sets staffID validity to be false
            staffIDSignInTeacherInput.clear();//clears the text field
        }
        if (passwordSignInTeacherInput.getText().equals("")) {//checks if the password input field is blank
            errorPasswordSignInTeacherInput.setText("Cannot be empty");//display a message to the user to re-enter
            passwordValid = false;//sets password validity to be false
            passwordSignInTeacherInput.clear();//clears the text field
        }
        if (firstNameValid && lastNameValid && DOBValid && contactNoValid && emailValid && staffIDValid && passwordValid) {//if the above inputs done by the user is valid the data will be stored
            firstNameSignInTeacherInput.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            lastNameSignInTeacherInput.clear();
            dateSignInTeacherInput.clear();
            contactNoSignInTeacherInput.clear();
            emailSignInTeacherInput.clear();
            staffIDSignInTeacherInput.clear();
            passwordSignInTeacherInput.clear();
            // Hammad complete this part this is linked with the database u have to store these data there
        }
    }

    public void onLogInButtonClicked(ActionEvent event) throws  IOException {
        boolean IDValid = true;//initially all input fields are said to be valid
        boolean passwordValid = true;
        String idInput = null;
        String passwordInput=null;

        errorIDLoginInput.setText("");//all the error labels are made invisible at the start of the validation
        errorPasswordLoginInput.setText("");
        if (IDLoginInput.getText().equals("")) {//checks if the ID input field is blank
            errorIDLoginInput.setText("Cannot be empty");//display a message to the user to re-enter
            IDValid = false;//sets ID validity to be false
            IDLoginInput.clear();//clears the text field
        }else if (IDLoginInput.getText().toCharArray().length>0 && IDLoginInput.getText().toCharArray().length<=5) {//checks if the ID input field is more than 5 digits
            if (IDLoginInput.getText().toUpperCase().toCharArray()[0]=='S'){
                sessionUser="Student";
            }else if (IDLoginInput.getText().toUpperCase().toCharArray()[0]=='T'){
                sessionUser="Teacher";
            }else if (IDLoginInput.getText().toUpperCase().toCharArray()[0]=='C' && IDLoginInput.getText().toUpperCase().toCharArray()[1]=='A'){
                sessionUser="ClubAdvisor";
            }else{
                IDValid=false;//incase if its either student/teacher or clubadvisor id they are reffering to id will be invalid
            }
        }else{
            errorIDLoginInput.setText("Example S0001");//display a message to the user to re-enter
            IDValid = false;//sets ID validity to be false
            IDLoginInput.clear();//clears the text field
        }

        if (passwordLoginInput.getText().equals("")) {//checks if the password input field is blank
            errorPasswordLoginInput.setText("Cannot be empty");//display a message to the user to re-enter
            passwordValid = false;//sets password validity to be false
            passwordLoginInput.clear();//clears the text field
        }
        if (IDValid && passwordValid) {
            idInput=IDLoginInput.getText().toString();
            passwordInput=passwordLoginInput.getText().toString();
            //read from the database for exsisting records
            if (sessionUser.equals("Student")){
                        ArrayList<Student> registeredStudents=Student.loadStudentDataFromDatabase();
                        for (Student student:registeredStudents){
                            if (student.getStudentID().equals(idInput)){
                                if(student.getPassword().equals(passwordInput)){
                                    this.setUserLabelDashboardText(student.greetUser().toString());
                                    onDashboardScreenButtonClicked(event);
                                    System.out.println(getUserLabelDashboardText());

                                }
                            }
                            errorPasswordLoginInput.setText("Incorrect ID or Password");
                        }

            }
            if (sessionUser.equals("Teacher")){
                /*ArrayList<Student> registeredStudents=Student.loadStudentDataFromDatabase();
                for (Student student:registeredStudents){
                    if (student.getStudentID().equals(idInput)){
                        if(student.getPassword().equals(passwordInput)){
                            text=student.greetUser();
                            onDashboardScreenButtonClicked(event);

                        }
                        errorPasswordLoginInput.setText("Incorrect ID or Password");
                    }
                }*/
            }if (sessionUser.equals("Club Advisor")){
                /*ArrayList<Student> registeredStudents=Student.loadStudentDataFromDatabase();
                for (Student student:registeredStudents){
                    if (student.getStudentID().equals(idInput)){
                        if(student.getPassword().equals(passwordInput)){
                            text=student.greetUser();
                            onDashboardScreenButtonClicked(event);

                        }
                        errorPasswordLoginInput.setText("Incorrect ID or Password");
                    }
                }*/
            }

        }




    }
    public String getUserLabelDashboardText(){
        return this.text;
    }
    public void setUserLabelDashboardText(String text){
        this.text=text;
    }


}