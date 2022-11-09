import { useState } from "react";
import { API_ADDRESS } from "../config";
import { Pagination } from "../components/Pagination";
import { Items } from "../components";

const DEFAULT = [];
const FOOD = null;
const IPP = 5;
const LIMIT = 10;

export const Home = () => {
  let [food, setFood] = useState(FOOD);
  let [items, setItems] = useState(DEFAULT);
  let timeout;
  let keyword = "";

  const searchItem = async (keyword) => {
    const res = await fetch(`${API_ADDRESS}/ingredients?keywords=${keyword}`);
    if (res.status === 200) {
      const json = await res.json();
      setItems(json);
    }
  };

  // shoutout to my prev repo
  const registerUpdate = (e) => {
    clearTimeout(timeout);
    timeout = setTimeout(async () => {
      keyword = e.target.value;
      if (keyword.length === 0) {
        setItems(DEFAULT);
        setInfo(<></>);
      } else {
        searchItem(keyword);
      }
      setPage(1);
    }, 500);
  };

  let [page, setPage] = useState(1);
  const [info, setInfo] = useState(<></>);

  return (
    <div className="flex mx-auto my-auto h-[32rem] shadow-2xl rounded-3xl">
      <div className="bg-[#DDBEA9] rounded-l-3xl py-5 px-5">
        <div className="flex flex-col h-full text-center  gap-10">
          <a
            href="/login"
            className="bg-[#A5A58D] shadow-lg px-8 py-3 rounded-lg font-semibold transition hover:scale-105 duration-300"
          >
            Login
          </a>
          <h1 className="font-bold text-xl">Guest Mode</h1>
          <a
            href="/register"
            className="bg-[#B7B7A4] shadow-lg px-8 py-3 rounded-lg font-semibold transition hover:scale-105 duration-300"
          >
            Register
          </a>
          <a
            href="/team"
            className="bg-[#A5A58D] shadow-lg px-8 py-3 rounded-lg font-semibold transition hover:scale-105 duration-300"
          >
            Team
          </a>
          {info}
        </div>
      </div>
      <div className="bg-[#FFE8D6] w-[32rem] rounded-r-3xl py-5 px-10 items-center text-center">
        <div className="flex flex-col h-full">
          <div className="">
            <h1 className="font-bold text-2xl">NUTRiAPP</h1>
            <form className="flex items-center justify-center">
              <input
                className="bg-[#6B705C] text-white text-sm font-semibold rounded-lg py-2 text-center"
                placeholder="Search a food..."
                onChange={(e) => registerUpdate(e)}
              />
              <div className="flex flex-col text-left ml-3">
                <div className="space-x-3">
                  <input
                    type="checkbox"
                    onChange={() => setFood("ingredients")}
                    name="Food"
                  />
                  <label>Ingredients</label>
                </div>
                <div className="space-x-3">
                  <input
                    type="checkbox"
                    onChange={() => setFood("recipes")}
                    name="Food"
                  />
                  <label>Recipes</label>
                </div>
                <div className="space-x-3">
                  <input
                    type="checkbox"
                    onChange={() => setFood("meals")}
                    name="Food"
                  />
                  <label>Meals</label>
                </div>
              </div>
            </form>
          </div>
          <div className="flex-1 flex flex-col border-black border-2 rounded-lg mt-3">
            <div className="flex flex-col flex-1 h-full">
              <Items page={page} items={items} ipp={IPP} updateInfo={setInfo} />
              <Pagination
                currentPage={page}
                updatePage={setPage}
                items={items}
                ipp={IPP}
                limit={LIMIT}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
