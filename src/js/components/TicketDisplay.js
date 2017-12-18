import React from "react";
import Trains from "./TicketDisplay/Trains"

//import CalcElement from "./Calculator/CalcElement"

export default class TicketDisplay extends React.Component{
	constructor(){
    super();
    
    
		
	}

  componentDidMount(){
    // console.log("here");
    // var i;
    // var ele = document.getElementById("passengers");
    // var ele1 = document.getElementById("trains");
    // var bod = "<ul>";
    // for(i=0;i<this.props.ticket.passenger.length;++i){
    //   bod += "<li> Name:" + this.props.ticket.passenger[i].name + "  Seat Number:"+  this.props.ticket.passenger[i].seatNumber + "</li>";

    // }
    // bod += "</ul>";
    // console.log(bod);
    // console.log(ele);
    // ele.innerHTML=bod;

   

  }
	render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    return (
      <table>
        <tbody>
          <tr>
            <td> id </td>
            <td> {this.props.ticket.id} </td>
          </tr>
          <tr>
            <td colspan='2'>
              
                {this.props.ticket.trains.map(train => <Trains ticket={train} />)}
              
            </td>
          </tr>
          <tr>
            <td> cost </td>
            <td> {this.props.ticket.cost} </td>
          </tr>
          <tr>
            <td>Passengers</td>
            <td colspan='2'> 
            {this.props.ticket.passenger.map(pass => <span>{pass.name} <br/></span>)}
              {/* <div id ="passengers"></div> */}
            </td>
          </tr>
        </tbody>
      </table>


    );
  }



}