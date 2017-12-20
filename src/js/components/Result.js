import React from "react"

export default class Result extends React.Component{

  book(){
    if(this.props.round=="No"){
      for(var i=0;i<this.props.trains.length;++i){
        this.props.trains[i]=this.props.trains[i]+this.props.date1;
      }
    }else{
      for(var i=0;i<this.props.trains.length/2;++i){
        this.props.trains[i]=this.props.trains[i]+this.props.date1;
      }
      for(;i<this.props.trains.length;++i){
        this.props.trains[i]=this.props.trains[i]+this.props.date2;
      }
    }
      
    var element={
      "userid":this.props.userid,
      "trains":this.props.trains,
      "route":this.props.route,
      "cost":this.props.cost,
      "passenger":this.props.passengers
    };

    var url="http://52.90.170.105:80/"+this.props.userid+"/ticket/book";
    
    console.log(JSON.stringify(element));
    fetch(url, 
      {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body:JSON.stringify(element)
      })
    .then(result => result.json())
    .then(items => this.Handle(items));
  }


  Handle(items){
    var url="http://search-url-shortener-t76w2u5nrhzsacc3n2oy3lvozi.us-west-1.es.amazonaws.com/cusr/cusr/"+items.id;
    var element={
      "userid":this.props.userid,
      "trains":this.props.trains,
      "route":this.props.route,
      "cost":this.props.cost,
      "passenger":this.props.passengers
    };
    fetch(url, 
      {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body:JSON.stringify(element)
      });

  }


	render() {
    
    return (
     	<div class="container">
     	  <p>Trains that need to be taken: {this.props.trains.join(", ")}</p>
        <p>Arrival Time at Destination: {this.props.arrivalTime}</p>
        {this.props.arrivalTime2 && <p>Return Trip Arrival Time: {this.props.arrivalTime2}</p>}
        <p>Route: {this.props.route.join("->")} </p>
        <p>Cost: {this.props.cost}</p>
        <button class="btn" onClick={()=>{this.book()}}>BOOK</button>
		  </div>    
    );
  }



}