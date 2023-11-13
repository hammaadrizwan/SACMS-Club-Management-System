package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Club {
    private String clubName;
    private String clubDescription;
    private String clubID;
    private String TeacherID;
    /*
    private ArrayList<String> clubMembersID;
    private int noOfClubEvents;
    private int clubMembershipCount;
    private ArrayList<Event> listOfEventDetails;
    */

    Club(String clubID,String clubName, String clubDescription, String TeacherID){//To get it from the database
        this.clubID=clubID;
        this.clubDescription=clubDescription;
        this.clubName=clubName;
        this.TeacherID=TeacherID;
    }
    Club(String clubName, String clubDescription, String TeacherID){//if we create a new club its automatically set
        this.clubID=generateClubID();
        this.clubDescription=clubDescription;
        this.clubName=clubName;
        this.TeacherID=TeacherID;
    }

    public String getClubName() {
        return clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }
    public String getClubID() {
        return clubID;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public void displayReport() {

    }

    public String generateClubID() {
        ArrayList<Club> clubs = loadClubsFromDatabase();
        int noOfClubs = clubs.size();
        String generatedClubID = "C000"+ Integer.toString(Character.getNumericValue(clubs.get(noOfClubs).clubID.toCharArray()[4])+1);
        return generatedClubID;
    }

    public static void createClubTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE IF NOT exists Club (" +
                    "    ClubID VARCHAR(5) PRIMARY KEY," +
                    "    ClubName VARCHAR(255)," +
                    "    Description VARCHAR(255)," +
                    "    TeacherID VARCHAR(5)," +
                    "    FOREIGN KEY (TeacherID) REFERENCES Teacher(TeacherID)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finally its then executed on the database
                System.out.println("Club table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Club> loadClubsFromDatabase()  {//Load data from the student database
        createClubTableOnDatabase();
        ArrayList<Club> clubs = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM club");
             ResultSet results = preparedStatement.executeQuery()) {

            while (results.next()) {
                Club club = new Club(results.getString("ClubID"),results.getString("ClubName"),results.getString("Description"),results.getString("StaffID"));
                clubs.add(club);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }
}
