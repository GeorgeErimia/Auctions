import React, { useEffect, useState } from "react";
import { getLoggedInUser, logout } from "../services/AuthService";
import { useNavigate } from "react-router-dom";

const HeaderComponent = () => {
  const [currentUser, setCurrentUser] = useState("");

  useEffect(() => {
    setCurrentUser(getLoggedInUser());
  });

  const navigator = useNavigate();

  function handleLogout() {
    logout();
    navigator("/");
  }

  return (
    <header className="fixed-header">
      <nav className="navbar">
        <div>
          <a
            href={`${currentUser != null ? "/auctions" : "/"}`}
            className="navbar-logo"
          >
            Auctions App
          </a>
        </div>
        <div>
          {/* Display the current user */}
          {currentUser != null && (
            <span className="text-light">
              <a href={`/user/${currentUser}`}>Logged in as: {currentUser}</a>
            </span>
          )}

          {/* Display the Logout Button */}
          {currentUser != null && (
            <button className="btn btn-danger" onClick={() => handleLogout()}>
              Logout
            </button>
          )}
        </div>
      </nav>
    </header>
  );
};

export default HeaderComponent;
