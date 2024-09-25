import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import LoginForm from "../components/authentication/LoginForm";
import RegisterForm from "../components/authentication/RegisterForm";

const AuthenticationPage = () => {
  const location = useLocation();
  const [isSignIn, setIsSignIn] = useState(true);

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const form = queryParams.get("form");
    setIsSignIn(form !== "register");
  }, [location.search]);

  const toggleForm = () => {
    setIsSignIn(!isSignIn);
  };

  return (
    <div
      className="flex items-center justify-center h-screen w-screen bg-cover bg-center"
      style={{
        backgroundImage:
          'url("https://images.pexels.com/photos/17377922/pexels-photo-17377922/free-photo-of-super-car-wallpaper.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2")',
        backgroundSize: "cover",
        backgroundPosition: "center",
        minHeight: "100vh",
      }}
    >
      <div className="w-full bg-white max-w-md rounded-md shadow-2xl p-8">
        {isSignIn ? (
          <LoginForm onSwitch={toggleForm} />
        ) : (
          <RegisterForm onSwitch={toggleForm} />
        )}
      </div>
    </div>
  );
};

export default AuthenticationPage;
