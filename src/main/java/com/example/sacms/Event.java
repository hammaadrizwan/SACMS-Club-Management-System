package com.example.sacms;

import java.util.ArrayList;

public class Event {
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String clubID;
    private String eventDescription;
    private String eventID;
    private String eventLocation;
    private int eventAttendance;
    private ArrayList<String> eventAttendeesID;
    public String getEventName() {
        return eventName;
    }
    public String getEventDate() {
        return eventDate;
    }
    public String getEventTime() {
        return eventTime;
    }
    public String getClubID() {
        return clubID;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public String getEventID() {
        return eventID;
    }
    public String getEventLocation() {
        return eventLocation;
    }
    public int getEventAttendance() {
        return eventAttendance;
    }
    public ArrayList<String> getEventAttendeesID() {
        return eventAttendeesID;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    public void setClubID(String clubID) {
        this.clubID = clubID;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
    public void setEventAttendance(int eventAttendance) {
        this.eventAttendance = eventAttendance;
    }
    public void setEventAttendeesID(ArrayList<String> eventAttendeesID) {
        this.eventAttendeesID = eventAttendeesID;
    }
    public String generateEEventID() {
        // gotto complete this
        return "";
    }
}
