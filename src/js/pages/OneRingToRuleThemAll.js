import React from "react";

import HistoryLayout from "./HistoryLayout"
import SearchLayout from "./SearchLayout"
import TicketLayout from "./TicketLayout"
import UserLayout from "./UserLayout"
import RegisterLayout from "./RegisterLayout"
import Header from "../components/Header"
import Footer from "../components/Footer"


export default class Layout extends React.Component{
	constructor(){
    super();
    this.state = {
        "current":"user",
        "email":null,
        "userid": 1,
        "ticketid": null,
        "ticket": null,
        "trains": null,
        "arrival": null,
        "from":"Station",
        "to":"Station",
        "time":null,
        "date":null,
        "time2":null
        };
    
    }
    settime2(time2){
        this.setState({"time2":time2});
    }
    gettime2(){
        return this.state.time2;
    }
    setdate(date){
        this.setState({"date":date});
    }
    getdate(){
        return this.state.date;
    }
    getemail(){
        return this.state.email;
    }
    getuserid(){
        return this.state.userid;
    }
    getticketid(){
        return this.state.ticketid;
    }
    getticket(){
        return this.state.ticket;
    }
    gettrains(){
        return this.state.trains;
    }
    getarrival(){
        return this.state.arrival;
    }
    getfrom(){
        return this.state.from;
    }
    getto(){
        return this.state.to;
    }
    gettime(){
        return this.state.time;
    }
    
    setemail(email){
        this.setState({"email":email});
        var url = "http://52.90.170.105:80/sociallogin?email="+email;
        fetch(url, 
              {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
              })
        .then(result => result.json())
        .then(item => console.log(item));
    }
    setuserid(userid){
        this.setState({"userid":userid});
    }
    setticketid(ticketid){
        this.setState({"ticketid":ticketid});
    }
    setticket(ticket){
        this.setState({"ticket":ticket});
    }
    settrains(trains){
        this.setState({"trains":trains});
    }
    setarrival(arrival){
        this.setState({"arrival":arrival});
    }
    setfrom(from){
        this.setState({"from":from});
    }
    setto(to){
        this.setState({"to":to});
    }
    settime(time){
        this.setState({"time":time});
    }
    setcurrent(current){
        this.setState({"current":current});
    }
    

	render() {
    
    return (
      <div>
          <Header />
            {this.state.current=="user" && <UserLayout setcurrent= {this.setcurrent.bind(this)} setuserid={this.setuserid.bind(this)} setemail={this.setemail.bind(this)} />}
            {this.state.current=="search" && <SearchLayout userid={this.state.userid} getfrom={this.getfrom.bind(this)} getto={this.getto.bind(this)} getdate={this.getdate.bind(this)} gettime={this.gettime.bind(this)} gettime2={this.gettime2.bind(this)} settime2={this.settime2.bind(this)} setcurrent={this.setcurrent.bind(this)} settrains={this.settrains.bind(this)} gettrains={this.gettrains.bind(this)} setdate={this.setdate.bind(this)} getarrival={this.getarrival.bind(this)} setarrival={this.setarrival.bind(this)} settime={this.settime.bind(this)} setfrom={this.setfrom.bind(this)} setto={this.setto.bind(this)} setticketid={this.setticketid.bind(this)}/>}
            {this.state.current=="ticket" && <TicketLayout setcurrent={this.setcurrent.bind(this)} getuserid={this.getuserid.bind(this)} getticketid={this.getticketid.bind(this)}/>}
            {this.state.current=="history" && <HistoryLayout setcurrent={this.setcurrent.bind(this)} getuserid={this.getuserid.bind(this)} setticketid={this.setticketid.bind(this)}/>}
            {this.state.current=="register" && <RegisterLayout/>}

          <Footer />

      </div>


    );
  }



}