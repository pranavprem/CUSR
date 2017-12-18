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
    this.state={"email":null};  
  }
  
  responseFacebook (response) {
      this.setState({"email":response.email});
    }

  responseGoogle (googleUser) {
        var id_token = googleUser.getAuthResponse().id_token;
        var url = ""+id_token;
        fetch(url, 
          {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
          })
          .then(result => result.json())
          .then(item => this.setState({"email":item.email}));
        
      }
  
  render() {
    
    return (
      <div className="container">
        <Header/>

        <FacebookLogin
        appId="164569124158280"
        autoLoad={true}
        fields="name,email,picture"
        callback={this.responseFacebook} />
      
        <br/>

        <GoogleLogin socialId="887093608373-qpgq6rm2adpa6mnik8c1v69f45tdj2kt.apps.googleusercontent.com"
                     className="google-login"
                     scope="profile"
                     fetchBasicProfile={true}
                     responseHandler={this.responseGoogle}
                     buttonText="Login With Google"/>
        
        <Footer/>

        {this.state.email && <Link to='/search'>Proceed, {this.state.email}</Link>}

      </div>    
    );
  }
}
