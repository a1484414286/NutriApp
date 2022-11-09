export const Pagination = ({ items, currentPage, updatePage, ipp, limit }) => {
  const numbers = [];

  for (
    let i = 1;
    i <= Math.ceil(Object.keys(items).length / ipp) && i <= limit;
    i++
  ) {
    numbers.push(i);
  }

  return (
    <div className="mt-auto mb-2 space-x-1 flex justify-center">
      {numbers.map((n) => {
        let className = "rounded font-bold text-sm ";

        if (currentPage === n) {
          className += "bg-[#B7B7A4]";
        } else {
          className += "bg-[#6B705C]";
        }

        return (
          <div className={className} key={n}>
            <button className="px-2" onClick={() => updatePage(n)}>
              {n}
            </button>
          </div>
        );
      })}
    </div>
  );
};
