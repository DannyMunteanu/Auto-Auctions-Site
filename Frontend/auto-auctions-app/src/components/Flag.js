import React, { useState, useEffect } from 'react';
import axios from 'axios';

const fetchCountryCode = async (countryName) => {
  try {
    const response = await axios.get(`https://restcountries.com/v3.1/name/${countryName}`);
    const countryData = response.data[0];
    return countryData.cca2.toLowerCase();
  } catch (error) {
    console.error("Error fetching country code:", error);
    return null;
  }
};

const Flag = ({ country }) => {
  const [flagCode, setFlagCode] = useState(null);

  useEffect(() => {
    const fetchCode = async () => {
      const code = await fetchCountryCode(country);
      setFlagCode(code);
    };

    fetchCode();
  }, [country]);

  const flagUrl = flagCode ? `https://flagcdn.com/w20/${flagCode}.png` : null;

  return (
    <div className="w-5 h-5 rounded-full overflow-hidden">
      <img
        src={flagUrl}
        alt={`${country} flag`}
        className="w-full h-full object-cover"
        onError={(e) => {
          e.target.onerror = null;
          e.target.src = 'https://flagcdn.com/w20/un.png';
        }}
      />
    </div>
  );
};

export default Flag;
