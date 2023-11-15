package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class ClubAdvisor{
    private String clubAdvisorID;
    private String studentID;
    private String clubID;
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

    public ClubAdvisor(String clubAdvisorID,String studentID, String clubID,String position){
        this.studentID=studentID;
        this.clubAdvisorID=clubAdvisorID;
        this.position = position;
        this.clubID = clubID;
    }
    public static void createClubAdvisorTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE IF NOT EXISTS ClubAdvisor ("+
                    "    ClubAdvisorID VARCHAR(5) PRIMARY KEY," +
                    "    StudentID VARCHAR(5)," +
                    "    ClubID VARCHAR(5)," +
                    "    Position VARCHAR(255)," +
                    "    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)," +
                    "    FOREIGN KEY (ClubID) REFERENCES Club(ClubID));";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the databas
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ClubAdvisor> loadClubAdvisorsFromDatabase()  {//Load data from the student database
        createClubAdvisorTableOnDatabase();
        ArrayList<ClubAdvisor> clubAdvisors = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM clubAdvisor");
             ResultSet results = preparedStatement.executeQuery()) {

            while (results.next()) {
                ClubAdvisor clubAdvisor = new ClubAdvisor(results.getString("ClubAdvisorID"),results.getString("StudentID"),results.getString("ClubID"),results.getString("Position"));
                clubAdvisors.add(clubAdvisor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubAdvisors;
    }

    public static String generateClubAdvisorID() {
        int idLength = 3;//Membreshoip ID of 10 digits
        StringBuilder stringBuilder = new StringBuilder("CA");
        Random random = new Random();
        for (int i = 0; i < idLength; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
    public void insertIntoClubAdvisorTable(){
        String insertClubAdvisorQuery = "INSERT INTO ClubAdvisor VALUES (?, ?,?,?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertClubAdvisorQuery)) {
            preparedStatement.setString(1,getClubAdvisorID());
            preparedStatement.setString(2,getStudentID());
            preparedStatement.setString(3,getClubID());
            preparedStatement.setString(4,getPosition());

            preparedStatement.executeUpdate();//push

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
