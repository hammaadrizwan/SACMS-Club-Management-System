package com.example.sacms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ClubAdvisorTest {
    Controller controller = new Controller();

    ClubAdvisorTest() {

    }

    @Test
    public void invalidStudentID() {
        Assertions.assertFalse(this.controller.isCreateClubAdvisorStudentIDValid("S001342"));
    }

}