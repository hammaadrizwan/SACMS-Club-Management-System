package com.example.sacms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Club implements Report {//implements the method of the report
    private String clubName;
    private String clubDescription;
    private String clubID;
    private String teacherID;
    private ArrayList<Student> students;
    private ArrayList<Event> events;//composition
    private ArrayList<ClubAdvisor> clubAdvisors;//composition
    private Teacher teacher;//aggregation

    Club(String clubID,String clubName, String clubDescription, String teacherIncharge){//To get it from the database
        this.clubID=clubID;
        this.clubDescription=clubDescription;
        this.clubName=clubName;
        this.teacherID = teacherIncharge;
        this.students=loadStudentsOfClub(getClubID());
        this.clubAdvisors=loadClubAdvisorsOfClub(getClubID());//sets the clubadvisors,students and teachers of that club
        this.teacher = loadTeacherOfClub(teacherIncharge);
    }

    public String getClubName() {
        return clubName;
    }
    public String getTeacherID() {
        return teacherID;
    }
    public String getClubDescription() {
        return clubDescription;
    }
    public String getClubID() {
        return clubID;
    }public ArrayList<Event> getEvents(){
        return events;
    }
    public  void setEvents(){
        this.events=loadEventsOfClub(clubID);
    }
    //1.5.2.1 mapping from create club sequence diagram


    //DATABASE METHODS FOR CLUB
    public void insertIntoClubs(){
        String insertClubQuery = "INSERT INTO club VALUES (?, ?,?,?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertClubQuery)) {
            preparedStatement.setString(1,getClubID());//inserts the Membership ID,student ID and the club ID to the table
            preparedStatement.setString(2,getClubName());
            preparedStatement.setString(3,getClubDescription());
            preparedStatement.setString(4, getTeacherID());

            preparedStatement.executeUpdate();//push

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateClubID() {//randomlt generate a clubID
        int idLength = 4;//club ID of 5 digits
        StringBuilder stringBuilder = new StringBuilder("C");
        Random random = new Random();
        for (int i = 0; i < idLength; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();// returns it
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

    public static void createClubTableOnDatabase() {
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE IF NOT exists Club (" +
                    "    ClubID VARCHAR(5) PRIMARY KEY," +
                    "    ClubName VARCHAR(255)," +
                    "    Description VARCHAR(255)," +
                    "    Teacher VARCHAR(5)," +
                    "    FOREIGN KEY (TeacherID) REFERENCES Teacher(TeacherID)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finally its then executed on the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createClubMembershipTableOnDatabase() {//to store students and club ID
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

    public static ArrayList<Club> loadClubsFromDatabase()  {//Load data from the student database, 1.4.2 mapping from view clubs sequence diagram, 3 mapping from download attendance sequence diagram, 1.4.5 mapping from Approval of club advisor sequence diagram
        createClubTableOnDatabase();
        ArrayList<Club> clubs = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM club");//prepare the statement
             ResultSet results = preparedStatement.executeQuery()) {//we get it as a Result set

            while (results.next()) {// until the set of results are empty we populate the list of clubs and return it
                Club club = new Club(results.getString("ClubID"),results.getString("ClubName"),results.getString("Description"),results.getString("TeacherID"));
                clubs.add(club);//loads all clubs to the ArrayList
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }

    public void addStudent(Student student){
        students.add(student);//add the studetn to tthe list of students
        String membershipID;
        do {
            membershipID = generateMembershipID();//generate a membership ID for each student
        } while (loadExistingMemberships().contains(membershipID));//until its unique we generate a new ID
        String insertClubMembershipQuery = "INSERT INTO clubsmembership VALUES (?, ?,?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertClubMembershipQuery)) {
            preparedStatement.setString(1, membershipID);//inserts the Membership ID,student ID and the club ID to the table
            preparedStatement.setString(2, getClubID());
            preparedStatement.setString(3, student.getStudentID());
            preparedStatement.executeUpdate();//push

    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeStudent(Student student){
        students.remove(student);//remove the student from the database
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clubsMembership WHERE StudentID = ? AND ClubID = ?")) {// deketes the row where the student ID is equal to the on e entered

            preparedStatement.setString(1, student.getStudentID());
            preparedStatement.setString(2,getClubID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public static ArrayList<Event> loadEventsOfClub(String clubID) {
        ArrayList<Event> events = new ArrayList<>();//Loads all the events from the database of that club
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT Events.EventID, Events.EventName, Events.Date, Events.Time, Events.Location, Events.ClubID, Events.EventDescription FROM Events JOIN Club ON club.ClubID = Events.ClubID WHERE club.ClubID =?")) {

            preparedStatement.setString(1, clubID);// returns all the students for that specific club by joingin the studentID FK from the clubmembership table to the student Table

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    Event event = new Event(
                            results.getString("EventID"),
                            results.getString("EventName"),
                            results.getString("Date"),
                            results.getString("Time"),
                            results.getString("Location"),
                            results.getString("ClubID"),
                            results.getString("EventDescription")
                    );
                    events.add(event);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
    public ArrayList<ClubAdvisor> loadClubAdvisorsOfClub(String clubID) {
        ArrayList<ClubAdvisor> clubAdvisors = new ArrayList<>();//Loads all the CA from the database who are the boardMembers of that club
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ClubAdvisor.ClubAdvisorID, ClubAdvisor.StudentID, ClubAdvisor.ClubID, ClubAdvisor.Position FROM ClubAdvisor JOIN Club ON club.ClubID = ClubAdvisor.ClubID WHERE club.ClubID =?")) {

            preparedStatement.setString(1, clubID);
            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    ClubAdvisor clubAdvisor = new ClubAdvisor(
                            results.getString("ClubAdvisorID"),
                            results.getString("StudentID"),
                            results.getString("ClubID"),
                            results.getString("Position")
                    );
                    clubAdvisors.add(clubAdvisor);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubAdvisors; // return the list of students
    }
    public static Teacher loadTeacherOfClub(String teacherID) {
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT  Teacher.FirstName, Teacher.LastName, Teacher.Email, Teacher.Password, Teacher.DateOfBirth, Teacher.ContactNo,Teacher.TeacherID FROM Teacher JOIN Club ON club.TeacherID = Teacher.TeacherID WHERE club.TeacherID =?")) {

            preparedStatement.setString(1, teacherID);// returns all the students for that specific club by joingin the studentID FK from the clubmembership table to the student Table

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    Teacher teacher = new Teacher(
                            results.getString("firstName"),
                            results.getString("lastName"),
                            results.getString("email"),
                            results.getString("password"),
                            results.getString("dateOfBirth"),
                            results.getString("contactNo"),
                            results.getString("teacherID")
                    );
                    return teacher;//finally creates an object of the student class
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // return the list of students
    }

    public static void deleteClub(String clubID){
        createRequestTableOnDatabase();
        String deleteRequestQuery = "Delete from request where request.clubId = ?;";
        String deleteEventQuery = "Delete from events where events.clubId = ?;";
        String deleteClubAdvisorQuery = "Delete from clubadvisor where clubadvisor.clubid = ?;";//to delete a club first we remove them from the club advisor thenclub membership and finally in the club id
        String deleteClubMembershipQuery = "Delete from clubsmembership where clubsmembership.clubid = ?;";
        String deleteClubQuery = "Delete from club where clubid = ?;";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedDeleteRequestQueryStatement = connection.prepareStatement(deleteRequestQuery);
             PreparedStatement preparedDeletedeleteEventQueryStatement = connection.prepareStatement(deleteEventQuery);
             PreparedStatement preparedDeleteClubAdvisorQueryStatement = connection.prepareStatement(deleteClubAdvisorQuery);
             PreparedStatement preparedDeleteClubMembershipQueryStatement = connection.prepareStatement(deleteClubMembershipQuery);
             PreparedStatement preparedDeleteClubQueryStatement = connection.prepareStatement(deleteClubQuery)) {

            connection.setAutoCommit(false);//pause autocommit as theres a squence of insturections to be followed

            preparedDeleteRequestQueryStatement.setString(1,clubID);
            preparedDeleteRequestQueryStatement.executeUpdate();

            preparedDeletedeleteEventQueryStatement.setString(1,clubID);
            preparedDeletedeleteEventQueryStatement.executeUpdate();

            preparedDeleteClubAdvisorQueryStatement.setString(1,clubID);
            preparedDeleteClubAdvisorQueryStatement.executeUpdate();

            preparedDeleteClubMembershipQueryStatement.setString(1,clubID);
            preparedDeleteClubMembershipQueryStatement.executeUpdate();

            preparedDeleteClubQueryStatement.setString(1,clubID);
            preparedDeleteClubQueryStatement.executeUpdate();// once its updated we then set commit all at once


            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRequestID() {//to store the request of becoming a CA
        int idLength = 9;
        StringBuilder stringBuilder = new StringBuilder("R");
        Random random = new Random();
        for (int i = 0; i < idLength; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();// returns it
    }
    public static void createRequestTableOnDatabase(){
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String query ="CREATE TABLE IF NOT exists Request (" +
                    "    RequestID VARCHAR(10) PRIMARY KEY," +
                    "    ClubID VARCHAR(5),"+
                    "    TeacherID VARCHAR(5)," +
                    "    StudentID VARCHAR(5)," +
                    "    Position VARCHAR(25)," +
                    "    FOREIGN KEY (ClubID) REFERENCES Club(ClubID)," +
                    "    FOREIGN KEY (TeacherID) REFERENCES Teacher(TeacherID)," +
                    "    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)" +
                    ");";// same SQL query is given here as string
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {//this is then converted to a prerpare statment
                preparedStatement.executeUpdate();// finally its then executed on the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addRequest(String requestID, String clubID, String teacherID, String studentID, String position){
        try (Connection connection = Database.getConnection()) {//gets the connection from the database using the Database class getConnection method
            String insertRequest = "INSERT INTO REQUEST VALUES (?,?,?,?,?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertRequest)) {//this is then converted to a prerpare statment
                preparedStatement.setString(1,requestID);//inserts the Membership ID,student ID and the club ID to the table
                preparedStatement.setString(2,clubID);
                preparedStatement.setString(3,teacherID);
                preparedStatement.setString(4,studentID);
                preparedStatement.setString(5,position);
                preparedStatement.executeUpdate();//push
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String[]> loadRequestsOfClub(String teacherID) { //1.4.2.1 mapping from Approval of club advisor sequence diagram
        createRequestTableOnDatabase();//creates the table if not exists
        ArrayList<String[]> result = new ArrayList<String[]>();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from Request where Request.TeacherID=?;")) {//reads all the request of that teacher id

            preparedStatement.setString(1, teacherID);

            try (ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String[] row = new String[5];
                    row[0] = results.getString("RequestID");
                    row[1] = results.getString("ClubID");
                    row[2] = results.getString("TeacherID");
                    row[3] = results.getString("StudentID");
                    row[4] = results.getString("Position");
                    result.add(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void deleteRequest(String requestID){
        String deleteRequestQuery = "Delete from Request where requestID = ?;";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedDeleteEventQueryStatement = connection.prepareStatement(deleteRequestQuery)) {
            connection.setAutoCommit(false);//pause autocommit as a squence of insturections to be followed
            preparedDeleteEventQueryStatement.setString(1,requestID);
            preparedDeleteEventQueryStatement.executeUpdate();// once its updated we then set commit all at once
            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String> loadExistingRequestsIds(){
        createRequestTableOnDatabase();
        ArrayList<String> existingRequestsIds = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM request");
             ResultSet results = preparedStatement.executeQuery()) {

            while (results.next()) {
                existingRequestsIds.add(results.getString("RequestID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existingRequestsIds;
    }
    @Override
    public String displayReport(){//method implementatio for the report Class
        String studentCount = "Total number of students: ";
        ArrayList<Student> student = loadStudentsOfClub(getClubID());
        studentCount+= String.valueOf(student.size());
        return studentCount;
    }



}

