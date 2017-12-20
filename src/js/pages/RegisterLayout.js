import React from 'react';
import ReactDOM from 'react-dom';

import Header from "../components/Header.js"
import Footer from "../components/Footer.js"



export default class RegisterLayout extends React.Component {
  constructor(){
    super();
    //this.state={"email":null};  
  }
  
    register(){
      var username=document.getElementById("username").value;
      var password=document.getElementById("password").value;
      var firstname=document.getElementById("firstname").value;
      var lastname=document.getElementById("lastname").value;

      var url = "http://52.90.170.105:80/login?user="+username+"&password="+password+"&firstname="+firstname+"&lastname="+lastname;
      fetch(url, 
            {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json'
              }
            })
      .then(result => result.json())
      .then(item => this.props.setuserid(item.userid));

      this.props.setcurrent("search");

    }
  
  render() {
    
    return (
      <div className="container">
        <center>
        <input placeholder="Email ID" type="text" id="username"/><br/><br/>
        <input placeholder="Password" type="password" id="password"/><br/><br/>
        <input placeholder="firstname" type="text" id="firstname"/><br/><br/>
        <input placeholder="lastname" type="text" id="lastname"/><br/><br/>
        <button class="btn" onClick={()=>{this.register()}}>LOGIN</button><br/>
        
      </center>

        {/* {this.state.email && <Link to='/search'>Proceed, {this.state.email}</Link>} */}
        
        
        
        

        

      </div>    
    );
  }
}
