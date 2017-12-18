import React from "react";
import Header from "../components/Header.js"
import Footer from "../components/Footer.js"
import Search from "../components/Search.js"
import Result from "../components/Result.js"

export default class SearchLayout extends React.Component {
  constructor(){
    super();
    this.state = {"trains":null, "arrival":null, "from":"Station", "to":"Station", "time":""};
    //const client = require('./Restclient');
    this.onResponse = this.onResponse.bind(this);
  }
  SetFrom(from){
    this.setState({"from":from.listValue});
  }
  SetTo(to){
    this.setState({"to":to.listValue});
  }
  SetTime(time){
    this.setState({"time":time});
  }
  onResponse(result){
    this.setState({"trains":result[0].trains,"arrival":result[0].arrivalTime});
  }
  Search(from, to, time){
    var url = "http://52.90.170.105:80/search/"+this.state.from+"/"+this.state.to+"/"+"15:30";
    fetch(url, 
          {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
          })
    .then(result => result.json())
    .then(items => this.setState({"trains":items[0].trains,"arrival":items[0].arrivalTime}));

  }
  render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    return (
      <div class="container">
        <Header/>
          <h1>Search</h1>
        <Search Search={this.Search.bind(this)} SetFrom={this.SetFrom.bind(this)} SetTo={this.SetTo.bind(this)} From={this.state.from} To={this.state.to}/>
        <br/><br/><br/><br/><br/><br/>
        {this.state.trains && this.state.arrival && <Result trains={this.state.trains} arrival={this.state.arrival}/>}
        <Footer/>
      </div>    
    );
  }
}
