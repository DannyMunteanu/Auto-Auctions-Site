import React, { useState, useEffect } from "react";
import axios from "axios";
import ListingCard from "../components/ListingCard";
import Pagination from "../components/Pagination";
import "../styles/ListingsPage.css";

const ListingsPage = () => {
  const [listings, setListings] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);

  const listingsPerPage = 30;

  const calculateTimeRemaining = (endTime) => {
    const currentTime = new Date().getTime();
    const listingEndTime = new Date(endTime).getTime();
    const timeDifference = listingEndTime - currentTime;

    if (timeDifference <= 0) {
      return "Auction ended";
    }

    const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    const hours = Math.floor(
      (timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
    );
    const minutes = Math.floor(
      (timeDifference % (1000 * 60 * 60)) / (1000 * 60)
    );
    const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);

    return `${days}D ${hours}H ${minutes}M ${seconds}S`;
  };

  useEffect(() => {
    axios
      .post("http://localhost:8080/api/listing/public/search", {
      })
      .then((response) => {
        setListings(response.data);
      })
      .catch((error) => {
        console.error("Error fetching listings:", error);
      });
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      setListings((prevListings) => [...prevListings]);
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  const indexOfLastListing = currentPage * listingsPerPage;
  const indexOfFirstListing = indexOfLastListing - listingsPerPage;
  const currentListings = listings.slice(
    indexOfFirstListing,
    indexOfLastListing
  );

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="listings-container mx-auto px-4 py-6">
      <div className="grid grid-cols-1 xl:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {currentListings.map((listing) => (
          <ListingCard
            key={listing.listingid}
            id={listing.listingid}
            carmodel={
              listing.car.model.manufacturer.make +
              " " +
              listing.car.model.modelname
            }
            series={listing.car.model.series}
            color={listing.car.color}
            engine={
              listing.car.model.displacement +
              "L " +
              listing.car.model.cylinders +
              " Cylinder"
            }
            year={listing.car.year}
            registration={listing.car.registration}
            mileage={listing.car.mileage}
            previousowners={listing.car.previousowners}
            country={listing.user.country}
            username={listing.user.username}
            reserve={listing.reserve}
            startTime={new Date(listing.start)}
            endTime={new Date(listing.end)}
            timeRemaining={calculateTimeRemaining(listing.end)} // Calculate and pass timeRemaining to the card
          />
        ))}
      </div>
      <div className="flex justify-center mt-8">
        <Pagination
          listingsPerPage={listingsPerPage}
          totalListings={listings.length}
          paginate={paginate}
          currentPage={currentPage}
        />
      </div>
    </div>
  );
};

export default ListingsPage;
