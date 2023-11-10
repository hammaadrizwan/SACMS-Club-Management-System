package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClubAdvisor{
    private String studentID;
    private String clubID;
    private String clubAdvisorID;
    private String position;

    public String getStudentID() {
        return studentID;
    }

    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public String getClubAdvisorID() {
        return clubAdvisorID;
    }

    public void setClubAdvisorID(String clubAdvisorID) {
        this.clubAdvisorID = clubAdvisorID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ClubAdvisor(String studentID, String position, String clubID){
        this.studentID=studentID;
        this.clubAdvisorID=null;
        this.position = position;
        this.clubID = clubID;

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
}
