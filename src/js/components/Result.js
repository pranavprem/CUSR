import React from "react"

export default class Result extends React.Component{

  book(){
    this.props.setticketid("ticketid");
  }


	render() {
    
    return (
     	<div class="container">
     	  <p>Trains that need to be taken: {this.props.trains.join(", ")}</p>
        <p>Arrival Time at Destination: {this.props.arrival}</p>
        <button class="btn" onClick={()=>{this.book}}>SEARCH</button>
		  </div>    
    );
  }



}