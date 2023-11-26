package com.example.sacms;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Controller {
    private Stage stage;//main stage where all our windows appear
    private Scene scene;//changes depending on the users requirement each scene is a window
    public String sessionUser;//variable to check who the user is
    //declaring the id's which were named in the fxml
    @FXML
    private AnchorPane joinClubsPane,leaveClubsPane,deleteClubsPane,checkInEventsPane,checkOutEventsPane,deleteEventsPane,todayEventOnePane,todayEventTwoPane,tomorrowEventOnePane,userIconButtonOptionPane; //This displays the options available to a user when icon is clicked
    @FXML
    private TextField clubIDEditEventInput,locationEventEditInput,eventIDDelete,locationEventCreationInput,eventNameEditEventInput, eventDateEditEventInput, eventTimeEditEventInput, eventIDEditEventInput, clubAdvisorIdInputEventsScreen, eventIDCheckOut,clubIDDeleteInput,clubAdvisorIDInputClubsScreen,clubIDInputLeaveClubsStudetnsAndTeachers,clubIDInputJoinClubsStudetnsAndTeachers,studentIdInputClubsScreen,studentIdInputEventsScreen,eventIDCheckIn,clubNameInputClubCreationScreen, clubAdvisorIDInputClubCreationScreen, eventNameEventCreationInput, eventDateEventCreationInput, eventTimeEventCreationInput, clubIDEventCreationInput, studentIDSigInClubAdvisorScreen, positionSigInClubAdvisorScreen, clubIDSigInClubAdvisorScreen, firstNameSignInStudentInput, lastNameSignInStudentInput, dateSignInStudentInput, classSignInStudentInput, emailSignInStudentInput, contactNoSignInStudentInput, passwordSignInStudentInput, studentIDSignInStudentInput, firstNameSignInTeacherInput, lastNameSignInTeacherInput, dateSignInTeacherInput, contactNoSignInTeacherInput, emailSignInTeacherInput, teacherIDSignInTeacherInput, passwordSignInTeacherInput, IDLoginInput, passwordLoginInput;
    @FXML
    private TextArea clubDescriptionInputClubCreationScreen, eventDescriptionEventCreationInput, eventDescriptionEditEventInput;
    @FXML
    private Label dashboardLabelClubAdvisorSignin,errorClubIDEditEventInput,errorEventNameEditEventInput, errorEventDateEditEventInput, errorEventTimeEditEventInput, errorEventIDEditEventInput, errorEventDescriptionEditEventInput, errorClubAdvisorIDInputClubsScreen, errorStudentIdInputClubsScreen, errorClubAdvisorIdInputEventsLabel, errorStudentIDEventsLabel, errorCheckInEventsLabel, errorCheckOutEventsLabel,notificationLabel,todayEventOneTime,todayEventOneName,todayEventOneOrganisingClubName,tomorrowEventOneTime,tomorrowEventOneName,tomorrowEventOneOrganisingClubName,todayEventTwoTime,todayEventTwoName,todayEventTwoOrganisingClubName,errorLocationEventEditInput,errorEventIDDelete,errorLocationEventCreationInput,messageLabel,userNameLabelDashboard,errorDeleteClubsLabel,errorJoinClubsLabel,errorleaveClubsLabel1,dayLabelDashboard,timeLabelDashboard,errorClubNameInputClubCreationScreen, errorClubAdvisorIDInputClubCreationScreen, errorClubDescriptionInputClubCreationScreen, errorTeacherIDInputClubCreationScreen, errorEventNameEventCreationInput, errorEventDateEventCreationInput, errorEventTimeEventCreationInput, errorClubIDEventCreationInput, errorEventDescriptionEventCreationInput, errorStudentIDSigInClubAdvisorScreen, errorPositionSigInClubAdvisorScreen, errorClubIDSigInClubAdvisorScreen, errorFirstNameSignInStudentInput, errorLastNameSignInStudentInput, errorDateSignInStudentInput, errorClassSignInStudentInput, errorEmailSignInStudentInput, errorContactNoSignInStudentInput, errorPasswordSignInStudentInput, errorStudentIDSignInStudentInput, errorFirstNameSignInTeacherInput, errorLastNameSignInTeacherInput, errorDateSignInTeacherInput, errorContactNoSignInTeacherInput, errorEmailSignInTeacherInput, errorTeacherIDSignInTeacherInput, errorPasswordSignInTeacherInput, errorIDLoginInput, errorPasswordLoginInput;
    @FXML
    private Text messageTeacherPopUpScreen,studentNameTeacherPopUpScreen,clubNameTeacherPopUpScreen;
    @FXML
    private Button refreshReportViewButton,requestClubAdvisorRoleButtonSignInTwo,dashboardButtonClubAdvisorSignin,refreshClubsInchargeList,refreshClubsViewButton,refreshButtonStudentsAndTeachersDashboard,refreshButtonTeacherPopUp,rejectButtonTeacherScreen,approveButtonTeacherScreen, refreshEventsViewButton;
    @FXML
    private ChoiceBox<String> clubTeacherIDInputClubCreationScreen;
    @FXML
    private TableView<Club> clubsViewTable;
    @FXML
    private TableColumn<Club, String> clubIDColumnClubTable;
    @FXML
    private TableColumn<Club, String> clubNameColumnClubTable;
    @FXML
    private TableColumn<Club, String> clubDescriptionColumnClubTable;
    @FXML
    private TableColumn<Club, String> teacherInchargeColumnClubTable;
    @FXML
    private TableView<Event> eventsViewTable;
    @FXML
    private TableColumn<Event, String> eventIDColumn ;
    @FXML
    private TableColumn<Event, String> eventNameColumn;
    @FXML
    private TableColumn<Event, String> eventDateColumn;
    @FXML
    private TableColumn<Event, String> eventTimeColumn;
    @FXML
    private TableColumn<Event, String> eventLocationColumn;
    @FXML
    private TableColumn<Event, String> eventDescriptionColumn;
    @FXML
    private TableView<String> clubAdvisorTable,reportsTable;
    @FXML
    private TableColumn<String, String> clubAdvisorIDColumnClubAdvisorTable,clubNameReportColumn;
    @FXML
    private TableColumn<String, String> clubNameColumnClubAdvisorTable,NoOfClubMembersColumn;
    @FXML
    private TableColumn<String, String> studentIDColumnClubAdvisorTable,NoOfClubEventsHeldColumn;
    @FXML
    private TableColumn<String, String> nameColumnClubAdvisorTable,recentEventNameColumn;
    @FXML
    private TableColumn<String, String> contactNoColumnClubAdvisorTable,recentEventAttendance;
    @FXML
    private TableColumn<String, String> positionColumnClubAdvisorTable;
    @FXML
    private TableColumn<String, String> emailColumnClubAdvisorTable;
    //arraylists which will be used to get the details of each respective arraylist from the database
    ArrayList<Teacher> registeredTeachers=Teacher.loadTeachersFromDatabase();
    ArrayList<Student> registeredStudents=Student.loadStudentsFromDatabase();
    ArrayList<ClubAdvisor> registeredClubAdvisors=ClubAdvisor.loadClubAdvisorsFromDatabase();
    ArrayList<Club> registeredClubs = Club.loadClubsFromDatabase();
    ArrayList<Event> registeredEvents = Event.loadEventsFromDatabase();
    public static Controller instance;
    public String sessionID;
    public String getSessionID(){//to check whos currently logged in to the system
        return this.sessionID;
    }
    public void setSessionID(String id){
        this.sessionID=id;
    }
    public Controller() {
    }
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }//create a new controller instance if its null

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
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("dashboardViewClubAdvisor.fxml"));
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
        messageLabel.setOpacity(0.0);
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("eventCreation.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Schedule Event");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    //1.4 mapping from create club sequence diagram
    @FXML
    public void onCreateClubButtonOptionClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
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
    public void onLoadClubInchargeListStudentsAndTeachers(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("clubInchargeScreenStudentsAndTeachers.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("View Club Advisor of all clubs");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    public void onLoadClubInchargeListClubAvisor(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("clubInchargeScreenClubAdvisor.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("View Club Advisor of all clubs");
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
    //1.1 mapped from sequence diagram
    @FXML
    public void onLogInScreenButtonClicked(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("loginScreen.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Login");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
    public void onEditEventsViewOptionClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("editEventScreen.fxml"));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setTitle("Edit Event Screen");
        this.stage.setScene(this.scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    //method to display the time and date of the current day to day
    public void onRefreshDashboardScreenButtonClicked(ActionEvent event) throws IOException {
        LocalDateTime now = LocalDateTime.now();//gets the current time
        refreshButtonStudentsAndTeachersDashboard.setOpacity(0.0);//hides the refresh button when clicked
        refreshButtonStudentsAndTeachersDashboard.setDisable(true);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE  dd  MMM");
        String formattedDate = now.format(dateFormatter).toUpperCase();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(timeFormatter);

        // Sets the formatted date and time to the label
        dayLabelDashboard.setText(formattedDate);
        timeLabelDashboard.setText(formattedTime);
        userNameLabelDashboard.setText("");

        ArrayList<Event> todaysEvents = new ArrayList<>();
        ArrayList<Event> tomorrowEvents = new ArrayList<>();


        LocalDate currentDate = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentDate.format(formatter);
        String tomorrowDate = tomorrow.format(formatter);


        for (Event eventExisting:registeredEvents){
            if (eventExisting.getEventDate().equals(date)){
                todaysEvents.add(eventExisting);
            }
            else if(eventExisting.getEventDate().equals(tomorrowDate)){
                tomorrowEvents.add(eventExisting);
            }

        }
        Collections.sort(todaysEvents, Comparator.comparing(eventTime -> LocalTime.parse(eventTime.getEventTime())));

        // Sorts tomorrow's events by time
        Collections.sort(tomorrowEvents, Comparator.comparing(eventTime -> LocalTime.parse(eventTime.getEventTime())));



        if (todaysEvents.size() > 0) {//sorts today's events
            int numberOfEventsToDisplay = Math.min(todaysEvents.size(), 2);
            for (int i = 0; i < numberOfEventsToDisplay; i++) {
                Event sortEvent = todaysEvents.get(i);
                String eventName = sortEvent.getEventName();
                String eventTime = sortEvent.getEventTime();
                String clubID = sortEvent.getClubID();
                String clubName = "";
                for (Club club : registeredClubs) {
                    if (club.getClubID().equals(clubID)) {
                        clubName=(club.getClubName());
                        break;
                    }
                }

                // Set the values based on the index
                if (i == 0) {
                    todayEventOneTime.setText(sortEvent.getEventTime());
                    todayEventOneName.setText(sortEvent.getEventName());
                    todayEventOneOrganisingClubName.setText(clubName);
                    todayEventOnePane.setOpacity(1.0);
                } else if (i == 1) {
                    todayEventTwoTime.setText(eventTime);
                    todayEventTwoName.setText(eventName);
                    todayEventTwoOrganisingClubName.setText(clubName);
                    todayEventTwoPane.setOpacity(1.0);
                }
            }

            if (!tomorrowEvents.isEmpty()) {
                Event tomorrowFirstEvent = tomorrowEvents.get(0);//have to find the next closest event

                tomorrowEventOneTime.setText(tomorrowFirstEvent.getEventTime());
                tomorrowEventOneName.setText(tomorrowFirstEvent.getEventName());
                String clubName = "";
                for (Club club : registeredClubs) {
                    if (club.getClubID().equals(tomorrowFirstEvent.getClubID())) {
                        clubName = club.getClubName();
                    }
                }
                tomorrowEventOneOrganisingClubName.setText(clubName);
                tomorrowEventOnePane.setOpacity(1.0);
            } else {
                tomorrowEventOnePane.setOpacity(0.0);
            }


        }
        boolean studentFound = false;
        Controller controller = Controller.getInstance();
        String loggedInStudentID = controller.getSessionID();
        for (ClubAdvisor clubAdvisor:registeredClubAdvisors){
            if (clubAdvisor.getStudentID().equals(loggedInStudentID)){
                studentFound=true;
            }
        }
        if (studentFound==true){
            notificationLabel.setText("You have Club Advisor Privileges! Check out the Club In charge list to find out your ClubAdvisorID");
        }else{
            notificationLabel.setText("No Notifications");
        }


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
    //1.5 mapping from create club sequence diagram
    public void onCreateClubButtonClicked(ActionEvent event) throws IOException, InterruptedException {
        messageLabel.setOpacity(0.0);
        boolean clubNameValid;
        boolean clubAdvisorIDValid;
        boolean clubDescriptionValid;
        boolean teacherIDValid = false;
        String existingStudentID = "";
        clubNameValid = checkName(clubNameInputClubCreationScreen, errorClubNameInputClubCreationScreen);//to check if the Club id is valid
        for (Club club:registeredClubs){
            if (club.getClubName().equals(clubNameInputClubCreationScreen.getText())){
                errorClubNameInputClubCreationScreen.setText("There Exists a Club");//if there exists a club then doesnt get added
                clubNameInputClubCreationScreen.clear();
                clubNameValid = false;
                break;
            }
        }
        clubAdvisorIDValid = checkID(clubAdvisorIDInputClubCreationScreen, errorClubAdvisorIDInputClubCreationScreen);//checks the club advisor id
        if (clubAdvisorIDValid) {
            String clubAdvisorID = clubAdvisorIDInputClubCreationScreen.getText();
            for (ClubAdvisor clubAdvisor:registeredClubAdvisors){
                if (clubAdvisor.getClubAdvisorID().equals(clubAdvisorID)){
                    clubAdvisorIDValid = true;
                    existingStudentID = clubAdvisor.getStudentID();//if the clubAdvisor is found then loop breaks
                    break;
                } else {
                    messageLabel.setText("Club Advisor doesn't exist".toUpperCase());
                    messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                    messageLabel.setOpacity(1.0);
                    errorClubAdvisorIDInputClubCreationScreen.setText("Invalid ID");
                    clubAdvisorIDInputClubCreationScreen.clear();
                    clubAdvisorIDValid=false;
                }
            }
        }

        if (clubTeacherIDInputClubCreationScreen.getValue() != null && !clubTeacherIDInputClubCreationScreen.getValue().equals("Please Select")) {
            teacherIDValid = true;
            errorTeacherIDInputClubCreationScreen.setText("");
        } else {
            errorTeacherIDInputClubCreationScreen.setText("Please Select a Teacher");
        }

        clubDescriptionValid = checkDescription(clubDescriptionInputClubCreationScreen, errorClubDescriptionInputClubCreationScreen);

        if (clubNameValid && clubAdvisorIDValid && clubDescriptionValid && teacherIDValid) {//if the above inputs done by the user is valid the data will be stored
            String clubName = clubNameInputClubCreationScreen.getText();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
            String clubDescription = clubDescriptionInputClubCreationScreen.getText();
            String clubTeacherID = clubTeacherIDInputClubCreationScreen.getValue();
            String clubID;

            ArrayList registeredClubsID = new ArrayList<>();
            //1.5.2 mapping from create club sequence diagram
            for (Club club:registeredClubs){
                registeredClubsID.add(club.getClubID());//stores existing list of clubID's
            }
            do {
                clubID = Club.generateClubID();//generate a new clubID
            } while (registeredClubsID.contains(clubID));
            Club club = new Club(clubID,clubName,clubDescription,clubTeacherID);
            club.insertIntoClubs();//updated the club table

            ArrayList registeredClubsAdvisorIDs = new ArrayList<>();
            String newClubAdvisorID;
            for (ClubAdvisor clubAdvisor:registeredClubAdvisors){
                registeredClubsAdvisorIDs.add(clubAdvisor.getClubAdvisorID());
            }
            do {
                newClubAdvisorID = ClubAdvisor.generateClubAdvisorID();//generate a new ID
            } while (registeredClubsAdvisorIDs.contains(newClubAdvisorID));
            ClubAdvisor newClubAdvisor = new ClubAdvisor(newClubAdvisorID,existingStudentID,clubID,"Founder Member");
            newClubAdvisor.insertIntoClubAdvisorTable();//update the club advisor table
            messageLabel.setText("ClUB CREATED SUCCESSFULLY");
            messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
            messageLabel.setOpacity(1.0);

            clubNameInputClubCreationScreen.clear();
            clubDescriptionInputClubCreationScreen.clear();
            clubAdvisorIDInputClubCreationScreen.clear();
            errorClubAdvisorIDInputClubCreationScreen.setText("");
        }
    }
    public void onScheduleEventButtonTwoClicked (ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        boolean eventNameValid;
        boolean eventDateValid;
        boolean eventTimeValid;
        boolean clubIDValid;
        boolean eventLocationValid;
        boolean eventDescriptionValid;
        boolean eventExist=false;
        String eventNameInput=eventNameEventCreationInput.getText();
        String eventDateInput=eventDateEventCreationInput.getText();
        String eventTimeInput=eventTimeEventCreationInput.getText();
        String clubIDInput=clubIDEventCreationInput.getText();
        String eventLocationInput=locationEventCreationInput.getText();
        String eventDescriptionInput=eventDescriptionEventCreationInput.getText();

        if (eventNameEventCreationInput.getText().equals(null) || eventNameEventCreationInput.getText().equals("")){//checks if the event name is empty or not
            eventNameValid=false;
            eventNameEventCreationInput.clear();
            errorEventNameEventCreationInput.setText("Cannot be empty");
        }else{
            eventNameValid= true;
            errorEventNameEventCreationInput.setText("");
        }
        eventDateValid = checkDate(eventDateEventCreationInput, errorEventDateEventCreationInput);//event date is validated
        eventTimeValid = checkTime(eventTimeEventCreationInput, errorEventTimeEventCreationInput);//event time is validated
        clubIDValid = checkID(clubIDEventCreationInput, errorClubIDEventCreationInput);//validates the id
        if (clubIDValid) {
            ArrayList<String> registeredClubsID = new ArrayList<>();
            for (Club club : registeredClubs) {
                club.setEvents();
                registeredClubsID.add(club.getClubID());
            }
            if (!registeredClubsID.contains(clubIDEventCreationInput.getText())) {//checks if this club exists in the database
                clubIDValid = false;
                clubIDEventCreationInput.clear();
                errorClubIDEventCreationInput.setText("Club not Found");
            } else {
                clubIDValid = true;
                errorClubIDEventCreationInput.setText("");
            }
        }
        eventDescriptionValid = checkDescription(eventDescriptionEventCreationInput, errorEventDescriptionEventCreationInput);//validates the event description
        eventLocationValid = checkName(locationEventCreationInput, errorLocationEventCreationInput);//validates the location
        if (eventNameValid && eventDateValid && eventTimeValid && clubIDValid && eventDescriptionValid && eventLocationValid) {//if the above inputs done by the user is valid the data will be stored
            ArrayList registeredEventsID = new ArrayList<>();
            for (Event eventInformation:registeredEvents){
                registeredEventsID.add(eventInformation.getEventID());
            }
            String newEventID;
            do {
                newEventID = Event.generateEventID();//system generates event id's
            } while (registeredEventsID.contains(newEventID));
            Event newEvent = new Event(newEventID,eventNameInput,eventDateInput,eventTimeInput,eventLocationInput,clubIDInput,eventDescriptionInput);
            for (Club registeredClub:registeredClubs) {//checks if the same event already exists
                if (registeredClub.getEvents().contains(newEvent)){
                    eventExist = true;
                }
            }

            if (!eventExist){
                newEvent.insertEvent();

                messageLabel.setText("EVENT CREATED SUCCESSFULLY");
                messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
                messageLabel.setOpacity(1.0);

                eventNameEventCreationInput.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
                eventDateEventCreationInput.clear();
                eventTimeEventCreationInput.clear();
                clubIDEventCreationInput.clear();
                locationEventCreationInput.clear();
                eventDescriptionEventCreationInput.clear();
            }else{
                messageLabel.setText("Event Exists");
                messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                messageLabel.setOpacity(1.0);
            }
        }

    }
    //Club Creation SCREEN
    public void onLoadStaffIDClubScreationScreenClicked (ActionEvent event) throws IOException, InterruptedException {
        messageLabel.setOpacity(0.0);
        clubTeacherIDInputClubCreationScreen.getItems().clear();
        ArrayList<String> teacherIDs = new ArrayList<>();
        for (Teacher teacher : registeredTeachers) {
            boolean teacherExists = false;

            for (Club club : registeredClubs) {
                if (club.getTeacherID().equals(teacher.getStaffID())) {
                    teacherExists = true;
                    break; // No need to continue checking once we find a match
                }
            }

            if (!teacherExists) {
                teacherIDs.add(teacher.getStaffID());
            }
        }
        for (String teacher:teacherIDs){
            clubTeacherIDInputClubCreationScreen.getItems().add(teacher);
        }
        clubTeacherIDInputClubCreationScreen.setValue("Please Select");
        messageLabel.setText("AVAILABLE TEACHERS LOADED");
        messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
        messageLabel.setOpacity(1.0);
    }
    public void onClubAdvisorSignInTwoButtonClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        boolean studentIDValid;
        boolean positionValid;
        boolean clubIDValid;
        boolean clubFound = false;
        boolean studentFound = false;
        boolean positionFound = false;
        studentIDValid = checkID(studentIDSigInClubAdvisorScreen, errorStudentIDSigInClubAdvisorScreen);// validates the id
        if (studentIDValid) {
            for (Student student : registeredStudents) {
                if (student.getStudentID().equals(studentIDSigInClubAdvisorScreen.getText())) {//checks if the student id entered is in the database
                    studentFound = true;
                    errorStudentIDSigInClubAdvisorScreen.setText("");
                    break;
                } else {
                    errorStudentIDSigInClubAdvisorScreen.setText("Invalid ID");
                }
            }
            if (!studentFound) {
                studentIDSigInClubAdvisorScreen.clear();
            }
        }
        positionValid = checkName(positionSigInClubAdvisorScreen, errorPositionSigInClubAdvisorScreen);//validates the position
        clubIDValid = checkID(clubIDSigInClubAdvisorScreen, errorClubIDSigInClubAdvisorScreen);//validates the club id
        if (studentIDValid && positionValid && clubIDValid && studentFound) {//if the above inputs done by the user is valid the data will be stored
            String teacherID;
            String clubID;
            String requestID;
            ArrayList<ClubAdvisor> clubAdvisorDetails;
            do {
                requestID=Club.generateRequestID();//generates a random club advisor id from the system
            }while (Club.loadExistingRequestsIds().contains(requestID));//checks if the generated id already exists
            for (Club club:registeredClubs){
                if (club.getClubID().equals(clubIDSigInClubAdvisorScreen.getText())){//checks if the enetred club id is there in the database
                    teacherID=club.getTeacherID();
                    clubID=club.getClubID();
                    clubFound=true;
                    clubAdvisorDetails = club.loadClubAdvisorsOfClub(clubID);
                    for (ClubAdvisor clubAdvisor : clubAdvisorDetails) {
                        if (clubAdvisor.getPosition().equals(positionSigInClubAdvisorScreen.getText())){//checks if the position already exists in the club
                            positionFound = true;
                            break;
                        }
                    }
                    if (positionFound) {
                        errorPositionSigInClubAdvisorScreen.setText("Invalid position");
                        positionSigInClubAdvisorScreen.clear();
                        break;
                    }
                    else{
                        errorPositionSigInClubAdvisorScreen.setText("");
                        Club.addRequest(requestID,clubID,teacherID,studentIDSigInClubAdvisorScreen.getText(),positionSigInClubAdvisorScreen.getText());
                        messageLabel.setText("REQUEST SENT");
                        messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
                        messageLabel.setOpacity(1.0);
                        dashboardButtonClubAdvisorSignin.setDisable(false);
                        dashboardButtonClubAdvisorSignin.setOpacity(1.0);
                        dashboardLabelClubAdvisorSignin.setOpacity(1.0);
                        requestClubAdvisorRoleButtonSignInTwo.setDisable(true);
                        studentIDSigInClubAdvisorScreen.clear();//all the text fields will be cleared if the user inputs all valid details so the user can enter new details if he wishes
                        positionSigInClubAdvisorScreen.clear();
                        clubIDSigInClubAdvisorScreen.clear();
                        break;
                    }
                }
            }
        }if (!clubFound){
            messageLabel.setText("CLUB DOESNT EXIST");
            messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
            messageLabel.setOpacity(1.0);
        }if (!studentFound){
            messageLabel.setText("Student Doesnt exist".toUpperCase());
            messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
            messageLabel.setOpacity(1.0);
        }
    }
    public void onStudentSignInTwoButtonClicked(ActionEvent event) throws IOException, InterruptedException {
        messageLabel.setOpacity(0.0);
        boolean firstNameValid;
        boolean lastNameValid;
        boolean DOBValid;
        boolean classValid;
        boolean emailValid;
        boolean contactNoValid;
        boolean passwordValid;
        boolean studentIDValid;
        firstNameValid = checkName(firstNameSignInStudentInput, errorFirstNameSignInStudentInput);//validates the first and last names
        lastNameValid = checkName(lastNameSignInStudentInput, errorLastNameSignInStudentInput);
        for (Student student : registeredStudents) {
            if (student.getFirstName().equals(firstNameSignInStudentInput.getText()) && student.getLastName().equals(lastNameSignInStudentInput.getText())) {//checks if the student already exist in the database
                errorFirstNameSignInStudentInput.setText("Invalid name");
                errorLastNameSignInStudentInput.setText("Invalid name");
                firstNameSignInStudentInput.clear();
                lastNameSignInStudentInput.clear();
                firstNameValid = false;
                lastNameValid = false;
                break;
            }
        }
        DOBValid = checkDate(dateSignInStudentInput, errorDateSignInStudentInput);//checks DOB, class, email, contact number, password and id are valid
        classValid = checkClass(classSignInStudentInput, errorClassSignInStudentInput);
        emailValid = checkEmail(emailSignInStudentInput, errorEmailSignInStudentInput);
        contactNoValid = checkContactNo(contactNoSignInStudentInput, errorContactNoSignInStudentInput);
        passwordValid = checkPassword(passwordSignInStudentInput, errorPasswordSignInStudentInput);
        studentIDValid = checkID(studentIDSignInStudentInput, errorStudentIDSignInStudentInput);
        if (studentIDValid) {
            if (!sessionUser.equals("Student")) {//checks whether the id starts with an "s" indicating student
                messageLabel.setText("Duplicate record exists".toUpperCase());
                messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                messageLabel.setOpacity(1.0);
                errorStudentIDSignInStudentInput.setText("Invalid ID");
                studentIDSignInStudentInput.clear();
                studentIDValid = false;
            }
            String newStudentID = studentIDSignInStudentInput.getText();
            for (Student student:registeredStudents){
                if (student.getStudentID().equals(newStudentID)){//To check whether the user has entered a stuedent id which is there in the database
                    messageLabel.setText("Duplicate record exists".toUpperCase());
                    messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                    messageLabel.setOpacity(1.0);
                    errorStudentIDSignInStudentInput.setText("Invalid ID");
                    studentIDSignInStudentInput.clear();
                    studentIDValid=false;
                    break;
                }
            }
        }
        if (firstNameValid && lastNameValid && DOBValid && classValid && emailValid && contactNoValid && passwordValid && studentIDValid) {//if the above inputs done by the user is valid the data will be stored
            Student student = new Student(firstNameSignInStudentInput.getText(),lastNameSignInStudentInput.getText(),emailSignInStudentInput.getText(), passwordSignInStudentInput.getText(), dateSignInStudentInput.getText(), contactNoSignInStudentInput.getText(), studentIDSignInStudentInput.getText(),classSignInStudentInput.getText());
            student.insertToDatabase();//creates a student object and then inserts into the database, and redirects to the home screen
            messageLabel.setText(student.greetUser().toUpperCase());
            messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
            messageLabel.setOpacity(1.0);

            onDashboardStudentsAndTeachersScreenButtonClicked(event);
        }
    }

    public void onTeacherSignInTwoButtonClicked(ActionEvent event) throws IOException, SQLException, InterruptedException {
        messageLabel.setOpacity(0.0);
        boolean firstNameValid;
        boolean lastNameValid;
        boolean DOBValid;
        boolean contactNoValid;
        boolean emailValid;
        boolean teacherIDValid;
        boolean passwordValid;
        firstNameValid = checkName(firstNameSignInTeacherInput, errorFirstNameSignInTeacherInput);//first name and last name are validated
        lastNameValid = checkName(lastNameSignInTeacherInput, errorLastNameSignInTeacherInput);
        for (Teacher teacher : registeredTeachers) {
            if (teacher.getFirstName().equals(firstNameSignInTeacherInput.getText()) && teacher.getLastName().equals(lastNameSignInTeacherInput.getText())) {//checks whether this teacher already exists in the database
                messageLabel.setText("Duplicate record exists".toUpperCase());
                messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                messageLabel.setOpacity(1.0);
                errorFirstNameSignInTeacherInput.setText("Invalid name");
                errorLastNameSignInTeacherInput.setText("Invalid name");
                firstNameSignInTeacherInput.clear();
                lastNameSignInTeacherInput.clear();
                firstNameValid = false;
                lastNameValid = false;
                break;
            }
        }
        DOBValid = checkDate(dateSignInTeacherInput, errorDateSignInTeacherInput);//DOB, contact number, email, id are validated
        contactNoValid = checkContactNo(contactNoSignInTeacherInput, errorContactNoSignInTeacherInput);
        emailValid = checkEmail(emailSignInTeacherInput, errorEmailSignInTeacherInput);
        teacherIDValid = checkID(teacherIDSignInTeacherInput, errorTeacherIDSignInTeacherInput);
        if (teacherIDValid) {
            if (!sessionUser.equals("Teacher")) {//checks whether the id starts with a "T" or not
                errorTeacherIDSignInTeacherInput.setText("Invalid ID");
                teacherIDSignInTeacherInput.clear();
                teacherIDValid = false;
            }
            String newTeacherID = teacherIDSignInTeacherInput.getText();
            for (Teacher teacher:registeredTeachers){
                if (teacher.getStaffID().equals(newTeacherID)){//checks for any duplicate record
                    messageLabel.setText("Duplicate record exists".toUpperCase());
                    messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                    messageLabel.setOpacity(1.0);
                    errorTeacherIDSignInTeacherInput.setText("Invalid ID");
                    teacherIDSignInTeacherInput.clear();
                    teacherIDValid=false;
                    break;
                }
            }
        }
        passwordValid = checkPassword(passwordSignInTeacherInput, errorPasswordSignInTeacherInput);//password is validated
        if (firstNameValid && lastNameValid && DOBValid && contactNoValid && emailValid && teacherIDValid && passwordValid) {//if the above inputs done by the user is valid the data will be stored
            Teacher teacher = new Teacher(firstNameSignInTeacherInput.getText(),lastNameSignInTeacherInput.getText(),emailSignInTeacherInput.getText(),passwordSignInTeacherInput.getText(),dateSignInTeacherInput.getText(),contactNoSignInTeacherInput.getText(),teacherIDSignInTeacherInput.getText());
            teacher.insertToDatabase();//creates a teacher object and then inserts into the database, and redirects to the home screen
            messageLabel.setText(teacher.greetUser().toUpperCase());
            messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
            messageLabel.setOpacity(1.0);

            onDashboardStudentsAndTeachersScreenButtonClicked(event);
        }
    }
    //1.2 mapping from sequence diagram
    public void onLogInButtonClicked(ActionEvent event) throws IOException, InterruptedException {
        errorPasswordLoginInput.setText("");
        errorIDLoginInput.setText("");
        messageLabel.setOpacity(0.0);
        boolean IDValid;
        boolean passwordValid;
        String idInput;
        String passwordInput;
        IDValid = checkID(IDLoginInput, errorIDLoginInput);//id is validated
        if (IDValid) {
            if (sessionUser.equals("Club") || sessionUser.equals("Event")) {//To check whether the user has entered a club/ event ID instead of student/ ClubAdvisor or teacher ID
                errorIDLoginInput.setText("Invalid ID");
                IDLoginInput.clear();
                IDValid = false;
            }
        }
        passwordValid = checkPassword(passwordLoginInput, errorPasswordLoginInput);//password is validated
        if (IDValid && passwordValid) {
            boolean IDFound = false;
            boolean empty = true;
            idInput = IDLoginInput.getText().toString();
            passwordInput = passwordLoginInput.getText().toString();
            //read from the database for exsisting records
            if (sessionUser.equals("Student")){//checks if the user is a student
                if (registeredStudents.size()>0) {
                    empty = false;
                    for (Student student : registeredStudents) {
                        if (student.getStudentID().equals(idInput)) {//checks if the id and password is correct or not
                            IDFound = true;
                            if (student.getPassword().equals(passwordInput)) {
                                onDashboardStudentsAndTeachersScreenButtonClicked(event);
                                Controller controller = Controller.getInstance(); // Get the singleton instance
                                controller.setSessionID(idInput);
                                break;
                            }
                        }
                    }
                }
            }
            else if (sessionUser.equals("Teacher")) {//checks if the user is a teacher
                if (registeredTeachers.size()>0) {
                    empty = false;
                    for (Teacher teacher : registeredTeachers) {
                        if (teacher.getStaffID().equals(idInput)) {
                            IDFound = true;
                            if (teacher.getPassword().equals(passwordInput)) {//checks if the id and password is correct or not
                                Controller controller = Controller.getInstance(); // Get the singleton instance
                                controller.setSessionID(idInput);
                                onTeacherPopUScreenButtonClicked(event);
                                break;
                            }
                        }
                    }
                }
            }
            else if (sessionUser.equals("ClubAdvisor")){//checks if the user is a club advisor
                if (registeredClubAdvisors.size()>0){
                    empty = false;
                    for (ClubAdvisor clubAdvisor:registeredClubAdvisors) {
                        if (clubAdvisor.getClubAdvisorID().equals(idInput)){//checks if the id and password is correct or not
                            String studentId=clubAdvisor.getStudentID();
                            for (Student student:registeredStudents) {
                                if (student.getStudentID().equals(studentId)) {
                                    IDFound = true;
                                    if (student.getPassword().equals(passwordInput)) {
                                        onDashboardScreenButtonClicked(event);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (empty) {
                errorIDLoginInput.setText("Please sign in");
                errorPasswordLoginInput.setText("");
                IDLoginInput.clear();
                passwordLoginInput.clear();
            }
            else if (!IDFound){
                messageLabel.setText("Not available".toUpperCase());
                messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                messageLabel.setOpacity(1.0);
                IDLoginInput.clear();
                passwordLoginInput.clear();
                errorIDLoginInput.setText("");
                errorPasswordLoginInput.setText("");
            } else {
                errorIDLoginInput.setText("");
                errorPasswordLoginInput.setText("Incorrect password");
                passwordLoginInput.clear();
            }
        }
    }
    public void onTeacherPopUpCloseButtonClicked(ActionEvent event) throws IOException { onDashboardStudentsAndTeachersScreenButtonClicked(event);}
    public void onRefreshTeacherScreenButtonClicked(ActionEvent event) throws IOException {
        refreshButtonTeacherPopUp.setOpacity(0.00);
        refreshButtonTeacherPopUp.setDisable(true);
        Controller controller = Controller.getInstance(); // Get the singleton instance
        String loggedInTeacherID = controller.getSessionID();
        boolean haveNotification=false;
        ArrayList<String[]> requests = Club.loadRequestsOfClub(loggedInTeacherID);
        if (!requests.isEmpty()){
            haveNotification=true;
        }

        if (!haveNotification){
            approveButtonTeacherScreen.setOpacity(0.00);
            approveButtonTeacherScreen.setDisable(true);
            rejectButtonTeacherScreen.setOpacity(0.00);
            rejectButtonTeacherScreen.setDisable(true);
            messageTeacherPopUpScreen.setText("No notifications");
            studentNameTeacherPopUpScreen.setText("");
            clubNameTeacherPopUpScreen.setText("");
        }else{
            String[] request = requests.get(0);
            String studentID = request[3];
            String studentName = "";
            String clubID = request[1];
            String clubName="";
            for (Student student: registeredStudents) {
                if (student.getStudentID().equals(studentID)){
                    studentName = student.getFirstName()+" "+student.getLastName();
                }
            }
            for (Club club: registeredClubs) {
                if (club.getClubID().equals(clubID)){
                    clubName = club.getClubName();
                }
            }
            studentNameTeacherPopUpScreen.setText(studentName);
            clubNameTeacherPopUpScreen.setText(clubName);
            studentNameTeacherPopUpScreen.setStyle("-fx-alignment: center");
            clubNameTeacherPopUpScreen.setStyle("-fx-alignment: center");
        }
    }
    public void onApproveButtonTeacherScreenClicked(ActionEvent event) throws IOException {
        Controller controller = Controller.getInstance(); // Get the singleton instance
        String loggedInTeacherID = controller.getSessionID();
        ArrayList<String[]> requests = Club.loadRequestsOfClub(loggedInTeacherID);
        String[] request = requests.get(0);
        ArrayList<String> exisitngClubAdvisorIDs = new ArrayList<String>();
        for (ClubAdvisor clubAdvisor :registeredClubAdvisors){//checks the database and adds all the club advisor id's into the array list
            exisitngClubAdvisorIDs.add(clubAdvisor.getClubAdvisorID());
        }
        String newClubAdvisorID;

        do {
            newClubAdvisorID = ClubAdvisor.generateClubAdvisorID();
        } while (exisitngClubAdvisorIDs.contains(newClubAdvisorID));
        ClubAdvisor clubAdvisor = new ClubAdvisor(newClubAdvisorID,request[3],request[1],request[4]);
        clubAdvisor.insertIntoClubAdvisorTable();//if the teacher approves a new club advisor object is created and added to the database

        Club.deleteRequest(request[0]);
        approveButtonTeacherScreen.setOpacity(0.00);
        approveButtonTeacherScreen.setDisable(true);
        rejectButtonTeacherScreen.setOpacity(0.00);
        rejectButtonTeacherScreen.setDisable(true);
        messageTeacherPopUpScreen.setText("No notifications");
        studentNameTeacherPopUpScreen.setText("");
        clubNameTeacherPopUpScreen.setText("");
        onTeacherPopUpCloseButtonClicked(event);
    }
    public void onRejectButtonTeacherScreenClicked(ActionEvent event) throws IOException {
        Controller controller = Controller.getInstance();
        String loggedInTeacherID = controller.getSessionID();
        ArrayList<String[]> requests = Club.loadRequestsOfClub(loggedInTeacherID);
        String[] request = requests.get(0);

        Club.deleteRequest(request[0]);
        onTeacherPopUpCloseButtonClicked(event);
    }
    //Clubs View Methods
    public void onJoinClubsViewOptionClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        errorStudentIdInputClubsScreen.setText("");
        errorJoinClubsLabel.setText("");
        joinClubsPane.setOpacity(1.0);
        leaveClubsPane.setOpacity(0.0);
    }
    public void onLeaveClubsViewOptionClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        errorStudentIdInputClubsScreen.setText("");
        errorleaveClubsLabel1.setText("");
        joinClubsPane.setOpacity(0.0);
        leaveClubsPane.setOpacity(1.0);
    }
    public void onJoinClubClicked(ActionEvent event) throws IOException, InterruptedException {
        messageLabel.setOpacity(0.0);
        String studentID = studentIdInputClubsScreen.getText();
        String clubID = clubIDInputJoinClubsStudetnsAndTeachers.getText();
        boolean studentIDValid;
        boolean clubIDValid;
        studentIDValid = checkID(studentIdInputClubsScreen, errorStudentIdInputClubsScreen);//id is checked whether valid or not
        if (studentIDValid) {
            if (!sessionUser.equals("Student")) {//To check whether the user has entered a student ID
                studentIDValid = false;
            }
            for (Student student : registeredStudents) {
                if (student.getStudentID().equals(studentID)) {//checks whether the entered student id is duplicated
                    studentIDValid = true;
                    break;
                } else {
                    studentIDValid = false;
                }
            }
            if (!studentIDValid) {
                errorStudentIdInputClubsScreen.setText("Invalid ID");
                studentIdInputClubsScreen.clear();
            }
        }
        clubIDValid = checkID(clubIDInputJoinClubsStudetnsAndTeachers, errorJoinClubsLabel);//id is checked whether valid or not
        if (clubIDValid) {
            for (Club club : registeredClubs) {
                if (club.getClubID().equals(clubID)) {//checks if the club id exists in the database
                    clubIDValid = true;
                    break;
                } else {
                    clubIDValid = false;
                }
            }
            if (!clubIDValid) {
                errorJoinClubsLabel.setText("Invalid ID");
                clubIDInputJoinClubsStudetnsAndTeachers.clear();
            }
        }
        if (studentIDValid && clubIDValid) {
            boolean studentAvailable = false;
            for (Club club : registeredClubs) {
                if (club.getClubID().equals(clubID)) {
                    ArrayList<Student> availableStudentsAtClub = club.loadStudentsOfClub(clubID);//returns the list of students in that club
                    for (Student student : availableStudentsAtClub) {//checks in that list if the student is available then we can say they are already in it
                        if (student.getStudentID().equals(studentID)) {
                            studentAvailable = true;
                            messageLabel.setText("Already Joined".toUpperCase());
                            messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                            messageLabel.setOpacity(1.0);
                            studentIdInputClubsScreen.clear();
                            clubIDInputJoinClubsStudetnsAndTeachers.clear();
                            break; // No need to continue checking
                        }
                    }

                    if (!studentAvailable) {
                        for (Student student : registeredStudents) {
                            if (student.getStudentID().equals(studentID)) {//else we get their records and then add it to the club Class
                                club.addStudent(student);
                                messageLabel.setText("Joined Successfully".toUpperCase());
                                messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
                                messageLabel.setOpacity(1.0);
                                studentIdInputClubsScreen.clear();
                                clubIDInputJoinClubsStudetnsAndTeachers.clear();
                                break; // No need to continue checking
                            }
                        }
                    }
                    // No need to check other clubs once a match is found
                    break;
                }
            }
        }
    }
    public void onLeaveClubClicked(ActionEvent event) throws IOException, InterruptedException {
        messageLabel.setOpacity(0.0);
        String studentID = studentIdInputClubsScreen.getText();
        String clubID = clubIDInputLeaveClubsStudetnsAndTeachers.getText();
        boolean studentIDValid;
        boolean clubIDValid;

        studentIDValid = checkID(studentIdInputClubsScreen, errorStudentIdInputClubsScreen);//validats the id
        if (studentIDValid) {
            if (!sessionUser.equals("Student")) {//To check whether the user has entered a student ID
                studentIDValid = false;
            }
            for (Student student : registeredStudents) {
                if (student.getStudentID().equals(studentID)) {//checks whether the id is duplicated
                    studentIDValid = true;
                    break;
                } else {
                    studentIDValid = false;
                }
            }
            if (!studentIDValid) {
                errorStudentIdInputClubsScreen.setText("Invalid ID");
                studentIdInputClubsScreen.clear();
            }
        }
        clubIDValid = checkID(clubIDInputLeaveClubsStudetnsAndTeachers, errorleaveClubsLabel1);//checks if the id is valid
        if (clubIDValid) {
            for (Club club : registeredClubs) {
                if (club.getClubID().equals(clubID)) {//checks if the club exists in the database
                    clubIDValid = true;
                    break;
                } else {
                    clubIDValid = false;
                }
            }
            if (!clubIDValid) {
                errorleaveClubsLabel1.setText("Invalid ID");
                clubIDInputLeaveClubsStudetnsAndTeachers.clear();
            }
        }
        if (studentIDValid && clubIDValid) {
            registeredStudents = Student.loadStudentsFromDatabase();
            boolean studentAvailable = false;
            for (Club club : registeredClubs) {// if leave then the opposite of join
                if (club.getClubID().equals(clubID)) {
                    for (Student student : club.loadStudentsOfClub(clubID)) {//checks the id against the id's which already exists in the club
                        if (student.getStudentID().equals(studentID)) {
                            club.removeStudent(student);
                            messageLabel.setText("Left the club".toUpperCase());
                            messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
                            messageLabel.setOpacity(1.0);
                            studentIdInputClubsScreen.clear();
                            clubIDInputLeaveClubsStudetnsAndTeachers.clear();
                            studentAvailable = true;
                            break;
                        }
                    }
                    if (!studentAvailable) {
                        messageLabel.setText("Please be a member inorder to leave".toUpperCase());
                        messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                        messageLabel.setOpacity(1.0);
                        studentIdInputClubsScreen.clear();
                        clubIDInputLeaveClubsStudetnsAndTeachers.clear();
                        break;
                    }
                }
            }
        }
    }
    public void onRefreshClubsViewButtonClicked(ActionEvent event) throws IOException {
        refreshClubsViewButton.setDisable(true);
        refreshClubsViewButton.setOpacity(0.0);
        ObservableList<Club> registeredClubsToTable = FXCollections.observableArrayList(registeredClubs);
        // Clear existing columns
        clubsViewTable.getColumns().clear();
        // Bind the columns to the corresponding properties of the Club class
        clubIDColumnClubTable.setCellValueFactory(new PropertyValueFactory<>("clubID"));
        clubNameColumnClubTable.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        clubDescriptionColumnClubTable.setCellValueFactory(new PropertyValueFactory<>("clubDescription"));
        teacherInchargeColumnClubTable.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        // Add columns to TableView
        clubsViewTable.getColumns().addAll(clubIDColumnClubTable, clubNameColumnClubTable, clubDescriptionColumnClubTable, teacherInchargeColumnClubTable);
        // Populate TableView with data
        clubsViewTable.setItems(registeredClubsToTable);
    }
    public void onHideClubsViewStudentsAndTeachersOptionClicked(ActionEvent event) throws IOException {
        joinClubsPane.setOpacity(0.00);
        leaveClubsPane.setOpacity(0.00);
    }
    //Events View Methods Students and Teachers
    public void onCheckInEventsViewOptionClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        errorStudentIDEventsLabel.setText("");
        errorCheckInEventsLabel.setText("");
        checkInEventsPane.setOpacity(1.0);
        checkOutEventsPane.setOpacity(0.0);
    }
    public void onCheckOutEventsViewOptionClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        errorStudentIDEventsLabel.setText("");
        errorCheckOutEventsLabel.setText("");
        checkOutEventsPane.setOpacity(1.0);
        checkInEventsPane.setOpacity(0.0);
    }
    public void onCheckInEventClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        String studentID=studentIdInputEventsScreen.getText();
        String eventID = eventIDCheckIn.getText();
        boolean studentIDValid;
        boolean eventIDValid;
        studentIDValid = checkID(studentIdInputEventsScreen, errorStudentIDEventsLabel);//validates the id
        if (studentIDValid) {
            if (!sessionUser.equals("Student")) {//To check whether the user has entered a student ID
                studentIDValid = false;
            }
            for (Student student : registeredStudents) {
                if (student.getStudentID().equals(studentID)) {//checks if the student exists in the database
                    studentIDValid = true;
                    break;
                } else {
                    studentIDValid = false;
                }
            }
            if (!studentIDValid) {
                errorStudentIDEventsLabel.setText("Invalid ID");
                studentIdInputEventsScreen.clear();
            }
        }
        eventIDValid = checkID(eventIDCheckIn, errorCheckInEventsLabel);//validates the id
        if (eventIDValid) {
            for (Event event1 : registeredEvents) {
                if (event1.getEventID().equals(eventID)) {//checks if the event exists
                    eventIDValid = true;
                    break;
                } else {
                    eventIDValid = false;
                }
            }
            if (!eventIDValid) {
                errorCheckInEventsLabel.setText("Invalid ID");
                eventIDCheckIn.clear();
            }
        }
        if (studentIDValid && eventIDValid) {
            boolean studentAvailable = false;
            for (Event eventInformation : registeredEvents) {
                if (eventInformation.getEventID().equals(eventID)) {
                    ArrayList<Student> availableStudentsAtEvent = eventInformation.loadStudentsOfEvent(eventID);//returns the list of students in that event
                    for (Student student : availableStudentsAtEvent) {//checks in that list if the student is available then we can say they are already in it
                        if (student.getStudentID().equals(studentID)) {
                            studentAvailable = true;
                            messageLabel.setText("Already Checked in".toUpperCase());
                            messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                            messageLabel.setOpacity(1.0);
                            studentIdInputEventsScreen.clear();
                            eventIDCheckIn.clear();
                            break; // No need to continue checking
                        }
                    }
                    if (!studentAvailable) {
                        for (Student student : registeredStudents) {
                            if (student.getStudentID().equals(studentID)) {//else we get their records and then add it to the club Class
                                eventInformation.addStudent(student);
                                messageLabel.setText("Checked in Successfully".toUpperCase());
                                messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
                                messageLabel.setOpacity(1.0);
                                studentIdInputEventsScreen.clear();
                                eventIDCheckIn.clear();
                                break; // No need to continue checking
                            }
                        }
                    }
                    // No need to check other clubs once a match is found
                    break;
                }
            }
        }
    }
    public void onCheckOutEventClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        String studentID=studentIdInputEventsScreen.getText();
        String eventID = eventIDCheckOut.getText();
        boolean studentIDValid;
        boolean eventIDValid;
        studentIDValid = checkID(studentIdInputEventsScreen, errorStudentIDEventsLabel);//id is validated

        if (studentIDValid) {
            if (!sessionUser.equals("Student")) {//To check whether the user has entered a student ID
                studentIDValid = false;
            }
            for (Student student : registeredStudents) {
                if (student.getStudentID().equals(studentID)) {//checks if the student exists
                    studentIDValid = true;
                    break;
                } else {
                    studentIDValid = false;
                }
            }
            if (!studentIDValid) {
                errorStudentIDEventsLabel.setText("Invalid ID");
                studentIdInputEventsScreen.clear();
            }
        }
        eventIDValid = checkID(eventIDCheckOut, errorCheckOutEventsLabel);//id is validated
        if (eventIDValid) {
            for (Event event1 : registeredEvents) {
                if (event1.getEventID().equals(eventID)) {//checks if the event exists
                    eventIDValid = true;
                    break;
                } else {
                    eventIDValid = false;
                }
            }
            if (!eventIDValid) {
                errorCheckOutEventsLabel.setText("Invalid ID");
                eventIDCheckOut.clear();
            }
        }
        if (studentIDValid && eventIDValid) {
            boolean studentAvailable = false;
            for (Event eventInformation : registeredEvents) {// if leave then the opposite of join
                if (eventInformation.getEventID().equals(eventID)) {
                    for (Student student : eventInformation.loadStudentsOfEvent(eventID)) {//returns the list of students in the events
                        if (student.getStudentID().equals(studentID)) {//checks in that list if the student is available then student is removed from the event
                            eventInformation.removeStudent(student);
                            messageLabel.setText("Successfully Checked Out from event".toUpperCase());
                            messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
                            messageLabel.setOpacity(1.0);
                            studentIdInputEventsScreen.clear();
                            eventIDCheckOut.clear();
                            studentAvailable = true;
                            break;
                        }
                    }
                    if (!studentAvailable) {
                        messageLabel.setText("You have not checked in".toUpperCase());
                        messageLabel.setStyle("-fx-background-color: #ff7f7f;-fx-background-radius: 10;-fx-alignment: center");
                        messageLabel.setOpacity(1.0);
                        studentIdInputEventsScreen.clear();
                        eventIDCheckOut.clear();
                        break;
                    }
                }
            }
        }
    }
    public void onRefreshEventsViewButtonClicked(ActionEvent event) throws IOException {
        refreshEventsViewButton.setDisable(true);
        refreshEventsViewButton.setOpacity(0.0);
        ObservableList<Event> registeredEventsToTable = FXCollections.observableArrayList(registeredEvents);
        // Clear existing columns
        eventsViewTable.getColumns().clear();
        eventIDColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("eventTime"));
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
        eventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        eventsViewTable.getColumns().addAll(eventIDColumn, eventNameColumn, eventDateColumn, eventTimeColumn, eventLocationColumn, eventDescriptionColumn);
        eventsViewTable.setItems(registeredEventsToTable);
    }
    public void onHideEventsViewStudentsAndTeachersOptionClicked(ActionEvent event) throws IOException {
        checkInEventsPane.setOpacity(0.00);
        checkOutEventsPane.setOpacity(0.00);
    }
    public void onDeleteEventsViewOptionClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        errorClubAdvisorIdInputEventsLabel.setText("");
        errorEventIDDelete.setText("");
        deleteEventsPane.setOpacity(1.0);
    }

    public void onDeleteButtonClickedEventsScreen(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        boolean clubAdvisorIDValid;
        boolean eventIDValid;
        clubAdvisorIDValid = checkID(clubAdvisorIdInputEventsScreen, errorClubAdvisorIdInputEventsLabel);//club advisor id is validated
        eventIDValid = checkID(eventIDDelete, errorEventIDDelete);//event id is validated
        if (clubAdvisorIDValid && eventIDValid) {
            boolean clubAdvisorFound = false;
            boolean eventsFound = false;
            String clubAdvisorID = clubAdvisorIdInputEventsScreen.getText();
            String eventsID = eventIDDelete.getText();
            for (Event event_1 : registeredEvents) {
                if (event_1.getEventID().equals(eventsID)) {//checks if the event is existing
                    eventsFound=true;
                    event_1.setClub();
                    ArrayList<ClubAdvisor> clubAdvisors = event_1.getClub().loadClubAdvisorsOfClub(event_1.getClubID());//loads the club advisor information of this specific club
                    ArrayList<String> clubAdvisorsIds = new ArrayList<>();
                    for (ClubAdvisor clubAdvisor:clubAdvisors){//adds all the club advisors ids in a separate array list
                        clubAdvisorsIds.add(clubAdvisor.getClubAdvisorID());
                    }
                    if (clubAdvisorsIds.contains(clubAdvisorID)){//checks whether the id is same as the one which the user has entered
                        clubAdvisorFound=true;
                    }
                    if (eventsFound  && clubAdvisorFound) {//if the event and club advisor is found the event is deleted
                        event_1.deleteEvent(eventsID);
                        errorClubAdvisorIdInputEventsLabel.setText("");
                        messageLabel.setText("event deleted successfully".toUpperCase());
                        messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
                        messageLabel.setOpacity(1.0);
                        eventIDDelete.clear();
                        clubAdvisorIdInputEventsScreen.clear();
                        refreshEventsViewButton.setDisable(false);
                        refreshEventsViewButton.setOpacity(1.0);
                        break;
                    }
                }
            }
            if (!eventsFound){
                errorEventIDDelete.setText("Event Not Available");
                eventIDDelete.clear();
            }
            if (!clubAdvisorFound){
                errorClubAdvisorIdInputEventsLabel.setText("Not part of this club");
                clubAdvisorIdInputEventsScreen.clear();
            }
        }
    }
    //clubs View Methods Club Advisors
    public void onDeleteClubsViewOptionClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        errorClubAdvisorIDInputClubsScreen.setText("");
        errorDeleteClubsLabel.setText("");
        deleteClubsPane.setOpacity(1.0);
    }

    public void onDeleteButtonClickedClubsScreen(ActionEvent event) throws IOException, InterruptedException {
        messageLabel.setOpacity(0.0);
        boolean clubAdvisorIDValid;//check if the CA and the C exists
        boolean clubIDValid;
        boolean clubFound=false;//check if the club is found
        boolean clubAdvisorFound=false;//check if the club is found
        clubAdvisorIDValid = checkID(clubAdvisorIDInputClubsScreen, errorClubAdvisorIDInputClubsScreen); // checks the ID whether its valid
        clubIDValid = checkID(clubIDDeleteInput, errorDeleteClubsLabel);//checks the id whether its valid
        if (clubAdvisorIDValid && clubIDValid) {
            String clubAdvisorID = clubAdvisorIDInputClubsScreen.getText();
            String clubID = clubIDDeleteInput.getText();
            for (ClubAdvisor clubAdvisor : registeredClubAdvisors) {//
                if (clubAdvisor.getClubAdvisorID().equals(clubAdvisorID) && clubAdvisor.getClubID().equals(clubID)) {//checks whether the club advisor is the club advisor of that specific club
                    clubAdvisorFound=true;
                    break;
                }
            }

            for (Club club : registeredClubs) {//checks if the club is there in the database
                if (club.getClubID().equals(clubID)) {
                    clubFound=true;
                    if (clubFound  && clubAdvisorFound) {//if both are true then the club is deleted
                        club.deleteClub(clubID);
                        messageLabel.setText("club deleted successfully".toUpperCase());
                        messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
                        messageLabel.setOpacity(1.0);
                        clubIDDeleteInput.clear();
                        clubAdvisorIDInputClubsScreen.clear();
                        refreshClubsViewButton.setDisable(false);
                        refreshClubsViewButton.setOpacity(1.0);
                        break;
                    }
                }
            }
            if (!clubFound){
                errorDeleteClubsLabel.setText("Club Not Available");
                clubIDDeleteInput.clear();
            }
            if (!clubAdvisorFound){
                errorClubAdvisorIDInputClubsScreen.setText("Incorrect club advisor");
                clubAdvisorIDInputClubsScreen.clear();
            }
        }
    }


    public void onUpdateEventButtonClicked(ActionEvent event) throws IOException {
        messageLabel.setOpacity(0.0);
        boolean eventNameValid;
        boolean eventDateValid;
        boolean eventTimeValid;
        boolean eventIDValid;
        boolean clubIDValid;
        boolean eventDescriptionValid;
        boolean eventLocationValid;
        if (eventNameEditEventInput.getText().equals(null) || eventNameEditEventInput.getText().equals("")  ){//checks if the event name is empty
            eventNameValid=false;
            eventNameEditEventInput.clear();
            errorEventNameEditEventInput.setText("Cannot be empty");
        }else{
            eventNameValid= true;
            errorEventNameEditEventInput.setText("");
        }
        if (locationEventEditInput.getText().equals(null) || locationEventEditInput.getText().equals("")  ){//checks if the location is empty
            eventLocationValid=false;
            locationEventEditInput.clear();
            errorLocationEventEditInput.setText("Cannot be empty");
        }else{
            eventLocationValid= true;
            errorLocationEventEditInput.setText("");
        }
        eventDateValid = checkDate(eventDateEditEventInput, errorEventDateEditEventInput);//date is validated
        eventTimeValid = checkTime(eventTimeEditEventInput, errorEventTimeEditEventInput);//time is validated
        eventIDValid = checkID(eventIDEditEventInput, errorEventIDEditEventInput);//id is validated
        if (eventIDValid) {
            ArrayList<String> registeredEventsID = new ArrayList<>();
            for (Event event_1 : registeredEvents) {
                registeredEventsID.add(event_1.getEventID());
            }
            if (!registeredEventsID.contains(eventIDEditEventInput.getText())) {//checks if the event is getting duplicated
                eventIDEditEventInput.clear();
                errorEventIDEditEventInput.setText("Invalid ID");
                eventIDValid = false;
            }
        }
        clubIDValid = checkID(clubIDEditEventInput, errorClubIDEditEventInput);//id is validated
        if (clubIDValid) {
            ArrayList<String> registeredClubsID = new ArrayList<>();
            for (Club club : registeredClubs) {
                registeredClubsID.add(club.getClubID());
            }
            if (!registeredClubsID.contains(clubIDEditEventInput.getText())) {//checks if the club is getting duplicated
                clubIDEditEventInput.clear();
                errorClubIDEditEventInput.setText("Invalid ID");
                clubIDValid = false;
            }
        }
        eventDescriptionValid = checkDescription(eventDescriptionEditEventInput, errorEventDescriptionEditEventInput);//description is validaeted
        if (eventNameValid && eventDateValid && eventTimeValid && clubIDValid && eventIDValid && eventDescriptionValid && eventLocationValid) {//if the above inputs done by the user is valid the data will be stored
            String eventIDInput=eventIDEditEventInput.getText();
            String eventNameInput=eventNameEditEventInput.getText();
            String eventDateInput=eventDateEditEventInput.getText();
            String eventTimeInput=eventTimeEditEventInput.getText();
            String eventClubIDInput=clubIDEditEventInput.getText();
            String eventLocationInput=locationEventEditInput.getText();
            String eventDescriptionInput=eventDescriptionEditEventInput.getText();
            for (Event existingEvent:registeredEvents){
                if (existingEvent.getEventID().equals(eventIDInput)){
                    existingEvent.setEventName(eventNameInput);
                    existingEvent.setEventDate(eventDateInput);
                    existingEvent.setEventTime(eventTimeInput);
                    existingEvent.setClubID(eventClubIDInput);
                    existingEvent.setEventLocation(eventLocationInput);
                    existingEvent.setEventDescription(eventDescriptionInput);//updates the event based on the new info
                    existingEvent.updateEvent();
                    messageLabel.setText("Event updated successfully".toUpperCase());
                    messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
                    messageLabel.setOpacity(1.0);
                    eventIDEditEventInput.clear();
                    eventNameEditEventInput.clear();
                    eventDateEditEventInput.clear();
                    eventTimeEditEventInput.clear();
                    clubIDEditEventInput.clear();
                    locationEventEditInput.clear();
                    eventDescriptionEditEventInput.clear();
                    break;
                }
            }
        }
    }

    //report View Methods
    public void onRefreshReportsViewButtonClicked(ActionEvent event) throws IOException {
        refreshReportViewButton.setDisable(true);
        refreshReportViewButton.setOpacity(0.0);
        reportsTable.getItems().clear();
        reportsTable.getColumns().clear();
        String query = "SELECT c.ClubName, COUNT(DISTINCT cm.StudentID) AS NumberOfClubMembers, COUNT(DISTINCT e.EventID) AS NumberOfEventsHeld, MAX(e.EventName) AS RecentEventName, COUNT(DISTINCT ea.StudentID) AS RecentEventAttendance FROM Club c LEFT JOIN ClubsMembership cm ON c.ClubID = cm.ClubID LEFT JOIN Events e ON c.ClubID = e.ClubID LEFT JOIN EventAttendance ea ON e.EventID = ea.EventID GROUP BY c.ClubID;";
        ArrayList<String[]> reportInformation = new ArrayList<>();// loads the information from the Database
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String[] row = new String[5];
                    row[0] = results.getString("ClubName");
                    row[1] = results.getString("NumberOfClubMembers");
                    row[2] = results.getString("NumberOfEventsHeld");
                    row[3] = results.getString("RecentEventName");
                    row[4] = results.getString("RecentEventAttendance");
                    reportInformation.add(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> data = FXCollections.observableArrayList();//the obsevable list is being crated
        for (String[] row : reportInformation) {//rows are being joined with the comma
            data.add(String.join(", ", row));
        }

        reportsTable.setItems(data);//sets the table with this data observalbe list

        clubNameReportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[0]));//each cell is going to store the values of a simple string Property and is considering only the 0 element
        NoOfClubMembersColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[1]));
        NoOfClubEventsHeldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[2]));
        recentEventNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[3]));
        recentEventAttendance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[4]));

        // Add columns to the TableView
        reportsTable.getColumns().addAll(clubNameReportColumn, NoOfClubMembersColumn, NoOfClubEventsHeldColumn, recentEventNameColumn, recentEventAttendance);


    }

    public void onDownloadReportClicked(ActionEvent event) {
        // Assuming you have already initialized registeredClubs and registeredEvents
        ArrayList<Club> existingClubs = registeredClubs;

        String reportContent="";
        for (Club club : existingClubs) {
            String clubName = club.getClubName();
            String clubReportStudentAttendance = club.displayReport(); // Assuming displayReport returns a string
            int clubEventCount = 0;
            for (Event existingEvent : registeredEvents) {
                if (existingEvent.getClubID().equals(club.getClubID())) {
                    clubEventCount++;
                }
            }

            ArrayList<String[]> result = new ArrayList<String[]>();

            try (Connection connection = Database.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.EventName, COUNT(ea.StudentID) AS AttendanceCount FROM Events e LEFT JOIN EventAttendance ea ON e.EventID = ea.EventID WHERE e.ClubID = ? GROUP BY e.EventID, e.EventName;")) {

                preparedStatement.setString(1, club.getClubID());

                try (ResultSet results = preparedStatement.executeQuery()) {
                    while (results.next()) {
                        String[] row = new String[2];
                        row[0] = results.getString("EventName");
                        row[1] = results.getString("AttendanceCount");
                        result.add(row);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            String noOfEventsHeld = "Total Number of events Held at : " + String.valueOf(clubEventCount);
            reportContent+=clubName + "\n" +noOfEventsHeld + "\n"+clubReportStudentAttendance+"\n";
            reportContent+="Attendance for each event"+"\n";
            for (String[] row :result){
                reportContent+="      "+row[0]+"- "+row[1]+"\n";
            }
            reportContent += "\n\n";

        }
        saveReportToFile(reportContent);
    }

    private void saveReportToFile( String content) {
        String fileName = "report.txt"; // Adjust the file name as needed
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            messageLabel.setText("REPORT SAVED AS  report.txt");
            messageLabel.setStyle("-fx-background-color: #a3d563;-fx-background-radius: 10;-fx-alignment: center");
            messageLabel.setOpacity(1.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onRefreshClubsInchargeListClicked(ActionEvent event) throws IOException {
        refreshClubsInchargeList.setOpacity(0.0);
        refreshClubsInchargeList.setDisable(true);

        clubAdvisorTable.getItems().clear();//Clears the existing values in the table view
        clubAdvisorTable.getColumns().clear();
        ArrayList<String[]> clubAdvisorInformation = ClubAdvisor.getClubAdvisorInformation();// loads the infor from the Database


        ObservableList<String> data = FXCollections.observableArrayList();//the obsevable list is being crated
        for (String[] row : clubAdvisorInformation) {//rows are being joined with the comma
            data.add(String.join(", ", row));
        }

        clubAdvisorTable.setItems(data);//sets the table with this data observalbe list

        clubAdvisorIDColumnClubAdvisorTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[0]));//each cell is going to store the values of a simple string Property and is considering only the 0 element
        clubNameColumnClubAdvisorTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[1]));
        studentIDColumnClubAdvisorTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[2]));
        nameColumnClubAdvisorTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[3]));
        contactNoColumnClubAdvisorTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[4]));
        positionColumnClubAdvisorTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[5]));
        emailColumnClubAdvisorTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(", ")[6]));//repeat for each column


        // Add columns to the TableView
        clubAdvisorTable.getColumns().addAll(clubAdvisorIDColumnClubAdvisorTable, clubNameColumnClubAdvisorTable, studentIDColumnClubAdvisorTable, nameColumnClubAdvisorTable, contactNoColumnClubAdvisorTable,positionColumnClubAdvisorTable,emailColumnClubAdvisorTable);
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
            if (contactNoCharacters.length == 9) {//checks whether the contact number has only 10 digits
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
        } else if (textField.getText().toCharArray().length <=4) {//checks if the ID input field is more than 5 digits
            label.setText("ID should contain at least 5 characters");//display a message to the user to re-enter
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
            textField.clear();//clears the text field
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
            textField.clear();//clears the text field
        }
        return passwordValid;
    }

    public boolean checkDate(TextField textField, Label label) {
        boolean dateValid = true;
        label.setText(""); // Make the error label invisible at the start of the validation
        String dateStr = textField.getText();
        String[] dateComponents = dateStr.split("-");
        if (dateStr.equals("")) { // Check if the date input field is blank
            label.setText("Cannot be empty"); // Displaying a message to the user to re-enter
            dateValid = false; // Set date validity to be false
            textField.clear(); // Clears the text field
        } else if (dateComponents.length == 3) {
            try {
                int year = Integer.parseInt(dateComponents[0]);
                int month = Integer.parseInt(dateComponents[1]);
                int day = Integer.parseInt(dateComponents[2]);
                // Check if the date is valid
                if (year < 1970 || year > 2030 || month < 1 || month > 12 || day < 1 || day > LocalDate.of(year, month, 1).lengthOfMonth()) {
                    label.setText("Invalid Date");
                    dateValid = false;
                    textField.clear();
                }
            } catch (NumberFormatException | DateTimeParseException e) {
                label.setText("Invalid date format");
                dateValid = false;
                textField.clear();
            }
        } else {
            label.setText("Invalid date format");
            dateValid = false;
            textField.clear();
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

    public boolean isStudentFirstNameValid(String firstName) {
        char[] ch = firstName.toCharArray();
        StringBuilder firstNameBuild = new StringBuilder();
        for (char c : ch) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        if (firstName.isEmpty()) {
            return false;
        }

        return firstName.matches("[a-zA-Z]+");
    }

    public boolean isStudentLastNameValid(String lastName) {
        char[] ch = lastName.toCharArray();
        StringBuilder lastNameBuild = new StringBuilder();
        for (char c : ch) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        if (lastName.isEmpty()) {
            return false;
        }

        return lastName.matches("[a-zA-Z]+");
    }

    public boolean checkStudentDOB(String dateOfBirth) {
        if (dateOfBirth.isEmpty()) {
            return false;
        }
        String[] dateComponents = dateOfBirth.split("-");
        int year = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int day = Integer.parseInt(dateComponents[2]);

        if (year < 1970 || year > 2030 || month < 1 || month > 12 || day < 1 || day > LocalDate.of(year, month, 1).lengthOfMonth()) {
            return false;
        }
        return true;
    }

    public boolean isTeacherEmailValid(String email) {
        if (email.isEmpty()) {
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            return false;
        }

        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public boolean isTeacherContactValid(String contactNumber) {
        char[] contactNoCharacters = contactNumber.toCharArray();
        if (contactNoCharacters.length == 9) {//checks whether the contact number has only 10 digits
            for (char character : contactNoCharacters) {//and by using an enhanced for loop the program checks whether a string character is present in the list of characters
                if (!Character.isDigit(character)) {
                    return false;
                }
            }
        }else{
            return false;
        }
        if (contactNumber.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isTeacherPasswordValid(String password) {
        if (password.isEmpty()){
            return false;
        }
        if (password.toCharArray().length < 8){
            return false;
        }
        return true;
    }

    public boolean isCreateClubAdvisorStudentIDValid(String studentID) {
        if (studentID.isEmpty()){
            return false;
        }
        if (studentID.toCharArray().length != 5){
            return false;
        }
        return true;
    }

}