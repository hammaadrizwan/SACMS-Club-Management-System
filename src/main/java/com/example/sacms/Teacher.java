package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Teacher extends Person{ // inherits the behavoirs and attributes from the Person class[INHERITANCE]
    private String staffID;

    public String getStaffID() {
        return staffID;
    }
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
    public boolean approveClubAdvisor(String clubAdvisorID){
        return false;
    }//to approve a clubAdvisor

    @Override
    public String greetUser() {
        return null;
    }

    @Override
    public void getUser(){//polymorphism is used as the teacher has a different implementation
        String time="";
        String greeting="Good "+time+" Teacher "+getFirstName()+" "+getLastName()+"!";
        System.out.println(greeting);
    }

    public Teacher(String firstName,String lastName,String email,String password,String dateOfBirth,String contactNo,String staffID){
        super(firstName,lastName,email,password,dateOfBirth,contactNo);//automatically initialse
        this.staffID=staffID;
    }//Encapsulation is used here
    public void createTeacherTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query = "CREATE TABLE Teacher (" +
                    "    StaffID VARCHAR(5) PRIMARY KEY," +
                    "    FirstName VARCHAR(25)," +
                    "    LastName VARCHAR(25)," +
                    "    Email VARCHAR(30)," +
                    "    DateOfBirth VARCHAR(10)," +
                    "    ContactNo VARCHAR(9)," +
                    "    Password VARCHAR(255)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                System.out.println("Teacher table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
