import { useState } from "react";

export const Items = ({ items, page, ipp, updateInfo }) => {
  const keys = Object.keys(items);
  const currentItems = keys.slice(page * ipp - ipp, page * ipp);

  const displayInfo = (k) => {
    const item = items[k];
    setItem(k);
    updateInfo(
      <div className="flex flex-col text-left font-semibold">
        <div>{`Unit: ${item.unit}`}</div>
        <div>{`Caloriers Per Unit: ${item.caloriesPerUnit}`}</div>
        <div>{`Fat Content: ${item.fatContent}`}</div>
        <div>{`Carbs: ${item.carbContent}`}</div>
        <div>{`Fiber: ${item.fiberContent}`}</div>
        <div>{`Protein: ${item.proteinContent}`}</div>
      </div>
    );
  };

  const [item, setItem] = useState();

  return (
    <div className="mt-3">
      <div className="flex flex-col space-y-3">
        {currentItems.map((k) => {
          let className = "text-sm font-bold py-1 mx-2 rounded ";
          if (item === k) {
            className += "bg-[#B7B7A4]";
          } else {
            className += "bg-[#A5A58D]";
          }
          return (
            <div key={k} onClick={() => displayInfo(k)} className={className}>
              {k}
            </div>
          );
        })}
      </div>
    </div>
  );
};
