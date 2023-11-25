package com.example.sacms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Controller controller = new Controller();

    StudentTest() {

    }

    @Test
    public void invalidFirstName() {
        Assertions.assertFalse(this.controller.isStudentFirstNameValid("Jason7"));
    }

    @Test
    public void invalidLastName() {
        Assertions.assertFalse(this.controller.isStudentLastNameValid("Sa8muel"));
    }

    @Test
    public void invalidDOB() {
        Assertions.assertFalse(this.controller.checkStudentDOB("2050-14-45"));
    }

}