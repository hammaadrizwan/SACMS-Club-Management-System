package com.example.sacms;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dateOfBirth;
    private int contactNo;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }
    public void greetUser(){

    }

    public void getUser(){
        System.out.println("Hello "+getFirstName()+" "+getLastName());
    }

    public Person(String firstName, String lastName, String email, String password, String dateOfBirth, int contactNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.contactNo = contactNo;
    }
}
