import React from 'react';
import ReactDOM from 'react-dom';
import FacebookLogin from 'react-facebook-login';
import {GoogleLogin} from 'react-google-login-component';

import Header from "../components/Header.js"
import Footer from "../components/Footer.js"



export default class UserLayout extends React.Component {
  constructor(){
    super();  
  }
  
  responseFacebook = (response) => {
      console.log(response);
    }

  responseGoogle (googleUser) {
        var id_token = googleUser.getAuthResponse().id_token;
        console.log({accessToken: id_token});
        //anything else you want to do(save to localStorage)...
      }
  
  render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    return (
      <div className="container">
        <Header/>

        {/*  <FacebookLogin
            appId="164569124158280"
            autoLoad={true}
            fields="name,email"
            onClick={componentClicked}
            callback={responseFacebook} /> */}

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
      </div>    
    );
  }
}
