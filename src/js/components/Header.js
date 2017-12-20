import React from "react";


export default class Header extends React.Component {

  cancel(){
    var res = document.getElementById("cancel").value;
  }
  reset(){
    var res = document.getElementById("reset").value;

  }
  render() {
    return (
    	<div>
      		<h1> California Ultra Speed Rail </h1>
          <input type="text" id="cancel" placeholder="Cancel Train"/>
      		<button class="btn" onClick={()=>{this.cance()}}>Cancel Train</button>
          <input type="text" id="reset" placeholder="Reset" />
      		<button class="btn" onClick={()=>{this.reset()}}>Reset</button>
      	</div>
    );
  }
}
