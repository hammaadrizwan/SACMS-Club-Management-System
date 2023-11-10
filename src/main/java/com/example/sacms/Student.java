package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends Person { // inherits the behavoirs and attributes from the Person class
    private String studentID;
    private String classroom;
    private ArrayList<Club> clubs;//Due to Association we gotto add them here
    private ArrayList<Event> events;

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

    public Student(String firstName, String lastName, String email, String password, String dateOfBirth, String contactNo, String studentID, String classroom) {
        super(firstName, lastName, email, password, dateOfBirth, contactNo);
        this.studentID=studentID;
        this.classroom=classroom;
    }//Creates the Student object

    public static void createStudentTableOnDatabase() {//Create a table on the database
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE IF NOT EXISTS Student (StudentID VARCHAR(5) PRIMARY KEY,FirstName VARCHAR(25),LastName VARCHAR(25),Email VARCHAR(30),DateOfBirth VARCHAR(10),Password VARCHAR(255),ContactNo VARCHAR(9),Classroom VARCHAR(6));";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finaly its then executed on the database
                //System.out.println("Student table created");//confirmation message on the GUI
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Student> loadStudentsFromDatabase() {//Load data from the student database
        createStudentTableOnDatabase();//creates it if not available
        ArrayList<Student> students = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Student");//gets all the students
             ResultSet results = preparedStatement.executeQuery()) {

            while (results.next()) {
                Student student = new Student(results.getString("FirstName"),results.getString("LastName"),results.getString("Email"),results.getString("Password"),results.getString("DateOfBirth"),results.getString("ContactNo"),results.getString("StudentID"),results.getString("Classroom"));
                students.add(student);//adds the student to a list of available students
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void addEvent(Event event){
        events.add(event);
    }//to remove inconsistency we add it from here

    public void addCLub(Club club){
        clubs.add(club);
    }
    public ArrayList<Club> getCLubs(){
        return clubs;
    }//returns all the clubs


    @Override// own implementation of person class get behaviour method
    public String greetUser() {
        return("Hello "+getFirstName()+" !");
    }
}
