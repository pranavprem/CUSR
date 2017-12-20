import React from "react";
import Menu from "./Search/Menu.js"
import Datetime from 'react-datetime'
import Passenger from "./Search/Passenger"

export default class Search extends React.Component{
	constructor(){
    super();
    this.type = ["Regular", "Express"];
    this.passen = [1,2,3,4,5];
    this.round = ["Yes", "No"];
    this.stations = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
  }

	render() {

    return (
      <div>
      	From: <Menu options={this.stations} setter={this.props.setfrom} getter={this.props.getfrom}/>
      	To: <Menu options={this.stations} setter={this.props.setto} getter={this.props.getto}/>
        Departure: <Datetime onChange={(mom)=>{this.props.settime(mom.format('HH:mm')); this.props.setdate(mom.format('MM/DD/YYYY')); }} />
        Number of Passengers: <Menu options={this.passen} setter={this.props.setpassengers} getter={this.props.getpassengers} />
        RoundTrip: <Menu options={this.round} setter={this.props.setround} getter={this.props.getround} />
        {/* Type of Trip: <Menu options={this.type} setter={this.props.settype} getter={this.props.gettype} /> */}
        {this.props.getround()=='Yes' && <p>Roundtrip Departure Time:</p>}
        {this.props.getround()=='Yes' && <Datetime onChange={(mom)=>{this.props.settime2(mom.format('HH:mm')); this.props.setdate2(mom.format('MM/DD/YYYY')); }} />}
        Passengers: <br/>
        <Passenger setter={this.props.pass1}/>
        {this.props.getpassengers()>1 && <Passenger setter={this.props.pass2}/>}
        {this.props.getpassengers()>2 && <Passenger setter={this.props.pass3}/>}
        {this.props.getpassengers()>3 && <Passenger setter={this.props.pass4}/>}
        {this.props.getpassengers()>4 && <Passenger setter={this.props.pass5}/>}
        <button class="btn" onClick={()=>{this.props.Search()}}>SEARCH</button>
      </div>
          
    );
  }



}