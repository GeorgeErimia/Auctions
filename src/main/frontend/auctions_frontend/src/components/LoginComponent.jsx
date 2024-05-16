import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  loginApiCall,
  saveLoggedInUser,
  storeToken,
} from "../services/AuthService";

const LoginComponent = () => {
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
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <div className="card">
            <div className="card-header">
              <h2 className="text-center">Login</h2>
            </div>
            <div className="card-body">
              <form action="">
                <div className="row mb-3">
                  <label className="col-md-3 control-label">
                    Username or Email
                  </label>
                  <div className="col-md-9">
                    <input
                      type="text"
                      name="username"
                      className="form-control"
                      placeholder="Enter Username"
                      value={usernameOrEmail}
                      onChange={(e) => setUsernameOrEmail(e.target.value)}
                    />
                  </div>
                </div>
                <div className="row mb-3">
                  <label className="col-md-3 control-label">Password</label>
                  <div className="col-md-9">
                    <input
                      type="password"
                      name="password"
                      className="form-control"
                      placeholder="Enter Password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                  </div>
                </div>
                <div className="form-group mb-3">
                  <button
                    className="btn btn-primary"
                    onClick={(e) => handleLoginForm(e)}
                  >
                    Submit
                  </button>
                </div>
              </form>
              <span>
                Don't have an account? <a href="/register">Register Here</a>
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginComponent;
