package com.example.sacms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    Controller controller = new Controller();

    EventTest() {

    }

    @Test
    public void invalidEventDate() {
        Assertions.assertFalse(this.controller.isEventDateValid("2023-15-65"));
    }
    @Test
    public void invalidEventTime() {
        Assertions.assertFalse(this.controller.isEventTimeValid("34:78"));
    }
    @Test
    public void invalidEventLocation() {Assertions.assertFalse(this.controller.isEventLocationValid(""));}
}