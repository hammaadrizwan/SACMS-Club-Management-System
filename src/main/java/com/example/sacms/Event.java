package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Event {
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String clubID;
    private String eventDescription;
    private String eventID;
    private String eventLocation;
    private int eventAttendance;
    private ArrayList<Student> eventAttendeesID;

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
    public int getEventAttendance() {
        return eventAttendance;
    }
    //public ArrayList<String> getEventAttendeesID() {
    //    return eventAttendeesID;
    //}
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
    public void setEventAttendance(int eventAttendance) {
        this.eventAttendance = eventAttendance;
    }
    //public void setEventAttendeesID(ArrayList<String> eventAttendeesID) {
    //    this.eventAttendeesID = eventAttendeesID;
    //}
    public String generateEventID() {
        // gotto complete this
        return "";
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
}
