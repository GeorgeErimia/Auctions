import React, { useEffect, useState } from "react";
import HeaderComponent from "./HeaderComponent";
import FooterComponent from "./FooterComponent";
import { useNavigate } from "react-router-dom";
import { getLoggedInUser, isUserLoggedIn } from "../services/AuthService";

const HomeComponent = () => {
  const [loggedInUser, setLoggedInUser] = useState("");

  const navigator = useNavigate();

  useEffect(() => {
    if (isUserLoggedIn()) {
      navigator("/auctions");
    } else {
      navigator("/login");
    }
    // setLoggedInUser(getLoggedInUser());
    // if (loggedInUser != null) {
    //   navigator("/login");
    // }
  });

  return <div>Home Component</div>;
};

export default HomeComponent;
