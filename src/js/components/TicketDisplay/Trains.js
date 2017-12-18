import React from "react";


export default class menu extends React.Component{
	constructor(){
		super();
	}

	render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    var Setter = this.props.Setter;
    return (
      <table>
        <tbody>
          <tr>
            <td> train </td>
            <td> {this.props.ticket.train} </td>
          </tr>
          <tr>
            <td> departure station </td>
            <td> {this.props.ticket.departureStation} </td>
          </tr>
          <tr>
            <td> arrival station </td>
            <td> {this.props.ticket.arrivalStation} </td>
          </tr>
          <tr>
            <td> departure time </td>
            <td> {this.props.ticket.departureTime} </td>
          </tr>
          <tr>
            <td> arrival time </td>
            <td> {this.props.ticket.arrivalTime} </td>
          </tr>
        </tbody>
      </table>
          
    );
  }



}

