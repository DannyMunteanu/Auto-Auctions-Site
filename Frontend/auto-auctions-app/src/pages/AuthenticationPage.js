import React, { useState } from "react";
import LoginForm from "../components/LoginForm";
import RegisterForm from "../components/RegisterForm";

const AuthenticationPage = () => {
  const [isSignIn, setIsSignIn] = useState(true);

  const toggleForm = () => {
    setIsSignIn(!isSignIn);
  };

  return (
    <div
      className="flex items-center justify-center h-screen w-screen bg-cover bg-center"
      style={{ backgroundImage: 
        "url('https://images.pexels.com/photos/17377922/pexels-photo-17377922/free-photo-of-super-car-wallpaper.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2')" }}
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
