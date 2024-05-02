import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getUserByUsername } from "../services/UserService";

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
    <div>
      <div className="profile">
        <h1 className="profile-name">User: {name}</h1>
        <h5 className="profile-data">Email: {email}</h5>
        <h5 className="profile-data">Username: {username}</h5>
      </div>
    </div>
  );
};

export default UserComponent;
