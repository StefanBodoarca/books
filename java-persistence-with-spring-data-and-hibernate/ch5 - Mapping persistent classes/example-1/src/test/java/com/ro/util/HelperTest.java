package com.ro.util;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @Test
    void testTomorrowGeneration() {
        Date tomorrow = Helper.tomorrow();

        Date today = new Date();
        int todayDay = today.getDay();

        assertEquals(todayDay + 1, tomorrow.getDay());
    }

}