import React, { useState, useEffect } from "react";
import axios from "axios";

const PlaceBid = ({ highestBid, listing, startTime, endTime, onBidPlaced }) => {
  const [amount, setAmount] = useState("");
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(false);

  const getTokenFromCookies = () => {
    const cookieString = document.cookie;
    const tokenCookie = cookieString
      .split("; ")
      .find((row) => row.startsWith("jwt="));
    return tokenCookie ? tokenCookie.split("=")[1] : null;
  };

  const token = getTokenFromCookies();

  useEffect(() => {
    const fetchUsername = async () => {
      if (token) {
        try {
          const response = await axios.get(
            "http://localhost:8080/api/user/retrieve",
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
          setUser(response.data.username);
        } catch (err) {
          console.error("Error fetching user:", err);
          setError("Failed to retrieve user information.");
        }
      }
    };

    fetchUsername();
  }, [token]);

  const validateAmount = () => {
    const bidAmount = parseFloat(amount);
    if (isNaN(bidAmount) || bidAmount <= 0) {
      setError("Bid amount must be a positive number.");
      return false;
    }
    if (highestBid && bidAmount <= highestBid.amount) {
      setError(`Bid must be greater than £${highestBid.amount}`);
      return false;
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    const currentTime = new Date();
    const auctionStartTime = new Date(startTime);
    const auctionEndTime = new Date(endTime);

    if (currentTime < auctionStartTime) {
      setError("Auction has not started yet.");
      return;
    }

    if (currentTime > auctionEndTime) {
      setError("Auction has ended.");
      return;
    }

    if (!user) {
      setError("You must be signed in to place a bid.");
      return;
    }

    if (!validateAmount()) {
      return;
    }

    const bidData = {
      listingId: listing,
      username: user,
      amount: parseFloat(amount),
      time: new Date().toISOString(),
    };

    setLoading(true);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/bid/add",
        bidData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      if (response.status === 200) {
        setSuccess("Bid placed successfully!");
        setError(null);
        setAmount("");
        onBidPlaced();
        setTimeout(() => {
          setSuccess(null);
        }, 1000);
      }
    } catch (err) {
      console.error("Error placing bid:", err);
      if (err.response) {
        console.error("Response data:", err.response.data);
        setError(
          err.response.data.message || "Failed to place bid. Please try again."
        );
      } else {
        setError("Failed to place bid. Please try again.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mt-2 bg-white border rounded-lg p-6 shadow w-96 px-6 py-3 items-center">
      <h2 className="text-lg mb-2 text-center text-gray-600">
        {error ? (
          <span className="text-red-600">{error}</span>
        ) : success ? (
          <span className="text-green-600">{success}</span>
        ) : (
          "Submit Your bid here"
        )}
      </h2>
      {token ? (
        <form
          className="flex items-center justify-center space-x-1"
          onSubmit={handleSubmit}
        >
          <input
            type="number"
            id="bid-amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            className="border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-400 transition duration-200 w-48"
            placeholder="Enter your bid amount"
            required
            step="0.01"
            min="0"
          />
          <button
            type="submit"
            className={`bg-blue-600 text-white rounded-lg py-2 px-4 hover:bg-blue-700 transition duration-300 ${
              loading ? "opacity-50 cursor-not-allowed" : ""
            }`}
            disabled={loading}
          >
            {loading ? "Submitting..." : "Submit Bid"}
          </button>
        </form>
      ) : (
        <div className="flex items-center justify-center">
          <a
            href="/auth?form=signin"
            className="text-gray-600 text-lg font-normal"
          >
            Please <strong>Sign In</strong> to place a bid
          </a>
        </div>
      )}
    </div>
  );
};

export default PlaceBid;
