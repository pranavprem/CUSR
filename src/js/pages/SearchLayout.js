import React from "react";
import Header from "../components/Header.js"
import Footer from "../components/Footer.js"
import Search from "../components/Search.js"
import Result from "../components/Result.js"




export default class SearchLayout extends React.Component {
  constructor(){
    super();
    this.state = {"round":"No","passengers":1, "type":"Regular","pass":["","","","",""]}; 
    this.setround = this.setround.bind(this);
    this.setpassengers = this.setpassengers.bind(this);
    this.settype = this.settype.bind(this);
    this.getround = this.getround.bind(this);
    this.getpassengers = this.getpassengers.bind(this);
    this.gettype = this.gettype.bind(this);
  }

  getpass(){
    return this.state.pass;
  }

  setpass1(name){
    var pass=this.state.pass;
    pass[0]=name.target.value;
    this.setState({"pass":pass});
  }

  setpass2(name){
    var pass=this.state.pass;
    pass[1]=name.target.value;
    this.setState({"pass":pass});
  }

  setpass3(name){
    var pass=this.state.pass;
    pass[2]=name.target.value;
    this.setState({"pass":pass});
  }

  setpass4(name){
    var pass=this.state.pass;
    pass[3]=name.target.value;
    this.setState({"pass":pass});
  }

  setpass5(name){
    var pass=this.state.pass;
    pass[4]=name.target.value;
    this.setState({"pass":pass});
  }

  setround(round){
    this.setState({"round":round.listValue});
  }
  setpassengers(round){
    this.setState({"passengers":round.listValue});
  }
  settype(round){
    this.setState({"type":round.listValue});
  }

  getround(){
    return this.state.round;
  }
  getpassengers(){
    return this.state.passengers;
  }
  gettype(){
    return this.state.type;
  }


  SetFrom(from){
    this.props.setfrom(from.listValue);
  }
  SetTo(to){
    this.props.setto(to.listValue);
  }

  Handle(items){
    var i;
    var trains="", arrivalTime="";
    trains += items[0].trains;
    arrivalTime += items[0].arrivalTime;
    for(i=1;i<items.length();++i){
      trains += "," + items[i].trains;
      arrivalTime += "," + items[i].arrivalTime;
    }
    this.props.settrains(trains);
    this.props.setarrival(arrivalTime);
  }
  
  Search(from, to, time){
    if(this.state.type == "express")
    {  var url = "http://52.90.170.105:80/search/"+this.props.getfrom()+"/"+this.props.getto()+"/"+this.props.gettime();
      fetch(url, 
            {
              method: 'GET',
              headers: {
                  'Content-Type': 'application/json'
              }
            })
      .then(result => result.json())
      .then(items => this.Handle(items));
    }
    else {
      var url = "http://52.90.170.105:80/searchregular/"+this.props.getfrom()+"/"+this.props.getto()+"/"+this.props.gettime();
      fetch(url, 
            {
              method: 'GET',
              headers: {
                  'Content-Type': 'application/json'
              }
            })
      .then(result => result.json())
      .then(items => this.Handle(items));
    }

  }
  render() {
    
    return (
      <div class="container">
          <h1>Search</h1>
        <Search pass1={this.setpass1.bind(this)} pass2={this.setpass2.bind(this)} pass3={this.setpass3.bind(this)} pass4={this.setpass4.bind(this)} pass5={this.setpass5.bind(this)} settype = {this.settype} gettype={this.gettype} setround={this.setround} getround={this.getround} setpassengers={this.setpassengers} getpassengers={this.getpassengers} Search={this.Search.bind(this)} setfrom={this.SetFrom.bind(this)} setto={this.SetTo.bind(this)} getfrom={this.props.getfrom} getto={this.props.getto} settime={this.props.settime} setdate={this.props.setdate}/>
        <br/><br/><br/><br/><br/><br/>
        {this.props.gettrains() && this.props.getarrival() && <Result pass={this.props.getpass} trains={this.props.gettrains} arrival={this.props.getarrival} setticketid={this.props.setticketid} From={this.props.getfrom} To={this.props.getto}/>}
        
      </div>    
    );
  }
}
