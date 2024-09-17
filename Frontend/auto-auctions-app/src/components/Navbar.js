import React, { useState, useEffect } from "react";
import Cookies from "js-cookie";
import axios from "axios";

const Navbar = () => {
  const [user, setUser] = useState(null);
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const token = Cookies.get("jwt");

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const handleLogout = () => {
    axios
      .post("http://localhost:8080/api/user/logout", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .catch((error) => {
        console.error("Error validating token:", error);
      });
    Cookies.remove("jwt");
    setUser(null);
    setDropdownOpen(false);
  };

  const handleSignIn = () => {
    window.location.href = "/auth?form=signin";
  };

  const handleCreateAccount = () => {
    window.location.href = "/auth?form=register";
  };

  useEffect(() => {
    if (token) {
      axios
        .get("http://localhost:8080/api/user/retrieve", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          setUser(response.data.username);
        })
        .catch((error) => {
          Cookies.remove("jwt");
        });
    }
  }, []);

  return (
    <nav className="bg-white shadow-lg border-b-2 border-blue-600">
      <div className="container mx-auto px-4 py-3 flex justify-between items-center">
        <div className="flex items-center space-x-8">
          <div className="text-xl font-bold text-blue-600">
            Auto<span className="text-gray-700">Auctions</span>
          </div>
          <div className="hidden md:flex space-x-4">
            <a href="/" className="text-gray-600 hover:text-blue-600">
              View Listings
            </a>
            <a
              href="/create-listing"
              className="text-gray-600 hover:text-blue-600"
            >
              Create Listing
            </a>
          </div>
        </div>
        <div className="relative">
          <button
            onClick={toggleDropdown}
            className="flex items-center space-x-2 text-gray-600 hover:text-blue-600 focus:outline-none"
          >
            <span>{user ? `${user}` : "Get Started"}</span>
            <svg
              className="w-5 h-5"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M19 9l-7 7-7-7"
              />
            </svg>
          </button>
          {dropdownOpen && (
            <div className="absolute right-0 mt-2 w-48 bg-white border rounded-lg shadow-lg">
              <ul className="py-2">
                {user ? (
                  <>
                    <li className="px-4 py-2 hover:bg-gray-100 cursor-pointer">
                      <a href="/view-account">View Account</a>
                    </li>
                    <li
                      onClick={handleLogout}
                      className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                    >
                      Logout
                    </li>
                  </>
                ) : (
                  <>
                    <li
                      onClick={handleSignIn}
                      className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                    >
                      Sign In
                    </li>
                    <li
                      onClick={handleCreateAccount}
                      className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                    >
                      Create Account
                    </li>
                  </>
                )}
              </ul>
            </div>
          )}
        </div>
      </div>
      <div className="md:hidden bg-gray-100">
        <div className="flex justify-center space-x-4 p-3">
          <a href="/" className="text-gray-600 hover:text-blue-600">
            View Listings
          </a>
          <a
            href="/create-listing"
            className="text-gray-600 hover:text-blue-600"
          >
            Create Listing
          </a>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
