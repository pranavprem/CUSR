import React from 'react';
import ReactDOM from 'react-dom';
import FacebookLogin from 'react-facebook-login';
import {GoogleLogin} from 'react-google-login-component';
import {Link} from 'react-router-dom' 

import Header from "../components/Header.js"
import Footer from "../components/Footer.js"



export default class UserLayout extends React.Component {
  constructor(){
    super();
    //this.state={"email":null};  
  }
  
  responseFacebook = (response) => {
      this.props.setemail(response.email);
      this.props.setcurrent("search");
    }

      
    

  responseGoogle (googleUser) {
        var id_token = googleUser.getAuthResponse().id_token;
        var url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token="+id_token;
        fetch(url, 
          {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
          })
          .then(result => result.json())
          .then(item => this.props.setemail(item.email));
      }

    login(){
      var username=document.getElementById("username").value;
      var password=document.getElementById("password").value;

      var url = "http://52.90.170.105:80/login?user="+username+"&password="+password;
      fetch(url, 
            {
              method: 'GET',
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
        <button class="btn" onClick={()=>{this.login()}}>LOGIN</button><br/>
        
<br/><br/><br/>


        <FacebookLogin
        appId="164569124158280"
        autoLoad={true}
        fields="name,email,picture"
        callback={this.responseFacebook} />
      
        <br/><br/>

        <GoogleLogin socialId="887093608373-qpgq6rm2adpa6mnik8c1v69f45tdj2kt.apps.googleusercontent.com"
                     className="google-login"
                     scope="profile"
                     fetchBasicProfile={true}
                     responseHandler={this.responseGoogle}
                     >
        
    <span> Login with Google</span>
      </GoogleLogin>
      </center>

        {/* {this.state.email && <Link to='/search'>Proceed, {this.state.email}</Link>} */}
        
        
        
        

        

      </div>    
    );
  }
}
