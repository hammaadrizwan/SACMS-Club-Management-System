package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Student extends Person { // inherits the behavoirs and attributes from the Person class
    private String studentID;
    private String classroom;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Student(String firstName, String lastName, String email, String password, String dateOfBirth, int contactNo, String studentID, String classroom) {
        super(firstName, lastName, email, password, dateOfBirth, contactNo);
        this.studentID=studentID;
        this.classroom=classroom;
    }
    public void createStudentTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE Student ("+
                    "StudentID VARCHAR(5) PRIMARY KEY," +
                    "FirstName VARCHAR(25)," +
                    "LastName VARCHAR(25)," +
                    "Email VARCHAR(30)," +
                    "DateOfBirth VARCHAR(10)," +
                    "Password VARCHAR(255)," +
                    "ContactNo VARCHAR(9)," +
                    "Classroom VARCHAR(6));";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                System.out.println("Event table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
