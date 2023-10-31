package com.example.sacms;

import java.util.ArrayList;

public class ClubAdvisor{
    private String studentID;
    private String clubAdvisorID;
    private String postition;

    public String getClubAdvisorID() {
        return clubAdvisorID;
    }

    public void setClubAdvisorID(String clubAdvisorID) {
        this.clubAdvisorID = generateClubAdvisorID();
    }

    public String getPostition() {
        return postition;
    }

    public void setPostition(String postition) {
        this.postition = postition;
    }

    public String generateClubAdvisorID(){
        String clubAdvisorID = " ";
        return clubAdvisorID;
    }

    public ClubAdvisor(String studentID,String postition, String staffID){
        this.clubAdvisorID=null;
        this.postition=postition;

    }
}
