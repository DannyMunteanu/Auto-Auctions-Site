import React, { useEffect, useState } from "react";
import axios from "axios";
import RangeDropdown from "./RangeDropdown";
import DateTimeInput from "./DateTimeInput";

const ListingFilter = ({ onFilterSubmit, onFilterReset }) => {
  const min = 0;
  const max = 2147483647;

  const [manufacturers, setManufacturers] = useState([]);
  const [selectedManufacturer, setSelectedManufacturer] = useState("");
  const [models, setModels] = useState([]);
  const [selectedModel, setSelectedModel] = useState("");

  const [cylinderFilter, setCylinderFilter] = useState([min, max]);
  const [displacementFilter, setDisplacementFilter] = useState([min, max]);
  const [mileageRange, setMileageRange] = useState([min, max]);
  const [yearRange, setYearRange] = useState([min, max]);
  const [ownersRange, setOwnersRange] = useState([min, max]);
  const [timeRange, setTimeRange] = useState([
    new Date(),
    new Date(new Date().setFullYear(new Date().getFullYear() + 1)),
  ]);
  const [reserveRange, setReserveRange] = useState([min, max]);

  const [listings, setListings] = useState([]);
  const [cars, setCars] = useState([]);

  const [loadingModels, setLoadingModels] = useState(false);
  const [loadingCars, setLoadingCars] = useState(false);
  const [isFilterChanged, setIsFilterChanged] = useState(false);

  useEffect(() => {
    const initialFilters = [
      "",
      "",
      [min, max],
      [min, max],
      [min, max],
      [min, max],
      [min, max],
      [timeRange[0], timeRange[1]],
      [min, max],
    ];

    const currentFilters = [
      selectedManufacturer,
      selectedModel,
      cylinderFilter,
      displacementFilter,
      mileageRange,
      yearRange,
      ownersRange,
      timeRange,
      reserveRange,
    ];

    const hasChanged = !initialFilters.every((filter, index) =>
      Array.isArray(filter)
        ? filter[0] === currentFilters[index][0] &&
          filter[1] === currentFilters[index][1]
        : filter === currentFilters[index]
    );

    setIsFilterChanged(hasChanged);
  }, [
    selectedManufacturer,
    selectedModel,
    cylinderFilter,
    displacementFilter,
    mileageRange,
    yearRange,
    ownersRange,
    timeRange,
    reserveRange,
  ]);

  useEffect(() => {
    const fetchManufacturers = async () => {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/manufacturer/search",
          {}
        );
        setManufacturers(response.data);
      } catch (error) {
        console.error("Error fetching manufacturers:", error);
      }
    };
    fetchManufacturers();
  }, []);

  useEffect(() => {
    const fetchModels = async () => {
      setLoadingModels(true);
      try {
        const fetchModelsByManufacturer = async (manufacturer) => {
          return axios.post("http://localhost:8080/api/model/search", {
            make: manufacturer.make,
            cylinders: cylinderFilter,
            displacement: displacementFilter,
          });
        };

        if (selectedManufacturer) {
          const response = await fetchModelsByManufacturer({
            make: selectedManufacturer,
          });
          setModels(response.data);
        } else {
          const allModels = [];
          for (const manufacturer of manufacturers) {
            const response = await fetchModelsByManufacturer(manufacturer);
            allModels.push(...response.data);
          }
          setModels(allModels);
        }
      } catch (error) {
        console.error("Error fetching models:", error);
      } finally {
        setLoadingModels(false);
      }
    };

    if (manufacturers.length > 0) {
      fetchModels();
    }
  }, [selectedManufacturer, cylinderFilter, displacementFilter, manufacturers]);

  const handleSubmit = async () => {
    if (loadingModels || loadingCars) return;
    try {
      const listingResponse = await axios.post(
        "http://localhost:8080/api/listing/public/search",
        {
          time: timeRange,
          reserve: reserveRange,
        }
      );
      const fetchedListings = listingResponse.data;
      setListings(fetchedListings);

      setLoadingCars(true);
      let carResponseData = [];
      if (selectedModel) {
        const carResponse = await axios.post(
          "http://localhost:8080/api/car/search",
          {
            modelId: selectedModel,
            mileage: mileageRange,
            year: yearRange,
            previousOwners: ownersRange,
          }
        );
        carResponseData = carResponse.data;
      } else {
        for (const model of models) {
          const carResponse = await axios.post(
            "http://localhost:8080/api/car/search",
            {
              modelId: model.modelid,
              mileage: mileageRange,
              year: yearRange,
              previousOwners: ownersRange,
            }
          );
          carResponseData = [...carResponseData, ...carResponse.data];
        }
      }
      setCars(carResponseData);

      const filteredListings = fetchedListings.filter((listing) =>
        carResponseData.some(
          (car) => car.registration === listing.car.registration
        )
      );

      if (typeof onFilterSubmit === "function") {
        onFilterSubmit(filteredListings);
      } else {
        console.warn(
          "onFilterSubmit is not a function. Unable to update listings."
        );
      }
    } catch (error) {
      console.error("Error submitting filters:", error);
    } finally {
      setLoadingCars(false);
    }
  };

  const handleReset = async () => {
    setSelectedManufacturer("");
    setSelectedModel("");
    setCylinderFilter([min, max]);
    setDisplacementFilter([min, max]);
    setMileageRange([min, max]);
    setYearRange([min, max]);
    setOwnersRange([min, max]);
    setTimeRange([
      new Date(),
      new Date(new Date().setFullYear(new Date().getFullYear() + 1)),
    ]);
    setReserveRange([min, max]);

    try {
      const listingResponse = await axios.post(
        "http://localhost:8080/api/listing/public/search",
        {
          time: timeRange,
          reserve: reserveRange,
        }
      );
      const fetchedListings = listingResponse.data;
      setListings(fetchedListings);

      if (typeof onFilterReset === "function") {
        onFilterReset(fetchedListings);
      } else {
        console.warn(
          "onFilterReset is not a function. Unable to update listings."
        );
      }
    } catch (error) {
      console.error("Error resetting filters:", error);
    }
  };

  return (
    <div className="bg-gradient-to-b from-blue-900 to-sky-600 p-1 rounded-xl mb-3 drop-shadow-lg">
      <div className="flex items-center justify-center h-16 space-x-4">
        <button
          onClick={handleReset}
          disabled={!isFilterChanged || loadingModels || loadingCars}
          className="px-4 py-2 bg-white rounded-full font-light hover:font-semibold focus:font-bold"
        >
          {loadingModels || loadingCars ? "..." : "↺"}
        </button>

        <div className="border-l border-gray-300 h-8"></div>

        <div className="flex-1 flex items-center justify-center">
          <label
            htmlFor="manufacturer"
            className="block text-white text-lg font-light mr-2"
          >
            Manufacturer
          </label>
          <select
            id="manufacturer"
            className="w-3/5 h-10 rounded-md p-2"
            value={selectedManufacturer}
            onChange={(e) => {
              setSelectedManufacturer(e.target.value);
              setSelectedModel("");
            }}
          >
            <option value="">Any Manufacturer</option>
            {manufacturers.map((manufacturer) => (
              <option key={manufacturer.make} value={manufacturer.make}>
                {manufacturer.make}
              </option>
            ))}
          </select>
        </div>

        <div className="flex-1 flex items-center justify-center">
          <label
            htmlFor="model"
            className="block text-white text-lg font-light mr-2"
          >
            Model
          </label>
          <select
            id="model"
            className="w-3/5 h-10 rounded-md p-2"
            value={selectedModel}
            onChange={(e) => setSelectedModel(e.target.value)}
            disabled={loadingModels}
          >
            <option value="">Any Model</option>
            {models.map((model) => (
              <option key={model.modelid} value={model.modelid}>
                {model.modelname}
              </option>
            ))}
          </select>
        </div>

        <div className="border-l border-gray-300 h-8"></div>

        <button
          onClick={handleSubmit}
          disabled={loadingModels || loadingCars}
          className="px-4 py-2 bg-white rounded-full font-light focus:font-semibold"
        >
          {loadingModels || loadingCars ? "  .   .   .  " : "Search"}
        </button>
      </div>

      <div className="flex justify-between bg-white rounded-lg">
        <RangeDropdown
          className="item-left"
          value={cylinderFilter}
          onChange={setCylinderFilter}
          label="Cylinders"
          scale="cylinders"
        />
        <RangeDropdown
          className="item-center"
          value={displacementFilter}
          onChange={setDisplacementFilter}
          label="Displacement"
          scale="displacement"
        />
        <RangeDropdown
          className="item-center"
          value={mileageRange}
          onChange={setMileageRange}
          label="Mileage"
          scale="mileage"
        />
        <RangeDropdown
          className="item-right"
          value={ownersRange}
          onChange={setOwnersRange}
          label="Owners"
          scale="owners"
        />
      </div>
         
      <div className="flex justify-between bg-white rounded-lg">
        <RangeDropdown
          className="item-left"
          value={yearRange}
          onChange={setYearRange}
          label="Year"
          scale="year"
        />
        <DateTimeInput
          className="item-center"
          value={timeRange}
          onChange={setTimeRange}
        />
        <RangeDropdown
          className="item-right"
          value={reserveRange}
          onChange={setReserveRange}
          label="Reserve"
          scale="reserve"
        />
      </div>
    </div>
  );
};

export default ListingFilter;
