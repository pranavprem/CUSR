import React from "react";
import Header from "../components/Header.js"
import Footer from "../components/Footer.js"
import Search from "../components/Search.js"
import Result from "../components/Result.js"




export default class SearchLayout extends React.Component {
  constructor(){
    super();
    this.state = {"round":"No","passengers":1, "type":"Regular", "pass":["","","","",""], "result":null, "time2":null, "date2":null}; 
    this.setround = this.setround.bind(this);
    this.setpassengers = this.setpassengers.bind(this);
    this.settype = this.settype.bind(this);
    this.getround = this.getround.bind(this);
    this.getpassengers = this.getpassengers.bind(this);
    this.gettype = this.gettype.bind(this);
    this.getresult = this.getresult.bind(this);
    this.setresult = this.setresult.bind(this);
    this.settime2 = this.settime2.bind(this);
    this.gettime2 = this.gettime2.bind(this);
    this.getdate2 = this.getdate2.bind(this);
    this.setdate2 = this.setdate2.bind(this);
  }

  setdate2(date2){
    this.setState({"date2":date2});
  }

  getdate2(){
    return this.state.date2;
  }

  getpass(){
    return this.state.pass;
  }

  getresult(){
    return this.state.result;
  }

  setresult(result){
    this.setState({"result":result});
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


  settime2(time2){
    this.setState({"time2":time2});
  }

  gettime2(){
    return this.state.time2;
  }

  SetFrom(from){
    this.props.setfrom(from.listValue);
  }
  SetTo(to){
    this.props.setto(to.listValue);
  }

  Handle(items){
    // var i;
    console.log(items);
    // var trains=[], arrivalTime=[];
    // //var ele = document.getElementById("results");
    // var inside = "";
    
    // for(i=0;i<items.length;++i){
    //   trains [i]= items[i].trains;
    //   arrivalTime [i]= items[i].arrivalTime;
    //   inside +="<Result pass={this.props.getpass} trains={["+items[i].trains+"]} arrival={"+items[i].arrivalTime+"} setticketid={this.props.setticketid} From={this.props.getfrom} To={this.props.getto}/>";



    // }
    // this.props.settrains(trains);
    // this.props.setarrival(arrivalTime);

    // console.log(inside);
    // console.log(ele);
    //ele.innerHTML=inside;
    this.setState({"result":items});



  }
  
  Search(from, to, time){
    var url = "http://52.90.170.105:80/search/"+this.props.getfrom()+"/"+this.props.getto()+"/"+this.props.gettime()+"/"+this.getround()+'/'+this.gettime2();
    
      
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

  done(){
    this.props.setcurrent("history");
  }

  render() {
    
    return (
      <div class="container">
          <h1>Search</h1>
        <Search getdate2={this.setdate2} setdate2={this.setdate2} gettime2={this.gettime2} settime2={this.settime2} pass1={this.setpass1.bind(this)} pass2={this.setpass2.bind(this)} pass3={this.setpass3.bind(this)} pass4={this.setpass4.bind(this)} pass5={this.setpass5.bind(this)} settype = {this.settype} gettype={this.gettype} setround={this.setround} getround={this.getround} setpassengers={this.setpassengers} getpassengers={this.getpassengers} Search={this.Search.bind(this)} setfrom={this.SetFrom.bind(this)} setto={this.SetTo.bind(this)} getfrom={this.props.getfrom} getto={this.props.getto} settime={this.props.settime} setdate={this.props.setdate}/>
        <br/><br/><br/><br/><br/><br/>
        <div id="results"></div>
        {this.state.result && this.state.result.map(res=><Result round={this.state.round} userid={this.props.userid} date1={this.props.getdate()} date2={this.state.date2} passengers={this.state.pass} trains={res.trains} arrivalTime={res.arrivalTime} arrivalTime2={res.arrivalTime2} route={res.route} cost={res.cost} />)} 
        <button class="btn" onClick={()=>{this.done}}>DONE</button>   
      </div>    
    );
  }
}
