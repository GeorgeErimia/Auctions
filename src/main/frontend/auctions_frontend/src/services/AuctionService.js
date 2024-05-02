import axios from "axios";
import { getToken } from "./AuthService";
import { getUserById } from "./UserService";

const BASE_REST_API_URL = "http://localhost:8080/api/v2/auctions";

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

export const getAllAuctions = () => axios.get(BASE_REST_API_URL);

export const createAuction = (auctionObj) =>
  axios.post(BASE_REST_API_URL, auctionObj);

export const getAuction = (id) => axios.get(BASE_REST_API_URL + "/" + id);

export const updateAuction = (id, auctionObj) =>
  axios.put(BASE_REST_API_URL + "/" + id, auctionObj);

export const removeAuction = (id) => axios.delete(BASE_REST_API_URL + "/" + id);
