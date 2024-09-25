import React, { useState, useEffect } from "react";
import axios from "axios";
import Flag from "../additional/Flag";

const ListingCard = ({
  id,
  carmodel,
  series,
  color,
  engine,
  year,
  registration,
  mileage,
  previousowners,
  country,
  username,
  reserve,
  startTime,
  endTime,
  timeRemaining,
}) => {
  const [highestBid, setHighestBid] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/bid/public/highest?id=${id}`)
      .then((response) => {
        setHighestBid(response.data.amount);
      })
      .catch((error) => {
        console.error("Error fetching highest bid:", error);
      });
  }, [id]);

  const reserveColor =
    highestBid !== null && highestBid >= reserve
      ? "text-green-600"
      : "text-red-600";

  const bidMessage =
    new Date(startTime) < new Date() ? "Bids Open" : "Bids Not Open";

  const formatDate = (date) => {
    const options = {
      weekday: "short",
      year: "numeric",
      month: "short",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
    };
    return new Date(date).toLocaleDateString(undefined, options);
  };

  // Function to format currency to 2 decimal places
  const formatCurrency = (amount) => {
    return `£${Number(amount).toFixed(2)}`;
  };

  return (
    <div className="border rounded-lg shadow-md p-4 bg-white max-w-4xl mx-auto">
      <div className="flex">
        <div className="relative">
          <img
            src="https://images.pexels.com/photos/100656/pexels-photo-100656.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
            alt="Listing"
            className="w-80 h-60 object-cover border rounded-lg"
          />
          <h2 className="absolute top-2 left-2 text-blue-600 text-sm font-medium bg-white px-2 py-1 rounded-2xl shadow-lg">
            {timeRemaining}
          </h2>
        </div>

        <div className="flex-1 ml-4">
          <div>
            <h2 className="text-black text-2xl mr-2">{carmodel}</h2>
            <div className="flex space-x-2 text-sms">
              <p className="text-grey-600 font-medium text-lg">{year}</p>
              <p className="text-gray-600 text-lg">{series}</p>
              <p className="text-gray-600 text-lg">{engine}</p>
            </div>
          </div>

          <div className="mb-2 mt-2">
            <a href="/" className="text-blue-600">
              {username}
            </a>
          </div>

          <div className="flex space-x-2 text-md mt-2 mb-2">
            <p className="text-black font-semibold">{registration}</p>
            <Flag country={country} />
            <p className="text-black">{country}</p>
          </div>

          <div className="flex space-x-6 text-sm mb-2">
            <p className="text-black">{mileage} miles</p>
            <p className="text-gray-500">Owners: {previousowners}</p>
            <p className="text-black">{color}</p>
          </div>

          <div className="text-sm text-gray-500 mt-2">
            <div className="flex justify-between border-t pt-4">
              <div className="flex-1 pr-4">
                <p className="text-gray-600 font-semibold">Start Time</p>
                <p>{formatDate(startTime)}</p>
              </div>
              <div className="flex-1 pl-4">
                <p className="text-gray-600 font-semibold">End Time</p>
                <p>{formatDate(endTime)}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="mt-4 border-t-2 pt-4 flex items-center justify-between">
        <p className="text-sm text-gray-600">Listing ID: {id}</p>
        <h2 className="text-lg font-semibold text-center flex-1">
          {highestBid !== null && highestBid > 0
            ? `Current Bid: ${formatCurrency(highestBid)}`
            : bidMessage}
        </h2>
        <p className={`text-sm ${reserveColor}`}>Reserve: {formatCurrency(reserve)}</p>
      </div>
    </div>
  );
};

export default ListingCard;
