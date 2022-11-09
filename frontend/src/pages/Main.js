import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "universal-cookie";
import { Data, Home, Inventory, Settings, Teams } from "../components";
import { API_ADDRESS } from "../config";

export const Main = () => {
  const nav = useNavigate();
  const [data, setData] = useState(null);
  const [content, setContent] = useState(<></>);
  const [sub, setSub] = useState(<Home />);

  const logout = () => {
    const cookies = new Cookies();
    cookies.remove("username");
    nav("/");
  };

  useEffect(() => {
    const func = async () => {
      const cookies = new Cookies();
      const username = cookies.get("username");
      const res = await fetch(`${API_ADDRESS}/profile/${username}`);
      const json = await res.json();
      if (json === null) {
        nav("/");
      }
      setData({ ...json, username });
    };
    func();
  }, []);

  const updateSub = (component) => setSub(component);

  useEffect(() => {
    if (data !== null) {
      setContent(
        <div className="flex mx-auto my-auto h-[35rem] shadow-2xl rounded-3xl">
          <div className="bg-[#DDBEA9] rounded-l-3xl py-5 px-5 flex flex-col ">
            <h1 className="font-bold text-2xl no-underline mb-2">NUTRiAPP</h1>
            <hr className="border-2 rounded  border-black" />
            <div className="flex flex-col p-2">
              <p className="font-bold">Welcome, {data.name}</p>
              <p className="font-bold">Username: {data.username}</p>
              <p className="font-bold">Age: {data.information.age}</p>
              <p className="font-bold">Height: {data.information.height}</p>
              <p className="font-bold">Weight: {data.information.weight}</p>
            </div>
            <hr className="border-2 rounded  border-black" />
            <div className="flex flex-col gap-5 my-5">
              <button
                onClick={() => updateSub(<Home />)}
                className="font-semibold bg-[#A5A58D] rounded px-2 py-1"
              >
                Home
              </button>
              <button
                onClick={() => updateSub(<Inventory />)}
                className="font-semibold bg-[#A5A58D] rounded px-2 py-1"
              >
                View Inventory
              </button>
              <button
                onClick={() => updateSub(<Teams />)}
                className="font-semibold bg-[#A5A58D] rounded px-2 py-1"
              >
                Teams
              </button>
              <button
                onClick={() => updateSub(<Data data={data} />)}
                className="font-semibold bg-[#A5A58D] rounded px-2 py-1"
              >
                Manage Data
              </button>
              <button
                onClick={() => updateSub(<Settings />)}
                className="font-semibold bg-[#A5A58D] rounded px-2 py-1"
              >
                Settings
              </button>
            </div>
            <hr className="border-2 rounded  border-black" />
            <button
              onClick={() => logout()}
              className="mt-auto font-semibold bg-[#f94144] rounded px-2 py-1"
            >
              Logout
            </button>
          </div>
          <div className="bg-[#FFE8D6] w-[32rem] rounded-r-3xl py-5 px-10 items-center text-center">
            {sub}
          </div>
        </div>
      );
    }
  }, [data, sub]);

  return content;
};
