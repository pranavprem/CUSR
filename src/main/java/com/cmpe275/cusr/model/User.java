package com.cmpe275.cusr.model;

import javax.persistence.*;

@Entity
@Table ( name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "token")
    private String token;
    /*@OneToMany(fetch = FetchType.EAGER, mappedBy = "tickets")
    @JoinTable(name = "user_tickets")*/
    /*private List<Ticket>  tickets;*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

/*    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTicket(List<Ticket> tickets) {
        this.tickets = tickets;
    }*/
}
