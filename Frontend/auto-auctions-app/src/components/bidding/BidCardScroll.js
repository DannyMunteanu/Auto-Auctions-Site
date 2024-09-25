import React from "react";
import BidCard from "./BidCard";

const BidCardScroll = ({ bids }) => {
  return (
    <div className="overflow-x-auto whitespace-nowrap">
      {bids.length === 0 ? (
        <div className="flex items-center justify-center h-52 p-4 text-gray-600">
          <h1 className="text-2xl font-light">
            No bid history for this listing.
          </h1>
        </div>
      ) : (
        <div className="flex space-x-4 p-4">
          {bids.map((bid) => (
            <div key={bid.bidid} className="flex-shrink-0">
              <BidCard bid={bid} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default BidCardScroll;
