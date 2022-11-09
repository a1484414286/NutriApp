import React, { useState } from "react";
import { API_ADDRESS } from "../config";
import Cookies from "universal-cookie";

export const Team = () => {

  // useEffect(() => {
  //   const func = async (event) => {
  //     const invite = event.target[0].value;
  //     const cookies = new Cookies();
  //     const username = cookies.get("username");
  //     const res = await fetch(`${API_ADDRESS}/${username}/${invite}`);
  //     const json = await res.json();

  //     setData({ ...json, username });
  //   };
  //   func();
  // }, []);
  return (
    <div className="flex mx-auto my-auto h-[18rem] shadow-2xl rounded-3xl">
      <div className="flex flex-col bg-[#DDBEA9] rounded-3xl py-5 px-8">
        <h1 className="font-bold text-2xl">Team Page</h1>
        <form
          className="flex-1 flex flex-col gap-4"
        >
          <div className="mt-auto flex flex-col">
            <label
              type="text"
              name="username"
              className="font-semibold text-left"
            >
              Person you want to invite
            </label>
            <input
              required
              className="bg-[#6B705C] text-white text-sm font-semibold rounded-lg py-1 px-1.5"
            />
          </div>
    
          <button
            type="submit"
            className="my-auto bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
          >
            Submit
            
          </button>
          <div className="bg-[#FFE8D6] w-[32rem] rounded-r-3xl py-5 px-10 items-center text-center">
  
      </div>
        </form>
      </div>
      <div className="flex mx-auto my-auto h-[18rem] shadow-2xl rounded-3xl">
      <div className="flex flex-col bg-[#DDBEA9] rounded-3xl py-5 px-8">
        <form
          className="flex-1 flex flex-col gap-4"
        >
          <button
            type="submit"
            className="my-auto bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
            >
            Team history
            <div className="mt-auto flex flex-col">
          </div>
            
          </button>

                    
    
          <button
            type="submit"
            className="my-auto bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
            >
            personal history
            <div className="mt-auto flex flex-col">
          </div>
            
          </button>
        </form>
      </div>
      </div>
      <div className="flex flex-col bg-[#DDBEA9] rounded-3xl py-5 px-8">
        <h1 className="font-bold text-2xl"></h1>
        <form
          className="flex-1 flex flex-col gap-4"
        >
          <button
            type="submit"
            className="my-auto bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
            >
            Create a team
            <div className="mt-auto flex flex-col">
          </div>
            
          </button>

          </form>

        <form
          className="flex-1 flex flex-col gap-4"
        >
        <button
            type="submit"
            className="my-auto bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
            >
            Issue a challenge

            <div className="mt-auto flex flex-col">
          </div>
            
          </button>
          </form>
      

        <button
            type="submit"
            className="my-auto bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
            >
            Leave team

            <div className="mt-auto flex flex-col">
          </div>
            
          </button>
      </div>
      <div className="flex flex-col bg-[#DDBEA9] rounded-3xl py-5 px-8">
        <h1 className="font-bold text-2xl"></h1>
        <form
          className="flex-1 flex flex-col gap-4"
        >
          <button
            type="submit"
            className="my-auto bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
            >
            View total ranking
        
            <div className="mt-auto flex flex-col">
          </div>
            
          </button>

          <button
            type="submit"
            className="my-auto bg-[#A5A58D] shadow-lg py-1.5 rounded-lg font-semibold transition hover:scale-105 duration-300"
            >
            View weekly ranking
        
            <div className="mt-auto flex flex-col">
          </div>
            
          </button>
          </form>


      </div>
    
    </div>
  );
};
