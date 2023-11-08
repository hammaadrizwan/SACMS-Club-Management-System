package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Controller {
    public String entity = null;
    private Stage stage;//main stage where all our windows appear
    private Scene scene;//changes depending on the users requirement each scene is a window
    @FXML
    private AnchorPane userIconButtonOptionPane; //This displays the options available to a user when icon is clicked
    @FXML
    private TextField clubNameInputClubCreationScreen, clubAdvisorIDInputClubCreationScreen, clubTeacherIDInputClubCreationScreen, eventNameEventCreationInput, eventDateEventCreationInput, eventTimeEventCreationInput, clubIDEventCreationInput, studentIDSigInClubAdvisorScreen, positionSigInClubAdvisorScreen, clubIDSigInClubAdvisorScreen, firstNameSignInStudentInput, lastNameSignInStudentInput, dateSignInStudentInput, classSignInStudentInput, emailSignInStudentInput, contactNoSignInStudentInput, passwordSignInStudentInput, studentIDSignInStudentInput, firstNameSignInTeacherInput, lastNameSignInTeacherInput, dateSignInTeacherInput, contactNoSignInTeacherInput, emailSignInTeacherInput, teacherIDSignInTeacherInput, passwordSignInTeacherInput, IDLoginInput, passwordLoginInput;
    @FXML
    private TextArea clubDescriptionInputClubCreationScreen, eventDescriptionEventCreationInput;
    @FXML
    private Label errorClubNameInputClubCreationScreen, errorClubAdvisorIDInputClubCreationScreen, errorClubDescriptionInputClubCreationScreen, errorTeacherIDInputClubCreationScreen, errorEventNameEventCreationInput, errorEventDateEventCreationInput, errorEventTimeEventCreationInput, errorClubIDEventCreationInput, errorEventDescriptionEventCreationInput, errorStudentIDSigInClubAdvisorScreen, errorPositionSigInClubAdvisorScreen, errorClubIDSigInClubAdvisorScreen, errorFirstNameSignInStudentInput, errorLastNameSignInStudentInput, errorDateSignInStudentInput, errorClassSignInStudentInput, errorEmailSignInStudentInput, errorContactNoSignInStudentInput, errorPasswordSignInStudentInput, errorStudentIDSignInStudentInput, errorFirstNameSignInTeacherInput, errorLastNameSignInTeacherInput, errorDateSignInTeacherInput, errorContactNoSignInTeacherInput, errorEmailSignInTeacherInput, errorTeacherIDSignInTeacherInput, errorPasswordSignInTeacherInput, errorIDLoginInput, errorPasswordLoginInput;

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
        boolean clubNameValid;
        boolean clubAdvisorIDValid;
        boolean clubDescriptionValid;
        boolean teacherIDValid;
        clubNameValid = checkName(clubNameInputClubCreationScreen, errorClubNameInputClubCreationScreen);
        clubAdvisorIDValid = checkID(clubAdvisorIDInputClubCreationScreen, errorClubAdvisorIDInputClubCreationScreen);
        if (clubAdvisorIDValid) {
            if (!entity.equals("ClubAdvisor")) {//To check whether the user has entered a clubAdvisorID or not
                errorClubAdvisorIDInputClubCreationScreen.setText("Invalid ID");
                clubAdvisorIDValid = false;
            }
        }
        clubDescriptionValid = checkDescription(clubDescriptionInputClubCreationScreen, errorClubDescriptionInputClubCreationScreen);
        teacherIDValid = checkID(clubTeacherIDInputClubCreationScreen, errorTeacherIDInputClubCreationScreen);
        if (teacherIDValid) {
            if (!entity.equals("Teacher")) {//To check whether the user has entered a teacherID or not
                errorTeacherIDInputClubCreationScreen.setText("Invalid ID");
                teacherIDValid = false;
            }
        }
        if (clubNameValid && clubAdvisorIDValid && clubDescriptionValid && teacherIDValid) {//if the above inputs done by the user is valid the data will be stored
            clubNameInputClubCreationScreen.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            clubAdvisorIDInputClubCreationScreen.clear();
            clubDescriptionInputClubCreationScreen.clear();
            clubTeacherIDInputClubCreationScreen.clear();
            // Hammad complete this part this is linked with the database u have to store these data there
        }
    }

    public void onScheduleEventButtonTwoClicked (ActionEvent event) throws IOException {
        boolean eventNameValid;
        boolean eventDateValid = true;
        boolean eventTimeValid = true;
        boolean clubIDValid;
        boolean eventDescriptionValid;
        eventNameValid = checkName(eventNameEventCreationInput, errorEventNameEventCreationInput);
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
        clubIDValid = checkID(clubIDEventCreationInput, errorClubIDEventCreationInput);
        if (clubIDValid) {
            if (!entity.equals("Club")) {//To check whether the user has entered a clubID or not
                errorClubIDEventCreationInput.setText("Invalid ID");
                clubIDValid = false;
            }
        }
        eventDescriptionValid = checkDescription(eventDescriptionEventCreationInput, errorEventDescriptionEventCreationInput);
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
        boolean studentIDValid;
        boolean positionValid;
        boolean clubIDValid;
        studentIDValid = checkID(studentIDSigInClubAdvisorScreen, errorStudentIDSigInClubAdvisorScreen);
        if (studentIDValid) {
            if (!entity.equals("Student")) {//To check whether the user has entered a studentID or not
                errorStudentIDSigInClubAdvisorScreen.setText("Invalid ID");
                studentIDValid = false;
            }
        }
        positionValid = checkName(positionSigInClubAdvisorScreen, errorPositionSigInClubAdvisorScreen);
        clubIDValid = checkID(clubIDSigInClubAdvisorScreen, errorClubIDSigInClubAdvisorScreen);
        if (clubIDValid) {
            if (!entity.equals("Club")) {//To check whether the user has entered a clubID or not
                errorClubIDSigInClubAdvisorScreen.setText("Invalid ID");
                clubIDValid = false;
            }
        }
        if (studentIDValid && positionValid && clubIDValid) {//if the above inputs done by the user is valid the data will be stored
            studentIDSigInClubAdvisorScreen.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            positionSigInClubAdvisorScreen.clear();
            clubIDSigInClubAdvisorScreen.clear();
            // Hammad complete this part this is linked with the database u have to store these data there
        }
    }

    public void onStudentSignInTwoButtonClicked(ActionEvent event) throws  IOException {
        boolean firstNameValid;
        boolean lastNameValid;
        boolean DOBValid = true;
        boolean classValid;
        boolean emailValid;
        boolean contactNoValid;
        boolean passwordValid;
        boolean studentIDValid;
        firstNameValid = checkName(firstNameSignInStudentInput, errorFirstNameSignInStudentInput);
        lastNameValid = checkName(lastNameSignInStudentInput, errorLastNameSignInStudentInput);
        /*if (dateSignInStudentInput.getText().equals("")) {//checks if the DOB input field is blank
            errorDateSignInStudentInput.setText("Cannot be empty");//display a message to the user to re-enter
            DOBValid = false;//sets DOBValid validity to be false
            dateSignInStudentInput.clear();//clears the text field
        } else {
            char[] date=dateSignInStudentInput.toString().toCharArray();

        }*/
        classValid = checkClass(classSignInStudentInput, errorClassSignInStudentInput);
        emailValid = checkEmail(emailSignInStudentInput, errorEmailSignInStudentInput);
        contactNoValid = checkContactNo(contactNoSignInStudentInput, errorContactNoSignInStudentInput);
        passwordValid = checkPassword(passwordSignInStudentInput, errorPasswordSignInStudentInput);
        studentIDValid = checkID(studentIDSignInStudentInput, errorStudentIDSignInStudentInput);
        if (studentIDValid) {
            if (!entity.equals("Student")) {//To check whether the user has entered a studentID or not
                errorStudentIDSignInStudentInput.setText("Invalid ID");
                studentIDValid = false;
            }
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
            createStudentTableOnDatabase();
        }
    }

    public void onTeacherSignInTwoButtonClicked(ActionEvent event) throws  IOException {
        boolean firstNameValid;
        boolean lastNameValid;
        boolean DOBValid = true;
        boolean contactNoValid;
        boolean emailValid;
        boolean teacherIDValid;
        boolean passwordValid;
        firstNameValid = checkName(firstNameSignInTeacherInput, errorFirstNameSignInTeacherInput);
        lastNameValid = checkName(lastNameSignInTeacherInput, errorLastNameSignInTeacherInput);
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
        contactNoValid = checkContactNo(contactNoSignInTeacherInput, errorContactNoSignInTeacherInput);
        emailValid = checkEmail(emailSignInTeacherInput, errorEmailSignInTeacherInput);
        teacherIDValid = checkID(teacherIDSignInTeacherInput, errorTeacherIDSignInTeacherInput);
        if (teacherIDValid) {
            if (!entity.equals("Teacher")) {//To check whether the user has entered a teacherID or not
                errorTeacherIDSignInTeacherInput.setText("Invalid ID");
                teacherIDValid = false;
            }
        }
        passwordValid = checkPassword(passwordSignInTeacherInput, errorPasswordSignInTeacherInput);
        if (firstNameValid && lastNameValid && DOBValid && contactNoValid && emailValid && teacherIDValid && passwordValid) {//if the above inputs done by the user is valid the data will be stored
            firstNameSignInTeacherInput.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            lastNameSignInTeacherInput.clear();
            dateSignInTeacherInput.clear();
            contactNoSignInTeacherInput.clear();
            emailSignInTeacherInput.clear();
            teacherIDSignInTeacherInput.clear();
            passwordSignInTeacherInput.clear();
            // Hammad complete this part this is linked with the database u have to store these data there
        }
    }

    public void onLogInButtonClicked(ActionEvent event) throws  IOException {
        boolean IDValid;
        boolean passwordValid;
        String idInput = null;
        String passwordInput=null;
        IDValid = checkID(IDLoginInput, errorIDLoginInput);
        if (IDValid) {
            if (entity.equals("Club") || entity.equals("Event")) {//To check whether the user has entered a club/ event ID instead of student/ parent or teacher ID
                errorIDLoginInput.setText("Invalid ID");
                IDValid = false;
            }
        }
        passwordValid = checkPassword(passwordLoginInput, errorPasswordLoginInput);
        if (IDValid && passwordValid) {
            IDLoginInput.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            passwordLoginInput.clear();
            idInput=IDLoginInput.getText().toString();
            passwordInput=passwordLoginInput.getText().toString();
            //read from the database for exsisting records
            if (entity.equals("Student")){
             /*
                boolean exists = false;//check if the Student Id is available
                try (Connection connection = Database.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT COUNT(*) as count FROM Student WHERE StudentID = {}", idInput))) {
                    preparedStatement.setString(1, idInput);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        exists = count > 0;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (exists){
                    //code to read the Password
                }
                */
            }
        }
    }

    //Database Table Creation
    public void createStudentTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query = "CREATE TABLE Student (" +
                    "    StudentID VARCHAR(5) PRIMARY KEY," +
                    "    FirstName VARCHAR(25)," +
                    "    LastName VARCHAR(25)," +
                    "    Email VARCHAR(30)," +
                    "    DateOfBirth VARCHAR(10)," +
                    "    Password VARCHAR(255)," +
                    "    ContactNo VARCHAR(9)," +
                    "    Classroom VARCHAR(6)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                System.out.println("Student table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createTeacherTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query = "CREATE TABLE Teacher (" +
                    "    StaffID VARCHAR(5) PRIMARY KEY," +
                    "    FirstName VARCHAR(25)," +
                    "    LastName VARCHAR(25)," +
                    "    Email VARCHAR(30)," +
                    "    DateOfBirth VARCHAR(10)," +
                    "    ContactNo VARCHAR(9)," +
                    "    Password VARCHAR(255)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                System.out.println("Teacher table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createClubTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE Club (" +
                    "    ClubID VARCHAR(5) PRIMARY KEY," +
                    "    ClubName VARCHAR(255)," +
                    "    Description VARCHAR(255)," +
                    "    StaffID VARCHAR(5)," +
                    "    FOREIGN KEY (StaffID) REFERENCES Teacher(StaffID));";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                System.out.println("Club table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createClubAdvisorTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE ClubAdvisor ("+
                    "    ClubAdvisorID VARCHAR(5) PRIMARY KEY," +
                    "    StudentID VARCHAR(5)," +
                    "    ClubID VARCHAR(5)," +
                    "    Position VARCHAR(255)," +
                    "    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)," +
                    "    FOREIGN KEY (ClubID) REFERENCES Club(ClubID));";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                System.out.println("Club Advisor table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createEventTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE Events ("+
                    "    EventID VARCHAR(5) PRIMARY KEY," +
                    "    EventName VARCHAR(255)," +
                    "    Date VARCHAR(10)," +
                    "    Time VARCHAR(5)," +
                    "    Location VARCHAR(25)," +
                    "    ClubID VARCHAR(5)," +
                    "    EventDescription VARCHAR(255)," +
                    "    FOREIGN KEY (ClubID) REFERENCES Club(ClubID)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                System.out.println("Event table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createEventsAttendanceTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE EventsAttendance (" +
                    "    AttendanceID VARCHAR(5) PRIMARY KEY," +
                    "    EventID VARCHAR(5)," +
                    "    StudentID VARCHAR(5)," +
                    "    FOREIGN KEY (EventID) REFERENCES Events(EventID)," +
                    "    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                System.out.println("Event Attendance table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createClubsMembershipTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE ClubsMembership (" +
                    "    MembershipID VARCHAR(5) PRIMARY KEY," +
                    "    ClubID VARCHAR(5)," +
                    "    StudentID VARCHAR(5)," +
                    "    FOREIGN KEY (ClubID) REFERENCES Club(ClubID)," +
                    "    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                System.out.println("Club Membership created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkName(TextField textField, Label label) {
        boolean nameValid = true;
        label.setText("");//the error label made invisible at the start of the validation
        if (textField.getText().equals("")) {//checks if the name input field is blank
            label.setText("Cannot be empty");//display a message to the user to re-enter
            nameValid = false;//sets name validity to be false
            textField.clear();//clears the text field
        } else {//checks whether there is at least one integer in the name, since the name cannot contain any numbers/digits
            char[] nameCharacters = textField.getText().toCharArray();//converts the string to a sequence of characters
            for (char character : nameCharacters) {//and by using an enhanced for loop the program checks whether a number is present in the list of characters
                if (Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is a digit
                    nameValid = false;//if it is then name input will be invalid and the loop will break
                    break;
                }
            }
            if (!nameValid) {//if the name is invalid, a message will be displayed to the user saying its incorrect
                label.setText("Invalid name");
                textField.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                label.setText("");
            }
        }
        return nameValid;//returns whether name is valid or not
    }

    public boolean checkDescription(TextArea textArea, Label label) {
        boolean descriptionValid = true;
        label.setText("");//the error label made invisible at the start of the validation
        if (textArea.getText().equals("")) {//checks if the description input field is blank
            label.setText("Cannot be empty");//display a message to the user to re-enter
            descriptionValid = false;//sets description validity to be false
            textArea.clear();//clears the text field
        }
        return descriptionValid;//returns whether description is valid or not
    }

    public boolean checkEmail(TextField textField, Label label) {
        boolean emailValid = true;
        label.setText("");//the error label made invisible at the start of the validation
        if (textField.getText().equals("")) {//checks if the email input field is blank
            label.setText("Cannot be empty");//display a message to the user to re-enter
            emailValid = false;//sets email validity to be false
            textField.clear();//clears the text field
        } else {
            if (textField.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {//checks if the email is in the correct format
                label.setText("");//if so then no error message will be displayed
            } else {
                label.setText("Invalid email");//if there is an error then an error message is displayed
                emailValid = false;//sets email validity to be false
                textField.clear();//clears the text field
            }
        }
        return emailValid;//returns whether email is valid or not
    }

    public boolean checkContactNo(TextField textField, Label label) {
        boolean contactNoValid = true;
        label.setText("");//the error label made invisible at the start of the validation
        if (textField.getText().equals("")) {//checks if the contact No input field is blank
            label.setText("Cannot be empty");//display a message to the user to re-enter
            contactNoValid = false;//sets contactNo validity to be false
            textField.clear();//clears the text field
        } else {//checks whether there is at least one string in the contactNo, since contactNo cannot contain any string characters
            char[] contactNoCharacters = textField.getText().toCharArray();//converts the string to a sequence of characters
            if (contactNoCharacters.length == 10) {//checks whether the contact number has only 10 digits
                for (char character : contactNoCharacters) {//and by using an enhanced for loop the program checks whether a string character is present in the list of characters
                    if (!Character.isDigit(character)) {//using the Character objects in built isDigit() method to check whether the character is not a digit
                        contactNoValid = false;//if it is then contactNo input will be invalid and the loop will break
                        break;
                    }
                }
            } else {
                contactNoValid = false;//if it doesn't have 10 digits then contactNo is invalid
            }
            if (!contactNoValid) {//if the contactNo is invalid, a message will be displayed to the user saying its incorrect
                label.setText("Invalid contact no.");
                textField.clear();//clears the text field
            } else {//if it is valid there will be no message displayed as the field is of correct data type
                label.setText("");
            }
        }
        return contactNoValid;//returns whether contactNo is valid or not
    }

    public boolean checkClass(TextField textField, Label label) {
        boolean classValid = true;
        label.setText("");//the error label made invisible at the start of the validation
        if (textField.getText().equals("")) {//checks if the class input field is blank
            label.setText("Cannot be empty");//display a message to the user to re-enter
            classValid = false;//sets class validity to be false
            textField.clear();//clears the text field
        }
        return classValid;//returns whether class is valid or not
    }

    public boolean checkID(TextField textField, Label label) {
        boolean IDValid = true;
        label.setText("");//the error label made invisible at the start of the validation
        if (textField.getText().equals("")) {//checks if the ID input field is blank
            label.setText("Cannot be empty");//display a message to the user to re-enter
            IDValid = false;//sets ID validity to be false
            textField.clear();//clears the text field
        } else if (textField.getText().toCharArray().length > 5) {//checks if the ID input field is more than 5 digits
            label.setText("ID should contain maximum 5 characters");//display a message to the user to re-enter
            IDValid = false;//sets ID validity to be false
            textField.clear();//clears the text field
        } else if (textField.getText().toUpperCase().toCharArray()[0] == 'S') {
            entity = "Student";
        } else if (textField.getText().toUpperCase().toCharArray()[0] == 'T') {
            entity = "Teacher";
        } else if (textField.getText().toUpperCase().toCharArray()[0] == 'C' && textField.getText().toUpperCase().toCharArray()[1] == 'A') {
            entity = "ClubAdvisor";
        } else if (textField.getText().toUpperCase().toCharArray()[0] == 'C') {
            entity = "Club";
        } else if (textField.getText().toUpperCase().toCharArray()[0] == 'E') {
            entity = "Event";
        } else {
            IDValid = false;//if the id is not in either of the above formats the id is invalid
            label.setText("Invalid ID");
        }
        return IDValid;
    }

    public boolean checkPassword(TextField textField, Label label) {
        boolean passwordValid = true;
        label.setText("");//the error label made invisible at the start of the validation
        if (textField.getText().equals("")) {//checks if the password input field is blank
            label.setText("Cannot be empty");//display a message to the user to re-enter
            passwordValid = false;//sets password validity to be false
            textField.clear();//clears the text field
        } else if (textField.getText().toCharArray().length < 8) {
            label.setText("Password should contain minimum of 8 characters");
            passwordValid = false;
        }
        return passwordValid;
    }

}