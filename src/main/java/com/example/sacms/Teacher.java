package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Teacher extends Person{ // inherits the behavoirs and attributes from the Person class[INHERITANCE]
    private String staffID;
    public Teacher(String firstName,String lastName,String email,String password,String dateOfBirth,String contactNo,String staffID){
        super(firstName,lastName,email,password,dateOfBirth,contactNo);//automatically initialse
        this.staffID=staffID;
    }//Encapsulation is used here

    public String getStaffID() {
        return staffID;
    }
    @Override
    public String greetUser(){//polymorphism is used as the teacher has a different implementation
        String greeting="Welcome Teacher, "+getFirstName()+" "+getLastName()+"!";
        return greeting;
    }//own implementation of the greet user method

    //DATABASE METHODS
    public static void createTeacherTableOnDatabase()  {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query = "CREATE TABLE IF NOT EXISTS Teacher (TeacherID VARCHAR(5) PRIMARY KEY,FirstName VARCHAR(255),LastName VARCHAR(255),Email VARCHAR(255),DateOfBirth VARCHAR(255),ContactNo VARCHAR(255),Password VARCHAR(255));";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                //System.out.println("Teacher table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//create the teacher table

    public static ArrayList<Teacher> loadTeachersFromDatabase()  {//Load data from the teacher from database
        createTeacherTableOnDatabase();
        ArrayList<Teacher> teachers = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teacher");
             ResultSet results = preparedStatement.executeQuery()) {

            while (results.next()) {
                Teacher teacher = new Teacher(results.getString("FirstName"),results.getString("LastName"),results.getString("Email"),results.getString("Password"),results.getString("DateOfBirth"),results.getString("ContactNo"),results.getString("TeacherID"));
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }//reads it from the database

    public void insertToDatabase() {
        try (Connection connection = Database.getConnection()){
            String insertEmployeeQuery = "INSERT INTO teacher (TeacherID, FirstName, LastName,Email,DateOfBirth,ContactNo,Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertEmployeeQuery)) {
                statement.setString(1, getStaffID());
                statement.setString(2, getFirstName());
                statement.setString(3, getLastName());
                statement.setString(4, getEmail());
                statement.setString(5, getDateOfBirth());
                statement.setString(6, getContactNo());
                statement.setString(7, getPassword());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//inserts into the database

}
