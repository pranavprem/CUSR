import React from "react";


//import CalcElement from "./Calculator/CalcElement"

export default class menu extends React.Component{
	constructor(){
		super();
    
  }

  
  

	render() {
    
    var setter = this.props.setter;
    var getter = this.props.getter;
    var options = this.props.options;
    return (
      <div class="dropdown">
        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">{getter()}
        <span class="caret"></span></button>
        <ul class="dropdown-menu" >
          {options.map(function(listValue){
            return <li> <a href="#" key={getter()+listValue} onClick={()=>{setter({listValue})}} > {listValue} </a> </li>;
          })}  
        </ul>


      </div>
          
    );
  }



}