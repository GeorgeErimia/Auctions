import axios from "axios";
import { getLoggedInUser, getToken } from "./AuthService";
const USER_REST_API_BASE_URL = "http://localhost:8080/api/v2/users";

// Add a request interceptor
axios.interceptors.request.use(
  function (config) {
    config.headers["Authorization"] = getToken();
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

export const getLoggedInUserObj = () => {
  let username = getLoggedInUser();
  return axios.get(USER_REST_API_BASE_URL + "/username/" + username);
};

export const getUserById = (id) => {
  return axios.get(USER_REST_API_BASE_URL + "/" + id);
};

export const getUserByUsername = (username) => {
  return axios.get(USER_REST_API_BASE_URL + "/username/" + username);
};
