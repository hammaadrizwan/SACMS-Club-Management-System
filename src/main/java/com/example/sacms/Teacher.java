package com.example.sacms;

public class Teacher extends Person{
    private String staffID;

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
    public boolean approveClubAdvisor(String clubAdvisorID){
        return false;
    }

    @Override
    public void greetUser() {
        //fname lname
    }
}
