import React from "react"

export default class Result extends React.Component{

	render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    return (
     	<div class="container">
     	  <p>Trains that need to be taken: {this.props.trains.join(", ")}</p>
        <p>Arrival Time at Destination: {this.props.arrival}</p>
		  </div>    
    );
  }



}