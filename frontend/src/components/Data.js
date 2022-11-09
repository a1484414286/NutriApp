import { API_ADDRESS } from "../config";
import { toast } from "react-toastify";

export const Data = ({ data }) => {
  const exportData = () => {};

  const importData = async (e, type) => {
    const file = e.target.files[0];
    toast.promise(
      async () => {
        const formData = new FormData();
        formData.append("file", file, file.name);
        console.log(file);
        const res = await fetch(
          `${API_ADDRESS}/import?username=${data.username}`,
          {
            method: "POST",
            body: formData,
          }
        );
        if (res.status === 400) throw new Error();
        return true;
      },
      {
        success: `Successfully imported ${file.name}`,
        error: `Failed to import ${file.name}`,
      }
    );
  };

  return (
    <div className="flex justify-around">
      <div className="flex flex-col">
        <h1>Import Data</h1>
        <div className="flex flex-col gap-5 mt-5">
          <label
            onChange={(e) => importData(e)}
            className="bg-[#A5A58D] rounded py-1 px-2 flex hover:cursor-pointer"
          >
            <img alt="import" src={process.env.PUBLIC_URL + "/import.png"} />
            Ingredients
            <input name="" type="file" id="formId" hidden />
          </label>
          <label
            onChange={(e) => importData(e)}
            className="bg-[#A5A58D] rounded py-1 px-2 flex hover:cursor-pointer"
          >
            <img alt="import" src={process.env.PUBLIC_URL + "/import.png"} />
            Recipes
            <input name="" type="file" id="formId" hidden />
          </label>
          <label
            onChange={(e) => importData(e)}
            className="bg-[#A5A58D] rounded py-1 px-2 flex hover:cursor-pointer"
          >
            <img alt="import" src={process.env.PUBLIC_URL + "/import.png"} />
            Meals
            <input name="" type="file" id="formId" hidden />
          </label>
        </div>
      </div>
      <div className="flex flex-col">
        <h1>Export Data</h1>
        <div className="flex flex-col gap-5 mt-5">
          <label className="bg-[#A5A58D] rounded py-1 px-2 flex hover:cursor-pointer">
            <img
              className="mr-1"
              alt="import"
              src={process.env.PUBLIC_URL + "/export.png"}
            />
            Ingredients
            <input name="" type="file" id="formId" hidden />
          </label>
          <label className="bg-[#A5A58D] rounded py-1 px-2 flex hover:cursor-pointer">
            <img
              className="mr-1"
              alt="import"
              src={process.env.PUBLIC_URL + "/export.png"}
            />
            Recipes
            <input name="" type="file" id="formId" hidden />
          </label>
          <label className="bg-[#A5A58D] rounded py-1 px-2 flex hover:cursor-pointer">
            <img
              className="mr-1"
              alt="import"
              src={process.env.PUBLIC_URL + "/export.png"}
            />
            Meals
            <input name="" type="file" id="formId" hidden />
          </label>
        </div>
      </div>
    </div>
  );
};
