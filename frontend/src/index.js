import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Home, Login, Main, Register, Team } from "./pages";
import { Profile } from "./components/Profile/Profile";
import "./index.css";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/team" element={<Team />} />
        <Route path="/home" element={<Main />} />
        <Route path="*" element={<div>No page found</div>} />
      </Routes>
    </BrowserRouter>
  );
};

ReactDOM.render(
  <React.StrictMode>
    <App />
    <ToastContainer
      position="top-right"
      autoClose={5000}
      hideProgressBar={false}
      newestOnTop={false}
      closeOnClick
      rtl={false}
      pauseOnFocusLoss
      draggable
      pauseOnHover
      theme="light"
      
    />
  </React.StrictMode>,
  document.getElementById("root")
);
