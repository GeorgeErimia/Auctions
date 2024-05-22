import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  loginApiCall,
  saveLoggedInUser,
  storeToken,
} from "../services/AuthService";

const LoginComponentV2 = () => {
  const [usernameOrEmail, setUsernameOrEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigator = useNavigate();

  async function handleLoginForm(e) {
    e.preventDefault();

    // You have to use this object to login with because otherwise
    // If you use the plain variables (username, password) as parameters
    // You will get HttpMediaTypeNotSupportedException in Spring Boot App
    const loginObj = { usernameOrEmail, password };

    await loginApiCall(loginObj)
      .then((response) => {
        console.log(response.data);

        // Generate an authentication token and store it in the browser's local storage
        // window.btoa() - converts a string to base64 text

        // const token = "Basic " + window.btoa(usernameOrEmail + ":" + password);
        const token = "Bearer " + response.data.accessToken;
        storeToken(token);

        const role = response.data.role;

        // Save the logged in user in the session storage
        saveLoggedInUser(usernameOrEmail, role);

        navigator("/auctions");

        // The page has to be refreshed to show the changes in the component
        window.location.reload(false);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  return (
    <>
      <div className="container-login">
        <h1 className="logo">Auction Haven</h1>
        <div className="form-auth">
          <div className="form-header">
            <h1>Welcome!</h1>
          </div>
          <div className="form-body">
            <div className="form-row">
              <div className="form-row-right">
                <input
                  type="text"
                  name="username"
                  className="form-input-box"
                  placeholder="Username or Email"
                  value={usernameOrEmail}
                  onChange={(e) => setUsernameOrEmail(e.target.value)}
                />
              </div>
            </div>
            <div className="form-row">
              <div className="form-row-right">
                <input
                  type="password"
                  name="password"
                  className="form-input-box"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
            </div>
            <div className="form-buttons">
              <button
                className="button-submit"
                onClick={(e) => handleLoginForm(e)}
              >
                Log In
              </button>
            </div>
            <div className="form-row">
              {" "}
              <span>
                Don't have an account? <a href="/register">Register here!</a>
              </span>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default LoginComponentV2;
