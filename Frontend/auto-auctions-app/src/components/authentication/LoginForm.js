import React, { useState } from "react";
import axios from "axios";
import Cookies from 'js-cookie';

const LoginForm = ({ onSwitch }) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const [errors, setErrors] = useState({
    username: "",
    password: "",
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
    };

    setErrors(newErrors);

    if (newErrors.username || newErrors.password) {
      return;
    }

    console.log(formData);
    try {
      const response = await axios.post(
        "http://localhost:8080/api/user/login",
        formData
      );
      if(response.status === 200) {
        const jwt = response.data;
        Cookies.set('jwt', jwt, {expires:28, path:'/'});
        setSuccessMessage("Sign in successful! Redirecting...");
        setTimeout(() => {
          window.location.href = "/";
        }, 1000);

      }
    } catch (error) {
      console.error("Error during login:", error.response);
    }
  };

  return (
    <div className="w-full text-gray-700">
      <h2 className="text-3xl font-light text-center mb-6">Sign In</h2>
      <form onSubmit={handleSubmit} noValidate>
        <div className="mb-4">
          <label className="block text-sm font-normal text-gray-700">
            Username
          </label>
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleInputChange}
            className={`mt-1 w-full p-2 border rounded-md transition-all 
            duration-300 ease-in-out transform focus:border-black focus:scale-105 focus:shadow-lg ${
              errors.username ? "border-red-500" : "border-black"
            }`}
          />
          {errors.username && (
            <p className="text-red-500 text-xs mt-1">{errors.username}</p>
          )}
        </div>

        <div className="mb-4">
          <label className="block text-sm font-normal text-gray-700">
            Password
          </label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            className={`mt-1 w-full p-2 border rounded-md transition-all 
            duration-300 ease-in-out transform focus:border-black focus:scale-105 focus:shadow-lg ${
              errors.password ? "border-red-500" : "border-black"
            }`}
          />
          {errors.password && (
            <p className="text-red-500 text-xs mt-1">{errors.password}</p>
          )}
        </div>

        <button
          type="submit"
          className="w-full bg-gradient-to-r from-cyan-600 to-blue-900 text-white font-light font-sans py-2 px-4 rounded-md 
          hover:drop-shadow transition duration-50 focus:to-sky-500"
        >
          Sign In
        </button>

        {successMessage && (
          <p className="text-blue-700 text-xs mt-2 text-center">{successMessage}</p>
        )}
      </form>

      <p className="mt-4 text-center">
        Don't have an account?{" "}
        <button
          type="button"
          className="text-zinc-700 hover:shadow-2xl font-medium"
          onClick={onSwitch}
        >
          Register
        </button>
      </p>
    </div>
  );
};

export default LoginForm;
