import React from 'react';

const Pagination = ({ listingsPerPage, totalListings, paginate, currentPage }) => {
  const pageNumbers = [];

  // Calculate total number of pages
  for (let i = 1; i <= Math.ceil(totalListings / listingsPerPage); i++) {
    pageNumbers.push(i);
  }

  return (
    <nav>
      <ul className="flex space-x-2">
        {pageNumbers.map((number) => (
          <li key={number}>
            <button
              onClick={() => paginate(number)}
              className={`px-3 py-1 rounded ${
                currentPage === number
                  ? 'bg-blue-500 text-white'
                  : 'bg-gray-200 text-gray-700'
              }`}
            >
              {number}
            </button>
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default Pagination;
