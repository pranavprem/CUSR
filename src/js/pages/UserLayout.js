import React from "react";

import Header from "../components/Header.js"
import Footer from "../components/Footer.js"

export default class UserLayout extends React.Component {
  constructor(){
    super();
    this.state = {"userid":null, "ticketid":null, "ticket":null, "isloading":true};
    //const client = require('./Restclient');
   
  }

  Login() {
    console.log("it did this");
    var url = "http://52.90.170.105:8080/"+this.state.userid+"/ticket/"+this.state.ticketid;
    fetch(url, 
          {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
          })
    .then(result => result.json())
    .then(item => this.setState({"isloading":"false", "ticket":item}));
    console.log(this.state.ticket);
    
  }

  
  
  render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    return (
      <div className="container">
        <Header/>
        
        <Footer/>
      </div>    
    );
  }
}
