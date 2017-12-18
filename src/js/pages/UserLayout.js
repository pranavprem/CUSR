import React from 'react';
import ReactDOM from 'react-dom';
import FacebookLogin from 'react-facebook-login';

import Header from "../components/Header.js"
import Footer from "../components/Footer.js"

const responseFacebook = (response) => {
    console.log(response);
  }

export default class UserLayout extends React.Component {
  constructor(){
    super();  
  }
  
  render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    return (
      <div className="container">
        <Header/>

         <FacebookLogin
            appId="164569124158280"
            autoLoad={true}
            fields="name,email"
            onClick={componentClicked}
            callback={responseFacebook} />
        
        <Footer/>
      </div>    
    );
  }
}
