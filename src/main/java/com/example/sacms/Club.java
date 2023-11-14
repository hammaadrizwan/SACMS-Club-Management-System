package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Club {
    private String clubName;
    private String clubDescription;
    private String clubID;
    private String TeacherID;
    private ArrayList<Event> events;
    private ArrayList<Student> students;
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
        this.students=loadStudentsOfClub(getClubID());
        //this.events=loadStudentsOfClub(clubID);

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createClubMembershipTableOnDatabase() {//to store studetn and club ID
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE IF NOT EXISTS ClubsMembership (" +
                    "    MembershipID VARCHAR(10) PRIMARY KEY," +
                    "    ClubID VARCHAR(5)," +
                    "    StudentID VARCHAR(5)," +
                    "    FOREIGN KEY (ClubID) REFERENCES Club(ClubID)," +
                    "    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finally its then executed on the database
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
                Club club = new Club(results.getString("ClubID"),results.getString("ClubName"),results.getString("Description"),results.getString("TeacherID"));
                clubs.add(club);//loads all clubs to the ArrayList
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }

    public void addStudent(Student s){
        students.add(s);//add the studetn to tthe list of students
        String membershipID;
        do {
            membershipID = generateMembershipID();//generate a membership ID for each student
        } while (loadExistingMemberships().contains(membershipID));//until its unique we generate a new ID
        String insertClubMembershipQuery = "INSERT INTO clubsmembership VALUES (?, ?,?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertClubMembershipQuery)) {
            preparedStatement.setString(1, membershipID);//inserts the Membership ID,student ID and the club ID to the table
            preparedStatement.setString(2, getClubID());
            preparedStatement.setString(3, s.getStudentID());
            preparedStatement.executeUpdate();//push

    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeStudent(Student s){
        students.remove(s);//remove the student from the database
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clubsMembership WHERE StudentID = ? AND ClubID = ?")) {// deketes the row where the student ID is equal to the on e entered

            preparedStatement.setString(1, s.getStudentID());
            preparedStatement.setString(2,getClubID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String generateMembershipID() {

        int idLength = 9;//Mmebreshoip ID of 10 digits
        StringBuilder stringBuilder = new StringBuilder("M");
        Random random = new Random();
        for (int i = 0; i < idLength; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }

    public ArrayList<String> loadExistingMemberships(){//get the exsisting Memberships to an arraylist, count howmany members
        ArrayList<String> existingMembershipIDs = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM clubsMembership");
             ResultSet results = preparedStatement.executeQuery()) {


            while (results.next()) {
                existingMembershipIDs.add(results.getString("MembershipID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existingMembershipIDs;

    }

    public static ArrayList<Student> loadStudentsOfClub(String clubID) {
        createClubMembershipTableOnDatabase();//create teh membership table if not exsist
        ArrayList<Student> students = new ArrayList<>();//Loads all the students from the database who are the members of that club
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT Student.StudentID, Student.FirstName, Student.LastName, Student.Email, Student.DateOfBirth, Student.Password, Student.ContactNo, Student.Classroom " +
                             "FROM Student " +
                             "JOIN ClubsMembership ON Student.StudentID = ClubsMembership.StudentID " +
                             "WHERE ClubsMembership.ClubID = ?")) {

            preparedStatement.setString(1, clubID);// returns all the students for that specific club by joingin the studentID FK from the clubmembership table to the student Table

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    Student student = new Student(
                            results.getString("FirstName"),
                            results.getString("LastName"),
                            results.getString("Email"),
                            results.getString("Password"),
                            results.getString("DateOfBirth"),
                            results.getString("ContactNo"),
                            results.getString("StudentID"),
                            results.getString("Classroom")
                    );//finally creates an object of the student class
                    students.add(student);//adds to the students list
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students; // return the list of students
    }

/*
    public void saveToDatabase(Connection connection) throws SQLException {
        //INSERT INTO Clubmembership values()
        String insertClubMembershipQuery = "INSERT INTO banks (id, name) VALUES (?, ?)";
        String insertEmployeeQuery = "INSERT INTO employees (id, name, bank_id) VALUES (?, ?, ?)";

        try (PreparedStatement bankStatement = connection.prepareStatement(insertBankQuery);
             PreparedStatement employeeStatement = connection.prepareStatement(insertEmployeeQuery)) {

            // Start a transaction
            connection.setAutoCommit(false);

            // Insert bank
            bankStatement.setString(1, getId());
            bankStatement.setString(2, getName());
            bankStatement.executeUpdate();//push

            // Insert employees with the retrieved bank ID
            for (Employee employee : employees) {
                employeeStatement.setString(1, employee.id);
                employeeStatement.setString(2, employee.name);
                employeeStatement.setString(3, getId());
                employeeStatement.executeUpdate();//push
            }

            // Commit the transaction
            connection.commit();

        } catch (SQLException e) {
            // Rollback the transaction if there's an error
            connection.rollback();
            throw e;
        } finally {
            // Restore auto-commit mode
            connection.setAutoCommit(true);
        }*/
    }

