package com.cmpe275.cusr.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloAndHomeRespond() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello"));
    }

    @Test
    void quotedCostEndpointRoundsPartialBlocksUp() throws Exception {
        mockMvc.perform(get("/cost")
                        .param("from", "A")
                        .param("to", "G")
                        .param("count", "2")
                        .param("trainFare", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("4"));
    }

    @Test
    void emptyTicketListIsOk() throws Exception {
        mockMvc.perform(get("/ticket"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void registerRejectsADuplicateEmailAndLoginReportsUnknownUsers() throws Exception {
        long id = register("Ada", "Lovelace", "ada@example.com", "secret");

        mockMvc.perform(post("/user")
                        .param("firstName", "Ada")
                        .param("lastName", "Lovelace")
                        .param("email", "ada@example.com")
                        .param("password", "secret"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already exists."));

        mockMvc.perform(post("/userlogin").param("user", "ada@example.com").param("password", "secret"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(post("/userlogin").param("user", "missing@example.com").param("password", "secret"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Invalid Credentials!"));

        mockMvc.perform(post("/userlogin").param("user", "ada@example.com").param("password", "wrong"))
                .andExpect(status().isNotFound());
    }

    @Test
    void profileUpdateCanKeepTheSameEmail() throws Exception {
        long id = register("Grace", "Hopper", "grace@example.com", "secret");

        mockMvc.perform(put("/user/" + id)
                        .param("firstName", "Grace")
                        .param("lastName", "Murray")
                        .param("email", "grace@example.com")
                        .param("password", "secret"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Murray"));
    }

    @Test
    void socialLoginCreatesAUserOnce() throws Exception {
        mockMvc.perform(get("/sociallogin").param("email", "social@example.com").param("name", "Social"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("social@example.com"))
                .andExpect(jsonPath("$.firstName").value("Social"));

        mockMvc.perform(get("/sociallogin").param("email", "social@example.com").param("name", "Other"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Social"));
    }

    @Test
    void searchReturnsItineraries() throws Exception {
        mockMvc.perform(get("/search/A/F/08:00/No/00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trains").isArray())
                .andExpect(jsonPath("$[0].cost").isNumber());
    }

    @Test
    void bookingAcceptsTheReactPassengerFieldAndCancelRestoresSeats() throws Exception {
        long userId = register("Booker", "Test", "booker@example.com", "secret");
        String trainId = "SB235012/20/2099";

        MvcResult booked = mockMvc.perform(post("/" + userId + "/ticket/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson(userId, trainId, "passenger")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passenger[0].name").value("Ada"))
                .andExpect(jsonPath("$.trains[0].departureTime").value("235012/20/2099"))
                .andReturn();

        int ticketId = com.jayway.jsonpath.JsonPath.read(booked.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/seats").param("trainId", trainId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seats").value(999));

        mockMvc.perform(get("/" + userId + "/ticket/" + ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketId));

        mockMvc.perform(get("/ticket"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(org.hamcrest.Matchers.hasItem(ticketId)));

        mockMvc.perform(delete("/" + userId + "/ticket/" + ticketId)).andExpect(status().isOk());

        mockMvc.perform(get("/seats").param("trainId", trainId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seats").value(1000));
    }

    @Test
    void passengersCannotCancelInsideTheFinalHourButATrainCancelStillDoes() throws Exception {
        long userId = register("Late", "Rider", "late@example.com", "secret");
        String trainId = "SB000001/01/2018";

        MvcResult booked = mockMvc.perform(post("/" + userId + "/ticket/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson(userId, trainId, "passengers")))
                .andExpect(status().isOk())
                .andReturn();
        int ticketId = com.jayway.jsonpath.JsonPath.read(booked.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(delete("/" + userId + "/ticket/" + ticketId))
                .andExpect(status().isForbidden())
                .andExpect(content().string("It's too late"));

        mockMvc.perform(get("/cancel").param("trainId", trainId))
                .andExpect(status().isOk())
                .andExpect(content().string("Train Cancelled"));

        mockMvc.perform(get("/" + userId + "/ticket/" + ticketId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Get Ticket not found!"));
    }

    private long register(String first, String last, String email, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/user")
                        .param("firstName", first)
                        .param("lastName", last)
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().isOk())
                .andReturn();
        return ((Number) com.jayway.jsonpath.JsonPath.read(result.getResponse().getContentAsString(), "$.id"))
                .longValue();
    }

    private String bookJson(long userId, String trainId, String passengerField) {
        return "{\"userId\":" + userId + ",\"trains\":[\"" + trainId + "\"],\"route\":[\"A\",\"C\"],\"cost\":10,\""
                + passengerField + "\":[\"Ada\"]}";
    }
}
