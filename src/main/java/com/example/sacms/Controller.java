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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Controller {
    public String sessionUser;
    public String text;
    private Stage stage;//main stage where all our windows appear
    private Scene scene;//changes depending on the users requirement each scene is a window
    @FXML
    private AnchorPane userIconButtonOptionPane; //This displays the options available to a user when icon is clicked
    @FXML
    private TextField studentIdInputEventsScreen,eventIDCheckIn,clubNameInputClubCreationScreen, clubAdvisorIDInputClubCreationScreen, clubTeacherIDInputClubCreationScreen, eventNameEventCreationInput, eventDateEventCreationInput, eventTimeEventCreationInput, clubIDEventCreationInput, studentIDSigInClubAdvisorScreen, positionSigInClubAdvisorScreen, clubIDSigInClubAdvisorScreen, firstNameSignInStudentInput, lastNameSignInStudentInput, dateSignInStudentInput, classSignInStudentInput, emailSignInStudentInput, contactNoSignInStudentInput, passwordSignInStudentInput, studentIDSignInStudentInput, firstNameSignInTeacherInput, lastNameSignInTeacherInput, dateSignInTeacherInput, contactNoSignInTeacherInput, emailSignInTeacherInput, teacherIDSignInTeacherInput, passwordSignInTeacherInput, IDLoginInput, passwordLoginInput;
    @FXML
    private TextArea clubDescriptionInputClubCreationScreen, eventDescriptionEventCreationInput;
    @FXML
    private Label dayLabelDashboard,timeLabelDashboard,errorClubNameInputClubCreationScreen, errorClubAdvisorIDInputClubCreationScreen, errorClubDescriptionInputClubCreationScreen, errorTeacherIDInputClubCreationScreen, errorEventNameEventCreationInput, errorEventDateEventCreationInput, errorEventTimeEventCreationInput, errorClubIDEventCreationInput, errorEventDescriptionEventCreationInput, errorStudentIDSigInClubAdvisorScreen, errorPositionSigInClubAdvisorScreen, errorClubIDSigInClubAdvisorScreen, errorFirstNameSignInStudentInput, errorLastNameSignInStudentInput, errorDateSignInStudentInput, errorClassSignInStudentInput, errorEmailSignInStudentInput, errorContactNoSignInStudentInput, errorPasswordSignInStudentInput, errorStudentIDSignInStudentInput, errorFirstNameSignInTeacherInput, errorLastNameSignInTeacherInput, errorDateSignInTeacherInput, errorContactNoSignInTeacherInput, errorEmailSignInTeacherInput, errorTeacherIDSignInTeacherInput, errorPasswordSignInTeacherInput, errorIDLoginInput, errorPasswordLoginInput;
    @FXML
    private Text messageTeacherPopUpScreen,studentNameTeacherPopUpScreen,clubNameTeacherPopUpScreen;
    @FXML
    private Label userNameLabelDashboard;
    @FXML
    private Button refreshEventsViewClubAdvisorButton,refreshClubsViewClubAdvisorButton,refreshClubsStudentsAndTeachersViewButton, refreshButtonStudentsAndTeachersDashboard,refreshButtonTeacherPopUp,rejectButtonTeacherScreen,approveButtonTeacherScreen, refreshEventsStudentsAndTeachersViewButton;
    @FXML
    private AnchorPane checkInEventsPane,checkOutEventsPane,deleteEventsPane;
    @FXML
    private AnchorPane joinClubsPane,leaveClubsPane,deleteClubsPane;

    ArrayList<Teacher> registeredTeachers=Teacher.loadTeachersFromDatabase();
    ArrayList<Student> registeredStudents=Student.loadStudentsFromDatabase();
    ArrayList<ClubAdvisor> registeredClubAdvisors=ClubAdvisor.loadClubAdvisorsFromDatabase();

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
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("dashboardClubAdvisor.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Dashboard");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onDashboardStudentsAndTeachersScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("dashboardViewStudentsAndTeachers.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Dashboard");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onTeacherPopUScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("teacherPopUp.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Notifications");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    public void onRefreshDashboardScreenButtonClicked(ActionEvent event) throws IOException {
        LocalDateTime now = LocalDateTime.now();//gets the current time
        refreshButtonStudentsAndTeachersDashboard.setOpacity(0.0);//hides the refresh button when clicked
        refreshButtonStudentsAndTeachersDashboard.setDisable(true);

        // Format the date to "THU 03 OCT" using a custom DateTimeFormatter
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE  dd  MMM");
        String formattedDate = now.format(dateFormatter).toUpperCase();

        // Format the time to "15:24"
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(timeFormatter);

        // Set the formatted date and time to the label
        dayLabelDashboard.setText(formattedDate);
        timeLabelDashboard.setText(formattedTime);
        userNameLabelDashboard.setText("");
    }

    @FXML
    public void onReportsScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("reportsClubAdvisorView.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Reports");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onReportsStudentAndTeacherScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("reportsViewStudentAndTeachers.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Reports");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onClubsViewClubAdvisorScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("clubsViewClubAdvisor.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Clubs");

        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onClubsViewStudentsAndTeacherScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("clubsViewStudentsAndTeachers.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Clubs");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onEventsViewScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("eventsClubAdvisorView.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Events");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    @FXML
    public void onEventsViewStudentsAndTeacherScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("eventsViewStudentsAndTeachers.fxml"));
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
        this.stage.setTitle("Teacher Sign-In");
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
        boolean clubNameValid;
        boolean clubAdvisorIDValid;
        boolean clubDescriptionValid;
        boolean teacherIDValid;
        clubNameValid = checkName(clubNameInputClubCreationScreen, errorClubNameInputClubCreationScreen);
        clubAdvisorIDValid = checkID(clubAdvisorIDInputClubCreationScreen, errorClubAdvisorIDInputClubCreationScreen);
        if (clubAdvisorIDValid) {
            if (!sessionUser.equals("ClubAdvisor")) {//To check whether the user has entered a clubAdvisorID or not
                errorClubAdvisorIDInputClubCreationScreen.setText("Invalid ID");
                clubAdvisorIDValid = false;
            }
        }
        clubDescriptionValid = checkDescription(clubDescriptionInputClubCreationScreen, errorClubDescriptionInputClubCreationScreen);
        teacherIDValid = checkID(clubTeacherIDInputClubCreationScreen, errorTeacherIDInputClubCreationScreen);
        if (teacherIDValid) {
            if (!sessionUser.equals("Teacher")) {//To check whether the user has entered a teacherID or not
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
        boolean eventDateValid;
        boolean eventTimeValid;
        boolean clubIDValid;
        boolean eventDescriptionValid;
        eventNameValid = checkName(eventNameEventCreationInput, errorEventNameEventCreationInput);
        eventDateValid = checkDate(eventDateEventCreationInput, errorEventDateEventCreationInput);
        eventTimeValid = checkTime(eventTimeEventCreationInput, errorEventTimeEventCreationInput);
        clubIDValid = checkID(clubIDEventCreationInput, errorClubIDEventCreationInput);
        if (clubIDValid) {
            if (!sessionUser.equals("Club")) {//To check whether the user has entered a clubID or not
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
            if (!sessionUser.equals("Student")) {//To check whether the user has entered a studentID or not
                errorStudentIDSigInClubAdvisorScreen.setText("Invalid ID");
                studentIDValid = false;
            }
        }
        positionValid = checkName(positionSigInClubAdvisorScreen, errorPositionSigInClubAdvisorScreen);
        clubIDValid = checkID(clubIDSigInClubAdvisorScreen, errorClubIDSigInClubAdvisorScreen);
        if (clubIDValid) {
            if (!sessionUser.equals("Club")) {//To check whether the user has entered a clubID or not
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
        boolean DOBValid;
        boolean classValid;
        boolean emailValid;
        boolean contactNoValid;
        boolean passwordValid;
        boolean studentIDValid;
        firstNameValid = checkName(firstNameSignInStudentInput, errorFirstNameSignInStudentInput);
        lastNameValid = checkName(lastNameSignInStudentInput, errorLastNameSignInStudentInput);
        DOBValid = checkDate(dateSignInStudentInput, errorDateSignInStudentInput);
        classValid = checkClass(classSignInStudentInput, errorClassSignInStudentInput);
        emailValid = checkEmail(emailSignInStudentInput, errorEmailSignInStudentInput);
        contactNoValid = checkContactNo(contactNoSignInStudentInput, errorContactNoSignInStudentInput);
        passwordValid = checkPassword(passwordSignInStudentInput, errorPasswordSignInStudentInput);
        studentIDValid = checkID(studentIDSignInStudentInput, errorStudentIDSignInStudentInput);
        if (studentIDValid) {
            if (!sessionUser.equals("Student")) {//To check whether the user has entered a studentID or not
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
            Student student = new Student(firstNameSignInStudentInput.getText(),lastNameSignInStudentInput.getText(),dateSignInStudentInput.getText(), classSignInStudentInput.getText(), emailSignInStudentInput.getText(), contactNoSignInStudentInput.getText(), passwordSignInStudentInput.getText(),studentIDSignInStudentInput.getText());
            // Hammad complete this part this is linked with the database u have to store these data there
        }
    }

    public void onTeacherSignInTwoButtonClicked(ActionEvent event) throws IOException, SQLException {
        boolean firstNameValid;
        boolean lastNameValid;
        boolean DOBValid;
        boolean contactNoValid;
        boolean emailValid;
        boolean teacherIDValid;
        boolean passwordValid;
        firstNameValid = checkName(firstNameSignInTeacherInput, errorFirstNameSignInTeacherInput);
        lastNameValid = checkName(lastNameSignInTeacherInput, errorLastNameSignInTeacherInput);
        DOBValid = checkDate(dateSignInTeacherInput, errorDateSignInTeacherInput);
        contactNoValid = checkContactNo(contactNoSignInTeacherInput, errorContactNoSignInTeacherInput);
        emailValid = checkEmail(emailSignInTeacherInput, errorEmailSignInTeacherInput);
        teacherIDValid = checkID(teacherIDSignInTeacherInput, errorTeacherIDSignInTeacherInput);
        if (teacherIDValid) {
            if (!sessionUser.equals("Teacher")) {//To check whether the user has entered a teacherID or not
                errorTeacherIDSignInTeacherInput.setText("Invalid ID");
                teacherIDValid = false;
            }
        }
        passwordValid = checkPassword(passwordSignInTeacherInput, errorPasswordSignInTeacherInput);
        if (firstNameValid && lastNameValid && DOBValid && contactNoValid && emailValid && teacherIDValid && passwordValid) {//if the above inputs done by the user is valid the data will be stored
            Teacher teacher = new Teacher(firstNameSignInTeacherInput.getText(),lastNameSignInTeacherInput.getText(),emailSignInTeacherInput.getText(),passwordSignInTeacherInput.getText(),dateSignInTeacherInput.getText(),contactNoSignInTeacherInput.getText(),teacherIDSignInTeacherInput.getText());
            teacher.insertToDatabase();//creates a teacher object and then inserts into the databse, and redirects to the home screen
            onDashboardStudentsAndTeachersScreenButtonClicked(event);
        }
    }

    public void onLogInButtonClicked(ActionEvent event) throws  IOException {
        boolean IDValid;
        boolean passwordValid;
        String idInput = null;
        String passwordInput=null;
        IDValid = checkID(IDLoginInput, errorIDLoginInput);
        if (IDValid) {
            if (sessionUser.equals("Club") || sessionUser.equals("Event")) {//To check whether the user has entered a club/ event ID instead of student/ ClubAdvisor or teacher ID
                errorIDLoginInput.setText("Invalid ID");
                IDValid = false;
            }
        }
        passwordValid = checkPassword(passwordLoginInput, errorPasswordLoginInput);
        if (IDValid && passwordValid) {
            boolean found=false;
            idInput=IDLoginInput.getText().toString();
            passwordInput=passwordLoginInput.getText().toString();
            //read from the database for exsisting records
            if (sessionUser.equals("Student")){
                        registeredStudents=Student.loadStudentsFromDatabase();
                        for (Student student:registeredStudents){
                            if (student.getStudentID().equals(idInput)){
                                if(student.getPassword().equals(passwordInput)){
                                    text = (student.greetUser().toString());
                                    found=true;
                                    onDashboardStudentsAndTeachersScreenButtonClicked(event);
                                }
                            }
                            errorPasswordLoginInput.setText("Incorrect Password");
                        }
            }
            if (sessionUser.equals("Teacher")){
                registeredTeachers=Teacher.loadTeachersFromDatabase();
                if (registeredTeachers.size()>0){
                    for (Teacher teacher:registeredTeachers){
                        if (teacher.getStaffID().equals(idInput)){
                            if(teacher.getPassword().equals(passwordInput)){
                                text=teacher.greetUser();
                                found=true;
                                onTeacherPopUScreenButtonClicked(event);
                            }
                            errorPasswordLoginInput.setText("Incorrect ID or Password");
                        }
                    }
                }

            }
            if (sessionUser.equals("ClubAdvisor")){
                registeredStudents=Student.loadStudentsFromDatabase();
                registeredClubAdvisors=ClubAdvisor.loadClubAdvisorsFromDatabase();
                if (registeredClubAdvisors.size()>0){
                    for (ClubAdvisor clubAdvisor:registeredClubAdvisors) {
                        if (clubAdvisor.getClubAdvisorID().equals(idInput)){
                            String studentId=clubAdvisor.getStudentID();
                            for (Student student:registeredStudents) {
                                if (student.getStudentID().equals(studentId) && student.getPassword().equals(passwordInput)){
                                    found=true;
                                    onDashboardScreenButtonClicked(event);
                                }

                            }
                        }
                    }
                }
            }
            if (!found){
                errorPasswordLoginInput.setText("Not Available");
            }

        }
    }

    public void onTeacherPopUpCloseButtonClicked(ActionEvent event) throws IOException { onDashboardStudentsAndTeachersScreenButtonClicked(event);}
    public void onRefreshTeacherScreenButtonClicked(ActionEvent event) throws IOException {
        refreshButtonTeacherPopUp.setOpacity(0.00);
        refreshButtonTeacherPopUp.setDisable(true);

        boolean haveNotification=false;

        if (!haveNotification){
            approveButtonTeacherScreen.setOpacity(0.00);
            approveButtonTeacherScreen.setDisable(true);
            rejectButtonTeacherScreen.setOpacity(0.00);
            rejectButtonTeacherScreen.setDisable(true);
            messageTeacherPopUpScreen.setText("No notifications");
            studentNameTeacherPopUpScreen.setText("");
            clubNameTeacherPopUpScreen.setText("");
        }

    }
    public void onApproveButtonTeacherScreenClicked(ActionEvent event) throws IOException {
        //code to approve the club advisor
        onTeacherPopUpCloseButtonClicked(event);
    }
    public void onRejectButtonTeacherScreenClicked(ActionEvent event) throws IOException {
        //test
        onTeacherPopUpCloseButtonClicked(event);
    }

    //Clubs View Methods
    public void onJoinClubsViewOptionClicked(ActionEvent event) throws IOException {
        joinClubsPane.setOpacity(1.0);
        leaveClubsPane.setOpacity(0.0);

    }
    public void onLeaveClubsViewOptionClicked(ActionEvent event) throws IOException {
        joinClubsPane.setOpacity(0.0);
        leaveClubsPane.setOpacity(1.0);
    }
    public void onJoinClubClicked(ActionEvent event) throws IOException {

    }
    public void onLeaveClubClicked(ActionEvent event) throws IOException {

    }
    public void onRefreshClubsViewStudentsAndTeachersButtonClicked(ActionEvent event) throws IOException {
        refreshClubsStudentsAndTeachersViewButton.setDisable(true);
        refreshClubsStudentsAndTeachersViewButton.setOpacity(0.0);

    }
    public void onHideClubsViewStudentsAndTeachersOptionClicked(ActionEvent event) throws IOException {
        joinClubsPane.setOpacity(0.00);
        leaveClubsPane.setOpacity(0.00);

    }



    //Events View Methods Students and Teachers
    public void onCheckInEventsViewOptionClicked(ActionEvent event) throws IOException {
        checkInEventsPane.setOpacity(1.0);
        checkOutEventsPane.setOpacity(0.0);

    }
    public void onCheckOutEventsViewOptionClicked(ActionEvent event) throws IOException {
        checkOutEventsPane.setOpacity(1.0);
        checkInEventsPane.setOpacity(0.0);

    }
    public void onCheckInEventClicked(ActionEvent event) throws IOException {
        String studentID=studentIdInputEventsScreen.getText();
        String eventID = eventIDCheckIn.getText();

    }
    public void onCheckOutEventClicked(ActionEvent event) throws IOException {
        String studentID=studentIdInputEventsScreen.getText();
        String eventID = eventIDCheckIn.getText();

    }
    public void onRefreshEventsViewStudentsAndTeachersButtonClicked(ActionEvent event) throws IOException {
        refreshEventsStudentsAndTeachersViewButton.setDisable(true);
        refreshEventsStudentsAndTeachersViewButton.setOpacity(0.0);
    }
    public void onHideEventsViewStudentsAndTeachersOptionClicked(ActionEvent event) throws IOException {
        checkInEventsPane.setOpacity(0.00);
        checkOutEventsPane.setOpacity(0.00);

    }

    //Events View Methods Club Advisors
    public void onEditEventsViewOptionClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("editEventScreen.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Edit Event Screen");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    public void onDeleteEventsViewOptionClicked(ActionEvent event) throws IOException {
        deleteEventsPane.setOpacity(1.0);
    }

    public void onDeleteButtonClickedEventsScreen(ActionEvent event) throws IOException {

    }
    public void onRefreshEventsViewClubAdvisorButtonClicked(ActionEvent event) throws IOException {
        refreshEventsViewClubAdvisorButton.setDisable(true);
        refreshEventsViewClubAdvisorButton.setOpacity(0.0);
    }

    //clubs View Methods Club Advisors
    public void onDeleteClubsViewOptionClicked(ActionEvent event) throws IOException {
        deleteClubsPane.setOpacity(1.0);
    }

    public void onDeleteButtonClickedClubsScreen(ActionEvent event) throws IOException {

    }
    public void onRefreshClubsViewClubAdvisorButtonClicked(ActionEvent event) throws IOException {
        refreshClubsViewClubAdvisorButton.setDisable(true);
        refreshClubsViewClubAdvisorButton.setOpacity(0.0);
    }

    public void onUpdateEventButtonClicked(ActionEvent event) throws IOException {

    }

    //report View Methods
    public void onRefreshReportsViewButtonClicked(ActionEvent event) throws IOException {

    }

    // Validation methods
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
            sessionUser = "Student";
        } else if (textField.getText().toUpperCase().toCharArray()[0] == 'T') {
            sessionUser = "Teacher";
        } else if (textField.getText().toUpperCase().toCharArray()[0] == 'C' && textField.getText().toUpperCase().toCharArray()[1] == 'A') {
            sessionUser = "ClubAdvisor";
        } else if (textField.getText().toUpperCase().toCharArray()[0] == 'C') {
            sessionUser = "Club";
        } else if (textField.getText().toUpperCase().toCharArray()[0] == 'E') {
            sessionUser = "Event";
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

    public boolean checkDate(TextField textField, Label label) {
        // split based on the  (-). if
        boolean dateValid = true;
        label.setText("");//the error label made invisible at the start of the validation
        if (textField.getText().equals("")) {//checks if the date input field is blank
            label.setText("Cannot be empty");//display a message to the user to re-enter
            dateValid = false;//sets date validity to be false
            textField.clear();//clears the text field
        } else {
            try {
                LocalDate.parse(textField.getText());//checks if the date is in the correct format
            } catch (DateTimeParseException e) {//exception handling to catch for DateTimeParseException error
                label.setText("Invalid date");//if the date is invalid, a message will be displayed to the user saying its incorrect
                dateValid = false;
                textField.clear();//clears the text field
            }
        }
        return dateValid;
    }

    public boolean checkTime(TextField textField, Label label) {
        boolean timeValid = true;
        label.setText("");//the error label made invisible at the start of the validation
        if (textField.getText().equals("")) {//checks if the time input field is blank
            label.setText("Cannot be empty");//display a message to the user to re-enter
            timeValid = false;//sets time validity to be false
            textField.clear();//clears the text field
        } else {
            try {
                LocalTime.parse(textField.getText());//checks if the time is in the correct format
            } catch (DateTimeParseException e) {//exception handling to catch for DateTimeParseException error
                label.setText("Invalid time");//if the time is invalid, a message will be displayed to the user saying its incorrect
                timeValid = false;
                textField.clear();//clears the text field
            }
        }
        return timeValid;
    }
}