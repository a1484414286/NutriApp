import React, { useState } from "react";
import Cookies from "universal-cookie";
import { useNavigate } from "react-router-dom";
import { API_ADDRESS } from "../config";
import { toast } from "react-toastify";

export const Register = () => {
  const nav = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const [username, password, name, height, weight, dob] = [
      event.target[0].value,
      event.target[1].value,
      event.target[3].value,
      event.target[4].value,
      event.target[5].value,
      event.target[6].value,
    ];
    console.log(name, password, height, weight, dob);
    const res = await fetch(`${API_ADDRESS}/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password, name, height, weight, dob }),
    });
    if (res.status === 200) {
      const cookies = new Cookies();
      cookies.set("username", username, { path: "/" });
      nav("/home");
      toast.success("Successfully registered!");
    }
  };

  return (
    <div className="flex mx-auto my-auto h-[21rem] shadow-2xl rounded-3xl">
      <div className="flex flex-col bg-[#DDBEA9] rounded-3xl py-5 px-8">
        <h1 className="font-bold text-2xl">Register Page</h1>
        <form
          className="flex-1 flex mt-3 gap-x-3"
          onSubmit={(e) => handleSubmit(e)}
        >
          <div className="flex flex-col gap-y-3">
            <div className=" flex flex-col">
              <label name="username" className="font-semibold text-left">
                Username
              </label>
              <input
                required
                className="bg-[#6B705C] text-white text-sm font-semibold rounded-lg py-1 px-1.5"
              />
            </div>
            <div className="flex flex-col">
              <label name="password" className="font-semibold text-left">
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
              className="mt-4 bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
            >
              Register
            </button>
          </div>
          <div className="flex flex-col gap-y-3">
            <div className=" flex flex-col">
              <label name="name" className="font-semibold text-left">
                Name
              </label>
              <input
                required
                className="bg-[#6B705C] text-white text-sm font-semibold rounded-lg py-1 px-1.5"
              />
            </div>
            <div className="flex flex-col">
              <label name="height" className="font-semibold text-left">
                Height (in inches)
              </label>
              <input
                required
                className="bg-[#6B705C] text-white text-sm font-semibold rounded-lg py-1 px-1.5"
              />
            </div>
            <div className="flex flex-col">
              <label name="weight" className="font-semibold text-left">
                Weight (lbs)
              </label>
              <input
                required
                className="bg-[#6B705C] text-white text-sm font-semibold rounded-lg py-1 px-1.5"
              />
            </div>
            <div className="flex flex-col">
              <label name="dob" className="font-semibold text-left">
                Date of Birth
              </label>
              <input
                required
                type="date"
                className="bg-[#6B705C] text-white text-sm font-semibold rounded-lg py-1 px-1.5"
              />
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};
