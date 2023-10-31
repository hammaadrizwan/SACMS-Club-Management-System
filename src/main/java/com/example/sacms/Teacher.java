package com.example.sacms;

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
    public void getUser(){//polymorphism is used as the teacher has a different implementation
        String time="";
        String greeting="Good "+time+" Teacher "+getFirstName()+" "+getLastName()+"!";
        System.out.println(greeting);
    }

    public Teacher(String firstName,String lastName,String email,String password,String dateOfBirth,int contactNo,String staffID){
        super(firstName,lastName,email,password,dateOfBirth,contactNo);//automatically initialse
        this.staffID=staffID;
    }//Encapsulation is used here
}
