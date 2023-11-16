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

    public ClubAdvisor(String clubAdvisorID,String studentID, String clubID,String position){
        this.studentID=studentID;
        this.clubAdvisorID=clubAdvisorID;
        this.position = position;
        this.clubID = clubID;
    }
    public String getStudentID() {
        return studentID;
    }

    public String getClubID() {
        return clubID;
    }

    public String getClubAdvisorID() {
        return clubAdvisorID;
    }

    public String getPosition() {
        return position;
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

    public static ArrayList<ClubAdvisor> loadClubAdvisorsFromDatabase()  {//Load data from the clubAdvisor database
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
        String insertClubAdvisorQuery = "INSERT INTO ClubAdvisor VALUES (?, ?,?,?)";//inserts the values into the database
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

    public static ArrayList<String[]> getClubAdvisorInformation()  {//Load data from the clubAdvisor database
        ArrayList<String[]> result = new ArrayList<String[]>();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT clubadvisor.ClubAdvisorID, club.ClubName, student.StudentID, CONCAT(student.FirstName, ' ', student.LastName) AS StudentName, student.ContactNo,  clubadvisor.Position,Student.Email FROM ClubAdvisor JOIN Student ON ClubAdvisor.StudentID = Student.StudentID JOIN Club  ON ClubAdvisor.ClubID = Club.ClubID;");
             ResultSet results = preparedStatement.executeQuery()) {

            while (results.next()) {
                String[] row = new String[7];
                row[0]=results.getString("ClubAdvisorID").toString();
                row[1]=results.getString("ClubName").toString();
                row[2]=results.getString("StudentID").toString();
                row[3]=results.getString("StudentName").toString();
                row[4]=results.getString("ContactNo").toString();
                row[5]=results.getString("Position").toString();
                row[6]=results.getString("Email").toString();
                result.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }




}
