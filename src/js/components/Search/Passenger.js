import React from "react";


export default class Passenger extends React.Component {

  render() {
    return (
    	<div>
      	    <label>
            Passenger Name:
            <input type="text" onChange={this.props.setter} />
      		</label>
      	</div>
    );
  }
}
