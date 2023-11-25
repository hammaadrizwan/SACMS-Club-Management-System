package com.example.sacms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TeacherTest {

    Controller controller = new Controller();

    TeacherTest() {

    }

    @Test
    public void invalidEmail() {
        Assertions.assertFalse(this.controller.isTeacherEmailValid("@jason.gmail"));
    }

    @Test
    public void invalidPassword() {
        Assertions.assertFalse(this.controller.isTeacherPasswordValid("parrot "));
    }

    @Test
    public void invalidContactNumber() {
        Assertions.assertFalse(this.controller.isTeacherContactValid("0113452553434"));
    }
}