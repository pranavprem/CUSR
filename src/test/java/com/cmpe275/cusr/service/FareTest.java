package com.cmpe275.cusr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FareTest {

    private final TicketServiceImpl quotedFare = new TicketServiceImpl();
    private final SearchServiceImpl searchFare = new SearchServiceImpl();

    @Test
    void quotedFareRoundsAPartialBlockUp() {
        assertEquals(1, quotedFare.cost("A", "F", 1, 1));
        assertEquals(2, quotedFare.cost("A", "G", 1, 1));
        assertEquals(4, quotedFare.cost("A", "G", 2, 1));
    }

    @Test
    void searchFareKeepsTheHistoricalFloorAndExpressMultiplier() {
        assertEquals(1, searchFare.cost("A", "G", 1, "SB1215"));
        assertEquals(2, searchFare.cost("A", "G", 1, "SB1200"));
        assertEquals(1, searchFare.cost("A", "F", 1, "NB0900".replace("00", "15")));
    }
}
