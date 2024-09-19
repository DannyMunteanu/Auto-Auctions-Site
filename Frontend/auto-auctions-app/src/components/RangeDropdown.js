import React, { useState, useEffect } from "react";

const generateOptions = (min, max, step, reverse = false, isDisplacement = false) => {
  const options = [];
  if (reverse) {
    for (let i = min; i >= max; i -= step) {
      options.push(isDisplacement ? parseFloat(i.toFixed(1)) : i);
    }
  } else {
    for (let i = min; i <= max; i += step) {
      options.push(isDisplacement ? parseFloat(i.toFixed(1)) : i);
    }
  }
  return options;
};

const RangeDropdown = ({ value, onChange, label, scale }) => {
  const [minOptions, setMinOptions] = useState([]);
  const [maxOptions, setMaxOptions] = useState([]);

  useEffect(() => {
    let minRange, maxRange, step, reverse, isDisplacement;

    switch (scale) {
      case 'year':
        minRange = new Date().getFullYear();
        maxRange = 1886;
        step = 1;
        reverse = true;
        isDisplacement = false;
        break;
      case 'owners':
      case 'cylinders':
        minRange = 1;
        maxRange = 50;
        step = 1;
        reverse = false;
        isDisplacement = false;
        break;
      case 'displacement':
        minRange = 0.4;
        maxRange = 14.0;
        step = 0.1;
        reverse = false;
        isDisplacement = true;
        break;
      case 'mileage':
      case 'reserve':
        minRange = 5000;
        maxRange = 1000000;
        step = 5000;
        reverse = false;
        isDisplacement = false;
        break;
      default:
        minRange = 1;
        maxRange = 100;
        step = 1;
        reverse = false;
        isDisplacement = false;
        break;
    }

    setMinOptions(generateOptions(minRange, maxRange, step, reverse, isDisplacement));
    setMaxOptions(generateOptions(minRange, maxRange, step, reverse, isDisplacement));
  }, [scale]);

  const handleMinChange = (e) => {
    const minValue = Number(e.target.value);
    if (minValue <= (Number(value[1]) || maxOptions[maxOptions.length - 1])) {
      onChange([minValue, value[1]]);
    }
  };

  const handleMaxChange = (e) => {
    const maxValue = Number(e.target.value);
    if (maxValue >= (Number(value[0]) || minOptions[0])) {
      onChange([value[0], maxValue]);
    }
  };

  return (
    <div className="flex flex-col p-2 w-3/12 border border-gray-300 rounded-lg shadow-sm bg-white">
      <label className="font-light text-gray-700">{label}</label>
      <div className="flex space-x-4">
        <div className="flex-1">
          <label htmlFor="minSelect" className="block text-sm text-gray-500">From</label>
          <select
            id="minSelect"
            className="w-full h-10 px-3 border border-gray-300 rounded-lg focus:outline-none"
            value={value[0] || ''}
            onChange={handleMinChange}
          >
            <option value="" className="text-gray-400">Minimum</option>
            {minOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>
        <div className="flex-1">
          <label htmlFor="maxSelect" className="block text-sm text-gray-500">To</label>
          <select
            id="maxSelect"
            className="w-full h-10 px-3 border border-gray-300 rounded-lg focus:outline-none"
            value={value[1] || ''}
            onChange={handleMaxChange}
          >
            <option value="" className="text-gray-400">Maximum</option>
            {maxOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>
      </div>
    </div>
  );
};

export default RangeDropdown;
