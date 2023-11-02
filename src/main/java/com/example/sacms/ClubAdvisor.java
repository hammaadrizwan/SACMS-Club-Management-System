package com.example.sacms;

public class ClubAdvisor{
    private String studentID;
    private String clubID;
    private String clubAdvisorID;
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

    public ClubAdvisor(String studentID, String position, String clubID){
        this.studentID=studentID;
        this.clubAdvisorID=null;
        this.position = position;
        this.clubID = clubID;

    }
}
