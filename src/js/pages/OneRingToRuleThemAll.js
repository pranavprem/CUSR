import React from "react";

import HistoryLayout from "./HistoryLayout"
import SearchLayout from "./SearchLayout"
import TicketLayout from "./TicketLayout"
import UserLayout from "./UserLayout"
import Header from "../components/Header"
import Footer from "../components/Footer"


export default class Layout extends React.Component{
	constructor(){
    super();
    this.state = {
        "current":"user",
        "email":null,
        "userid": null,
        "ticketid": null,
        "ticket": null,
        "trains": null,
        "arrival": null,
        "from":"Station",
        "to":"Station",
        "time":null,
        };
    this.getemail.bind(this);
    this.getuserid.bind(this);
    this.getarrival.bind(this);
    this.getfrom.bind(this);
    this.getto.bind(this);
    this.gettime.bind(this);
    this.gettrains.bind(this);
    this.getticket.bind(this);
    this.getticketid.bind(this);
    this.setarrival.bind(this);
    this.setcurrent.bind(this);
    this.setemail.bind(this);
    this.setfrom.bind(this);
    this.setticketid.bind(this);
    this.setticket.bind(this);
    this.settime.bind(this);
    this.setto.bind(this);
    this.settrains.bind(this);
    this.setuserid.bind(this);
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
            {this.state.current=="user" && <UserLayout setcurrent= {this.setcurrent.bind(this)} setuserid={this.setuserid} setemail={this.setemail.bind(this)} />}
            {this.state.current=="search" && <SearchLayout setcurrent={this.setcurrent} settrains={this.settrains} setarrival={this.setarrival} settime={this.settime} setfrom={this.setfrom} setto={this.setto}/>}
            {this.state.current=="ticket" && <SearchLayout setcurrent={this.setcurrent} getuserid={this.getuserid} getticketid={this.getticketid}/>}
            {this.state.current=="history" && <SearchLayout setcurrent={this.setcurrent} getuserid={this.getuserid}/>}


          <Footer />

      </div>


    );
  }



}