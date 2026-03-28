import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "./styles.css";

// Hier wird die React-Anwendung in das Root-Element aus index.html eingehangen.
ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
