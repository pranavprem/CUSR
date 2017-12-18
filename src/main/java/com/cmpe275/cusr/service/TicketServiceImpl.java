package com.cmpe275.cusr.service;

import com.cmpe275.cusr.dao.PassengerDao;
import com.cmpe275.cusr.dao.TicketDao;
import com.cmpe275.cusr.dao.TicketDetailDao;
import com.cmpe275.cusr.dao.TrainDao;
import com.cmpe275.cusr.model.Passenger;
import com.cmpe275.cusr.model.Ticket;
import com.cmpe275.cusr.model.TicketDetail;
import com.cmpe275.cusr.model.Train;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private PassengerDao passengerDao;
    @Autowired
    private TrainDao trainDao;
    @Autowired
    private TicketDetailDao ticketDetailDao;

    private static long TOTAL_SEATS = 1000;

    @Override
    public List<Ticket> getTickets(long userId) {
        List<Ticket> tickets = ticketDao.findAllByUserId(userId);
        for(Ticket ticket: tickets){
            ticket.setPassenger(passengerDao.findAllByTicketId(ticket.getId()));
            List<TicketDetail> trainIds = ticketDetailDao.findAllByTicketId(ticket.getId());
            List<Train> trains = new ArrayList<>();
            for( TicketDetail ticketDetail : trainIds) {
                trains.add(trainDao.findOne(ticketDetail.getTrainId()));
            }

            ticket.setTrains(trains);
        }

        return tickets;
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        Ticket ticket1 = ticketDao.save(ticket);
        for(Passenger passenger : ticket.getPassenger()){
            passenger.setTicketId(ticket1.getId());
            passengerDao.save(passenger);
        }
        ticket1.setPassenger(ticket.getPassenger());

        for (Train train : ticket1.getTrains()) {
            TicketDetail ticketDetail = new TicketDetail();
            ticketDetail.setTicketId(ticket1.getId());
            ticketDetail.setTrainId(train.getTrain());
            ticketDetailDao.save(ticketDetail);
            train.setSeats(trainDao.exists(train.getTrain()) ?
                    trainDao.findOne(train.getTrain()).getSeats() + ticket.getPassenger().size() :
                    ticket1.getPassenger().size());
            trainDao.save(train);
        }

        return ticket1;
    }

    @Override
    public long getSeatAvailable(String ticketId) {
        Train train = trainDao.findOne(ticketId);
        if( train != null)
            return TOTAL_SEATS - train.getSeats();

        return TOTAL_SEATS;
    }

    @Override
    public void cancelTrain(String trainId) {

    }

    @Override
    public Ticket getTicketDetail(long ticketId) {
        Ticket ticket = ticketDao.findOne(ticketId);
        ticket.setPassenger(passengerDao.findAllByTicketId(ticket.getId()));

        List<TicketDetail> trainIds = ticketDetailDao.findAllByTicketId(ticket.getId());
        List<Train> trains = new ArrayList<>();
        for( TicketDetail ticketDetail : trainIds) {
            trains.add(trainDao.findOne(ticketDetail.getTrainId()));
        }

        ticket.setTrains(trains);

        return ticket;
    }

    @Override
    public Ticket deleteTicket(long ticketId) {
        Ticket ticket = ticketDao.findOne(ticketId);

        if (ticket == null)
            return null;

        DateTime departureTime = new DateTime(Long.parseLong(ticket.getTrains().get(0).getDepartureTime())*1000L);
        if (departureTime.minusHours(1).isAfterNow()) {
            ticketDao.delete(ticketId);

            return ticket;
        } else throw new IllegalStateException();
    }

    @Override
    public List<Ticket> getAllTicket() {
        return (List<Ticket>) ticketDao.findAll();
    }

    @Override
    public void resetData(long seats) {
        this.TOTAL_SEATS = seats;
        trainDao.deleteAll();
        passengerDao.deleteAll();
        ticketDao.deleteAll();
        ticketDetailDao.deleteAll();
        //TODO reset train, ticket, passenger and ticketdetail table
    }
}
