import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import Flag from "../components/additional/Flag";
import CountdownTimer from "../components/additional/CountdownTimer";
import BidCard from "../components/bidding/BidCard";
import BidCardScroll from "../components/bidding/BidCardScroll";
import PlaceBid from "../components/bidding/PlaceBid";

const ViewListingPage = () => {
  const { registration } = useParams();
  const [listing, setListing] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [highestBid, setHighestBid] = useState(null);
  const [bids, setBids] = useState([]);
  const [isImageModalOpen, setIsImageModalOpen] = useState(false);
  const [bidPlaced, setBidPlaced] = useState(false);
  useEffect(() => {
    fetchListing();
  }, [registration]);

  useEffect(() => {
    if (listing) {
      fetchHighestBid();
      fetchBids();
    }
  }, [listing, bidPlaced]);

  const fetchListing = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/listing/public/specified?registration=${registration}`
      );
      setListing(response.data);
      setLoading(false);
    } catch (err) {
      setError("Error fetching listing data.");
      setLoading(false);
    }
  };

  const fetchHighestBid = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/bid/public/highest?id=${listing.listingid}`
      );
      setHighestBid(response.data || null);
    } catch (error) {
      console.error("Error fetching highest bid:", error);
      setHighestBid(null);
    }
  };

  const fetchBids = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8080/api/bid/public/search`,
        {
          listingId: listing.listingid,
        }
      );
      const sortedBids = response.data.sort(
        (a, b) => new Date(a.time) - new Date(b.time)
      );
      setBids(sortedBids);
    } catch (error) {
      console.error("Error fetching all bids:", error);
      setBids([]);
    }
  };

  const handleBidPlaced = () => {
    setBidPlaced((prev) => !prev);
  };

  if (loading) {
    return <p className="text-center text-gray-600">Loading...</p>;
  }

  if (error) {
    return <p className="text-center text-red-600">{error}</p>;
  }

  if (!listing) {
    return (
      <p className="text-center text-gray-600">
        No listing found for registration: {registration}
      </p>
    );
  }

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

  const reserveColor =
    highestBid !== null && highestBid.amount >= listing.reserve
      ? "text-green-600"
      : "text-red-600";

  const toggleImageModal = () => {
    setIsImageModalOpen(!isImageModalOpen);
  };

  return (
    <div>
      <div className={`w-full mt-6 px-4 ${isImageModalOpen ? "blur-md" : ""}`}>
        <div className="grid grid-cols-1 lg:grid-cols-[3fr,2fr] gap-4">
          <div className="relative">
            <img
              src="https://images.pexels.com/photos/100656/pexels-photo-100656.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
              alt="Listing"
              className="w-full h-full object-cover rounded-lg shadow-lg transition-transform transform hover:scale-105 cursor-pointer"
              onClick={toggleImageModal}
            />
            <div className="absolute top-2 left-2 bg-white px-4 py-1 rounded-full shadow-lg text-blue-600">
              <CountdownTimer endTime={listing.end} />
            </div>
            <div className="absolute top-2 right-2 bg-white px-4 py-1 rounded-full shadow-lg">
              <p className={`text-lg font-light ${reserveColor}`}>
                Reserve: £
                {listing.reserve !== undefined
                  ? listing.reserve.toFixed(2)
                  : "N/A"}
              </p>
            </div>
          </div>

          <div className="flex flex-col justify-between bg-white rounded-lg shadow-lg px-6 py-3 border">
            <div>
              <div className="flex items-center justify-center space-x-3">
                <Flag country={listing.car.model.manufacturer.origincountry} />
                <h1 className="text-4xl font-bold text-gray-900">
                  {listing.car.model.manufacturer.make}{" "}
                  {listing.car.model.modelname}
                </h1>
              </div>
              <div className="flex items-center justify-center space-x-2 text-lg text-gray-600 mb-4">
                <p>
                  <strong>{listing.car.year}</strong>
                </p>
                <p>{listing.car.model.series}</p>
                <p>
                  <strong>
                    {listing.car.model.displacement}L{"  "}
                  </strong>
                  {listing.car.model.cylinders} Cylinder
                </p>
              </div>

              <div className="px-3 border rounded-xl shadow">
                <div className="mt-2">
                  <div className="flex items-center justify-center space-x-20">
                    <div className="flex items-center mb-2">
                      <p className="font-semibold">Registration:</p>
                      <p className="ml-2">{registration}</p>
                    </div>
                    <div className="flex items-center mb-2">
                      <p className="font-semibold">Location:</p>
                      <p className="ml-2">{listing.user.country}</p>
                      <Flag country={listing.user.country} />
                    </div>
                  </div>
                  <div className="flex items-center justify-between space-x-6">
                    <div className="flex items-center mb-2">
                      <p className="font-semibold">Mileage:</p>
                      <p className="ml-2">{listing.car.mileage} miles</p>
                    </div>
                    <div className="flex items-center mb-2">
                      <p className="font-semibold">Previous Owners:</p>
                      <p className="ml-2">{listing.car.previousowners}</p>
                    </div>
                    <div className="flex items-center mb-2">
                      <p className="font-semibold">Color:</p>
                      <p className="ml-2">{listing.car.color}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="mt-2 flex items-center justify-center space-x-8">
              <p className="text-gray-600">Listing ID: {listing.listingid}</p>
              <div className="flex space-x-2">
                <p className="font-semibold">Posted by:</p>
                <a href="/" className="text-blue-600">
                  {listing.user.username}
                </a>
              </div>
            </div>

            <div className="mt-3">
              <div className="flex justify-between text-lg border rounded-xl p-3 shadow">
                <div>
                  <p className="text-gray-600 font-semibold">
                    (GMT) Auction Start:
                  </p>
                  <p>{formatDate(listing.start)}</p>
                </div>
                <div>
                  <p className="text-gray-600 font-semibold">
                    (GMT) Auction End:
                  </p>
                  <p>{formatDate(listing.end)}</p>
                </div>
              </div>

              <div className="mt-2 flex flex-col items-center justify-center">
                <BidCard bid={highestBid} />
                {listing && (
                  <PlaceBid
                    highestBid={highestBid}
                    listing={listing.listingid}
                    startTime={listing.start}
                    endTime={listing.end}
                    onBidPlaced={handleBidPlaced}
                  />
                )}
              </div>
            </div>
          </div>
        </div>
        <BidCardScroll bids={bids} />
      </div>

      {isImageModalOpen && (
        <div
          className="fixed inset-0 bg-black bg-opacity-80 flex items-center justify-center z-50"
          onClick={toggleImageModal}
        >
          <div className="relative w-5/6 h-5/6">
            <img
              src="https://images.pexels.com/photos/100656/pexels-photo-100656.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
              alt="Expanded Listing"
              className="w-full h-full object-cover rounded-lg"
              onClick={(e) => e.stopPropagation()}
            />
            <button
              className="absolute top-2 right-2 text-white text-3xl font-bold"
              onClick={toggleImageModal}
            >
              ×
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ViewListingPage;
