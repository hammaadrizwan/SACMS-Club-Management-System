package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Request {
    private String requestID;
    private String teacherID;
    private String clubID;
    private String studentID;
    private String position;

    public String getRequestID() {
        return requestID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getPosition() {
        return position;
    }
    public String getClubID() {
        return clubID;
    }

    public Request(String requestID, String teacherID, String clubID, String studentID, String position) {
        this.requestID = requestID;
        this.teacherID = teacherID;
        this.clubID = clubID;
        this.studentID = studentID;
        this.position = position;
    }
    public static ArrayList<String> loadExistingRequests(){//get the exsisting Memberships to an arraylist, count howmany members
        ArrayList<String> existingRequestsIDs = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Request");
             ResultSet results = preparedStatement.executeQuery()) {


            while (results.next()) {
                existingRequestsIDs.add(results.getString("RequestID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existingRequestsIDs;

    }


}
