import React from "react";
import Menu from "./Search/Menu.js"

//import CalcElement from "./Calculator/CalcElement"

export default class Search extends React.Component{
	constructor(){
		super();
		
	}

	render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    return (
      <div>
      	From: <Menu Setter={this.props.SetFrom} SetValue={this.props.From}/>
      	To: <Menu Setter={this.props.SetTo} SetValue={this.props.To}/>
        <button class="btn" style = {this.buttoStyle} onClick={()=>{this.props.Search()}}>SEARCH</button>
      </div>
          
    );
  }



}