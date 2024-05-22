import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getUserByUsername } from "../services/UserService";
import ListAuctionsComponent from "./ListAuctionsComponent";
import { getLoggedInUser } from "../services/AuthService";

const UserComponent = () => {
  const { username } = useParams();
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  //   Set the user's name from the database
  useEffect(() => {
    getUserByUsername(username)
      .then((response) => {
        setName(response.data.name);
        setEmail(response.data.email);
      })
      .catch((error) => {
        console.error(error);
      });
  });

  return (
    <>
      <div className="container small">
        <div className="row">
          <div className="col">
            <img
              src=""
              alt=""
              width="500px"
              height="500px"
              className="profile-picture"
            />
          </div>
          <div className="col">
            {" "}
            <div className="profile">
              <h1 className="profile-name">User: {name}</h1>
              <h5 className="profile-data">Email: {email}</h5>
              <h5 className="profile-data">Username: {username}</h5>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col auctions-list">
            <span>Auctions List</span>
            <ListAuctionsComponent user={username} />
          </div>
        </div>
      </div>
    </>
  );
};

export default UserComponent;
