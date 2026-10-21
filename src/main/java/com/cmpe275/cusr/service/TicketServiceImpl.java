package com.cmpe275.cusr.service;

import com.cmpe275.cusr.dao.*;
import com.cmpe275.cusr.model.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class, noRollbackFor = IllegalStateException.class)
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private PassengerDao passengerDao;

    @Autowired
    private TrainDao trainDao;

    @Autowired
    private TicketDetailDao ticketDetailDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailService emailService;

    private static long TOTAL_SEATS = 1000;

    @Override
    public List<Ticket> getTickets(long userId) throws Exception {

        try {

            List<Ticket> tickets = ticketDao.findAllByUserId(userId);
            for (Ticket ticket : tickets) {
                ticket.setPassenger(passengerDao.findAllByTicketId(ticket.getId()));
                List<TicketDetail> trainIds = ticketDetailDao.findAllByTicketId(ticket.getId());
                List<Train> trains = new ArrayList<>();
                for (TicketDetail ticketDetail : trainIds) {
                    trains.add(trainDao.findById(ticketDetail.getTrainId()).orElse(null));
                }

                ticket.setTrains(trains);
            }

            return tickets;
        } catch (Exception e) {

            throw new Exception();
        }
    }

    @Override
    public Ticket addTicket(Ticket ticket) throws Exception {

        try {

            Ticket ticket1 = ticketDao.save(ticket);
            for (Passenger passenger : ticket.getPassenger()) {
                passenger.setTicketId(ticket1.getId());
                passengerDao.save(passenger);
            }
            ticket1.setPassenger(ticket.getPassenger());

            for (Train train : ticket1.getTrains()) {
                String depTime = train.getDepartureTime() + train.getTrain().substring(6);
                String arrTime = train.getArrivalTime() + train.getTrain().substring(6);

                train.setDepartureTime(depTime);
                train.setArrivalTime(arrTime);
                TicketDetail ticketDetail = new TicketDetail();
                ticketDetail.setTicketId(ticket1.getId());
                ticketDetail.setTrainId(train.getTrain());
                ticketDetailDao.save(ticketDetail);
                train.setSeats(
                        trainDao.existsById(train.getTrain())
                                ? trainDao.findById(train.getTrain())
                                                .orElseThrow()
                                                .getSeats()
                                        + ticket.getPassenger().size()
                                : ticket1.getPassenger().size());
                trainDao.save(train);
            }

            return ticket1;

        } catch (Exception e) {

            throw new Exception();
        }
    }

    @Override
    public long getSeatAvailable(String ticketId) throws Exception {

        try {
            Train train = trainDao.findById(ticketId).orElse(null);
            if (train != null) return TOTAL_SEATS - train.getSeats();

            return TOTAL_SEATS;

        } catch (Exception e) {

            throw new Exception();
        }
    }

    @Override
    public void cancelTrain(String trainId) {}

    @Override
    public Ticket getTicketDetail(long ticketId) throws Exception {

        try {
            Ticket ticket = ticketDao.findById(ticketId).orElse(null);
            if (ticket == null) {
                return null;
            }
            ticket.setPassenger(passengerDao.findAllByTicketId(ticket.getId()));

            List<TicketDetail> trainIds = ticketDetailDao.findAllByTicketId(ticket.getId());
            List<Train> trains = new ArrayList<>();
            for (TicketDetail ticketDetail : trainIds) {
                trains.add(trainDao.findById(ticketDetail.getTrainId()).orElse(null));
            }

            ticket.setTrains(trains);

            return ticket;

        } catch (Exception e) {

            throw new Exception();
        }
    }

    @Override
    public Ticket deleteTicket(long ticketId) throws Exception {
        return removeTicket(ticketId, true);
    }

    private Ticket removeTicket(long ticketId, boolean enforceCancellationWindow) throws Exception {
        Ticket ticket = ticketDao.findById(ticketId).orElse(null);
        if (ticket == null) {
            return null;
        }

        ticket.setPassenger(passengerDao.findAllByTicketId(ticket.getId()));
        List<TicketDetail> trainIds = ticketDetailDao.findAllByTicketId(ticket.getId());
        List<Train> trains = new ArrayList<>();
        for (TicketDetail ticketDetail : trainIds) {
            trains.add(trainDao.findById(ticketDetail.getTrainId()).orElse(null));
        }
        ticket.setTrains(trains);

        if (enforceCancellationWindow) {
            Date depDate = departureDate(ticket);
            if (depDate == null) {
                throw new IllegalArgumentException("Unreadable departure time");
            }
            DateTime departureTime = new DateTime(depDate);
            if (!departureTime.minusHours(1).isAfterNow()) {
                throw new IllegalStateException();
            }
        }

        releaseSeats(ticket);
        if (ticket.getPassenger() != null) {
            for (Passenger passenger : ticket.getPassenger()) {
                passengerDao.deleteById(passenger.getId());
            }
        }
        for (TicketDetail ticketDetail : trainIds) {
            ticketDetailDao.deleteById(ticketDetail.getId());
        }
        ticketDao.deleteById(ticketId);
        return ticket;
    }

    private Date departureDate(Ticket ticket) {
        if (ticket.getTrains() == null
                || ticket.getTrains().isEmpty()
                || ticket.getTrains().get(0) == null) {
            return null;
        }
        String departureTime = ticket.getTrains().get(0).getDepartureTime();
        if (departureTime == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("HHmmMM/dd/yyyy");
        try {
            return df.parse(departureTime);
        } catch (ParseException p) {
            p.printStackTrace();
            return null;
        }
    }

    private void releaseSeats(Ticket ticket) {
        if (ticket.getPassenger() == null || ticket.getTrains() == null) {
            return;
        }
        int count = ticket.getPassenger().size();
        for (Train train : ticket.getTrains()) {
            if (train == null || train.getTrain() == null) {
                continue;
            }
            Train persisted = trainDao.findById(train.getTrain()).orElse(null);
            if (persisted == null) {
                continue;
            }
            long remaining = persisted.getSeats() - count;
            if (remaining <= 0) {
                trainDao.deleteById(persisted.getTrain());
            } else {
                persisted.setSeats(remaining);
                trainDao.save(persisted);
            }
        }
    }

    @Override
    public List<Ticket> getAllTicket() throws Exception {
        try {

            return (List<Ticket>) ticketDao.findAll();

        } catch (Exception e) {

            throw new Exception();
        }
    }

    @Override
    public void resetData(long seats) {
        TOTAL_SEATS = seats;
        trainDao.deleteAll();
        passengerDao.deleteAll();
        ticketDao.deleteAll();
        ticketDetailDao.deleteAll();
    }

    @Override
    public String getEmailBody(Ticket ticket) throws Exception {

        String body = "";

        body += "<html><body>Your booking has been confirmed. Ticket Details are as below:<br>";
        body += "<p><b>" + "Cost: " + "</b>$" + ticket.getCost() + "</p>";
        body += "<p><b> Passengers: </b></p>";
        for (Passenger passenger : ticket.getPassenger()) {
            body += "<p>" + passenger.getName() + "</p>";
        }
        body += "<p><b>Trains: </b></p>";
        for (Train train : ticket.getTrains()) {
            body += "<p>Train: " + train.getTrain() + "</p>";
            body += "<p>Departure Station: " + train.getDepartureStation() + "</p>";
            body += "<p>Arrival Station: " + train.getArrivalStation() + "</p>";
            body += "<p>Departure Time: " + clock(train.getDepartureTime()) + "</p>";
            body += "<p>Arrival Time: " + clock(train.getArrivalTime()) + "</p>";
            body += "<br>";
        }
        body += "</body></html>";
        return body;
    }

    private String clock(String stored) {
        if (stored == null || stored.isEmpty()) {
            return "";
        }
        if (stored.length() >= 5 && stored.charAt(2) == ':') {
            return stored.substring(0, 5);
        }
        if (stored.length() >= 4) {
            return stored.substring(0, 2) + ":" + stored.substring(2, 4);
        }
        return stored;
    }

    @Override
    public int cost(String o, String d, int pcount, int trainfare) {
        char co = o.charAt(0);
        char cd = d.charAt(0);
        int diff = Math.abs(co - cd);
        int cost = diff / 5;

        return (diff % 5 > 0 ? cost + 1 : cost) * pcount * trainfare;
    }

    @Override
    public void trainCancel(String trainId) {
        List<TicketDetail> ticketDetails = new ArrayList<>(ticketDetailDao.findAllByTrainId(trainId));

        for (TicketDetail ticketDetail : ticketDetails) {
            try {
                if (!ticketDao.existsById(ticketDetail.getTicketId())) {
                    if (ticketDetailDao.existsById(ticketDetail.getId())) {
                        ticketDetailDao.deleteById(ticketDetail.getId());
                    }
                    continue;
                }
                Ticket ticket = removeTicket(ticketDetail.getTicketId(), false);
                if (ticket == null) {
                    continue;
                }
                User user = userDao.findById(ticket.getUserId()).orElse(null);
                if (user != null && user.getEmail() != null) {
                    String subject = "Ticket Cancellation";
                    String body = "<html><body>Ticket has been cancelled.</body></html>";
                    emailService.send(null, user.getEmail(), subject, body);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (trainDao.existsById(trainId)) {
            trainDao.deleteById(trainId);
        }
    }
}
