import React from "react";

const BidCard = ({ bid }) => {
  if (!bid) {
    return (
      <div className="border rounded-lg p-6 shadow w-96">
        <div className="flex items-center mb-3">
          <div className="w-12 h-12 rounded-full bg-gray-500 text-white flex items-center justify-center mr-4">
            <span className="font-bold">N/A</span>
          </div>
          <div>
            <p className="text-lg font-bold text-gray-800">No Bids Yet</p>
            <p className="text-gray-600 text-sm">
              There are currently no bids for this listing.
            </p>
          </div>
        </div>
      </div>
    );
  }

  const { user = {}, amount = 0, time, bidid, listing = {} } = bid;
  const { username = "Unknown User", country = "Unknown Country" } = user;
  const { listingid = "N/A" } = listing;
  const formattedTime = time ? new Date(time).toLocaleString() : "N/A";

  return (
    <div className="border rounded-lg p-3 shadow w-96">
      <div className="flex justify-between">
        <p className="text-gray-600 font-light">Bid ID:{bidid}</p>
        <p className="text-gray-600 font-light">Listing ID:{listingid}</p>
      </div>
      <div className="flex items-center mb-3">
        <div className="w-12 h-12 rounded-full bg-blue-500 text-white flex items-center justify-center mr-4">
          <span className="font-bold">{username.charAt(0).toUpperCase()}</span>
        </div>
        <div className="flex flex-col">
          <p className="text-gray-600 text-lg">
            <strong>£{amount.toFixed(2)}</strong>
          </p>
          <div className="flex space-x-2">
            <a href="/" className="text-blue-600">
              {username}
            </a>
            <p className="font-semibold">from {country}</p>
          </div>
          <p className="text-gray-600 item-center">
            Placed: <strong>{formattedTime} GMT</strong>
          </p>
        </div>
      </div>
    </div>
  );
};

export default BidCard;
