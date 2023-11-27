package com.example.sacms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClubTest {
    Controller controller = new Controller();

    ClubTest() {

    }

    @Test
    public void invalidClubName() {
        Assertions.assertFalse(this.controller.isClubNameValid("Bo5ok club7"));
    }
    @Test
    public void invalidClubID() {
        Assertions.assertFalse(this.controller.isClubIDValid("C014434"));
    }
}