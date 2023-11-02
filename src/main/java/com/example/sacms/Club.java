package com.example.sacms;

import java.util.ArrayList;

public class Club {
    private String clubName;
    private String clubAdvisorID;
    private String clubDescription;
    private String clubID;
    private int clubMembershipCount;
    private ArrayList<String> clubMembersID;
    private int noOfClubEvents;
    private String staffID;
    private ArrayList<Event> listOfEventDetails;
    public String getClubName() {
        return clubName;
    }
    public String getClubAdvisorID() {
        return clubAdvisorID;
    }
    public String getClubDescription() {
        return clubDescription;
    }
    public String getClubID() {
        return clubID;
    }
    public int getClubMembershipCount() {
        return clubMembershipCount;
    }
    public ArrayList<String> getClubMembersID() {
        return clubMembersID;
    }
    public int getNoOfClubEvents() {
        return noOfClubEvents;
    }
    public String getStaffID() {
        return staffID;
    }
    public ArrayList<Event> getListOfEventDetails() {
        return listOfEventDetails;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public void setClubAdvisorID(String clubAdvisorID) {
        this.clubAdvisorID = clubAdvisorID;
    }
    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
    public void setClubID(String clubID) {
        this.clubID = clubID;
    }
    public void setClubMembershipCount(int clubMembershipCount) {
        this.clubMembershipCount = clubMembershipCount;
    }
    public void setClubMembersID(ArrayList<String> clubMembersID) {
        this.clubMembersID = clubMembersID;
    }
    public void setNoOfClubEvents(int noOfClubEvents) {
        this.noOfClubEvents = noOfClubEvents;
    }
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
    public void setListOfEventDetails(ArrayList<Event> listOfEventDetails) {
        this.listOfEventDetails = listOfEventDetails;
    }
    public void displayReport() {

    }
    public String generateClubID() {

    }
}
