import React, { useState, useEffect } from "react";

const CountdownTimer = ({ endTime }) => {
  const [timeRemaining, setTimeRemaining] = useState("");

  useEffect(() => {
    const updateRemainingTime = () => {
      const currentTime = new Date().getTime();
      const listingEndTime = new Date(endTime).getTime();
      const timeDifference = listingEndTime - currentTime;

      if (timeDifference <= 0) {
        setTimeRemaining("Auction ended");
      } else {
        const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
        const hours = Math.floor(
          (timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        );
        const minutes = Math.floor(
          (timeDifference % (1000 * 60 * 60)) / (1000 * 60)
        );
        const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);

        setTimeRemaining(`${days}D ${hours}H ${minutes}M ${seconds}S`);
      }
    };

    updateRemainingTime();
    
    const interval = setInterval(updateRemainingTime, 1000);

    return () => clearInterval(interval);
  }, [endTime]);

  return <h2>{timeRemaining}</h2>;
};

export default CountdownTimer;
