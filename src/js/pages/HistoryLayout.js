import React from "react";

import Header from "../components/Header.js"
import Footer from "../components/Footer.js"
import TicketDisplay from "../components/TicketDisplay.js"

export default class HistoryLayout extends React.Component {
  constructor(){
    super();
    this.state = {"userid":1, "ticketid":1, "ticket":null, "isloading":true};
    //const client = require('./Restclient');
   
  }

  componentDidMount() {
    console.log("it did this");
    var url = "https://52.90.170.105:8080/"+this.state.userid+"/ticket/";
    fetch(url, 
          {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
          })
    .then(result => result.json())
    .then(items => this.setState({"tickets":items}));
    this.forceUpdate();
  }

  
  
  render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    return (
      <div className="container">
        <Header/>
        <h1> Ticket History </h1>
        <div>
            {this.state && this.state.tickets  &&  this.state.tickets.map(ticket => <TicketDisplay ticket={ticket} />)}
        
        </div>
        <Footer/>
      </div>    
    );
  }
}
