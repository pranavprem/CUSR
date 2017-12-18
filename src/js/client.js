import React from "react";
import ReactDOM from "react-dom";
import { Router, Route, IndexRoute, hashHistory } from "react-router";

import SearchLayout from "./pages/SearchLayout.js"
import TicketLayout from "./pages/TicketLayout.js"
import TicketHistory from "./pages/HistoryLayout.js"

const app = document.getElementById('app');

ReactDOM.render(
    <Router history={hashHistory}>
      <Route path="/" component={TicketLayout}>
        <Route path="/search" component={SearchLayout}></Route>
        <Route path="/ticketHistory" component={HistoryLayout}></Route>
      </Route>
    </Router>,
  app);
