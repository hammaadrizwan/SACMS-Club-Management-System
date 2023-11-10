package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Club {
    private String clubName;
    private String clubAdvisorID;
    private String clubDescription;
    private String clubID;
    private int clubMembershipCount;
    private ArrayList<String> clubMembersID;
    private int noOfClubEvents;
    private String staffID;
    private ArrayList<Event> listOfEventDetails;
    public String getClubName() {
        return clubName;
    }
    public String getClubAdvisorID() {
        return clubAdvisorID;
    }
    public String getClubDescription() {
        return clubDescription;
    }
    public String getClubID() {
        return clubID;
    }
    public int getClubMembershipCount() {
        return clubMembershipCount;
    }
    public ArrayList<String> getClubMembersID() {
        return clubMembersID;
    }
    public int getNoOfClubEvents() {
        return noOfClubEvents;
    }
    public String getStaffID() {
        return staffID;
    }
    public ArrayList<Event> getListOfEventDetails() {
        return listOfEventDetails;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public void setClubAdvisorID(String clubAdvisorID) {
        this.clubAdvisorID = clubAdvisorID;
    }
    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
    public void setClubID(String clubID) {
        this.clubID = clubID;
    }
    public void setClubMembershipCount(int clubMembershipCount) {
        this.clubMembershipCount = clubMembershipCount;
    }
    public void setClubMembersID(ArrayList<String> clubMembersID) {
        this.clubMembersID = clubMembersID;
    }
    public void setNoOfClubEvents(int noOfClubEvents) {
        this.noOfClubEvents = noOfClubEvents;
    }
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
    public void setListOfEventDetails(ArrayList<Event> listOfEventDetails) {
        this.listOfEventDetails = listOfEventDetails;
    }
    public void displayReport() {

    }
    public String generateClubID() {
        // gotto complete this
        return "";
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
}
