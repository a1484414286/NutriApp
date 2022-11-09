import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { API_ADDRESS } from "../config";
import Cookies from "universal-cookie";
import { toast } from "react-toastify";

export const Login = () => {
  const nav = useNavigate();
  const [errorMessages, setErrorMessages] = useState({});

  const handleSubmit = async (event) => {
    event.preventDefault();
    const [username, password] = [event.target[0].value, event.target[1].value];
    const res = await fetch(`${API_ADDRESS}/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    });
    const json = await res.json();

    if (res.status === 200 && Object.keys(json).length !== 0) {
      const cookies = new Cookies();
      cookies.set("username", username, { path: "/" });
      toast.success("Successfully logged in!");
      nav("/home");
    } else {
      toast.error("Incorrect credentials, please try agan!");
    }
  };

  return (
    <div className="flex mx-auto my-auto h-[18rem] shadow-2xl rounded-3xl">
      <div className="flex flex-col bg-[#DDBEA9] rounded-3xl py-5 px-8">
        <h1 className="font-bold text-2xl">Login Page</h1>
        <form
          className="flex-1 flex flex-col gap-3"
          onSubmit={(e) => handleSubmit(e)}
        >
          <div className="mt-auto flex flex-col">
            <label
              type="text"
              name="username"
              className="font-semibold text-left"
            >
              Username
            </label>
            <input
              required
              className="bg-[#6B705C] text-white text-sm font-semibold rounded-lg py-1 px-1.5"
            />
          </div>
          <div className="flex flex-col">
            <label
              type="password"
              name="password"
              className="font-semibold text-left"
            >
              Password
            </label>
            <input
              type="password"
              required
              className="bg-[#6B705C] text-white text-sm font-semibold rounded-lg py-1 px-1.5"
            />
          </div>
          <button
            type="submit"
            className="my-auto bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
          >
            Login
          </button>
        </form>
      </div>
    </div>
  );
};
