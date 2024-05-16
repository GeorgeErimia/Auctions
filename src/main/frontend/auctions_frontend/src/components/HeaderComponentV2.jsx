import React, { useEffect, useState } from "react";
import { getLoggedInUser, logout } from "../services/AuthService";
import { useNavigate } from "react-router-dom";

const HeaderComponentV2 = () => {
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
    <div className="header">
      <div className="header-logo">
        <a
          href={`${currentUser != null ? "/auctions" : "/"}`}
          className="header-logo-anchor"
        >
          Auction Haven
        </a>
      </div>
      {/* Here you need to implement a search bar */}
      {/* <div className="header-search-bar"></div> */}
      <div className="header-user-info">
        {currentUser != null && (
          <span className="header-user-span">Logged in as {currentUser}</span>
        )}
        <div className="header-user-dropdown">
          {currentUser != null && (
            // <button className="btn btn-danger" >
            //   Logout
            // </button>

            <a href={`/login`} onClick={() => handleLogout()}>
              Log out
            </a>
          )}
          {currentUser != null && (
            <a href={`/user/${currentUser}`}>View your Profile</a>
          )}
        </div>
      </div>
    </div>
  );
};

export default HeaderComponentV2;
