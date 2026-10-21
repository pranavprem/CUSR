package com.cmpe275.cusr.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SearchScheduleTest {

    private final Search search = new Search();

    @Test
    void southboundLocalTrainIdUsesDirectionAndPaddedTime() {
        assertEquals("SB0915", search.search('A', 'B', "09:00"));
    }

    @Test
    void northboundTrainId() {
        assertEquals("NB1030", search.search('Z', 'Y', "10:30"));
    }

    @Test
    void localArrivalAddsEightMinutesPerStation() {
        assertEquals("09:20", search.findtime("SB0915", 'B'));
    }

    @Test
    void expressArrivalUsesTheFiveStationGrid() {
        assertEquals("09:25", search.findtime("SB0900", 'F'));
    }
}
