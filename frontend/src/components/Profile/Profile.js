import React, { useEffect, useState } from "react";
import Cookies from "universal-cookie";
import { useNavigate } from "react-router-dom";
import { API_ADDRESS } from "../../config/index"
import "./Profile.css"

export const Profile = (props) => {
    let [state, setState] = useState("Data not found...");
    let user_weight = ""
    let user_goal = ""
    let user_ingredients = ""
    let user_team = ""
    var user_name = "";
    
    function foRequest(){
        const res = fetch(`${API_ADDRESS}/profile/${user_name}`, {
            method: "GET",
            headers: { "Content-Type": "text/json" },
            body: JSON.stringify(),
          }).then((response) => {
            console.log(response);
        });
          console.log(res.status)
    }
    useEffect(() => foRequest());

    return (
        <div className="mx-auto my-auto shadow-2xl rounded-3xl bg-[#DDBEA9] p-10">
            <h1>User Profile</h1>
            <div>
                
                {/* +++++++++++++++ */}
                <p>Below are details located for the User, if they wish to edit their page, they will be redirected to the edit screen</p>

                <div id="clubRow" className="m-5">
                    <div className="clubBox" >
                        <div className="clubContainer" id=""><p>User Weight: {user_weight}</p><br></br><p></p></div>
                    </div>
                    <div className="clubBox" >
                        <div className="clubContainer" id=""><p>User Goal: {user_goal}</p><br></br><p></p></div>
                    </div>
                    <div className="clubBox" >
                        <div className="clubContainer" id=""><p>User Team: {user_team}</p><br></br><p></p></div>
                    </div>
                    <div className="clubBox" >
                        <div className="clubContainer" id=""><p>User Ingredients: {user_ingredients}</p><br></br><p></p></div>
                    </div>
                </div>
                {/* +++++++++++++++ */}
            </div>
        </div>
  );
};
