import React from "react";

const DateTimeInput = ({ value, onChange }) => {
  const [start, end] = value;

  const handleStartChange = (e) => {
    const newStart = e.target.value ? new Date(e.target.value) : null;
    if (newStart && (!end || newStart <= end)) {
      onChange([newStart, end]);
    }
  };

  const handleEndChange = (e) => {
    const newEnd = e.target.value ? new Date(e.target.value) : null;
    if (newEnd && (!start || newEnd >= start)) {
      onChange([start, newEnd]);
    }
  };

  return (
    <div className="flex flex-col p-2 w-1/2 border border-gray-300 rounded-lg shadow-sm bg-white">
      <label className="font-light text-gray-700">Time Range</label>
      <div className="flex space-x-4">
        <div className="flex-1">
          <label htmlFor="startDate" className="block text-sm text-gray-500">From</label>
          <input
            type="datetime-local"
            id="startDate"
            value={start ? start.toISOString().slice(0, 16) : ""}
            onChange={handleStartChange}
            className="w-full h-10 px-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div className="flex-1">
          <label htmlFor="endDate" className="block text-sm text-gray-500">To</label>
          <input
            type="datetime-local"
            id="endDate"
            value={end ? end.toISOString().slice(0, 16) : ""}
            onChange={handleEndChange}
            className="w-full h-10 px-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
      </div>
    </div>
  );
};

export default DateTimeInput;
