import React from "react";
import ReactDOM from "react-dom";
//import { Router, Route, IndexRoute, hashHistory } from "react-router";

import SearchLayout from "./js/pages/SearchLayout.js"
import TicketLayout from "./js/pages/TicketLayout.js"
import HistoryLayout from "./js/pages/HistoryLayout.js"

import { BrowserRouter as Router, Route } from 'react-router-dom';
const app = document.getElementById('app');

ReactDOM.render(

    <Router>
    <div>
      <Route exact path ="/" component={SearchLayout}/>
      <Route exact path ="/ticket" component={TicketLayout}/>
      <Route exact path="/search" component={SearchLayout}></Route>
      <Route exact path="/tickethistory" component={HistoryLayout}></Route>
     </div> 
    </Router>,
   document.getElementById('root'));
