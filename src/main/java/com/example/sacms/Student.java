package com.example.sacms;

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

}
