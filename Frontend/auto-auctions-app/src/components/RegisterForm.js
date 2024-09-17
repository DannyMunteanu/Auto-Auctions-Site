import React, { useState } from "react";
import axios from "axios";

const RegisterForm = ({ onSwitch }) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
    telephone: "",
    postalCode: "",
    country: "",
  });

  const [errors, setErrors] = useState({
    username: "",
    password: "",
    email: "",
    telephone: "",
    postalCode: "",
    country: "",
  });

  const [successMessage, setSuccessMessage] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
    setErrors({
      ...errors,
      [name]: "",
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newErrors = {
      username: formData.username.trim() === "" ? "Username is required" : "",
      password: formData.password.trim() === "" ? "Password is required" : "",
      email: formData.email.trim() === "" ? "Email is required" : "",
      telephone:
        formData.telephone.trim() === "" ? "Telephone is required" : "",
      postalCode:
        formData.postalCode.trim() === "" ? "Postal Code is required" : "",
      country: formData.country.trim() === "" ? "Country is required" : "",
    };

    setErrors(newErrors);

    if (Object.values(newErrors).some((error) => error)) {
      return;
    }

    try {
      console.log(formData);
      const response = await axios.post(
        "http://localhost:8080/api/user/register",
        formData
      );
      if (response.status === 200) {
        setSuccessMessage("Account created successfully! Redirecting...");
        setTimeout(() => {
          onSwitch();
        }, 1000);
      } else {
        console.log(response);
      }
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div className="w-full text-gray-700">
      <h2 className="text-3xl font-light text-center mb-6">Create Account</h2>
      <form onSubmit={handleSubmit} noValidate>
        {["username", "password", "email", "telephone"].map((field) => (
          <div className="mb-4" key={field}>
            <label className="block text-sm font-normal text-gray-700 capitalize">
              {field
                .replace(/([A-Z])/g, " $1")
                .replace(/^./, (str) => str.toUpperCase())}
            </label>
            <input
              type={
                field === "email"
                  ? "email"
                  : field === "telephone"
                  ? "tel"
                  : "text"
              }
              name={field}
              value={formData[field]}
              onChange={handleInputChange}
              className={`mt-1 w-full p-2 border rounded-md transition-all 
              duration-300 ease-in-out transform focus:border-black focus:scale-105 focus:shadow-lg ${
                errors[field] ? "border-red-500" : "border-black"
              }`}
            />
            {errors[field] && (
              <p className="text-red-500 text-xs mt-1">{errors[field]}</p>
            )}
          </div>
        ))}

        <div className="flex gap-4 mb-4">
          <div className="flex-1">
            <label className="block text-sm font-normal text-gray-700">
              Postal Code
            </label>
            <input
              type="text"
              name="postalCode"
              value={formData.postalCode}
              onChange={handleInputChange}
              className={`mt-1 w-full p-2 border rounded-md transition-all 
              duration-300 ease-in-out transform focus:border-black focus:scale-105 focus:shadow-lg ${
                errors.postalCode ? "border-red-500" : "border-black"
              }`}
            />
            {errors.postalCode && (
              <p className="text-red-500 text-xs mt-1">{errors.postalCode}</p>
            )}
          </div>

          <div className="flex-1">
            <label className="block text-sm font-normal text-gray-700">
              Country
            </label>
            <input
              type="text"
              name="country"
              value={formData.country}
              onChange={handleInputChange}
              className={`mt-1 w-full p-2 border rounded-md transition-all 
              duration-300 ease-in-out transform focus:border-black focus:scale-105 focus:shadow-lg ${
                errors.country ? "border-red-500" : "border-black"
              }`}
            />
            {errors.country && (
              <p className="text-red-500 text-xs mt-1">{errors.country}</p>
            )}
          </div>
        </div>

        <button
          type="submit"
          className="w-full bg-gradient-to-r from-cyan-600 to-blue-900 text-white font-light font-sans py-2 px-4 rounded-md 
          hover:drop-shadow transition duration-50 focus:to-sky-500"
        >
          Create Account
        </button>
        
        {successMessage && (
          <p className="text-blue-700 text-xs mt-2 text-center">{successMessage}</p>
        )}
      </form>

      <p className="mt-4 text-center">
        Already have an account?{" "}
        <button
          type="button"
          className="text-zinc-700 hover:shadow-2xl font-medium"
          onClick={onSwitch}
        >
          Sign In
        </button>
      </p>
    </div>
  );
};

export default RegisterForm;
