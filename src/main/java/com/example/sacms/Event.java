package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Event {
    private String eventID;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private String eventDescription;
    private String clubID;
    private ArrayList<Student> students;

    public Event(String eventID, String eventName, String eventDate, String eventTime, String eventLocation, String clubID ,String eventDescription) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventLocation = eventLocation;
        this.clubID = clubID;
        this.eventDescription = eventDescription;
        this.students=loadStudentsOfEvent(getEventID());

    }

    public String getEventName() {
        return eventName;
    }
    public String getEventDate() {
        return eventDate;
    }
    public String getEventTime() {
        return eventTime;
    }
    public String getClubID() {
        return clubID;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public String getEventID() {
        return eventID;
    }
    public String getEventLocation() {
        return eventLocation;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    public void setClubID(String clubID) {
        this.clubID = clubID;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public static String generateEventID() {
        int idLength = 4;//Mmebreshoip ID of 10 digits
        StringBuilder stringBuilder = new StringBuilder("E");
        Random random = new Random();
        for (int i = 0; i < idLength; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
    public String generateAttendanceID() {

        int idLength = 9;//Mmebreshoip ID of 10 digits
        StringBuilder stringBuilder = new StringBuilder("A");
        Random random = new Random();
        for (int i = 0; i < idLength; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
    public static void createEventTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query = "CREATE TABLE IF NOT EXISTS Events (" +
                    "    EventID VARCHAR(5) PRIMARY KEY," +
                    "    EventName VARCHAR(255)," +
                    "    Date VARCHAR(10)," +
                    "    Time VARCHAR(10)," +
                    "    Location VARCHAR(25)," +
                    "    ClubID VARCHAR(5)," +
                    "    EventDescription VARCHAR(255)," +
                    "    FOREIGN KEY (ClubID) REFERENCES Club(ClubID)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createEventAttendanceTableOnDatabase() {//to store students and club ID
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE IF NOT EXISTS EventAttendance (" +
                    "    AttendanceID VARCHAR(10) PRIMARY KEY," +
                    "    EventID VARCHAR(5)," +
                    "    StudentID VARCHAR(5)," +
                    "    FOREIGN KEY (EventID) REFERENCES Events(EventID)," +
                    "    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finally its then executed on the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Event> loadEventsFromDatabase()  {//Load data from the student database
        createEventTableOnDatabase();
        ArrayList<Event> events = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events");//prepare the statement
             ResultSet results = preparedStatement.executeQuery()) {//we get it as a Result set

            while (results.next()) {// until the set of results are empty we populate the list of clubs and return it
                Event event = new Event(results.getString("EventID"),results.getString("EventName"),results.getString("Date"),results.getString("Time"),results.getString("Location"),results.getString("ClubID"),results.getString("EventDescription"));
                events.add(event);//loads all clubs to the ArrayList
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
    public void addStudent(Student student){
        students.add(student);//add the studetn to tthe list of students
        String attendanceID;
        do {
            attendanceID = generateAttendanceID();//generate a membership ID for each student
        } while (loadExsistingAttendanceID().contains(attendanceID));//until its unique we generate a new ID
        String insertClubMembershipQuery = "INSERT INTO EventAttendance VALUES (?, ?,?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertClubMembershipQuery)) {
            preparedStatement.setString(1, attendanceID);//inserts the Membership ID,student ID and the club ID to the table
            preparedStatement.setString(2, getEventID());
            preparedStatement.setString(3, student.getStudentID());
            preparedStatement.executeUpdate();//push

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void removeStudent(Student student){
        students.remove(student);//add the studetn to tthe list of students
        try (Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM EventAttendance WHERE StudentID = ? AND EventID = ?")) {// deketes the row where the student ID is equal to the on e entered

            preparedStatement.setString(1, student.getStudentID());
            preparedStatement.setString(2,getEventID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Student> loadStudentsOfEvent(String eventID){
        createEventAttendanceTableOnDatabase();//create teh membership table if not exsist
        ArrayList<Student> students = new ArrayList<>();//Loads all the students from the database who are the members of that club
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT Student.StudentID, Student.FirstName, Student.LastName, Student.Email, Student.DateOfBirth, Student.Password, Student.ContactNo, Student.Classroom " +
                             "FROM Student " +
                             "JOIN EventAttendance ON Student.StudentID = EventAttendance.StudentID " +
                             "WHERE EventAttendance.EventID = ?")) {

            preparedStatement.setString(1, eventID);// returns all the students for that specific club by joingin the studentID FK from the clubmembership table to the student Table

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    Student student = new Student(
                            results.getString("FirstName"),
                            results.getString("LastName"),
                            results.getString("Email"),
                            results.getString("Password"),
                            results.getString("DateOfBirth"),
                            results.getString("ContactNo"),
                            results.getString("StudentID"),
                            results.getString("Classroom")
                    );//finally creates an object of the student class
                    students.add(student);//adds to the students list
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    public ArrayList<String> loadExsistingAttendanceID(){
        ArrayList<String> existingAttendanceIDs = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM eventattendance");
             ResultSet results = preparedStatement.executeQuery()) {


            while (results.next()) {
                existingAttendanceIDs.add(results.getString("AttendanceID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existingAttendanceIDs;
    }

    public void deleteEvent(String eventID){
        String deleteEventAttendanceQuery = "Delete from eventattendance where eventattendance.eventid = ?;";
        String deleteEventQuery = "Delete from events where eventid = ?;";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedDeleteClubMembershipQueryStatement = connection.prepareStatement(deleteEventAttendanceQuery);
             PreparedStatement preparedDeleteClubQueryStatement = connection.prepareStatement(deleteEventQuery)) {

            connection.setAutoCommit(false);//pause autocommit as theres a squence of insturections to be followed

            preparedDeleteClubMembershipQueryStatement.setString(1,eventID);
            preparedDeleteClubMembershipQueryStatement.executeUpdate();

            preparedDeleteClubQueryStatement.setString(1,eventID);
            preparedDeleteClubQueryStatement.executeUpdate();// once its updated we then set commit all at once


            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateEvent(){
        String updateQuery = "UPDATE Events SET EventName = ?, Date = ?,Time = ?,Location = ?,ClubID=? ,EventDescription = ? WHERE EventID = ?;";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatementToEdit = connection.prepareStatement(updateQuery)) {

            connection.setAutoCommit(false);//pause autocommit as theres a squence of insturections to be followed

            preparedStatementToEdit.setString(1,getEventName());
            preparedStatementToEdit.setString(2,getEventDate());
            preparedStatementToEdit.setString(3,getEventTime());
            preparedStatementToEdit.setString(4,getEventLocation());
            preparedStatementToEdit.setString(5,getClubID());
            preparedStatementToEdit.setString(6,getEventDescription());
            preparedStatementToEdit.setString(7,getEventID());
            preparedStatementToEdit.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
