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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public List<Ticket> getTickets(long userId) throws Exception {
    	
    	try {
    		
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
    	}catch(Exception e) {
    		
			throw new Exception();

    	}
    }

    @Override
    public Ticket addTicket(Ticket ticket) throws Exception {
    	
    	try {
    	
        Ticket ticket1 = ticketDao.save(ticket);
        for(Passenger passenger : ticket.getPassenger()){
            passenger.setTicketId(ticket1.getId());
            passengerDao.save(passenger);
        }
        ticket1.setPassenger(ticket.getPassenger());

        for (Train train : ticket1.getTrains()) {
            /*int hour = Integer.parseInt(train.getDepartureTime().split(":")[0]);
            int minute = Integer.parseInt(train.getDepartureTime().split(":")[1]);
            int day = Integer.parseInt(train.getTrain().substring(5).split("/")[1].split("/")[0]);
            int month = Integer.parseInt(train.getTrain().substring(5).split("/")[0]);;
            int year = Integer.parseInt(train.getTrain().substring(5).split("/")[1].split("/")[1]);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            Date date = cal.getTime();*/
            String depTime = train.getDepartureTime() + train.getTrain().substring(6);
            String arrTime = train.getArrivalTime() + train.getTrain().substring(6);

            train.setDepartureTime(depTime);
            train.setArrivalTime(arrTime);
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
    	
    	}catch(Exception e) {
    		
			throw new Exception();

    	}
    }

    @Override
    public long getSeatAvailable(String ticketId) throws Exception {
    	
    	try {
        Train train = trainDao.findOne(ticketId);
        if( train != null)
            return TOTAL_SEATS - train.getSeats();

        return TOTAL_SEATS;
    	
    	}catch(Exception e) {
    		
			throw new Exception();

    	}
    }

    @Override
    public void cancelTrain(String trainId) {

    }

    @Override
    public Ticket getTicketDetail(long ticketId) throws Exception {
    	
    	try {
        Ticket ticket = ticketDao.findOne(ticketId);
        ticket.setPassenger(passengerDao.findAllByTicketId(ticket.getId()));

        List<TicketDetail> trainIds = ticketDetailDao.findAllByTicketId(ticket.getId());
        List<Train> trains = new ArrayList<>();
        for( TicketDetail ticketDetail : trainIds) {
            trains.add(trainDao.findOne(ticketDetail.getTrainId()));
        }

        ticket.setTrains(trains);

        return ticket;
    	
    	}catch(Exception e) {
    		
			throw new Exception();

    	}
    }

    @Override
    public Ticket deleteTicket(long ticketId) throws Exception{
    	try {

            Ticket ticket = ticketDao.findOne(ticketId);
            ticket.setPassenger(passengerDao.findAllByTicketId(ticket.getId()));
            List<TicketDetail> trainIds = ticketDetailDao.findAllByTicketId(ticket.getId());
            List<Train> trains = new ArrayList<>();
            for( TicketDetail ticketDetail : trainIds) {
                trains.add(trainDao.findOne(ticketDetail.getTrainId()));
            }

            ticket.setTrains(trains);

        if (ticket == null)
            return null;

            Date depDate = null;
            DateFormat df = new SimpleDateFormat("HHmmMM/dd/yyyy");
            try{
                depDate = df.parse(ticket.getTrains().get(0).getDepartureTime());
            }catch(ParseException p) {
                p.printStackTrace();
            }
        DateTime departureTime = new DateTime(depDate);
        if (departureTime.minusHours(1).isAfterNow()) {
            ticketDao.delete(ticketId);

            return ticket;
        } else throw new IllegalStateException();
        
    	}catch(Exception e) {
    		
			throw new Exception();

    	}
    }

    @Override
    public List<Ticket> getAllTicket() throws Exception {
        try {
        	
        	return (List<Ticket>) ticketDao.findAll();
        
        }catch(Exception e) {
    		
			throw new Exception();

    	}
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

	@Override
	public String getEmailBody(Ticket ticket) throws Exception {
		// TODO Auto-generated method stub
				
		String body = "";
		
		body += "<html><body>Your booking has been confirmed. Ticket Details are as below:<br>";		
		body += "<p><b>" + "Cost: " + "</b>$"+ ticket.getCost() + "</p>";		
		body += "<p><b> Passengers: </b></p>";				
        for(Passenger passenger : ticket.getPassenger()){
        	body += "<p>" + passenger.getName() + "</p>";
        
        }        	
        body += "<p><b>Trains: </b></p>";        
        for (Train train : ticket.getTrains()) {        	
        	body += "<p>Train: " + train.getTrain() + "</p>";
        	body += "<p>Departure Station: " + train.getDepartureStation() + "</p>";
        	body += "<p>Arrival Station: " + train.getArrivalStation() + "</p>";
        	body += "<p>Departure Time: " + train.getDepartureTime().substring(0, 2) + ":" + train.getDepartureTime().substring(2, 4) + "</p>";
        	body += "<p>Arrival Time: " + train.getArrivalTime().substring(0, 2) + ":" + train.getArrivalTime().substring(2, 4) + "</p>";
        	body += "<br>";    	
        }        
        body += "</body></html>";        
        return body;		
	}

    @Override
    public int cost(String o, String d, int pcount, int trainfare)
    {
        char co = o.charAt(0);
        char cd = d.charAt(0);
        int diff = Math.abs(co-cd);
        int cost = diff/5;

        return (diff%5 > 0 ? cost + 1 : cost) * pcount * trainfare;
    }

    @Override
    public void trainCancel(String trainId) {
        List<TicketDetail> ticketDetails = ticketDetailDao.findAllByTrainId(trainId);

        for (TicketDetail ticketDetail : ticketDetails) {
            try {
                deleteTicket(ticketDetail.getTicketId());
                ticketDetailDao.delete(ticketDetail.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
