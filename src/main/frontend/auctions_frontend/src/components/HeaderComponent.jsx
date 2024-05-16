import React, { useEffect, useState } from "react";
import { getLoggedInUser } from "../services/AuthService";

const HeaderComponent = () => {
  const [currentUser, setCurrentUser] = useState("");

  useEffect(() => {
    setCurrentUser(getLoggedInUser());
  });

  return (
    <div>
      <header>
        <nav className="navbar navbar-dark bg-dark">
          <div>
            <a href="/" className="navbar-brand">
              Auctions App
            </a>
          </div>
          <div>
            {/* Display the current user */}
            <span className="text-light">
              <a href={`/user/${currentUser}`}>Logged in as: {currentUser}</a>
            </span>
          </div>
        </nav>
      </header>
    </div>
  );
};

export default HeaderComponent;
