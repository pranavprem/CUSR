import React from "react";


//import CalcElement from "./Calculator/CalcElement"

export default class menu extends React.Component{
	constructor(){
		super();
    this.options=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
	}

	render() {
    // setTimeout(()=>{
    //   this.setState({name:"Pranav2"});
    // },2000);
    var Setter = this.props.Setter;
    return (
      <div class="dropdown">
        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">{this.props.SetValue}
        <span class="caret"></span></button>
        <ul class="dropdown-menu" >
          {this.options.map(function(listValue){
            return <li> <a href="#" onClick={()=>{Setter({listValue})}} > {listValue} </a> </li>;
          })}  
        </ul>


      </div>
          
    );
  }



}